package database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;

public class PingConnectionTask implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(PingConnectionTask.class);

    private final Connection connection;

    PingConnectionTask(Connection connection) {
        this.connection = connection;
    }

    private void closeConnection(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            log.error("Couldn't close connection", e);
        }
    }

    private boolean isConnectionValid() {
        try {
            if (!connection.isValid(1000)) {
                closeConnection(connection);
            } else {
                log.info("Connection is valid");
                return true;
            }
        } catch (SQLException e) {
            log.error("Connection is not valid", e);
        }
        return false;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                closeConnection(connection);
                log.info("Closing connection");
            }

            if (!isConnectionValid()) {
                return;
            }
        }
    }
}
