package model;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.util.List;

public class DriverResultTableModel implements TableModel {

    private final List<Driver> driverList;

    public DriverResultTableModel(List<Driver> driverList) {
        this.driverList = driverList;
    }

    @Override
    public int getRowCount() {
        return driverList.size();
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return "id";
            case 1:
                return "name";
            case 2:
                return "Car name";
            default:
                return "";
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return String.class;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
       Driver driver = driverList.get(rowIndex);
       switch (columnIndex) {
           case 0:
               return driver.getId();
           case 1:
               return driver.getName();
           case 2:
               return driver.getModel();
           default:
               return "";
       }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        // не нужно реализовывать
    }

    @Override
    public void addTableModelListener(TableModelListener l) {
        // не нужно реализовывать
    }

    @Override
    public void removeTableModelListener(TableModelListener l) {
        // не нужно реализовывать
    }
}
