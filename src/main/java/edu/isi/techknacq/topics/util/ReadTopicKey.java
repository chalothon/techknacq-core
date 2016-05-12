/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.isi.techknacq.topics.util;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.isi.techknacq.topics.topic.Indexpair;
import edu.isi.techknacq.topics.topic.Weightpair;

/**
 *
 * @author linhong
 */
public class ReadTopicKey {
    ArrayList<String> keynames;
    HashMap<String, Integer> words;
    List []topicinwords;
    int wordcount;
    ArrayList<String> wordlist;
    public void ReadTopicKey(){
        
    }
    public void read(String filename, int maxcount){
        try {
            wordcount=0;
            if(this.words==null){
                this.words=new HashMap<String,Integer>(4000);
                this.wordlist=new ArrayList<String>(4000);
            }
            else
            {
                this.words.clear();
                this.wordlist.clear();
            }
            if(this.keynames==null)
                this.keynames=new ArrayList<String>(200);
            else
                this.keynames.clear();
            FileInputStream fstream1 = null;
            fstream1 = new FileInputStream(filename);
            // Get the object of DataInputStream
            DataInputStream in1 = new DataInputStream(fstream1);
            BufferedReader br = new BufferedReader(new InputStreamReader(in1));
            String strline;
            int index;
            while((strline=br.readLine())!=null){
                Scanner sc=new Scanner(strline);
                sc.useDelimiter("\t| ");
                sc.next();
                sc.next();
                index=0;
                String name="";
                String tempword;
                while(sc.hasNext()&&index<maxcount){
                    tempword=sc.next();
                    if((!tempword.contains(name)&&!name.contains(tempword))||name.length()<1){
                        name+=tempword;
                        name+="-";
                        index++;
                    }
                    if(words.containsKey(tempword)==false){
                        words.put(tempword, wordcount);
                        this.wordlist.add(tempword);
                        wordcount++;
                    }
                }
                while(sc.hasNext()){
                    tempword=sc.next();
                    if(words.containsKey(tempword)==false){
                        words.put(tempword, wordcount);
                        this.wordlist.add(tempword);
                        wordcount++;
                    }
                }
                this.keynames.add(name);
            }
            in1.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ReadTopicKey.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ReadTopicKey.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void Concepttowords(String filename){
        try {
            FileInputStream fstream1 = null;
            fstream1 = new FileInputStream(filename);
            // Get the object of DataInputStream
            DataInputStream in1 = new DataInputStream(fstream1);
            BufferedReader br = new BufferedReader(new InputStreamReader(in1));
            String strline;
            int index=0;
            int conceptnum=this.keynames.size();
             topicinwords=new ArrayList[conceptnum];
            for(int i=0;i<conceptnum;i++){
                topicinwords[i]=new ArrayList<Indexpair>(21);
            }
            while((strline=br.readLine())!=null){
                Scanner sc=new Scanner(strline);
                sc.useDelimiter("\t| ");
                sc.next();
                sc.next();
                String tempword;
                while(sc.hasNext()){
                    tempword=sc.next();
                    int windex=this.words.get(tempword);
                    this.topicinwords[index].add(new Indexpair(windex,1));
                }
                index++;
            }
            in1.close();
        } catch (IOException ex) {
            Logger.getLogger(ReadTopicKey.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public List[]Getconceptinword(){
        return this.topicinwords;
    }
    public ArrayList<String> Getkeynames(){
        return this.keynames;
    }
    public HashMap<String, Integer> Getallwords(){
        return this.words;
    }
    public ArrayList<String> Getwordlist(){
        return this.wordlist;
    }
}