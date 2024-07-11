package com.HansEnterprices;

import java.io.Serializable;

final class Node implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7430310724561297175L;
	private String[] data;                         //Data Array
    protected int length;                          //Length of Data Array
    protected int index;                           //Entry index in table
    protected Node prev;                          //Pointer to previous entry
    protected Node next;                          //Pointer to next index
    private Node(int length)                       //Private constructor
    {
        this.data=new String[length];
        this.length=length;
    }
    public static Node generateNode(int length)    //Factory function
    {
        return new Node(length);
    }
    public void putData(String newData,int i)      //Input only single value into the Node
    {
        this.data[i]=newData;
    }
    public String[] getNodeArray()                 //Return Node data as a new Array
    {
        String[] data=new String[this.length];
        for(int i=0;i<this.length;i++)
        {
            data[i]=this.data[i];
        }
        return data;
    }
    public String getData(int index)               //Return data from entry object of a certain field
    {
        if(index<0) return new String();
        if(data[index]!=null) return new String(data[index]);
        else return new String();
    }

}
