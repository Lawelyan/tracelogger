/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.lawelyan.tracelogger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Properties;

/**
 *
 * @author Rainer
 */
public class TraceloggerConfig {

    private static File CONFIG_FILE = new File("config.xml");
    private static String OWNIP = "172.16";
    private static String TRACELOG = "/Logs";
    private static boolean ONLINE = true;
    private static ArrayList<String> TARGETS; // = ["172.16.1.1", "172.16.1.2"];
    private static final String CONFIG_OWNIP = "Eigene IP Maske";
    private static final String CONFIG_TRACELOG = "Trace Log Pfad";
    private static final String CONFIG_TARGET_PREFIX = "Target";
    private static final String CONFIG_ONLINE = "Online";

    protected static void showGreetings() {
        Logg.out("Trace Logger ist ein Tool um Traces in ein Logfile zu schreiben");
        Logg.out("Es wird kontinuerlich ausgefuehrt.");
        Logg.out("Zum Beenden bitte den Online Status in Config.xml auf false setzen");
        Logg.out("");

    }

    protected static void showHelpInfo() {
        Logg.out("Hilfe mit -?");
        Logg.out("");
    }

    protected static void showHelp() {
        Logg.out("Hilfe:");
        Logg.out("Die Konfiguration wird im Arbeitespfad mit dem namen config.xml gesucht");
        Logg.out("Man kann den Pfad zur Konfiguration auch als Parameter übergeben, muss dann auf \".xml\" enden ");
        Logg.out("Wenn keine config.xml gefunden werden kann, wird eine neue mit Standardeintraegen erstellt");
        Logg.out("");
    }

    /**
     * Setzt den Online FLag, = false bewirkt ein abbruch des Traceloggers nach
     * abschluss des aktuellen Tracert
     *
     * @param flag
     */
    public static void setOnline(boolean flag) {
        ONLINE = flag;
    }

    /**
     * Gibt den Onlinestatus zurück
     *
     * @return
     */
    public static boolean isOnline() {
        return ONLINE;
    }

    /**
     * lädt die Config neu ein
     */
    public static void reload() {
        init();
    }

    /**
     * Gibt das Config File zurück
     *
     * @return
     */
    public static File getFile() {
        return CONFIG_FILE;
    }

    /**
     * Ersetzt das zu lesende Config File
     *
     * @param file
     */
    public static void setFile(File file) {
        CONFIG_FILE = file;
    }

    /**
     * liefert die Liste mit Trace Targets
     *
     * @return
     */
    public static ArrayList<String> getTargets() {
        return TARGETS;
    }

    /**
     * Liefert den Pfad zu den LogFiles (CSV)
     *
     * @return
     */
    public static String getLogFilesPath() {
        return new File(TRACELOG).getAbsolutePath();
    }

    /**
     * Liefert das File Object
     *
     * @param target
     * @return
     */
    public static File getFileByTarget(String target) {
        return new File(TraceloggerConfig.getLogFilesPath() + "\\" + target + ".csv");
    }

    /**
     * gibt den IP Filter
     *
     * @return
     */
    public static String getIpFilter() {
        return OWNIP;
    }

    protected static void init() {
        Properties p = new Properties();
        Logg.out("Lade die Konfiguration: " + CONFIG_FILE.getName());

        try {
            p.loadFromXML(new FileInputStream(CONFIG_FILE));
        } catch (Exception e) {
            Logg.out("Keine Config.xml gefunden, erstelle ein Template");

            p.setProperty(CONFIG_OWNIP, OWNIP);
            p.setProperty(CONFIG_TRACELOG, new File(TRACELOG).getAbsolutePath());
            p.setProperty(CONFIG_TARGET_PREFIX + "1", "www.google.de");
            p.setProperty(CONFIG_ONLINE, (ONLINE) ? "true" : "false");
            try {
                new File(CONFIG_FILE.getAbsolutePath()).getParentFile().mkdirs();
                p.storeToXML(new FileOutputStream(CONFIG_FILE), "ownIP prüft nur auf die ersten zahlen \nlogPath gibt den Speicherort an\ntarget1...targetn Ziel IP-Adressen zum Tracen");
            } catch (Exception e2) {
                Logg.err("Speichern der " + CONFIG_FILE.getName() + " nicht möglich, Pfad: " + CONFIG_FILE.getAbsolutePath());
            }

        }
        ArrayList<String> a = new ArrayList<String>();
        Enumeration<?> enu = p.propertyNames();
        String key;
        while (enu.hasMoreElements()) {
            key = (String) enu.nextElement();
            if (key.toLowerCase().equals(CONFIG_OWNIP.toLowerCase())) {
                OWNIP = p.getProperty(CONFIG_OWNIP);
                continue;
            }
            if (key.toLowerCase().equals(CONFIG_TRACELOG.toLowerCase())) {
                TRACELOG = p.getProperty(CONFIG_TRACELOG);
                new File(TRACELOG).mkdirs();
                continue;
            }
            if (key.toLowerCase().equals(CONFIG_ONLINE.toLowerCase())) {
                ONLINE = (p.getProperty(CONFIG_ONLINE).equalsIgnoreCase("true")) ? true : false;
                continue;
            }
            if (!key.toLowerCase().startsWith(CONFIG_TARGET_PREFIX.toLowerCase())) {
                continue;
            }
            a.add(p.getProperty(key));
        }
        TARGETS = a;
    }
}
