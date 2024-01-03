package model;


import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;

@Getter
@Setter
public class KeeperCreator {
    public static long TASK_MAX_ID;
    private Keeper keeper;
    private Dimension dimension = new Dimension(500, 1000);

    public KeeperCreator() {
        this.keeper = new Keeper();
        keeper.setTasks(keeper.getTasks());
        TASK_MAX_ID = keeper.getLastId();
    }

    private void showMainWindow() {
        JFrame jf = new JFrame("Keeper");
        jf.setSize(this.dimension);
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jf.setLocationRelativeTo(null);
        jf.setLayout(new FlowLayout());
        JScrollPane jsp = getJTable();
        jf.add(jsp);
        jf.setVisible(true);

    }

    private JScrollPane getJTable() {
        PersonTableModel ptm = new PersonTableModel(keeper.getTasks());
        JTable jt = new JTable(ptm);
        Renderer r = new Renderer();
        jt.getColumnModel().getColumn(0).setMaxWidth(20);
        jt.getColumnModel().getColumn(0).setCellRenderer(r);
        jt.getColumnModel().getColumn(1).setMaxWidth(300);
        jt.getColumnModel().getColumn(1).setCellRenderer(r);
        jt.getColumnModel().getColumn(2).setMaxWidth(120);
        jt.getColumnModel().getColumn(2).setCellRenderer(r);
        jt.getColumnModel().getColumn(3).setMaxWidth(120);
        jt.getColumnModel().getColumn(3).setCellRenderer(r);
        JScrollPane jsp = new JScrollPane(jt);
        return jsp;
    }

    public static void main(String[] args) {
        KeeperCreator kc = new KeeperCreator();
        kc.showMainWindow();
    }
}
