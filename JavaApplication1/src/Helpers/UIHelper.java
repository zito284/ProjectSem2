/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Helpers;

import form.main.Main;
import java.awt.Component;
import java.awt.Container;
import java.lang.reflect.Field;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JToggleButton;
import javax.swing.text.JTextComponent;

/**
 *
 * @author Zito
 */
public class UIHelper {

    public static void resetForm(Container cont) {
        for (Component c : cont.getComponents()) {
            if (c instanceof JTextComponent) {
                ((JTextComponent) c).setText("");
            } else if (c instanceof JToggleButton) {
                ((JToggleButton) c).setSelected(false);
            } else if (c instanceof Container) {
                resetForm((Container) c);
            }
        }
    }

    public static void bindDataFromObj(Class obj, Container cont) {
        Field f;
        for (Component c : cont.getComponents()) {
            if (c instanceof JTextComponent) {
                try {
                    f = obj.getDeclaredField(c.getName());
                    if (f != null) {
                    }
                } catch (Exception e) {
                }

            } else if (c instanceof JToggleButton) {
                ((JToggleButton) c).setSelected(false);
            } else if (c instanceof Container) {
                resetForm((Container) c);
            }
        }
    }

    public static void bindBackground(JPanel pnl) {
        JLabel label_5 = new JLabel("");
        label_5.setIcon(new ImageIcon(Main.class
                .getResource("/image/wall3.jpg")));
        label_5.setBounds(0, 0, 2000, 1000);
        pnl.add(label_5);
    }

    public static void bindBackground(JPanel pnl, String pathImg) {
        JLabel label_5 = new JLabel("");
        label_5.setIcon(new ImageIcon(Main.class
                .getResource(pathImg)));
        label_5.setBounds(0, 0, 2000, 1000);
        pnl.add(label_5);
    }

    public static void hideColumnOfTable(JTable table, int column) {
        table.getColumnModel().getColumn(column).setMinWidth(0);
        table.getColumnModel().getColumn(column).setMaxWidth(0);
        table.getColumnModel().getColumn(column).setWidth(0);
    }

    public static void unhideColumnOfTable(JTable table, int column, int width) {
        table.getColumnModel().getColumn(column).setMinWidth(width);
        table.getColumnModel().getColumn(column).setMaxWidth(width);
        table.getColumnModel().getColumn(column).setWidth(width);
    }

    public static void setColumnWidth(JTable table, int[] colWidth) {
        int cols = table.getColumnCount();
        if (cols == colWidth.length) {
            for (int col = 0; col < cols; col++) {
                table.getColumnModel().getColumn(col).setMinWidth(colWidth[col]);
                table.getColumnModel().getColumn(col).setMaxWidth(colWidth[col]);
                table.getColumnModel().getColumn(col).setWidth(colWidth[col]);
            }
        }
    }
}
