/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.isi.techknacq.topics.topic;

import edu.isi.techknacq.topics.util.Pair;

/**
 *
 * @author linhong
 */
public class StringPair extends Pair implements Comparable
{
    public StringPair(String word, String name)
    {
        super(word, name);
    }

    public String getWord()
    {
        return (String)getKey();
    }

    public void setWord(String word)
    {
        setKey(word);
    }

    public String getname()
    {
        return (String)getValue();
    }

    public void setname(String name)
    {
        setValue(name);
    }

    public int compareTo(Object o)
    {
        return compareTo((StringPair)o);
    }

    private int compareTo(StringPair other)
    {
        return getWord().compareToIgnoreCase(other.getWord());
    }

    @Override
    public boolean equals(Object other)
    {
        if (other == this)
            return true;
        if (other == null)
            return false;
        if (this.getClass() != other.getClass())
            return false;
        return this.getWord().equalsIgnoreCase(((StringPair)other).getWord());
    }

    @Override
    public int hashCode()
    {
        return getWord().hashCode();
    }

    @Override
    public String toString()
    {
        String result = "[" + getWord() + ", " + getname() + "]";
        return result;
    }
}
