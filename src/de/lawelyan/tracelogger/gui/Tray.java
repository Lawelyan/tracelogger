/*
 * To change this template, choose Tools | Templates
 * and init the template in the editor.
 */
package de.lawelyan.tracelogger.gui;

import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionListener;

/**
 *
 * @author Rainer
 */
public class Tray {

    private static Image imageOffline;
    private static Image imageOnline;
    private static PopupMenu popup;
    private static TrayIcon trayIcon;
    private static SystemTray tray;

    /**
     * Initialisiert das Tray
     *
     * @throws Exception
     */
    public static void init() throws Exception {
        if (SystemTray.isSupported() == false) {
            throw new Exception("SystemTray not supported!");
        }


        tray = SystemTray.getSystemTray();
        imageOnline = Toolkit.getDefaultToolkit().getImage(GuiConfig.ICON_ONLINE);
        imageOffline = Toolkit.getDefaultToolkit().getImage(GuiConfig.ICON_OFFLINE);
        popup = new PopupMenu();
        trayIcon = new TrayIcon(imageOffline, GuiConfig.NAME, popup);

        trayIcon.setImage(imageOffline);
        tray.add(trayIcon);
        Menu.init();
        IconListener.init();
        trayIcon.setImageAutoSize(true);
    }

    /**
     * Setzt das Trayicon auf Online
     */
    public static void imageOnline() {
        trayIcon.setImage(imageOnline);
    }

    /**
     * Setzt das Trayicon auf Offline
     */
    public static void imageOffline() {
        trayIcon.setImage(imageOffline);
    }

    protected static void addMenuItem(MenuItem item) {
        popup.add(item);
    }

    protected static void addTrayIconActionListener(ActionListener actionListener) {
        trayIcon.addActionListener(actionListener);
    }

    /**
     * Zeigt eine Mitteiliung im Tray Bubble an
     *
     * @param caption
     * @param text
     */
    public static void show(String caption, String text) {
        trayIcon.displayMessage(caption, text, TrayIcon.MessageType.NONE);
    }

    /**
     * Zeigt eine Info im Tray Bubble an
     *
     * @param caption
     * @param text
     */
    public static void showInfo(String caption, String text) {
        trayIcon.displayMessage(caption, text, TrayIcon.MessageType.INFO);
    }

    /**
     * Zeigt eine Warnung im Tray Bubble an
     *
     * @param caption
     * @param text
     */
    public static void showWarning(String caption, String text) {
        trayIcon.displayMessage(caption, text, TrayIcon.MessageType.WARNING);
    }

    /**
     * Zeigt einen Fehler im Tray Bubble an
     *
     * @param caption
     * @param text
     */
    public static void showError(String caption, String text) {
        trayIcon.displayMessage(caption, text, TrayIcon.MessageType.ERROR);
    }

    /**
     * Beendet das Tray
     */
    public static void close() {
        tray.remove(trayIcon);

    }
//    private void lager() {
//        propListener = new PropertyChangeListener() {
//            public void propertyChange(PropertyChangeEvent evt) {
//                TrayIcon oldTray[] = (TrayIcon[]) evt.getOldValue();
//                TrayIcon newTray[] = (TrayIcon[]) evt.getNewValue();
//                System.out.println(oldTray.length + " / " + newTray.length);
//            }
//        };
//        tray.addPropertyChangeListener("trayIcons", propListener);
//
//
//        item = new MenuItem("Hello, World");
//        item2 = new MenuItem("Hello, World2");
//        item3 = new MenuItem("Hello, World3");
//        menuActionListener = new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                trayIcon.displayMessage("Good-bye", "Cruel World", TrayIcon.MessageType.WARNING);
//            }
//        };
//        item.addActionListener(menuActionListener);
//        popup.add(item);
//        ActionListener actionListener = new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                tray.remove(trayIcon);
//            }
//        };
//        trayIcon.addActionListener(actionListener);
//
//    }
}
