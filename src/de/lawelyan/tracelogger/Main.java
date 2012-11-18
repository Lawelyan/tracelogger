/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.lawelyan.tracelogger;

import de.lawelyan.tracelogger.gui.MainWindow;
import de.lawelyan.tracelogger.gui.Tray;
import java.io.File;

/**
 *
 * @author Rainer
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        checkParams(args);
        TraceLogger.init();
        try {
            Tray.init();
            Logg.silent(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        MainWindow.init();
    }

    private static void checkParams(String[] args) {
        if (args.length == 0) {
            TraceloggerConfig.showGreetings();
            TraceloggerConfig.showHelpInfo();
        }
        if (args.length >= 1) {
            if (args[0].toLowerCase().equals("silent")) {
                Logg.silent(true);
            }

            if (args[0].equals("/?") || args[0].equals("/help") || args[0].equals("-help") || args[0].equals("-?")) {
                TraceloggerConfig.showGreetings();
                TraceloggerConfig.showHelp();
                return;
            }
            if (args[0].endsWith(".xml")) {
                TraceloggerConfig.setFile(new File(args[0]));
                if (args.length == 2) {
                    if (args[1].toLowerCase().equals("silent")) {
                        Logg.silent(true);
                    }
                }
            }
        }
    }

    /**
     * Beendet die gesamte Anwendung
     */
    public static void quit() {
        Tray.close();
        TraceloggerConfig.setOnline(false);
        MainWindow.close();
    }
}
