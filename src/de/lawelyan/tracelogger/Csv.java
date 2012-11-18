/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.lawelyan.tracelogger;

import java.io.File;
import java.io.FileOutputStream;

/**
 *
 * @author Rainer
 */
public class Csv {

    protected static void write(File file, String[] elem) {
        try {
            try (FileOutputStream f = new FileOutputStream(file, true)) {
                for (String c : elem) {
                    f.write((c + ";").getBytes());
                }
                f.write("\r\n".getBytes());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
