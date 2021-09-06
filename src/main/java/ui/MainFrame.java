package ui;

import context.ApplicationContext;
import model.DriverResultTableModel;
import ui.dialogs.CreateCarDialog;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.JToolBar;
import java.awt.BorderLayout;

public class MainFrame extends JFrame {

    public MainFrame() {
        super("TransactionsDemo");
        setSize(600, 400);
        setLocation(100, 100);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JToolBar toolBar = new JToolBar();
        JButton actionButton = new JButton("Create row");
        actionButton.addActionListener(e -> new CreateCarDialog());

        toolBar.add(actionButton);
        add(toolBar, BorderLayout.NORTH);

        JTable resultTable = new JTable();
        add(resultTable, BorderLayout.CENTER);

        JButton showResults = new JButton("Show results");
        showResults.addActionListener(e -> resultTable.setModel(
                new DriverResultTableModel(ApplicationContext.userRepository.getResults())));
        toolBar.add(showResults);
    }

    public static void main(String[] args) {
        new MainFrame().setVisible(true);
    }
}
