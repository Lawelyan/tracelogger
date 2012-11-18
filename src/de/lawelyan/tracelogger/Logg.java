/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.lawelyan.tracelogger;

import de.lawelyan.tracelogger.gui.MainWindow;

/**
 * Sämtliche Ausgaben sollen über Logg gleitet werden, keine nutzung von
 * System.out bitte Bei eingeschaltetem Webservermode werden alle Ausgaben
 * unterdrückt. Es wird nur der Pfad zur HTML Seite ausgegeben und das öffnen
 * der Seite im Browser unterdrückt
 *
 * @author Corwin
 */
public class Logg {

    private static boolean silent = false;
    private static String logOnSilentMode = "";

    /**
     * Silent Mode ein/ausschalten
     *
     * @param sw
     */
    public static void silent(boolean sw) {
        silent = sw;
    }

    /**
     * Ist der Silent Mode eingeschaltet ?
     *
     * @return bool
     */
    public static boolean isSilent() {
        return silent;
    }

    /**
     * Logeintrag ausgeben
     *
     * @param o
     */
    public static void out(String o) {
        if (silent) {
            logOnSilentMode += o + "\r\n";
            MainWindow.refresh();
            return;
        }
        System.out.println(o);
    }

    /**
     * Gibt das komplette Log aus, wird nur im Silent Mode geschrieben
     *
     * @return
     */
    public static String getSilentLog() {
        return logOnSilentMode;
    }

    /**
     * Logeintrag in den Errorstream ausgeben
     *
     * @param o
     */
    public static void err(String o) {
        if (silent) {
            return;
        }
        System.err.println(o);
    }
}
