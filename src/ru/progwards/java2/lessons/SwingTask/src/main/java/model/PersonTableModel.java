package model;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class PersonTableModel extends AbstractTableModel {
    ArrayList<Task> tasks;

    public PersonTableModel(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public int getRowCount() {
        return this.tasks.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object result = null;
        Task temp = tasks.get(rowIndex);
        if (columnIndex == 0) {
            result = temp.getId();
        }
        if (columnIndex == 1) {
            result = temp.getInfo();
        }
        if (columnIndex == 2) {
            result = this.format(temp.getStartDate());
        }
        if (columnIndex == 3) {
            result = this.format(temp.getFinishDate());
        }
        return result;
    }

    private String format(String date) {
        StringBuilder sb = new StringBuilder();
        sb.append(date, 0, 10).append(", ").append("");
        sb.append(date, 11, 16);
        return sb.toString();
    }

    @Override
    public String getColumnName(int column) {
        String result;
        switch (column) {
            case 0:
                result = "ID";
                break;
            case 1:
                result = "Task information";
                break;
            case 2:
                result = "Start date";
                break;
            case 3:
                result = "Finish date";
                break;
            default:
                result = "";
        }
        return result;
    }


}
