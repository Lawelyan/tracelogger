/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.lawelyan.tracelogger;

import de.lawelyan.tracelogger.gui.MainWindow;
import java.awt.Desktop;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import org.omg.CORBA.Environment;

/**
 *
 * @author Rainer
 */
public class Tracert {

    private static String trace;

    protected static String[] getHops(String target) {
        String[] ret = new String[0];
        String result = "";
        Process p=new Process() {

            @Override
            public OutputStream getOutputStream() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public InputStream getInputStream() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public InputStream getErrorStream() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public int waitFor() throws InterruptedException {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public int exitValue() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void destroy() {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        };
        ArrayList<String> hops = new ArrayList<String>();
        trace = "";
        try {

            if (System.getProperty("os.name").toLowerCase().contains("nux")) {
                p = Runtime.getRuntime().exec("traceroute " + target);
            } else if (System.getProperty("os.name").toLowerCase().contains("win")) {
                p = Runtime.getRuntime().exec("cmd /c tracert.exe " + target);
            } else {
                return ret;               
            }

            int read = p.getInputStream().read();
            while (read != -1) {
                if (!TraceloggerConfig.isOnline()) {
                    p.destroy();
                    break;
                }
                char c = (char) read;
                read = p.getInputStream().read();
                result += c;
                if (c == '\n') {
                    trace += result;
                    result = parseLine(result);
                    if (result.length() > 0) {
                        hops.add(result);
                    }

                    result = "";
                    MainWindow.refresh();
                }
            }
        } catch (Exception e) {
        }
        ret = new String[hops.size()];
        ret = hops.toArray(ret);
        return ret;
    }

    /**
     * Liefert das Tracert Ergebniss vom System
     *
     * @return
     */
    public static String getTrace() {
        return trace;
    }

    private static String parseLine(String line) {

        for (String elem : line.split(" ")) {
            if (elem.equals("Routenverfolgung")) {
                return "";
            }
            elem = elem.replace('[', ' ').trim();
            elem = elem.replace(']', ' ').trim();
            elem = elem.replace('(', ' ').trim();
            elem = elem.replace(')', ' ').trim();

            if (isIpAdress(elem)) {
                return elem;
            }
        }
        return "";
    }

    private static boolean isIpAdress(String elem) {
        ArrayList<Integer> ip = new ArrayList<Integer>();
        try {
            for (String e : elem.split("\\.")) {
                ip.add(new Integer(e));
            }
            if (ip.size() != 4) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
