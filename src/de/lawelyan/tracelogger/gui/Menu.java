/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.lawelyan.tracelogger.gui;

import java.awt.Desktop;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import de.lawelyan.tracelogger.Main;
import de.lawelyan.tracelogger.TraceloggerConfig;

/**
 *
 * @author Rainer
 */
public class Menu {

    private static MenuItem i;
    private static String hilfsvar;

    protected static void init() {

        i = new MenuItem("Haupfenster zeigen");
        i.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainWindow.showMainWindow();
            }
        });
        Tray.addMenuItem(i);

        i = new MenuItem("Konfiguration neu Laden");
        i.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TraceloggerConfig.reload();
            }
        });
        Tray.addMenuItem(i);

        i = new MenuItem("Öffne Konfiguration");
        i.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Desktop.getDesktop().edit(de.lawelyan.tracelogger.TraceloggerConfig.getFile());
                } catch (Exception ex) {
                }
            }
        });
        Tray.addMenuItem(i);

        i = new MenuItem("Beenden");
        i.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.quit();

            }
        });
        Tray.addMenuItem(i);

    }
}
