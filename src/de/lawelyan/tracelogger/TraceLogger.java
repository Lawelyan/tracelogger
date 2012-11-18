/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.lawelyan.tracelogger;

import java.io.File;
import java.net.InetAddress;

/**
 *
 * @author Rainer
 */
public class TraceLogger {

    /**
     * Startet den TraceLogger in einem neuem Thread
     */
    public static void init() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                new TraceLogger();
            }
        }).start();
    }

    private TraceLogger() {
        TraceloggerConfig.init();
        while (true) {
            if (!TraceloggerConfig.isOnline()) {
                break;
            }
            sleep(30);
            if (!TraceloggerConfig.isOnline()) {
                break;
            }
            if (!onRightNet()) {
                de.lawelyan.tracelogger.gui.Tray.imageOffline();

            } else {
                de.lawelyan.tracelogger.gui.Tray.imageOnline();
                for (String target : TraceloggerConfig.getTargets()) {
                    if (target.length() == 0) {
                        continue;
                    }

                    String[] hops = Tracert.getHops(target);
                    if (!TraceloggerConfig.isOnline()) {
                        return;
                    }
                    Csv.write(TraceloggerConfig.getFileByTarget(target), hops);


                }
            }
        }
    }

    private boolean onRightNet() {
        try {
            return InetAddress.getLocalHost().toString().split("/")[1].contains(TraceloggerConfig.getIpFilter());
        } catch (Exception e) {
        }
        return false;
    }

    private void sleep(long seconds) {
        try {
            (Thread.currentThread()).sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
