package edu.isi.techknacq.topics.topic;

import edu.isi.techknacq.topics.util.Pair;

/**
 *
 * @author linhong
 * @email linhong.seba.zhu@gmail.com
 * @since April/19/2012
 */
public class IndexPair extends Pair implements Comparable {
    public IndexPair(int index, double value){
         super(index,value);
    }
        public void setindex(int indexvalue){
        setKey(indexvalue);
    }
    public void setvalue(double inputvalue){
        setValue(inputvalue);
    }
    public int getindex(){
        return ((Integer)this.getKey()).intValue();
    }
    public double getweight(){
        return ((Double)this.getValue()).doubleValue();
    }
    public int compareTo(Object o) {
         return compareTo((IndexPair)o);
    }
    public int compareTo(IndexPair o) {
        if(getindex()==o.getindex())
            return 0;
        else
            if(getindex()>o.getindex())
                return 1;
            else
                return -1;
    }
}

