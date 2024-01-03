package model;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.Date;

public class Renderer extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if (column == 3) { // todo: неправильная закраска
            String[] values = ((String)value).split("[\\-, :]+");
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime current = LocalDateTime.of(Integer.parseInt(values[0]), Integer.parseInt(values[1]),
                    Integer.parseInt(values[2]), Integer.parseInt(values[3]), Integer.parseInt(values[4]));
            if (now.plusDays(1).isAfter(current)) {
                cell.setBackground(Color.RED);
            } else if (now.plusDays(3).isAfter(current)) {
                cell.setBackground(Color.YELLOW);
            } else if (now.plusDays(7).isAfter(current)) {
                cell.setBackground(Color.GREEN);
            } else {
                cell.setBackground(Color.CYAN);
            }
        }
        return cell;
    }
}
