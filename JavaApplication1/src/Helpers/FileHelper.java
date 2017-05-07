/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Helpers;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 *
 * @author Zito
 */
public class FileHelper {
    public static String getFileNameWithoutExtension(File file) {
        return getFileNameWithoutExtension(file.getName());
    }

    public static String getFileNameWithoutExtension(String fileName) {
        int pos= fileName.lastIndexOf('.');
        if (pos< 0)
            return fileName;
        return fileName.substring(0, pos);
    }
    public static String getExtension(File f) {
       return getExtension(f.getName());
   }
   public static String getExtension(String s) {
       int p = -1;
       for (int i = s.length() - 1; i >= 0; --i) {
           if (s.charAt(i) == '.') {
               p = i;
               break;
           }
           if (s.charAt(i) == File.separatorChar) {
               break;
           }
       }
       if (p < 0) {
           return "";
       }
       return s.substring(p + 1);
   }
   
   public static boolean copy(File from, File to) {

       try {

           BufferedInputStream buffy = new BufferedInputStream(new FileInputStream(from));
           BufferedOutputStream wright = new BufferedOutputStream(new FileOutputStream(to));
           byte[] cbuf = new byte[1024];
           long bytesRead = 0, bytesTotal = from.length();
           int perc = 0;
           for (int c = 0, nb = 0; (nb = buffy.read(cbuf)) >= 0; ++c) {
               bytesRead += nb;
               wright.write(cbuf, 0, nb);

               if (c % 10 == 0) {

                   wright.flush();

               }

           }

           buffy.close();

           wright.flush();

           wright.close();

           return true;

 

       } catch (Exception e) {

           return false;

       }

   }
   public static void copyFile(File source, File dest) throws IOException {
            InputStream input = null;
            OutputStream output = null;
            try {
                    input = new FileInputStream(source);
                    output = new FileOutputStream(dest);
                    byte[] buf = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = input.read(buf)) > 0) {
                            output.write(buf, 0, bytesRead);
                    }
            } finally {
                    input.close();
                    output.close();
            }
    }
}
