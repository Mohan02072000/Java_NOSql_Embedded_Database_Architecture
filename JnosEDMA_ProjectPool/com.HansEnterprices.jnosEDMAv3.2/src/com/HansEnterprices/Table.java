package com.HansEnterprices;

import java.io.Serializable;
import java.sql.Date;
import java.text.SimpleDateFormat;

final class Table implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3836362232689676141L;
	public String name;                                                              //Name of the table
    private Node tail;                                                               //Tail of the entry linked list
    private Node head;                                                               //head of the Entry linked list
    private byte[] datatypeId;                                                       //Array mapping Datatype ID
    protected boolean[] noNull;                                                        //Array for No null fields
    protected boolean[] noDuplicate;                                                   //Array for no Duplicate fields
    protected int size;                                                              //Record counts
    protected int fields;                                                            //Total fields no
    private String[] datatypes=                                                      //data types
    {"Undefined","Boolean","Integer","Date","Character","String"};                   // Available data types in table object
    private int[] indent;                                                             
    private Table (String name,String [] header, String[] datatype,boolean[] noNull,boolean[] noDuplicate)
    {
        this.name=name;
        this.head=Node.generateNode(header.length);
        this.tail=head;
        this.datatypeId=new byte[header.length];
        this.noNull=new boolean[header.length];
        this.noDuplicate=new boolean[header.length];
        this.indent=new int[header.length];
        for(int i=0;i<indent.length;i++)
           indent[i]=15;
        for(int i=0;i<this.head.length;i++)
        head.putData(header[i],i);
        this.size=0;
        this.fields=header.length;
        head.index=0;
        //this.datatype=datatype;
        for(int i=0;i<datatype.length;i++)
        {
            for(byte j=0;j<this.datatypes.length;j++)
            {
                if(datatype[i].equalsIgnoreCase(this.datatypes[j]))
                {
                    datatypeId[i]=j;
                }
            }
        }
        for(int i=0;i<fields;i++)
        {
            this.noNull[i]=noNull[i];
            this.noDuplicate[i]=noDuplicate[i];
        }
        System.out.println("Table created Successfully");
    }
    private Table (String name,String [] header,byte[] datatypeID,boolean[] noNull,boolean[] noDuplicate)
    {
        this.name=name;
        this.head=Node.generateNode(header.length);
        this.tail=head;
        this.datatypeId=new byte[header.length];
        this.noNull=new boolean[header.length];
        this.noDuplicate=new boolean[header.length];
        this.indent=new int[header.length];
        for(int i=0;i<indent.length;i++)
           indent[i]=15;
        for(int i=0;i<this.head.length;i++)
        head.putData(header[i],i);
        this.size=0;
        this.fields=header.length;
        head.index=0;
        //this.datatype=datatype;
        this.datatypeId=datatypeID;
        for(int i=0;i<fields;i++)
        {
            this.noNull[i]=noNull[i];
            this.noDuplicate[i]=noDuplicate[i];
        }
        System.out.println("Table created Successfully");
    }
    public static Table generateTable (String name,String[] header, String[] datatype, boolean[] noNull, boolean[] noDuplicate) //factory function for setting no null and no dupl
    {
        return new Table(name,header,datatype,noNull, noDuplicate);
    }
    public static Table generateTable(String name,String[] header, String[] datatype)                                           //factory function dupl and null set to false
    {
        return new Table(name,header,datatype,new boolean[header.length],new boolean[header.length]);
    }
    public String[] getHeaders()                                                                                                //get headers array
    {
        return head.getNodeArray();
    }
    protected String[] getDataTypes()
    {
        String[] datatypes= new String[datatypeId.length];
        for(int i=0;i<datatypes.length;i++)
        {
            datatypes[i]=this.datatypes[datatypeId[i]];
        }
        return datatypes;
    }
    protected boolean[][] getFieldsAttributes()
    {
        boolean [][] fieldAttributes=new boolean[2][this.fields];
        for(int i=0;i<this.fields;i++)
        {
            fieldAttributes[0][i]=this.noNull[i];
            fieldAttributes[1][i]=this.noDuplicate[i];
        }
        return fieldAttributes;
    }
    public void recaliberate()throws NullPointerException                                                                       // Recaliberate indexes and Pointers
    {
        Node node=head;
        size=0;
        while(node!=null)
        {
            node.index=size;
            if(node!=null && node.next!=null)node.next.prev=node;
            node=node.next;
            if(node!=null)tail=node;
            size++;
        }
        size--;
    }
    public void showStructure()                                                                                                 //Show table structure
    {
        Node node=this.head;
            System.out.print(" --------------  ");
                for(int i=0;i<node.length;i++)
                {
                    System.out.printf("%-"+Integer.toString(this.indent[i])+"s ","----------------");
                }
                System.out.println();
                System.out.print("|FieldName-    |  ");
                
                
                for(int j=0;j<node.length;j++)
                    System.out.printf("%-"+Integer.toString(this.indent[j])+"s |",node.getData(j));
                
                System.out.println();
                System.out.print("|DataType-     |  ");
                for(int i=0;i<node.length;i++)
                {
                    System.out.printf("%-"+Integer.toString(this.indent[i])+"s |",datatypes[datatypeId[i]]);
                }
                
                System.out.println();
                System.out.print("|noNull-       |  ");
                for(int k=0;k<head.length;k++)
                {
                    System.out.printf("%-"+Integer.toString(this.indent[k])+"s |",noNull[k]);                    
                }
                
                System.out.println();
                System.out.print("|noDupl-       |  ");
                for(int k=0;k<head.length;k++)
                {
                    System.out.printf("%-"+Integer.toString(this.indent[k])+"s |",noDuplicate[k]);                    
                }
                
                System.out.println();
                System.out.print(" --------------  ");
                for(int i=0;i<node.length;i++)
                {
                    System.out.printf("%-"+Integer.toString(this.indent[i])+"s ","----------------");
                }
                System.out.println();        
    }
    public int[] getSequence(String[] header)                                                                                   //get headers sequence for custom insert and select
    {
        int[] index=new int[header.length];
        for(int i=0;i<header.length;i++)
        {
            index[i]=-1;
        for(int j=0;j<head.length;j++)
        {
            
            if(header[i].equalsIgnoreCase(head.getData(j)))
            index[i]=j;
        }
        }
        //System.out.print("Node Index Sequence : ");
        //for(int i=0;i<index.length;i++)
        //{
         //   System.out.print(Integer.toString(index[i])+"  ");
        //}
        //System.out.println();
        return index;
    }
    public String insert(String[] entry)throws NullPointerException                                                             //Insert
    {
        recaliberate();
        
        System.out.print("Insert String   : ");
        for(int j=0;j<entry.length;j++)
        {
           System.out.print("'"+entry[j]+"'  ");
        }
        //check length of string array
        //insert if only equal to no of fields(head.length)
        System.out.println();
        if(entry.length!=head.length)  
        {
            return ("Insertion Failed: Array size not in sync with the table size");
        } 

        
        
        //check for nulls in no null field
        for(int i=0;i<head.length;i++)
        {
            if(noNull[i]==true && entry[i]==null)
            {
                return ("Insertion Failed: Null detected in a no null Field");
                                
            }
        }  
        
        //check for duplicates
        Node node=this.head;
        for(int i=0;i<=this.size;i++)
        {
            //System.out.println("Checking for duplicates in index: "+Integer.toString(node.index));
            
            
            for(int j=0;j<head.length;j++)
            {
            //System.out.println("Checking for Duplicates in: "+Integer.toString(j));
            //System.out.println(node.getData(j)+" == "+entry[j]);
            try{
                if(noDuplicate[j]==true && node.getData(j).equals(entry[j])==true)
            {
                return("Insertion Failed: Duplicate detected in a no Duplicate Field"); 
            }
            }
            catch(NullPointerException e){}
            }
        
            if(node!=null)node=node.next;            
        }
        node=null;
            
            
            Node newNode=Node.generateNode(head.length);

            //Insertion Block
            
            try
            {
                
                for(int i=0;i<entry.length;i++)
                {
                    if(entry[i]!=null)
                    switch(this.datatypes[datatypeId[i]])
                    {
                        case "String":
                        if(entry[i].length()!=0) newNode.putData(entry[i].trim(),i);
                        break;
                        case "Boolean":
                        newNode.putData(Boolean.toString(Boolean.parseBoolean(entry[i].trim())),i);
                        break;
                        case "Integer":
                        newNode.putData(Integer.toString(Integer.parseInt(entry[i].trim())),i);
                        break;
                        case "Date":
                        if(entry[i].length()!=0) 
                        {
                            
                                SimpleDateFormat inputformat=new SimpleDateFormat("dd/MM/yyyy");
                                inputformat.setLenient(false);
                                SimpleDateFormat outputFormat=new SimpleDateFormat("dd/MM/YYYY");
                                newNode.putData(outputFormat.format((inputformat.parse(entry[i]))),i);
                            
                        }
                        break;
                        case "Character":
                        if(entry[i].length()!=1) throw new Exception();
                        newNode.putData(entry[i].trim(),i);
                        break;
                        case "Undefined":
                        if(entry[i].length()!=0) newNode.putData(entry[i].trim(),i);
                        break;
                    }

                }}
            catch(NullPointerException e){}
            catch(Exception e)
            {
                return("Insertion Failed: Illegal Datatype found");
                
            //return false;
            }

            //Get to the last node
            this.tail.next=newNode;
            newNode.index=tail.index+1;
            newNode.prev=this.tail;
            this.tail=newNode;
            
            recaliberate();
            return ("Insertion Successful");            
    }  
    public String insert(String[] header,String[] entry)throws NullPointerException                                             //custom insert
    {
        if(header.length!=entry.length) return "Insertion failed : Illegal input headers and values";
        int[] index= getSequence(header);
        String[] insertstring=new String[this.fields];
        //System.out.print("Custom Insert Index Sequence : ");
       // for(int i=0;i<index.length;i++)
        //{
         //   System.out.print(Integer.toString(index[i])+"  ");
        //}
        //System.out.println();
        for(int j=0;j<index.length;j++)
        {
            insertstring[index[j]]=entry[j];
        }
        
        //System.out.print("Insert String   : ");
        //for(int j=0;j<entry.length;j++)
        //{
        //    System.out.print(insertstring[j]+"  ");
        //}
        //System.out.println();
        recaliberate();
        return this.insert(insertstring);
    }
    public String insert(String[][] view)
    {
        for(int i=1;i<view.length;i++)
        {
            System.out.println(insert(view[0],view[i]));
        }
        return "Insertion successfull";

    }
    public String delete(int[] index)                                                                                           //Delete by index
    {
        Node node=this.head;
        int initcount=this.size;
        
        for(int j=0;j<index.length;j++)
        {
        while(node!=null)
        {
            try{
            if(node.index==index[j])
            {
                System.out.println("Index : "+node.index+" Deleted");
                node.prev.next=node.next;
                node.next.prev=node.prev; 
                
                break;                               
            }
             // System.out.println("Loop 1"); 
            } 
            catch(NullPointerException e){}
            //
            node=node.next;
            
            //System.out.println("Loop 2");
        }
        node=this.head;
        }
        //Recaliberate the Indexes
        recaliberate();
        recaliberate();
        
        
        recaliberate();
        return (Integer.toString(initcount-this.size)+" Entries deleted from list");
    }
    public void select()throws NullPointerException                                                                             //Select all 
    {
        System.out.println("Select "+this.name);
        select(this.head.getNodeArray());
    }
    public void select(String[] header)throws NullPointerException                                                              //Custom select
    {
        System.out.println("Select "+this.name);
        int[] index= getSequence(header);
        Node node=head;
        System.out.print("Custom Select Index Sequence : ");
        for(int i=0;i<index.length;i++)
        {
            System.out.print(Integer.toString(index[i])+"  ");
        }
        System.out.println();
        for(int i=0;i<=this.size;i++)
        {   
            
                
            if (i==0)
            {
                System.out.print("===Table=");
                for(int j=0;j<header.length;j++)
                {
                    if(index[j]<0) continue;
                    System.out.printf(" %-"+Integer.toString(this.indent[j])+"s","================ ");
                }   
                System.out.print("= ");
                System.out.println();
                System.out.printf(" %8s","|"+Integer.toString(node.index)+"|");
                System.out.print("|");
                for(int j=0;j<index.length;j++)
                    if(index[j]>=0)System.out.printf(" %-"+Integer.toString(this.indent[j])+"s |",node.getNodeArray()[index[j]]);
                //node.display_entry(index); 
                System.out.print("| ");
                System.out.println();
                System.out.print("         |");
                for(int j=0;j<header.length;j++)
                {
                    if(index[j]>=0) System.out.printf(" %-"+Integer.toString(this.indent[j])+"s |",datatypes[datatypeId[j]]);                    
                }
                System.out.print("| ");
                System.out.println();
                
                System.out.print("=========");
                for(int j=0;j<header.length;j++)
                {
                    if(index[j]<0) continue; 
                    System.out.printf(" %-"+Integer.toString(this.indent[j])+"s","================ ");
                }
                System.out.print("= ");
                System.out.println();
                node=node.next;
            }
            else
            {
                System.out.printf(" %8s","|"+Integer.toString(node.index)+"|");
                System.out.print("|");
                //node.display_entry(index); //Replaced below
                for(int j=0;j<index.length;j++)
                    if(index[j]!=-1)System.out.printf(" %-"+Integer.toString(this.indent[j])+"s |",node.getNodeArray()[index[j]]);
                System.out.print("|");
                System.out.println();
                node=node.next;
            }
            
        }
                System.out.print("=========");
                for(int j=0;j<header.length;j++)
                {
                    if(index[j]>=0)System.out.printf(" %-"+Integer.toString(this.indent[j])+"s","================ ");
                }
                System.out.print("= ");
        System.out.println();
        System.out.println("Selected result count: "+Integer.toString(size));
        System.out.println();
    } 
    public void select(int[] index)throws NullPointerException                                                                  //Filter select
    {
        System.out.println("Select "+this.name);
        select(this.head.getNodeArray(), index);

    }
    public void select(String[] header,int[] indexes)throws NullPointerException                                                //Custom filter select
    {
        System.out.println("Custom Filter Select "+this.name);
        int[] index= getSequence(header);
        Node node=head;
        System.out.print("Custom Filter Select header Index Sequence : ");
        for(int i=0;i<index.length;i++)
        {
            System.out.print(Integer.toString(index[i])+"  ");
        }
        System.out.println();
        System.out.print("Custom Filter Select filter Index Sequence : ");
        for(int i=0;i<indexes.length;i++)
        {
            System.out.print(Integer.toString(indexes[i])+"  ");
        }
        System.out.println();
        for(int i=0;i<=this.size;i++)
        {
            //if(indexes[i]<0) continue;
            if (i==0)
            {
                System.out.print("===Table=");
                for(int j=0;j<header.length;j++)
                {
                    if(index[j]>=0)System.out.printf(" %-"+Integer.toString(this.indent[j])+"s","================ ");
                }  
                System.out.print("= "); 
                System.out.println();
                System.out.printf("%8s","|"+Integer.toString(node.index)+"|  ");
                System.out.print("|");
                for(int j=0;j<index.length;j++)
                    if(node.getData(index[j]).equals("")==false)System.out.printf(" %-"+Integer.toString(this.indent[j])+"s |",node.getData(index[j]));
                System.out.print("|"); 
                
                System.out.println();
                System.out.print("        |");
                for(int j=0;j<header.length;j++)
                {
                    if(index[j]>=0)System.out.printf(" %-"+Integer.toString(this.indent[j])+"s |",datatypes[datatypeId[index[j]]]);                    
                }
                System.out.print("|");
                System.out.println();
                System.out.print("=========");
                for(int j=0;j<header.length;j++)
                {
                    if(index[j]>=0)System.out.printf(" %-"+Integer.toString(this.indent[j])+"s","================ ");
                }
                System.out.print("= ");
                System.out.println();
                node=node.next;
            }
            else
            {
                for(int j=0;j<indexes.length;j++)
                if(node.index==indexes[j])
                {
                System.out.printf("%8s","|"+Integer.toString(node.index)+"|  ");
                System.out.print("|");
                for(int k=0;k<index.length;k++)
                    if(node.getData(index[k])!=null && index[k]!=-1)System.out.printf(" %-"+Integer.toString(this.indent[k])+"s |",node.getData(index[k]));
                System.out.print("|");
                System.out.println();
                }
                node=node.next;
            }
           
        }
                System.out.print("=======  ");
                for(int j=0;j<header.length;j++)
                {
                    if(index[j]>=0)System.out.printf(" %-"+Integer.toString(this.indent[j])+"s","================ ");
                } 
                System.out.print("= ");
        System.out.println();
        System.out.println("Selected result count: "+Integer.toString(indexes.length));
        System.out.println();
        

    }
    public Node getEntry(int index)throws NullPointerException                                                                  //find entry as per index
    {
        if(index>0 && index<=size)
        {
        Node node=head;
        while(node.index!=index && node.next!=null)
        {
            node=node.next;
        }
        return node; 
        }
        else 
        System.out.println("Index out of bound: "+Integer.toString(index));
        System.out.println("Min Index is : 1");
        System.out.println("Max Index is : "+Integer.toString(this.size));

        return Node.generateNode(0);
    }
    public String updateTable(String[] headers,String[] values, int[] indexes)                                                  //Update values in the entry as per 
    {
        if(headers.length!=values.length) return "Table Update failed : different headers and values count";
        Node node;
        int Failure=0;
        int[] fieldsIndexSequence=getSequence(headers);
        for(int i=0;i<indexes.length;i++)                                 //access index record
        {
            if(indexes[i]>this.size) 
            {
                System.out.println("Updation failed for Index:"+Integer.toString(indexes[i])+". Index not in bound");
                Failure++;
                continue;
            }
            node=getEntry(indexes[i]);
            String[] bkp=node.getNodeArray();
            for(int j=0;j<fieldsIndexSequence.length;j++)                //access each fields
            {
                try{
                switch(this.datatypes[datatypeId[fieldsIndexSequence[j]]])
                {
                    case "String":  
                        node.putData(values[j].trim(), fieldsIndexSequence[j]);
                        break;
                    case "Boolean":
                        node.putData(Boolean.toString(Boolean.parseBoolean(values[j].trim())),fieldsIndexSequence[j]);
                        break;
                    case "Integer":
                        node.putData(Integer.toString(Integer.parseInt(values[j].trim())),fieldsIndexSequence[j]);
                        break;
                    case "Date":
                        if(values[i].trim().length()!=0) 
                        {
                            SimpleDateFormat inputformat=new SimpleDateFormat("dd/MM/yyyy");
                            SimpleDateFormat outputFormat=new SimpleDateFormat("dd/MM/yyyy");
                            node.putData(outputFormat.format((inputformat.parse(values[j]))),fieldsIndexSequence[j]);
                        }
                        break;
                    case "Character":
                        if(values[j].length()!=1) throw new Exception();
                        node.putData(values[j].trim(),fieldsIndexSequence[j]);
                        break;
                    case "Undefined":
                        if(values[j].length()!=0) node.putData(values[j].trim(),fieldsIndexSequence[j]);
                        break;
                    
                }
                }
                catch(NullPointerException e){}
            catch(Exception e)
            {
                for(int l=0;l<node.length;l++)
                node.putData(bkp[l], l);
                return("Updation Failed: Illegal Datatype found");
                
            //return false;
            }
               
            }
        }
        return "Table update complete for "+Integer.toString(indexes.length-Failure)+" records";
    }
    public String updateTable(String[] headers,String[] values)                                                                 //Update values in the whole table
    {
        int[] indexes=new int[this.size];
        for(int i=0;i<this.size;i++)
        indexes[i]=i;
        return updateTable(headers, values,indexes);
    }
    public String[][] getView()                                                                                                 //Return table data as a 2D array
    {
        String[][] view=new String[this.size+1][this.fields];
        Node node=head;
        for(int i=0;i<=this.size;i++)
        {
            view[i]=node.getNodeArray();
            node=node.next;
        }
        return view;
        
    }
    public String[][] getView(String[] headers)                                                                                 //Return table data of mentioned headers only
    {
        String[][] view=new String[this.size+1][headers.length];
        Node node=head;
        int[] fieldsIndexSequence=getSequence(headers); 
        for(int i=0;i<=this.size;i++)
        {
            for(int j=0;j<fieldsIndexSequence.length;j++)
            {
                
                view[i][j]=node.getData(fieldsIndexSequence[j]);
                
            }
            node=node.next;
        }
        return view;

    }
    public String[][] getView(int[] index)                                                                                      //Return table data of filtered indexes in 2D array
    {
        //System.out.println("inside getView(int[])");
        String[][] view=new String[index.length+1][this.fields];
        Node node;
        for(int i=0;i<=index.length;i++)
        {
            if(i==0)
            {
                view[i]=this.head.getNodeArray();
                continue;
            }
            node=this.getEntry(index[i-1]);
            view[i]=node.getNodeArray();
            //node=node.next;
        }
        return view;

    }
    public String[][] getView(String[] headers,int[] index)                                                                     //Return table data of filtered index of mentioned headers only
    {
        String[][] view=new String[index.length+1][headers.length];
        Node node;
        int[] headersIndex=this.getSequence(headers);
        for(int i=0;i<=index.length;i++)
        {
            for(int j=0;j<headers.length;j++)
            {
            if(i==0)
            {
                view[i][j]=this.head.getNodeArray()[headersIndex[j]];
                continue;
            }
            node=this.getEntry(index[i-1]);
            view[i][j]=node.getNodeArray()[headersIndex[j]];
            //node=node.next;
            }
        }
        return view;
    }
    public int[][] where(String[][] clause)throws Exception                                                                //Return indexes where conditions satisfy in the [n][3] clause matrix
    {
        if(clause[0].length!=3 )
        {
            System.out.println("Error: Clause matrix not in synk with the Function requirement");
            return new int[0][0];
        }
        else{
            boolean[][] indexFlag=new boolean[clause.length][this.size+1];
            SimpleDateFormat tableDateformatter=new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat compareDateformatter=new SimpleDateFormat("dd/MM/yyyy");
            
            for(int j=1;j<=this.size;j++)
            for(int i=0;i<clause.length;i++)
            {
                int headerIndex=this.getSequence(new String[]{clause[i][0]})[0];                                                //get Header index for comparision
                String compareValue=clause[i][2];
                String tableValue=this.getEntry(j).getNodeArray()[headerIndex];
                Date tableDateValue=null;
                Date compareDateValue=null;
                try{
                switch (clause[i][1])
                {
                    
                    case "String.equals":
                        //System.out.println("string.equals in If block in table index: "+Integer.toString(j)+" in header : "+this.getHeaders()[headerIndex]);
                        if(tableValue.trim().equals(compareValue.trim()))indexFlag[i][j]=true;
                        break;
                    case "String.equalsIgnoreCase":
                        //System.out.println("string.equalsIgnoreCase in If block in table index: "+Integer.toString(j)+" in header :"+this.getHeaders()[headerIndex]);
                        if(tableValue.trim().equalsIgnoreCase(compareValue.trim()))indexFlag[i][j]=true;
                        break;
                    case "Integer.lessThan":
                         //System.out.println("Integer.lessThen in If block in table index: "+Integer.toString(j)+" in header :"+this.getHeaders()[headerIndex]);
                         if(Integer.parseInt(tableValue.trim())<Integer.parseInt(compareValue.trim()))indexFlag[i][j]=true;
                         break;
                    case "Integer.greaterThan":
                         //System.out.println("Integer.greaterThen in If block in table index: "+Integer.toString(j)+" in header :"+this.getHeaders()[headerIndex]);
                         if(Integer.parseInt(tableValue.trim())>Integer.parseInt(compareValue.trim()))indexFlag[i][j]=true;
                         break;
                    case "Integer.equals":
                         //System.out.println("Integer.equals in If block in table index: "+Integer.toString(j)+" in header :"+this.getHeaders()[headerIndex]);
                         if(Integer.parseInt(tableValue.trim())==Integer.parseInt(compareValue.trim()))indexFlag[i][j]=true;
                         break;
                    case "Integer.lessThanEquals":
                         //System.out.println("Integer.lessThenEquals in If block in table index: "+Integer.toString(j)+" in header :"+this.getHeaders()[headerIndex]);
                         if(Integer.parseInt(tableValue.trim())<=Integer.parseInt(compareValue.trim()))indexFlag[i][j]=true;
                         break;
                    case "Integer.greaterThanEquals":
                         //System.out.println("Integer.greaterThenEquals in If block in table index: "+Integer.toString(j)+" in header :"+this.getHeaders()[headerIndex]);
                         if(Integer.parseInt(tableValue.trim())>=Integer.parseInt(compareValue.trim()))indexFlag[i][j]=true;
                         break;
                    case "Char.equals":
                         if(tableValue.trim().charAt(0)==compareValue.charAt(0))indexFlag[i][j]=true;
                         break;
                    case "Date.before":
                                tableDateValue=(Date) tableDateformatter.parse(tableValue.trim());
                                compareDateValue=(Date) compareDateformatter.parse(compareValue.trim());
                                if(tableDateValue.before(compareDateValue))indexFlag[i][j]=true;
                          break;
                    case "Date.after":
                                tableDateValue=(Date) tableDateformatter.parse(tableValue.trim());
                                compareDateValue=(Date) compareDateformatter.parse(compareValue.trim());
                                if(tableDateValue.after(compareDateValue))indexFlag[i][j]=true;
                                break;

                }                
                }
                catch (NullPointerException e){};
            }
            int[][] index=new int[2][];
            int indexSize=0;
            //System.out.println();
            //System.out.println();
            //for(int i=0;i<indexFlag.length;i++){
            //    System.out.print("   Cond "+Integer.toString(i)+": ");                                            
            //for(int j=0;j<indexFlag[i].length;j++)                                                                
            //{                                                                                                     
           // System.out.printf("%-5s ",Boolean.toString(indexFlag[i][j]));                                  //------ Display Logic                      
           // }
           // System.out.println();
           // }

            boolean[] orIndexFlag=new boolean[this.size+1];
            boolean[] andIndexFlag=new boolean[this.size+1];
            for(int i=0;i<=this.size;i++)
            {
                int j;
                for(j=0;j<clause.length;j++)                                               //OR Logic
                {
                    if(indexFlag[j][i]==true)
                    {
                        orIndexFlag[i]=true;
                        break;
                    }
                }
                
                for(j=0;j<clause.length;j++)                                              //AND Logic
                if(indexFlag[j][i]==false)break;   
                if(j==clause.length)andIndexFlag[i]=true; 
            }

            //System.out.println();
            //System.out.print(" Or logic :");

            //for(int i=0;i<orIndexFlag.length;i++)System.out.printf("%-5s ",orIndexFlag[i]);               //------ Display Or Logic table
            //System.out.println();

            //System.out.print("And logic :");
            //for(int i=0;i<orIndexFlag.length;i++)System.out.printf("%-5s ",andIndexFlag[i]);               //------ Display And Logic table
            //System.out.println();
            //System.out.println();
            indexSize=0;
            for(int i=0;i<orIndexFlag.length;i++)                                                                 //Build or index size
            {
                if(orIndexFlag[i]==true)indexSize++;
            }
            //System.out.println("Or Index size: "+indexSize);                                                          //show And Index size
            index[0]=new int[indexSize];
            for(int i=0,j=0;i<orIndexFlag.length;i++)                                                                 //Build or index list
            {
                if(orIndexFlag[i]==true)
                {
                    index[0][j]=i;
                    j++;
                }
            }
            indexSize=0;
            for(int i=0;i<orIndexFlag.length;i++)
            {
                if(andIndexFlag[i]==true)indexSize++;                                                               //build and index list
            }
            //System.out.println("And Index size: "+indexSize);                                                       //show AND Index size
            index[1]=new int[indexSize];

            for(int i=0,j=0;i<orIndexFlag.length;i++)                                                               //Build and index list
            {
                if(andIndexFlag[i]==true)
                {
                    index[1][j]=i;
                    j++;
                }
            }

            //for(int i=0;i<index.length;i++)                                                                          //Show output index data
            //{
            //for(int j=0;j<index[i].length;j++)
           // System.out.print(index[i][j]+"  ");
           // System.out.println();
           // }

            return index;
        }
    }
    protected Table duplicateTable()
    {
        Table duplicate =new Table(this.name,this.getHeaders(),this.datatypeId,this.noNull,this.noDuplicate);
        duplicate.insert(this.getView());
        return duplicate;
    }
}
