package edu.isi.techknacq.topics.readinglist;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.isi.techknacq.topics.topic.Weightpair;


public class BaselineReadingList {
    private Map<String, Double> paperpagerank;
    private List<String> topickeys;
    private Logger logger = Logger.getLogger(BaselineReadingList.class);

    public void readPageRankScore(String filename) {
        this.paperpagerank = new HashMap<String,Double>(this.topickeys.size());
        try {
            FileInputStream fstream1 = null;
            fstream1 = new FileInputStream(filename);
            // Get the object of DataInputStream
            DataInputStream in1 = new DataInputStream(fstream1);
            BufferedReader br = new BufferedReader(new InputStreamReader(in1));
            String strline;
            br.readLine(); // Skip node vertices line
            br.readLine(); // Skip column name line
            String keyname;
            double value;
            String sr;
            while((strline = br.readLine()) != null) {
                Scanner sc = new Scanner(strline);
                sc.useDelimiter("\t| ");
                sr = sc.next();
                if (sr.contains("*Edge") || sr.contains("*Arc"))
                    break;
                keyname = sc.next();
                keyname = keyname.substring(1, keyname.length()-1);
                value = sc.nextDouble();
                if (!this.paperpagerank.containsKey(keyname)) {
                    this.paperpagerank.put(keyname, value);
                }
            }
            in1.close();
        } catch (FileNotFoundException ex) {
            logger.log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }

    public String printDocName(String metadata, String did) {
        String name;
        int index1 = metadata.indexOf("author:");
        int index2 = metadata.indexOf("title:");
        String author;
        String title;
        if (index1 >= 0 && index2 >= 0) {
            author = metadata.substring(index1+8, index2);
        } else
            author = null;
        if (index2 >= 0) {
            title = metadata.substring(index2+7, metadata.length());
        } else
            title = null;
        name = author + ": " + "<a href=\"http://www.aclweb.org/anthology/" +
            did.charAt(0) + "/" + did.substring(0, 3) + "/" + did + ".pdf\">" +
            title + "</a>";
        name = name.replace(" A ", " a ");
        name = name.replace(" Of ", " of ");
        name = name.replace(" As ", " as ");
        name = name.replace(" The ", " the ");
        name = name.replace(" To ",  " to ");
        name = name.replace(" And ", " and ");
        name = name.replace(" For ",  " for ");
        name = name.replace(" In ",  " in ");
        name = name.replace(" With ", " with ");
        name = name.replace(" By ", " by ");
        name = name.replace(" On ", " on ");
        name = name.replace(" - ", " &ndash; ");
        name = name.replace(" -- ", " &ndash; ");
        return name;
    }

    public void run(String keyname, int K, String docfile, String pagerankfile,
                    String keyword, String doc2conceptfilename) {
        try {
            Keyword2concept match1 = new Keyword2concept();
            match1.readKey(keyname);
            List<Integer> hittopic = match1.getMatch(keyword);
            this.topickeys = match1.getTopics();
            Concept2doc doc = new Concept2doc();
            doc.initNum(this.topickeys.size());
            doc.getTopK(K*4, doc2conceptfilename);
            // doc.prune();
            List<String> docnames = doc.getDocName();
            List<Weightpair> mylist = new ArrayList<Weightpair>(100);
            double value;
            boolean []isvisit = new boolean[docnames.size()];
            for (int i = 0; i < isvisit.length; i++) {
                isvisit[i] = false;
            }
            this.readPageRankScore(pagerankfile);
            for (Integer hittopic1 : hittopic) {
                int tindex = hittopic1;
                ArrayList<Integer> mydocs = doc.getDocs(tindex);
                for (Integer mydoc : mydocs) {
                    int Did = mydoc;
                    if (!isvisit[Did])
                        isvisit[Did] = true;
                    else
                        continue;
                    String dockey = docnames.get(Did);
                    if (this.paperpagerank.containsKey(dockey))
                        value = this.paperpagerank.get(dockey);
                    else
                        value = -1;
                    if (value > -1)
                        mylist.add(new Weightpair(value, Did));
                }
            }
            Collections.sort(mylist);
            ReadDocumentkey rdk = new ReadDocumentkey(docfile);
            rdk.readFile();
            FileWriter fstream = null;
            fstream = new FileWriter("BaselineReadingList_" + keyword + ".html",
                                     false);
            BufferedWriter out = new BufferedWriter(fstream);
            String html = "
<html>
<head>
<title>TechKnAcq Reading List</title>
<style type=\"text/css\">
body {
    margin: 2em auto;
    font-family: 'Univers LT Std', 'Helvetica', sans-serif;
    max-width: 900px;
    width: 90%;
}
article {
    border-top: 4px solid #888;
    padding-top: 3em;
    margin-top: 3em;
}
section {
    padding-bottom: 3em;
    border-bottom: 4px solid #888;
    margin-bottom: 4em;
}
section section {
    border: 0px;
    padding: 0px;
    margin: 0em 0em 3em 0em;
}
h1 { font-size: 18pt; }
h2 { font-size: 14pt; }
label { margin-right: 6px; }
input { margin-left: 6px; }
div.topic {
    padding: 1em;
}
p.rate { font-weight: bold; margin-left: 2em; }
blockquote { margin-left: 40px; }
a {
    text-decoration: none;
    font-style: italic;
    border-bottom: 1px dotted grey;
}
a:hover { color: blue !important; }
a:hover span { color: blue !important; }
</style>
</head>
<body>
<h1>Reading List for " + keyword + " </h1>";
            out.write(html);
            for (int i = 0; i < K; i++) {
                Weightpair o = (Weightpair)mylist.get(i);
                int Did = o.getindex();
                String id = docnames.get(Did);
                String strval = rdk.getDocumentKey(id);
                String name = this.printDocName(strval, id);
                out.write("<li>" + name + "</li>");
            }
            out.write("</form>\n" +
                      "</article>\n" +
                      "</body>\n" +
                      "</html>");
            out.close();
        } catch (IOException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String []args) {
        if (args.length < 1) {
            System.err.println("Usage: [keyword][k] [topic-key-file] " +
                               "[doc-topic-composition] [document-meta-file] " +
                               "[page-rank-score]");
            System.exit(2);
        }
        BaselineReadingList myreader = new BaselineReadingList();
        myreader.run(args[2], Integer.parseInt(args[1]), args[4], args[5],
                     args[0], args[3]);
        // String keyname, int K, String docfile, String pagerankfile,
        // String keyword, String doc2conceptfilename
        // myreader.run("./old topic/mallet-weighted-key.txt", 10,
        //              "acl-meta.json", "Paperpagerank.txt", args[0],
        //              "./old topic/concept2doc.txt");
        // myreader.run("mallet-21185-weightedkey.txt", 10, "acl-meta.json",
        //              "Paperpagerank.txt", args[0], "mallet-comp.txt");
    }

}
