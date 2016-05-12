/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.isi.techknacq.topics.util;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author linhong
 */
public class Testprint {
    public static void printMap(Map mp, BufferedWriter out) {
        try {
            Iterator it = mp.entrySet().iterator();
            while (it.hasNext()) {
                try {
                    Map.Entry pairs = (Map.Entry)it.next();
                    Integer w=(Integer)pairs.getValue();
                    if(w>1)
                        out.write(pairs.getKey() + "\t" + pairs.getValue()+"\n");
                } catch (IOException ex) {
                    Logger.getLogger(Testprint.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            out.close();
        } catch (IOException ex) {
            Logger.getLogger(Testprint.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static void printcsvMap(Map mp, BufferedWriter out) {
        try {
            Iterator it = mp.entrySet().iterator();
            while (it.hasNext()) {
                try {
                    Map.Entry pairs = (Map.Entry)it.next();
                    out.write(pairs.getKey() + "," + pairs.getValue()+"\n");
                } catch (IOException ex) {
                    Logger.getLogger(Testprint.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            out.close();
        } catch (IOException ex) {
            Logger.getLogger(Testprint.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static void printMap(Map mp){
        Iterator it = mp.entrySet().iterator();
            while (it.hasNext()) {
                    Map.Entry pairs = (Map.Entry)it.next();
                    System.out.println(pairs.getKey() + "\t" + pairs.getValue());
            }
    }
    public static void PrintInArray(ArrayList a){
        for(int i=0;i<a.size();i++){
            System.out.println(a.get(i));
        }
    }
    public static void PrinttabArray(ArrayList a){
        for(int i=0;i<a.size();i++){
            System.out.print(a.get(i)+"\t");
        }
        System.out.println();
    }
}