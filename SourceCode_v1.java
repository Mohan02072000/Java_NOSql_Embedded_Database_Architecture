
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;


final class Node implements Serializable          //Node class
{
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

final class Table implements Serializable                                            //table Object class
{
    public String name;                                                              //Name of the table
    private Node tail;                                                               //Tail of the entry linked list
    private Node head;                                                               //head of the Entry linked list
    private byte[] datatypeId;                                                       //Array mapping Datatype ID
    private boolean[] noNull;                                                        //Array for No null fields
    private boolean[] noDuplicate;                                                   //Array for no Duplicate fields
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
                
                view[i][j]=node.getData(j);
                
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
    public int[][] where(String[][] clause)                                                                                     //Return indexes where conditions satisfy in the [n][3] clause matrix
    {
        if(clause[0].length!=3 )
        {
            System.out.println("Error: Clause matrix not in synk with the Function requirement");
            return new int[0][0];
        }
        else{
            boolean[][] indexFlag=new boolean[clause.length][this.size+1];
            
            for(int j=1;j<=this.size;j++)
            for(int i=0;i<clause.length;i++)
            {
                int headerIndex=this.getSequence(new String[]{clause[i][0]})[0];                                                //get Header index for comparision
                String compareValue=clause[i][2];
                String tableValue=this.getEntry(j).getNodeArray()[headerIndex];
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
                    case "Char.Equals":
                         if(tableValue.trim().charAt(0)==compareValue.charAt(0))indexFlag[i][j]=true;
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

final class Block implements Serializable
{
    protected Table table;
    protected String name;
    protected int version;
    private Block(String tableName,String[] headers,String[] DataType)
    {
        this.name=tableName;
        this.table=Table.generateTable(tableName,headers,DataType);
    }
    private Block(String tableName,String[] headers,String[] DataType, boolean[] noNull, boolean[] noDuplicate)
    {
        this.name=tableName;
        this.table=Table.generateTable(tableName,headers,DataType,noNull,noDuplicate);
    }
    public static Block generateBlock(String blockName,String[] headers,String[] DataType)
    {
        return new Block(blockName,headers,DataType);
    }
    public static Block generateBlock(String blockName,String[] headers,String[] DataType, boolean[] noNull, boolean[] noDuplicate)
    {
        return new Block(blockName,headers,DataType,noNull,noDuplicate);
    }

}

final class BlockEditor implements Serializable
{
    protected Table table;
    protected BlockEditor prev;
    protected String name;
    protected int version;
    private BlockEditor(String tableName,String[] headers,String[] DataType)
    {
        this.name=tableName;
        this.table=Table.generateTable(tableName,headers,DataType);
    }
    private BlockEditor(String tableName,String[] headers,String[] DataType, boolean[] noNull, boolean[] noDuplicate)
    {
        this.name=tableName;
        this.table=Table.generateTable(tableName,headers,DataType,noNull,noDuplicate);
    }
    private BlockEditor(BlockEditor prev)
    {
        System.out.println("    Block Duplication in progress");
        this.table=prev.table.duplicateTable();
        System.out.println("    Table Duplication complete.");
        this.prev=prev;
        this.name=prev.name;
        System.out.println("    Versioning in progress");
        this.version=prev.version+1;        
    }
    public BlockEditor rollback()
    {
        return this.prev;
        
    }
    public BlockEditor commit()
    {
        return new BlockEditor(this);
    }
    public static BlockEditor generateBlockEditor(String blockName,String[] headers,String[] DataType)
    {
        return new BlockEditor(blockName,headers,DataType);
    }
    public static BlockEditor generateBlockEditor(String blockName,String[] headers,String[] DataType, boolean[] noNull, boolean[] noDuplicate)
    {
        return new BlockEditor(blockName,headers,DataType,noNull,noDuplicate);
    }
    public static BlockEditor generateBlockEditor(Block block)
    {
        BlockEditor newBlockEditor= new BlockEditor(block.table.name,block.table.getHeaders(),block.table.getDataTypes(),block.table.getFieldsAttributes()[0],block.table.getFieldsAttributes()[1]);
        for(int i=1;i<block.table.size;i++)
        newBlockEditor.table.insert(block.table.getEntry(i).getNodeArray());
        return newBlockEditor;
    }
    public Block generateBlock()
    {
        Block newBlock=Block.generateBlock(this.name,this.table.getHeaders(),this.table.getDataTypes(),this.table.getFieldsAttributes()[0],this.table.getFieldsAttributes()[1]);
        for(int i=1;i<this.table.size;i++)
        newBlock.table.insert(this.table.getEntry(i).getNodeArray());
        return newBlock;
    }
}

class scriptRunner {
    public static void main(String args[])throws IOException
    {
        System.out.println("                               |-------------------Script Block------------------------|");
        System.out.println("                               |--------- Java Embedded Database Architecture ---------|");
        System.out.println("                               |------------------Java-NOSql-EDMA----------v3.Mk-I-----|");
        Block employee_table=null;
        Block Design_table=null;
        // -----------------------------Table 1 insertion----------------------------//
        //Block employee_table=Block.generateBlock("Employee_Table",
        //(new String[]{"Emp_No.","Emp_Name","Join_Date","Design_Code","Dept","Basic","HRA","IT"}),
        //(new String[]{"string","String","Date","character","String","integer","integer","integer"}),
        //(new boolean[]{true,true,false,false,false,false,false,false}),
        //(new boolean[]{true,true,false,false,false,false,false,false}));
       // employee_table.insert(new String[]{"1001","Asish","01/04/2009","e","R&D","20000","8000","3000"});                -|
        //employee_table.insert(new String[]{"1002","Sushma","23/08/2012","c","PM","30000","12000","9000"});                |
       // employee_table.insert(new String[]{"1003","Rahul","12/11/2008","k","Acct","10000","8000","1000"});                |
      //  employee_table.insert(new String[]{"1004","Chahat","29/01/2013","r","Front Desk","12000","6000","2000"});         |
      // employee_table.insert(new String[]{"1005","Ranjan","16/07/2005","m","Engg","50000","20000","20000"});              |
       // employee_table.insert(new String[]{"1006","Suman","01/01/2000","e","Manufacturing","23000","9000","4400"});       | --Redundant scripts
       // employee_table.insert(new String[]{"1007","Tanmay","12/06/2006","c","PM","29000","12000","10000"});               |   --V5 and beyond should be able to load data from views.
       // employee_table.insert(new String[]{"1010","Rajeev","12/08/2016","m","Engg","26000","17000","10000"});             |
        //employee_table.insert(new String[]{"4009","Sanjay","13/09/2021","e","Manufacturing","18000","7500","3000"});      |
        //employee_table.insert(new String[]{"1004","Chahat","29/01/2013","r","Front Desk","12000","6000","2000"});         |
        //employee_table.insert(new String[]{"4009","Sanjay","13/09/2021","e","Manufacturing","18000","7500","3000"});     -|
        try{
        FileInputStream fos=new FileInputStream("./ConsoleFiles/Source/Block@18769467.blo");
            ObjectInputStream ois = new ObjectInputStream(fos);
                employee_table=(Block)ois.readObject();
                fos.close();
                ois.close();
        }
        catch(ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        catch(FileNotFoundException e)
        {
            e.printStackTrace();
        }
        //-------------------------Custom select code --------------------------------//
        //employee_table.select(new String[]{"Emp_No.","Emp_Name","Dept","Join_Date","HRA","IT"}); 
        
        
        // -----------------------------Table 2 insertion----------------------------//                              //Lookup table creation 
        //Block Design_table=Block.generateBlock("Design_Table",new String[]{"Design_Code","Designation","DA"},new String[]{"character","String","Integer"});
        //Design_table.table.insert(new String[]{"e","Engineer","20000"});                                                 //Lookup table insertion
        //Design_table.table.insert(new String[]{"c","Consultant","32000"});
        //Design_table.table.insert(new String[]{"k","Clerk","12000"});
        //Design_table.table.insert(new String[]{"r","Receptionist","15000"});
        //Design_table.table.insert(new String[]{"m","Manager","40000"});
        //Design_table.select();
        //Design_table.insert(new String[]{"Designation","DA"},
        //new String[]{"Intern","1000"});
        //Design_table.select();
        try{
            FileInputStream fos=new FileInputStream("./ConsoleFiles/Source/Block@57fa26b7.blo");
                ObjectInputStream ois = new ObjectInputStream(fos);
                    Design_table=(Block)ois.readObject();
                    fos.close();
                    ois.close();
            }
            catch(ClassNotFoundException e)
            {
                e.printStackTrace();
            }
            catch(FileNotFoundException e)
            {
                e.printStackTrace();
            }

        DbObj database=new DbObj("Demo_DB","./dbFiles");
        database.newBlock(employee_table);
        database.newBlock(Design_table);
        database.newFunction(new fn_get_emp_table());
        database.newFunction(new fn_getDesign_table());
        database.newFunction(new fn_EmpDesignJoin_table());
        database.showAllBlocks();
        database.showAllFunctions();
        Table join_table=(Table)database.runFunction("fn_EmpDesignJoin_table");
        System.out.println("Base Table");
        database.selectTable("employee_table");
        System.out.println("Lookup Table");
        database.selectTableView("Design_Table");
        join_table.showStructure();
        join_table.select();
        
        //BlockRegistry br=new BlockRegistry("./dbFiles/Blocks/");
        //br.registerNewBlock(employee_table);
       // br.registerNewBlock(Design_table);
        //FunctionRegistry fr=new FunctionRegistry("./dbFiles/Functions/");
        //fr.registerNewFunction(new fn_get_emp_table());
        //fr.registerNewFunction(new fn_getDesign_table());
        //fr.registerNewFunction(new fn_EmpDesignJoin_table());
        
        //br.showAllBlocks();
        //System.out.println("Output comming from function");
        //fr.showAllFunctions();
        //Table emp_Table=(Table)fr.runFunction("fn_get_emp_table", br);
        //Table design_Table=(Table)fr.runFunction("fn_getDesign_table", br);
        //Table Join_Table=(Table)fr.runFunction("fn_EmpDesignJoin_table", br);
        //emp_Table.select();
        //design_Table.select();
        //Join_Table.showStructure();
        //Join_Table.select();


        //br.getBlock("Employee_Table").table.select();
        //br.getBlock("Design_Table").table.select();   
             
        // -----------------------------Join Table logic and insertion----------------------------//                //Join table creation
        //Table joint_table = new Table
       // (new String[]{"Emp_No.","Emp_Name","Join_Date","Dept","Basic","HRA","IT","Design_Code","Designation","DA",},
        //new String[]{"string.","string","date","character","integer","integer","Integer","Character","String","Integer",});
        //String[] str=new String[employee_table.fields+Design_table.fields-1];
        //joint_table.select();
    }
}
final class BlockUnit implements Serializable
{
    protected BlockUnit prev;
    protected BlockUnit next;
    String name;
    String address;
    String blockFileName;
    private BlockUnit(String name,String address,String BlockFileName)
    {
        this.name=name;
        this.address=address+"/Blocks/";
        this.blockFileName=BlockFileName;
    }
    public Block getBlock()throws IOException
    {
        Block block;
        try{
            FileInputStream fos=new FileInputStream(this.address+this.blockFileName+".blo");
            ObjectInputStream ois = new ObjectInputStream(fos);
                block=(Block)ois.readObject();
                ois.close();
                return block;
            }
            catch(FileNotFoundException e)
            {
                System.out.println("Error: File not found");
            }
            catch(ClassNotFoundException e)
            {
                System.out.println("Error: Incompatible file");
            }
            catch(InvalidClassException e)
            {
                System.out.println("Error: File version incompatible");
            }
            catch(StreamCorruptedException e)
            {
                System.out.println("Error: Corrupted file");
            }
            catch(ClassCastException e)
            {
                System.out.println("Error: failed to serialise object file");
            }
        return null;
    }
    public void putBlock(Block block)throws IOException
    {
        this.blockFileName= block.getClass().getName()+"@"+block.name;
        FileOutputStream fos=new FileOutputStream(this.address+this.blockFileName+".blo");
        ObjectOutputStream oos=new ObjectOutputStream(fos);
        oos.writeObject(block);
        oos.close();
        fos.close();
    }
    public static BlockUnit registerBlock(Block block,String Address)throws IOException
    {
        BlockUnit bUnit=new BlockUnit(block.name, Address, block.getClass().getName()+"@"+block.name);
        FileOutputStream fos=new FileOutputStream(bUnit.address+bUnit.blockFileName+".blo");
        ObjectOutputStream oos=new ObjectOutputStream(fos);
        oos.writeObject(block);
        oos.close();
        fos.close();
        return bUnit;
    }    

}
final class BlockRegistry implements Serializable
{
    private BlockUnit unitHead;
    private BlockUnit unitTail;
    private String address;
    BlockRegistry(String address)throws IOException
    {
        this.address=address;
    }
    public void registerNewBlock(Block block)throws IOException
    {
        BlockUnit newBlockUnit=BlockUnit.registerBlock(block,this.address);
        if(unitHead==null)
        {
            this.unitHead=newBlockUnit;
            this.unitTail=newBlockUnit;
            return;
        }
        
        this.unitTail.next=newBlockUnit;
        newBlockUnit.prev=this.unitTail;
        this.unitTail=newBlockUnit;
    }
    public Block getBlock(String name)throws IOException
    {
        BlockUnit bUnit=this.unitHead;
        while(bUnit!=null)
        {
            if(bUnit.name.equalsIgnoreCase(name))return bUnit.getBlock();
            bUnit=bUnit.next;
        }
        System.out.println("Error: Could not find block");
        return null;
    }
    public void updateBlock(String name,Block block)throws IOException
    {
        BlockUnit bUnit=this.unitHead;
        while(bUnit!=null)
        {
            if(bUnit.name.equalsIgnoreCase(name))
            {
                bUnit.putBlock(block);
                break;
            }
            bUnit=bUnit.next;
        }
        if(bUnit==null) System.out.println("Could not find block");     
    }
    public void removeBlock(String name)
    {
        BlockUnit bUnit=this.unitHead;
        if(bUnit.name.equalsIgnoreCase(name) && bUnit==this.unitHead)
        {
            try{
            unitHead=bUnit.next;
            bUnit.prev=null;
        }
        catch(NullPointerException e){}
        System.gc();
        }
        while(bUnit!=null)
        {
            if(bUnit.name.equalsIgnoreCase(name) && bUnit!=this.unitHead)
            {
                try{
                bUnit.prev.next=bUnit.next;
                bUnit.next.prev=bUnit.prev;
                }
                catch(NullPointerException e){}
                System.gc();
            }
            bUnit=bUnit.next;
        }
        System.out.println("Error: Could not find block");

    }
    public void showAllBlocks()throws IOException
    {
        System.out.println();
        System.out.printf("%-15s   ","---------------");
        System.out.printf("%-15s   ","---------------");
        System.out.printf("%-30s   ","-------------------------------");
        System.out.printf("%-15s   ","---------------");
        System.out.println();
        BlockUnit bUnit=this.unitHead;
        System.out.printf("%-15s ||","BLOCK NAME");
        System.out.printf("%-15s ||","TABLE NAME");
        System.out.printf("%-30s ||","BLOCK ADDRESS");
        System.out.printf("%-15s ||","BLOCK FILE NAME");
        System.out.println();
        System.out.printf("%-15s   ","---------------");
        System.out.printf("%-15s   ","---------------");
        System.out.printf("%-30s   ","-------------------------------");
        System.out.printf("%-15s   ","---------------");
        System.out.println();
        while(bUnit!=null)
        {
            
             System.out.printf("%-15s ||",bUnit.name);
             System.out.printf("%-15s ||",bUnit.getBlock().table.name);
             System.out.printf("%-30s ||",bUnit.address);
             System.out.printf("%-15s ||",bUnit.blockFileName);
             System.out.println();
             bUnit=bUnit.next;     
            
        }        
        System.out.printf("%-15s   ","---------------");
        System.out.printf("%-15s   ","---------------");
        System.out.printf("%-30s   ","-------------------------------");
        System.out.printf("%-15s   ","---------------");
        System.out.println();
    }    
}

interface Function extends Serializable
{
    String name=null;
    String Return_Type=null;
    String function_type=null;
    public abstract Object run(BlockRegistry block_Registry)throws IOException;
    public String getName();
    public String getReturnType();
    public String getFunctionType();
    
}

final class fn_get_emp_table implements Function
{
    String name="fn_get_emp_table";
    String Return_Type="Table";
    String function_type="Output";
    public Object run(BlockRegistry block_registry)throws IOException
    {
                      
        return block_registry.getBlock("Employee_table").table;
    }
    public String getName()
    {
        return this.name;
    } 
    public final String getReturnType()
    {
        return this.Return_Type;
    }
    public final String getFunctionType()
    {
        return this.function_type;
    }
    
}
final class fn_getDesign_table implements Function
{
    String name="fn_getDesign_table";
    String Return_Type="Table";
    String function_type="Output";
    public Object run(BlockRegistry block_registry)throws IOException
    {
                      
        return block_registry.getBlock("Design_Table").table;
    }
    public String getName()
    {
        return this.name;
    } 
    public final String getReturnType()
    {
        return this.Return_Type;
    }
    public final String getFunctionType()
    {
        return this.function_type;
    }
    
}

final class fn_EmpDesignJoin_table implements Function
{
    String name="fn_EmpDesignJoin_table";
    String Return_Type="Table";
    String function_type="Output";
    public Object run(BlockRegistry block_registry)throws IOException
    {
        Table emp_table=  block_registry.getBlock("Employee_Table").table;                    
        Table design_table=block_registry.getBlock("Design_Table").table;
        Table join_table=Table.generateTable("join_table",
        new String[]{"Emp_No.","Emp_Name","Join_Date","Designation","Dept","Basic","HRA","IT","DA","Total"},
        new String[]{"String","String","Date","String","String","Integer","Integer","Integer","Integer","Integer"},
        new boolean[]{true,true,false,false,false,false,false,false,false,false},
        new boolean[]{true,true,false,false,false,false,false,false,false,false});
        for(int i=1;i<=emp_table.size;i++)
        {
        try{
        join_table.insert(
            new String[]{
                emp_table.getEntry(i).getData(emp_table.getSequence(new String[]{"Emp_No."})[0]),
                emp_table.getEntry(i).getData(emp_table.getSequence(new String[]{"Emp_Name"})[0]),
                emp_table.getEntry(i).getData(emp_table.getSequence(new String[]{"Join_Date"})[0]),
                design_table.getEntry(design_table.where(new String[][]{new String[]{"Design_Code","String.equals",emp_table.getEntry(i).getData(emp_table.getSequence(new String[]{"Design_Code"})[0])}})[1][0]).getData(1),
                emp_table.getEntry(i).getData(emp_table.getSequence(new String[]{"Dept"})[0]),
                emp_table.getEntry(i).getData(emp_table.getSequence(new String[]{"Basic"})[0]),
                emp_table.getEntry(i).getData(emp_table.getSequence(new String[]{"HRA"})[0]),
                emp_table.getEntry(i).getData(emp_table.getSequence(new String[]{"IT"})[0]),
                design_table.getEntry(design_table.where(new String[][]{new String[]{"Design_Code","String.equals",emp_table.getEntry(i).getData(3)}})[1][0]).getData(2),
                Integer.toString(
                    Integer.parseInt(emp_table.getEntry(i).getData(emp_table.getSequence(new String[]{"Basic"})[0]))+
                    Integer.parseInt(emp_table.getEntry(i).getData(emp_table.getSequence(new String[]{"HRA"})[0]))+
                    Integer.parseInt(emp_table.getEntry(i).getData(emp_table.getSequence(new String[]{"IT"})[0]))+
                    Integer.parseInt(design_table.getEntry(design_table.where(new String[][]{new String[]{"Design_Code","String.equals",emp_table.getEntry(i).getData(emp_table.getSequence(new String[]{"Design_Code"})[0])}})[1][0]).getData(2)))
            }
        );        
        }
        catch(Exception e)
        {System.out.println("Insertion for record number :"+i+" failed");}
        }
        return join_table;
    }
    public String getName()
    {
        return this.name;
    } 
    public final String getReturnType()
    {
        return this.Return_Type;
    }
    public final String getFunctionType()
    {
        return this.function_type;
    }
    
}


final class FunctionUnit implements Serializable
{
    protected FunctionUnit prev;
    protected FunctionUnit next;
    String name;
    String function_type;
    String address;
    String return_type;
    String functionFileName;
    private FunctionUnit(String name,String function_type,String address, String functionFileName,String return_type)
    {
        this.name=name;
        this.function_type=function_type;
        this.address=address+"/Functions/";
        this.functionFileName=functionFileName;
        this.return_type=return_type;
    }
    public static FunctionUnit registerFunction(Function function, String address)throws IOException
    {
        FunctionUnit newFunctionUnit=new FunctionUnit(function.getName(),function.getFunctionType(),address,function.toString(),function.getReturnType());
        FileOutputStream fos=new FileOutputStream(newFunctionUnit.address+newFunctionUnit.functionFileName+".fno");
        ObjectOutputStream oos=new ObjectOutputStream(fos);
        oos.writeObject(function);
        oos.close();
        fos.close();
        return newFunctionUnit;
    }
    public Object functionReturn(BlockRegistry br)throws IOException
    {
        Function function;
        try{
            FileInputStream fos=new FileInputStream(this.address+this.functionFileName+".fno");
            ObjectInputStream ois = new ObjectInputStream(fos);
                function=(Function)ois.readObject();
                ois.close();
                return function.run(br);
            }
            catch(FileNotFoundException e)
            {
                System.out.println("Error: File not found");
            }
            catch(ClassNotFoundException e)
            {
                System.out.println("Error: Incompatible file");
            }
            catch(InvalidClassException e)
            {
                System.out.println("Error: File version incompatible");
            }
            catch(StreamCorruptedException e)
            {
                System.out.println("Error: Corrupted file");
            }
            catch(ClassCastException e)
            {
                System.out.println("Error: failed to serialise object file");
            }
        return null;
    }
    
}
final class FunctionRegistry implements Serializable
{
    private FunctionUnit unitHead;
    private FunctionUnit unitTail;
    private String address;
    FunctionRegistry(String address)throws IOException
    {
        this.address=address;
    }
    public void registerNewFunction(Function function)throws IOException
    {
        FunctionUnit functionUnit=FunctionUnit.registerFunction(function,this.address);
        if(this.unitHead==null)
        {
            this.unitHead=functionUnit;
            this.unitTail=functionUnit;
            return;
        }
        this.unitTail.next=functionUnit;
        functionUnit.prev=this.unitTail;
        this.unitTail=functionUnit;
    }
    public Object runFunction(String name,BlockRegistry br)throws IOException
    {
        FunctionUnit fn=this.unitHead;
        while(fn!=null)
        {
            if(fn.name.equalsIgnoreCase(name))return fn.functionReturn(br);
            fn=fn.next;
        }
        System.out.println("Error: Could not find Function");
        return null;
    }
    public void removeFunction(String name)
    {
        FunctionUnit fn=this.unitHead;
        while(fn!=null)
        {
            if(fn.name.equalsIgnoreCase(name))
            {
                fn.prev.next=fn.next;
                fn.next.prev=fn.prev;
                System.gc();
            }
            fn=fn.next;
        }
    }
    public void showAllFunctions()
    {
        System.out.println();
        System.out.printf("%-30s   ","-------------------------------");
        System.out.printf("%-15s   ","------------");
        System.out.printf("%-12s   ","------------");
        System.out.printf("%-30s   ","-------------------------------");
        System.out.printf("%-30s   ","-------------------------------");
        System.out.println();
        FunctionUnit fn=this.unitHead;
        System.out.printf("%-30s ||","FUNCTION NAME");
        System.out.printf("%-15s ||","FUNCTION TYPE");
        System.out.printf("%-12s ||","RETURN TYPE");
        System.out.printf("%-30s ||","FUNCTION ADDRESS");
        System.out.printf("%-30s ||","FUNCTION FILE NAME");
        System.out.println();
        System.out.printf("%-30s   ","-------------------------------");
        System.out.printf("%-15s   ","------------");
        System.out.printf("%-10s   ","------------");
        System.out.printf("%-30s   ","-------------------------------");
        System.out.printf("%-30s   ","-------------------------------");
        System.out.println();
        while(fn!=null)
        {
            
             System.out.printf("%-30s ||",fn.name);
             System.out.printf("%-15s ||",fn.function_type);
             System.out.printf("%-12s ||",fn.return_type);
             System.out.printf("%-30s ||",fn.address);
             System.out.printf("%-30s ||",fn.functionFileName);
             System.out.println();
             fn=fn.next;     
            
        }        
        System.out.printf("%-30s   ","-------------------------------");
        System.out.printf("%-15s   ","------------");
        System.out.printf("%-12s   ","------------");
        System.out.printf("%-30s   ","-------------------------------");
        System.out.printf("%-30s   ","-------------------------------");
        System.out.println();        
    }

}
final class DbObj implements Serializable
{
    String Address;
    String db_name;
    FunctionRegistry fnRegistry;
    BlockRegistry blRegistry;
    DbObj(String db_name,String address)throws IOException
    {
        this.db_name=db_name;
        this.Address=address;
        File fl=new File(this.Address+"/Blocks");
        fl.mkdir();
        fl=new File(this.Address+"/Functions");
        fl.mkdir();
        fl=new File(this.Address+"/Registries");
        fl.mkdir();
        this.fnRegistry=new FunctionRegistry(address);
        this.blRegistry=new BlockRegistry(address);
        FileOutputStream fosdb=new FileOutputStream(address+"/"+db_name+".dbo");
        ObjectOutput Oosdb =new ObjectOutputStream(fosdb);
        Oosdb.writeObject(this);
        FileOutputStream fosfn=new FileOutputStream(address+"/Registries/"+this.db_name+".blr");
        ObjectOutput Oosfn =new ObjectOutputStream(fosfn);
        Oosfn.writeObject(fnRegistry);
        FileOutputStream fosbl=new FileOutputStream(address+"/Registries/"+this.db_name+".fnr");
        ObjectOutputStream Oosbl= new ObjectOutputStream(fosbl);
        Oosbl.writeObject(blRegistry);
        fosdb.close();
        Oosdb.close();
        fosbl.close();
        Oosbl.close();
        fosfn.close();
        Oosfn.close();
    }
    public void newBlock(Block block)throws IOException
    {
        blRegistry.registerNewBlock(block);
        FileOutputStream fosbl=new FileOutputStream(Address+"/Registries/"+this.db_name+".blr");
        ObjectOutputStream Oosbl= new ObjectOutputStream(fosbl);
        FileOutputStream fosdb=new FileOutputStream(Address+"/"+db_name+".dbo");
        ObjectOutput Oosdb =new ObjectOutputStream(fosdb);
        Oosdb.writeObject(this);
        Oosbl.writeObject(blRegistry);
        fosbl.close();
        Oosbl.close();
        fosdb.close();
        Oosdb.close();
    }
    public void newFunction(Function function)throws IOException
    {
        fnRegistry.registerNewFunction(function);
        FileOutputStream fosfn=new FileOutputStream(Address+"/Registries/"+this.db_name+".fnr");
        ObjectOutput Oosfn =new ObjectOutputStream(fosfn);
        Oosfn.writeObject(fnRegistry);
        FileOutputStream fosdb=new FileOutputStream(Address+"/"+db_name+".dbo");
        ObjectOutput Oosdb =new ObjectOutputStream(fosdb);
        Oosdb.writeObject(this);        
        fosfn.close();
        Oosfn.close();
        fosdb.close();
        Oosdb.close();
    }
    public Block getBlock(String blockName)throws IOException
    {
        return blRegistry.getBlock(blockName);
    }
    public void removeBlock(String blockName)
    {
        blRegistry.removeBlock(blockName);
    }
    public void updateBlock(String name,Block block)throws IOException
    {
        blRegistry.updateBlock(name, block);
    }
    public void selectTable(String table_name)throws IOException
    {
        this.blRegistry.getBlock(table_name).table.select();
    }
    public void selectTableView(String table_name)throws IOException
    {
        String[][] view=this.blRegistry.getBlock(table_name).table.getView();
        System.out.print("---View- "); 
            for(int j=0;j<view[0].length;j++)
            {
                
                System.out.printf(" %-20s","-------------------");
            }
            System.out.println();
        for(int i=0;i<view.length;i++)                                                      
         
        {
            
            System.out.print(" ");
            System.out.printf("|%-8s",Integer.toString(i)+"|");
            for(int j=0;j<view[i].length;j++)
            {
                try{
                System.out.printf("|  %-15s|  ",view[i][j]);
                }
                catch(NullPointerException e){}
            }
            System.out.println();
            System.out.print("-------- ");
            for(int j=0;j<view[i].length;j++)
            {
                
                System.out.printf(" %-20s","-------------------");
            }
            System.out.println();

            

        }
        System.out.println();
        System.out.println("View length : "+Integer.toString(view.length));        
    }
    public Object runFunction(String fnName)throws IOException
    {
        return fnRegistry.runFunction(fnName, this.blRegistry);
    }
    public void showAllBlocks()throws IOException
    {
        blRegistry.showAllBlocks();
    }
    public void showAllFunctions()throws IOException
    {
        fnRegistry.showAllFunctions();
    }
    public void commit()throws IOException
    {
        FileOutputStream fosdb=new FileOutputStream(this.Address+"/"+db_name+".dbo");
        ObjectOutput Oosdb =new ObjectOutputStream(fosdb);
        Oosdb.writeObject(this);
        FileOutputStream fosfn=new FileOutputStream(this.Address+"/Registries/"+this.db_name+".blr");
        ObjectOutput Oosfn =new ObjectOutputStream(fosfn);
        Oosfn.writeObject(this.fnRegistry);
        FileOutputStream fosbl=new FileOutputStream(this.Address+"/Registries/"+this.db_name+".fnr");
        ObjectOutputStream Oosbl= new ObjectOutputStream(fosbl);
        Oosbl.writeObject(this.blRegistry);
        fosdb.close();
        Oosdb.close();
        fosbl.close();
        Oosbl.close();
        fosfn.close();
        Oosfn.close();        
    }

}

class Console {
	private static InputStreamReader i=new InputStreamReader(System.in);
	private static BufferedReader b= new BufferedReader(i);
    private static BlockEditor blockEditor;
	private static Table table;
    private static String[][] view;
	public static void CreateBlockEditor() throws Exception
	{
		String Input;
        String name;
		int col;
		System.out.print("Number of fields? :");
		col=Integer.parseInt(b.readLine());
		String[][] Headers=new String[2][col];
        System.out.print("Table Name >");
    	name=b.readLine();
		System.out.println("Provide headers with datatype. Separete them with space");
		for(int i=0;i<col;i++)
		{
		System.out.print(" Header DataType >");
    	Input=b.readLine();
    	Headers[0][i]=Input.split(" ")[0];
    	Headers[1][i]=Input.split(" ")[1];
		}
		blockEditor = BlockEditor.generateBlockEditor(name,Headers[0],Headers[1]);
        table=blockEditor.table;
		System.out.println("Block generation Successfull. Table loaded to Console");
	}
    public static void createBlockEditorAdvance()throws IOException
    {
        String name;
		int col;
		System.out.print("Number of fields? :");
		col=Integer.parseInt(b.readLine());
		String[][] Headers=new String[2][col];
        boolean[][] noNullDupl= new boolean[2][col];
        System.out.print("Table Name >");
    	name=b.readLine();
		System.out.println("Provide headers with datatype. Separete them with space");
		for(int i=0;i<col;i++)
		{
		System.out.print(" Header_"+Integer.toString(i+1)+" name >");
    	Headers[0][i]=b.readLine().trim();
        System.out.print(" Datatype >");
    	Headers[1][i]=b.readLine().trim();
        System.out.print(" enable No Nulls Y/N>");
        if(b.readLine().trim().equalsIgnoreCase("Y"))noNullDupl[0][i]=true;
        System.out.print(" enable No Dulicate Y/N>");
        if(b.readLine().trim().equalsIgnoreCase("Y"))noNullDupl[1][i]=true;
        System.out.println();
		}
		blockEditor = BlockEditor.generateBlockEditor(name,Headers[0],Headers[1],noNullDupl[0],noNullDupl[1]);
        table=blockEditor.table;
		System.out.println("BlockEditor generation Successfull. Table loaded to Console");
	}        
    public static void commitBlock()
    {
        blockEditor=blockEditor.commit();
        table=blockEditor.table;
        System.out.println("    Block Commited");
    }
    public static void rollbackBlock()
    {
        if(blockEditor.prev==null)
        {
            System.out.println("No previous version found");
            return;
        }
        blockEditor=blockEditor.rollback();
        table=blockEditor.table;
        System.out.println("    Block rolled back");
    }
    public static void customFormInsert(String[] headers)throws Exception
    {
        String[] values=new String[headers.length];
        System.out.println("Input values into the form below");
        for(int i=0;i<headers.length;i++)
        {
            System.out.printf("%15s",headers[i]+" : ");
            values[i]=b.readLine();
        }
        System.out.println(table.insert(headers,values));   
    }
    public static void formInput()throws Exception
    {
        String[] values=new String[table.getHeaders().length];
        System.out.println("Input values into the form below");
        
        for(int i=0;i<table.fields;i++)
        {
            String[] headers=table.getHeaders();
            System.out.printf("%15s",headers[i]+" : ");
            values[i]=b.readLine();
        }
        System.out.println(table.insert(values));

    }
    public static void updateTable()throws Exception
    {
        String[] values=new String[table.getHeaders().length];
        System.out.println("Input values into the form below ");
        String[] headers=table.getHeaders();
        for(int i=0;i<table.fields;i++)
        {
            
            System.out.printf("%15s",headers[i]+" : ");
            values[i]=b.readLine();
        }
        
        System.out.print("Give table index values >");
        String input=b.readLine();
        int[] index=new int[input.split(" ").length];
        for(int j=0;j<input.split(" ").length;j++)
        index[j]=Integer.parseInt(input.split(" ")[j]);
        System.out.println(table.updateTable(headers,values,index));
    }
    public static void updateTableWhere()throws Exception
    {
        String[] values=new String[table.getHeaders().length];
        System.out.println("Input values into the form below ");
        String[] headers=table.getHeaders();
        for(int i=0;i<table.fields;i++)
        {
            
            System.out.printf("%15s",headers[i]+" : ");
            values[i]=b.readLine();
        }
        System.out.print(" Enter number of where clauses :");
        int n=Integer.parseInt(b.readLine());
        String[][] clause=new String[n][3];
        for(int i=0;i<clause.length;i++)
        {
            System.out.println("Input for Clause ["+Integer.toString(i)+"]");
            System.out.printf("%15s","Header :");
            clause[i][0]=b.readLine().trim();
            System.out.printf("%15s","Operator:");
            clause[i][1]=b.readLine().trim();
            System.out.printf("%15s","Value :");
            clause[i][2]=b.readLine().trim();
        }
        System.out.print(" Enter joiner type (AND/OR) :");
        String input=b.readLine();
        int[][] index=table.where(clause);
        if(input.equalsIgnoreCase("OR"))
        System.out.println(table.updateTable(headers,values,index[0]));
        if(input.equalsIgnoreCase("AND"))
        System.out.println(table.updateTable(headers,values,index[1]));          
    }
    public static void customUpdate()throws Exception
    {
        System.out.print("Input header names for updation >");
        String[] headers=b.readLine().split(" ");
        String[] values=new String[headers.length];
        System.out.println("Input values into the form below ");
        for(int i=0;i<headers.length;i++)
        {
            
            System.out.printf("%15s",headers[i]+" : ");
            values[i]=b.readLine();
        }
        
        System.out.print("Give table index values >");
        String input=b.readLine();
        int[] index=new int[input.split(" ").length];
        for(int j=0;j<input.split(" ").length;j++)
        index[j]=Integer.parseInt(input.split(" ")[j]);
        System.out.println(table.updateTable(headers,values,index));
    }
    public static void customUpdateWhere()throws Exception
    {
        System.out.print("Input header names for updation >");
        String[] headers=b.readLine().split(" ");
        String[] values=new String[headers.length];
        System.out.println("Input values into the form below ");
        for(int i=0;i<headers.length;i++)
        {
            
            System.out.printf("%15s",headers[i]+" : ");
            values[i]=b.readLine();
        }
        System.out.print(" Enter number of where clauses :");
        int n=Integer.parseInt(b.readLine());
        String[][] clause=new String[n][3];
        for(int i=0;i<clause.length;i++)
        {
            System.out.println("Input for Clause ["+Integer.toString(i)+"]");
            System.out.printf("%15s","Header :");
            clause[i][0]=b.readLine().trim();
            System.out.printf("%15s","Operator:");
            clause[i][1]=b.readLine().trim();
            System.out.printf("%15s","Value :");
            clause[i][2]=b.readLine().trim();
        }
        System.out.print(" Enter joiner type (AND/OR) :");
        String input=b.readLine();
        int[][] index=table.where(clause);
        if(input.equalsIgnoreCase("OR"))
        System.out.println(table.updateTable(headers,values,index[0]));
        if(input.equalsIgnoreCase("AND"))
        System.out.println(table.updateTable(headers,values,index[1]));

    }
    public static void deleteFromTable()throws Exception
    {
        System.out.print("Indexes >");
        String Input=b.readLine();
        int[] arr=new int[Input.split(" ").length];
        for(int i=0;i<Input.split(" ").length;i++)
        arr[i]=Integer.parseInt(Input.split(" ")[i]);

        System.out.println(table.delete(arr));
    }
    public static void deleteFromTableWhere()throws Exception
    {
        
        System.out.print(" Enter number of where clauses :");
        int n=Integer.parseInt(b.readLine());
        String[][] clause=new String[n][3];
        for(int i=0;i<clause.length;i++)
        {
            System.out.println("Input for Clause ["+Integer.toString(i)+"]");
            System.out.printf("%15s","Header :");
            clause[i][0]=b.readLine().trim();
            System.out.printf("%15s","Operator:");
            clause[i][1]=b.readLine().trim();
            System.out.printf("%15s","Value :");
            clause[i][2]=b.readLine().trim();
        }
        System.out.print(" Enter joiner type (AND/OR) :");
        String input=b.readLine();
        int[][] index=table.where(clause);
        if(input.equalsIgnoreCase("OR"))
        System.out.println(table.delete(index[0]));
        if(input.equalsIgnoreCase("AND"))
        System.out.println(table.delete(index[1]));
    }
    public static void showView()throws Exception                                                 //Display view
    {
        System.out.print("---View- "); 
            for(int j=0;j<view[0].length;j++)
            {
                
                System.out.printf(" %-20s","-------------------");
            }
            System.out.println();
        for(int i=0;i<view.length;i++)                                                      
         
        {
            
            System.out.print(" ");
            System.out.printf("|%-8s",Integer.toString(i)+"|");
            for(int j=0;j<view[i].length;j++)
            {
                try{
                System.out.printf("|  %-15s|  ",view[i][j]);
                }
                catch(NullPointerException e){}
            }
            System.out.println();
            System.out.print("-------- ");
            for(int j=0;j<view[i].length;j++)
            {
                
                System.out.printf(" %-20s","-------------------");
            }
            System.out.println();

            

        }
        System.out.println();
        System.out.println("View length : "+Integer.toString(view.length));


    }
    public static void selectWhere()throws Exception
    {
        System.out.print(" Enter number of where clauses :");
        int n=Integer.parseInt(b.readLine());
        String[][] clause=new String[n][3];
        for(int i=0;i<clause.length;i++)
        {
            System.out.println("Input for Clause ["+Integer.toString(i)+"]");
            System.out.printf("%15s","Header :");
            clause[i][0]=b.readLine().trim();
            System.out.printf("%15s","Operator:");
            clause[i][1]=b.readLine().trim();
            System.out.printf("%15s","Value :");
            clause[i][2]=b.readLine().trim();
        }
        System.out.print(" Enter joiner type (AND/OR) :");
        String input=b.readLine();
        int[][] index=table.where(clause);
        if(input.equalsIgnoreCase("OR"))
        table.select(index[0]);
        if(input.equalsIgnoreCase("AND"))
        table.select(index[1]);
        
    }
    public static void getIndexWhere()throws Exception
    {
        System.out.print(" Enter number of where clauses :");
        int n=Integer.parseInt(b.readLine());
        
        String[][] clause=new String[n][3];
        for(int i=0;i<clause.length;i++)
        {
            System.out.println("Input for Clause ["+Integer.toString(i)+"]");
            System.out.printf("%15s","Header :");
            clause[i][0]=b.readLine().trim();
            System.out.printf("%15s","Operator:");
            clause[i][1]=b.readLine().trim();
            System.out.printf("%15s","Value :");
            clause[i][2]=b.readLine().trim();
        }
        System.out.println(" Enter joiner type (AND/OR) :");
        String input=b.readLine();
        int[][] index=table.where(clause);
        if(input.equalsIgnoreCase("OR"))
        {    
            System.out.print("  Indexes with the following data :");
            for(int i=0;i<index[0].length;i++)
            System.out.print(Integer.toString(index[0][i])+"   ");
        }
        if(input.equalsIgnoreCase("AND"))
        {    
            System.out.print("  Indexes with the following data :");
            for(int i=0;i<index[1].length;i++)
            System.out.print(Integer.toString(index[1][i])+"   ");
        }
    }
    public static void LoadEditorFromBlock(String filename)throws Exception
    {
        try{
        FileInputStream fos=new FileInputStream("./ConsoleFiles/Blocks/"+filename+".blo");
        ObjectInputStream ois = new ObjectInputStream(fos);
            blockEditor=BlockEditor.generateBlockEditor((Block)ois.readObject());
            System.out.println("Block Editor Loaded");
            table=blockEditor.table;
            System.out.println("Table Loaded");
            ois.close();        
        }
        catch(FileNotFoundException e)
        {
            System.out.println("Error: File not found");
        }
        catch(ClassNotFoundException e)
        {
            System.out.println("Error: Incompatible file");
        }
        catch(InvalidClassException e)
        {
            System.out.println("Error: File version incompatible");
        }
        catch(StreamCorruptedException e)
        {
            System.out.println("Error: Corrupted file");
        }
        catch(ClassCastException e)
        {
            System.out.println("Error: failed to serialise object file");
        }
    }
    public static void LoadEditor(String filename)throws Exception
    {
        try{
        FileInputStream fos=new FileInputStream("./ConsoleFiles/BlockEditor/"+filename+".bleo");
        ObjectInputStream ois = new ObjectInputStream(fos);
            blockEditor=(BlockEditor)ois.readObject();
            System.out.println("Block Editor Loaded");
            table=blockEditor.table;
            System.out.println("Table Loaded");
            ois.close();        
        }
        catch(FileNotFoundException e)
        {
            System.out.println("Error: File not found");
        }
        catch(ClassNotFoundException e)
        {
            System.out.println("Error: Incompatible file");
        }
        catch(InvalidClassException e)
        {
            System.out.println("Error: File version incompatible");
        }
        catch(StreamCorruptedException e)
        {
            System.out.println("Error: Corrupted file");
        }
        catch(ClassCastException e)
        {
            System.out.println("Error: failed to serialise object file");
        }
    }
    public static void loadBlockFromFile(String filename)throws Exception
    {
        try{
            FileInputStream fos=new FileInputStream("./ConsoleFiles/Source/"+filename+".blo");
            ObjectInputStream ois = new ObjectInputStream(fos);
                blockEditor=BlockEditor.generateBlockEditor((Block)ois.readObject());
                System.out.println("Block Loaded");
                table=blockEditor.table;
                System.out.println("Table Loaded");
                ois.close();        
            }
            catch(FileNotFoundException e)
            {
                System.out.println("Error: File not found");
            }
            catch(ClassNotFoundException e)
            {
                System.out.println("Error: Incompatible file");
            }
            catch(InvalidClassException e)
            {
                System.out.println("Error: File version incompatible");
            }
            catch(StreamCorruptedException e)
            {
                System.out.println("Error: Corrupted file");
            }
            catch(ClassCastException e)
            {
                System.out.println("Error: failed to serialise object file");
            }
    }        
    public static void LoadViewFromFile(String filename)throws Exception
    {
        try{
        FileInputStream fos=new FileInputStream("./ConsoleFiles/Source/"+filename+".view");
        ObjectInputStream ois = new ObjectInputStream(fos);
            view=(String[][])ois.readObject();
            System.out.println("View Loaded");
            ois.close();        
        }
        catch(FileNotFoundException e)
        {
            System.out.println(" Error: File not found");
        }
        catch(ClassNotFoundException e)
        {
            System.out.println("Error: Incompatible file");
        }
        catch(InvalidClassException e)
        {
            System.out.println("Error: File version incompatible");
        }
        catch(StreamCorruptedException e)
        {
            System.out.println("Error: Corrupted File");
        }
    }
    public static void LoadViewFromTable()
    {
        view=table.getView();
    }
    public static void xportTable()throws Exception
    { 
        File directory=new File("./ConsoleFiles/Tables/",table.toString()+".tbo");
        FileOutputStream fos=new FileOutputStream(directory);
        ObjectOutputStream oos=new ObjectOutputStream(fos);
        oos.writeObject(table);
        System.out.println("   Table exported to ./ConsoleFiles/Tables/ directory as "+table.toString()+".tbo");
        //System.out.println("SerialVersionUID :"+Long.toString(Table.SerialVersionUID));
        fos.close();
        oos.close();

    }
    public static void xportView()throws Exception
    { 
        FileOutputStream fos=new FileOutputStream("./ConsoleFiles/Views/"+table.toString()+".view");
        ObjectOutputStream oos=new ObjectOutputStream(fos);
        oos.writeObject(view);
        System.out.println("    View exported to ./ConsoleFiles/Views/ directory as "+table.toString()+".view");
        //System.out.println("SerialVersionUID :"+Long.toString(Table.SerialVersionUID));
        fos.close();
        oos.close();

    }
    public static void saveBlockEditor()throws Exception
    {
        FileOutputStream fos=new FileOutputStream("./ConsoleFiles/BlockEditor/"+blockEditor.toString()+".bleo");
        ObjectOutputStream oos=new ObjectOutputStream(fos);
        oos.writeObject(blockEditor);
        System.out.println("    BlockEditor Saved to ./ConsoleFiles/BlockEditor/ directory as "+blockEditor.toString()+".bleo");
        fos.close();
        oos.close();        
    }
    public static void createBlockFromEditor()throws Exception
    {
        Block block=blockEditor.generateBlock();
        FileOutputStream fos=new FileOutputStream("./ConsoleFiles/BlockS/"+block.toString()+".blo");
        ObjectOutputStream oos=new ObjectOutputStream(fos);
        oos.writeObject(block);
        System.out.println("    Block Saved to ./ConsoleFiles/Blocks/ directory as "+block.toString()+".blo");
        fos.close();
        oos.close();
    }
    public static void printLogo()
    {
        System.out.println();
        System.out.println();
        System.out.println("               /-------------------------/                                                                                                               ");
        System.out.println("              /   ______       ______   /                                 ");
        System.out.println("             /  /       /    /       / /-----------------------------------/");
        System.out.println("            /  /       /    /       /___________ ________ ____ _________  /");
        System.out.println("           /  /       /    /       //   ___    //       //   //   _____/ /");
        System.out.println("          /  /       /____/       //   /__/   //       //   //   /____  / ");
        System.out.println("         /  /                ____//_____     //   //  //   //____    / /  ");
        System.out.println("        /  /        ____    /          //   //   //       /_____/   / /   ");
        System.out.println("       /  /       /     /  /    ______//___//___//_______//________/ /------------------------------------::HANS ENTERPRISES PVT LTD::--------------/  ");
        System.out.println("      /  /       /     /  /    /__     ________ ____ _____________ __________ _________ __________ _________ ____ _________ __________ _________   /  ");
        System.out.println("     /  /       /     /  /       /    /       //   //____    ____//   ______//   _    //   __    //   _    //   //   _____//   ______//   _____/  /   ");
        System.out.println("    /  /_______/     /__/    ___/    /       //   /     /   /    /   /___   /   //   //   /_/   //   //   //   //   /____ /   /___   /   /____   /    ");
        System.out.println("   /                   /    /______ /   //  //   /     /   /    /   ____/  /   _  __//   ______//   _  __//   //____    //   ____/  /____     / /     ");                           
        System.out.println("  /----------------/  /           //   //       /     /   /    /   /_____ /   //   //   /      /   //   //   /_____/   //   /_____ ______/   / /      ");
        System.out.println("                  /  /___________//___//_______/     /___/    /_________//___//___//___/      /___//___//___//________//_________//_________/ /       ");
        System.out.println("                 /---------------------------------------------------------------------------------------------------------------------------/        ");
        System.out.println("                /----------------------------------------------------------------------------------/Author : JK MOHAN KAPUR HANSDAH/--------/         ");
        System.out.println("                                                                                                              ");
        System.out.println("                                                                                                 ");
        System.out.println("                                                   -:EMBEDDED DATABASE APPLICATION:-");
        System.out.println("                                              -:Java Embedded Data Management Architecture:-       Console Draft-II");
        System.out.println("                                                          -:J-NOSql-EDMA:-                            -:v3.Mk-I:-");
        System.out.println();
        System.out.println("                                                                                                                                      -------------");
        System.out.println("                                                                                                                                      :Compiled in:");
        System.out.println("                                                                                                                                      -------------");
        System.out.println("                                                                                                                                java 12.0.2 2019-07-16");
        System.out.println("                                                                                                     Java(TM) SE Runtime Environment (build 12.0.2+10)");
        System.out.println("                                                                               Java HotSpot(TM) 64-Bit Server VM (build 12.0.2+10, mixed mode, sharing");
        System.out.println();
        System.out.println();
        System.out.println();
    }
    public static void showCatalog()
    {
        
        System.out.println("|============================================================================================================================================================|");
        System.out.println("|:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: Commands Catalog ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::|");
        System.out.println("|============================================================================================================================================================|");
        System.out.println("|________________________________________________________________________________________________________________________________________________________    |");
        System.out.println("||                                                                                                                                                       |V.2|");
        System.out.println("|| CREATE TABLE :                     Create and load new table                                                                                          |   |");
        System.out.println("|| LOAD BLOCK :                       Load a Block with its table from file                                                                              |   |");
        System.out.println("|| LOAD BLOCK FROM FILE:              Load a Block with its table from ./ConsoleFiles/Source/ file                                                       |   |");
        System.out.println("|| COMMIT(new):                       Commit Block to the block chain                                                                                    |   |");
        System.out.println("|| ROLLBACK(new):                     Load the previous version of block from block chain                                                                |   |");
        System.out.println("|| LOAD VIEW FROM TABLE:              load a view from a table                                                                                           |   |");
        System.out.println("|| LOAD VIEW FROM FILE :              Load a 2D String[][] from an object file                                                                           |   |");
        System.out.println("|| INSERT INTO TABLE FROM VIEW:       Insert into table from the loaded view                                                                             |   |");
        System.out.println("|| SELECT VIEW :                      Show data in the loaded view                                                                                       |   |");
        System.out.println("|| SELECT TABLE:                      Show all fields Table data                                                                                         | M |");
        System.out.println("|| CUSTOM SELECT TABLE:               input field names to view their data                                                                               | A |");
        System.out.println("|| FILTER SELECT TABLE:               Input index values to view them (Glitchy)                                                                          | R |");
        System.out.println("|| CUSTOM FILTER SELECT TABLE:        input Field and indexes to view their data (Glitchy)                                                               | K |");
        System.out.println("|| WHERE SELECT TABLE:                input where clause to view table data (Glitchy)                                                                    |   |");
        System.out.println("|| INSERT TABLE :                     enter data as per the table structure to insert                                                                    |   |");
        System.out.println("|| CUSTOM INSERT TABLE:               enter field names and corresponding data to insert                                                                 |-VI|");
        System.out.println("|| FORM INPUT TABLE:                  insert data from a form structure                                                                                  |   |");
        System.out.println("|| CUSTOM FORM INPUT TABLE:           select fields for insert and insert values in a form structure                                                     |   |");
        System.out.println("|| UPDATE TABLE :                     Update entry in table  as per index                                                                                |   |");
        System.out.println("|| UPDATE TABLE WHERE(new) :          Update entry in table using the where function                                                                     |   |");
        System.out.println("|| CUSTOM UPDATE TABLE :              Update entry in table  as per index                                                                                |   |");
        System.out.println("|| CUSTOM UPDATE TABLE WHERE(new):    Update selected columns only in table  using the where clause                                                      |   |");
        System.out.println("|| DELETE FROM TABLE:                 delete records from index values                                                                                   |   |");
        System.out.println("|| DELETE FROM TABLE WHERE(new):      elete records froM Table using where clause                                                                        |   |");
        System.out.println("|| SHOW TABLE STRUCTURE :             Show structure of the table                                                                                        |   |");
        System.out.println("|| RECALIBERATE :                     run to recaliberate table object                                                                                   |   |");
        System.out.println("|| EXPORT TABLE :                     save and export table in ./ConsoleFiles/Tables/ directory                                                          |   |");
        System.out.println("|| EXPORT VIEW :                      save and export loaded view into the ./ConsoleFiles/Views/ directory as 2D array                                   |   |");
        System.out.println("|| EXPORT BLOCK :                     save and export loaded Block into the ./ConsoleFiles/Blocks/ directory                                             |   |");
        System.out.println("|| EXIT :                             Quit application                                                                                                   |   |");
        System.out.println("||                                                                                                                                                       |   |");
        System.out.println("||_______________________________________________________________________________________________________________________________________________________|   |");
        System.out.println("|                                                                                                          New features to be added in upcomming versions    |");
        System.out.println("|============================================================================================================================================================|");

    }
	public static void main(String args[]) throws IOException
    {
        printLogo();
        boolean run=true;
        System.out.println("Type SHOW CATALOG to view commands list");
        int[] index=null;
        String[] headers=null;
        
        while(run)
        {
            try{
        	
        	String Input;
            if(table!=null) System.out.print(table.name);
        	System.out.print(" >");
        	Input=b.readLine();
            Input=Input.toUpperCase();
        	switch(Input)
        	{
                
                case "LOAD BLOCK EDITOR FROM BLOCK":
                      System.out.print("File name >");
                      Input=b.readLine();
                      LoadEditorFromBlock(Input);
                      break;
                case "LOAD BLOCK EDITOR":
                      System.out.print("File name >");
                      Input=b.readLine();
                      LoadEditor(Input);
                      break;                      
        		case "CREATE BASIC BLOCK":
        			CreateBlockEditor();
        			break;
                case "CREATE ADVANCED BLOCK":
                     createBlockEditorAdvance();
                     break;
                case "COMMIT":
                     commitBlock();
                     break;
                case "ROLLBACK":
                     rollbackBlock();
                     break;
        		case "SELECT TABLE":
        			table.select();
        			break;
        		case "CUSTOM SELECT TABLE":
                    if(table!=null) System.out.print(table.name+" ");
                    System.out.print("Field Names >");
                    Input=b.readLine();
                    for(int i=0;i<Input.split(" ").length;i++)
                    System.out.print(Input.split(" ")[i]+" ");
        			table.select(Input.split(" "));
                    break;
                case "FILTER SELECT TABLE":
                    
                    if(table!=null) System.out.print(table.name+" ");
                    System.out.print("Indexes >");
                    Input=b.readLine();
                    index=new int[Input.split(" ").length];
                    for(int i=0;i<index.length;i++)
                    index[i]=Integer.parseInt(Input.split(" ")[i]);
                    table.select(index);
                    break;
                case "SELECT TABLE WHERE":
                     selectWhere();
                     break;
                case "CUSTOM FILTER SELECT TABLE":
                    if(table!=null) System.out.print(table.name+" ");
                    System.out.print("Field Names >");
                    Input=b.readLine();
                    headers=Input.split(" ");
                    System.out.print("Indexes >");
                    Input=b.readLine();
                    index=new int[Input.split(" ").length];
                    for(int i=0;i<index.length;i++)
                    index[i]=Integer.parseInt(Input.split(" ")[i]);
                    table.select(headers,index);
                    break;
        		case "INSERT INTO TABLE":
                    if(table!=null) System.out.print(table.name);
                while(true)
                    {
                        System.out.print("Insert >");
        			    Input=b.readLine();
                        if(Input.equalsIgnoreCase("exit insert")) break;
        			    System.out.println(table.insert(Input.split(" ")));
                    }
        			break;
                case "CUSTOM INSERT INTO TABLE":
                {
                    if(table!=null) System.out.print(table.name+" ");
                    while(true)
                    {
                        String head;
                        String value;
                        System.out.print("Headers >");
        			    Input=b.readLine();
                        head=Input;
                        if(Input.equalsIgnoreCase("exit insert") || Input.equalsIgnoreCase("cancel")) break;
                        System.out.print("Insert >");
                        Input=b.readLine();
                        value=Input;
                        if(Input.equalsIgnoreCase("exit insert") || Input.equalsIgnoreCase("cancel")) break;
        			    System.out.println(table.insert(head.split(" "),value.split(" ")));
                    }
                    break;
                }
                case "FORM INPUT TABLE":
                        formInput();
                        break;
                case "CUSTOM FORM INPUT TABLE":
                        System.out.print("Headers >");
        			    Input=b.readLine();
                        if(Input.equalsIgnoreCase("exit insert") || Input.equalsIgnoreCase("cancel")) break;
                        customFormInsert(Input.split(" "));
                        break;
                case "INSERT INTO TABLE FROM VIEW":
                        table.insert(view);
                        break;
                case "DELETE FROM TABLE":
                {
                     deleteFromTable();
                     break;
                }
                case "DELETE FROM TABLE WHERE":
                     deleteFromTableWhere();
                     break;                     
                case "EXPORT TABLE":
                    xportTable();
                    break;
                case "EXPORT VIEW" :
                     xportView();
                     break;
                case "EXPORT BLOCK" :
                     createBlockFromEditor();
                     break;
                case "SAVE BLOCK EDITOR":
                     saveBlockEditor();
                     break;                     
                case "SAVE EDITOR" :
                     saveBlockEditor();
                     break;
                case "SHOW TABLE STRUCTURE":
                     table.showStructure();
                     break; 
                case "UPDATE TABLE":
                     updateTable();
                     break;
                case "UPDATE TABLE WHERE":
                     updateTableWhere();
                     break;  
                case "CUSTOM UPDATE TABLE WHERE":
                     customUpdateWhere();
                     break;              
                case "LOAD VIEW FROM TABLE":
                    LoadViewFromTable();
                    System.out.println(" View loaded");
                    break;
                case "LOAD VIEW FROM FILE":
                      System.out.print("File name >");
                      Input=b.readLine();
                      LoadViewFromFile(Input);
                      break;
                case "SELECT VIEW":
                    showView();
                    break;
        		case "EXIT":
                    run=false;
                    break;
                case "CUSTOM UPDATE TABLE":
                      customUpdate();
                      break;        			
                case "SHOW CATALOG":
                    showCatalog();
                    break;

                case "RECALIBERATE":
                    table.recaliberate();
        		default:
        			System.out.println("Illegal command : Type SHOW CATALOG to view Console commands list");
                    table.recaliberate();
            }
        	}
            catch(NullPointerException e)
            {
                System.out.println("Error 404 : Object not loaded. Load the object file.");
            }
            catch(Exception e)
            {
                System.out.println("Error : Illegal input."); 
                //e.printStackTrace();               
            }           
        	
        }
        
    }
}
class dbConsole
{
    private static DbObj database=null;
    private static Table table=null;
    public static String[][] view=null;
    public static void createDb()throws IOException
    {
        InputStreamReader i= new InputStreamReader(System.in);
        BufferedReader b= new BufferedReader(i);
        System.out.printf("15s%"," Database name >");
        String db_name=b.readLine();
        System.out.printf("15s%"," Address > ");
        String address=b.readLine();
        database=new DbObj(db_name,address);
        System.out.println(db_name+" Database created in "+address);
    }
    public static void createTable()throws IOException
    {
        InputStreamReader i= new InputStreamReader(System.in);
        BufferedReader b= new BufferedReader(i);
        System.out.printf("%15s"," Table name >");
        String table_Name=b.readLine();
        System.out.printf("%15s"," Number of fields >");
        int n=Integer.parseInt(b.readLine());
        String[] headers=new String[n];
        String[] dataType=new String[n];
        boolean[] noNull=new boolean[n];
        boolean[] noDupl=new boolean[n];
        for(int o=0;o<n;o++)
        {
            System.out.println("Field ["+o+"]");
            System.out.printf("%15s"," Header name >");
            headers[o]=b.readLine(); 
            System.out.printf("%15s"," Data type >");
            dataType[o]=b.readLine(); 
            System.out.printf("%15s"," Enable no Nulls >");
            if(b.readLine().equalsIgnoreCase("y"))noNull[o]=true;
            else noNull[o]=false;
            System.out.printf("%15s"," Enable no Duplicates >");
            if(b.readLine().equalsIgnoreCase("y"))noDupl[o]=true;
            else noDupl[o]=false;        
        }
        table=Table.generateTable(table_Name,headers,dataType,noNull,noDupl);
    }
    public static void loadDb()throws IOException
    {
        InputStreamReader i= new InputStreamReader(System.in);
        BufferedReader b= new BufferedReader(i);
        String input;
        System.out.print("File name >");
        input=b.readLine();
        try{
        FileInputStream fis=new FileInputStream(input);
        ObjectInputStream ois=new ObjectInputStream(fis);
        database=(DbObj)ois.readObject();
        fis.close();
        ois.close();
        }
        catch(FileNotFoundException e)
            {
                System.out.println("Error: File not found");
            }
            catch(ClassNotFoundException e)
            {
                System.out.println("Error: Incompatible file");
            }
            catch(InvalidClassException e)
            {
                System.out.println("Error: File version incompatible");
            }
            catch(StreamCorruptedException e)
            {
                System.out.println("Error: Corrupted file");
            }
            catch(ClassCastException e)
            {
                System.out.println("Error: failed to serialise object file");
            }        
    }
    public static void selectDbTable()throws IOException
    {
        InputStreamReader i= new InputStreamReader(System.in);
        BufferedReader b= new BufferedReader(i);
        String input;
        System.out.print("Table name >");
        input=b.readLine();
        database.getBlock(input).table.select();
    }
    public static void showView()throws Exception                                                 //Display view
    {
        System.out.print("---View- "); 
            for(int j=0;j<view[0].length;j++)
            {
                
                System.out.printf(" %-20s","-------------------");
            }
            System.out.println();
        for(int i=0;i<view.length;i++)                                                      
         
        {
            
            System.out.print(" ");
            System.out.printf("|%-8s",Integer.toString(i)+"|");
            for(int j=0;j<view[i].length;j++)
            {
                try{
                System.out.printf("|  %-15s|  ",view[i][j]);
                }
                catch(NullPointerException e){}
            }
            System.out.println();
            System.out.print("-------- ");
            for(int j=0;j<view[i].length;j++)
            {
                
                System.out.printf(" %-20s","-------------------");
            }
            System.out.println();

            

        }
        System.out.println();
        System.out.println("View length : "+Integer.toString(view.length));


    }
    public static void selectDbTableWhere()throws IOException
    {
        InputStreamReader i= new InputStreamReader(System.in);
        BufferedReader b= new BufferedReader(i);
        String tableName;
        System.out.print("Table name >");
        tableName=b.readLine();
        System.out.print(" Enter number of where clauses :");
        int n=Integer.parseInt(b.readLine());
        String[][] clause=new String[n][3];
        for(int x=0;x<clause.length;x++)
        {
            System.out.println("Input for Clause ["+Integer.toString(x)+"]");
            System.out.printf("%15s","Header :");
            clause[x][0]=b.readLine().trim();
            System.out.printf("%15s","Operator:");
            clause[x][1]=b.readLine().trim();
            System.out.printf("%15s","Value :");
            clause[x][2]=b.readLine().trim();
        }
        System.out.print(" Enter joiner type (AND/OR) :");
        String input=b.readLine();
        int[][] index=table.where(clause);
        if(input.equalsIgnoreCase("OR"))
        database.getBlock(tableName).table.select(index[0]);
        if(input.equalsIgnoreCase("AND"))
        database.getBlock(tableName).table.select(index[1]);
    }
    public static void customSelectDbTable()throws IOException
    {
        InputStreamReader i= new InputStreamReader(System.in);
        BufferedReader b= new BufferedReader(i);
        String tableName;
        String headers;
        System.out.print("Table name >");
        tableName=b.readLine();
        System.out.print("Headers >");
        headers=b.readLine();
        database.getBlock(tableName).table.select(headers.trim().split(" "));
    }
    public static void selectTable()
    {
        table.select();
    }
    public static void selectWhere()throws IOException
    {
        InputStreamReader i= new InputStreamReader(System.in);
        BufferedReader b= new BufferedReader(i);
        System.out.print(" Enter number of where clauses :");
        int n=Integer.parseInt(b.readLine());
        String[][] clause=new String[n][3];
        for(int x=0;x<clause.length;x++)
        {
            System.out.println("Input for Clause ["+Integer.toString(x)+"]");
            System.out.printf("%15s","Header :");
            clause[x][0]=b.readLine().trim();
            System.out.printf("%15s","Operator:");
            clause[x][1]=b.readLine().trim();
            System.out.printf("%15s","Value :");
            clause[x][2]=b.readLine().trim();
        }
        System.out.print(" Enter joiner type (AND/OR) :");
        String input=b.readLine();
        int[][] index=table.where(clause);
        if(input.equalsIgnoreCase("OR"))
        table.select(index[0]);
        if(input.equalsIgnoreCase("AND"))
        table.select(index[1]);
        
    }
    public static void runFunction()throws IOException
    {
        InputStreamReader i= new InputStreamReader(System.in);
        BufferedReader b= new BufferedReader(i);
        String functionName;
        System.out.print("Function name >");
        functionName=b.readLine();
        table=(Table)database.runFunction(functionName);
        System.out.println("Function output Table loaded to console variable");
    }
    public static void loadConsoleTableToDb()throws IOException
    {
        database.newBlock(Block.generateBlock(table.name,table.getHeaders(), table.getDataTypes()));
        database.getBlock(table.name).table.insert(table.getView());
    }
    public static void dropBlock()throws IOException
    {
        InputStreamReader i= new InputStreamReader(System.in);
        BufferedReader b= new BufferedReader(i);  
        System.out.print("Block/table name >");
        database.removeBlock(b.readLine());
        System.out.println("Block removed from Database");      
    }
    public static void customFormInputDbTable()throws IOException
    {
        InputStreamReader i= new InputStreamReader(System.in);
        BufferedReader b= new BufferedReader(i);
        String tableName;
        String headers;
        System.out.print("Table name >");
        tableName=b.readLine();
        System.out.print("Headers >");
        headers=b.readLine();
        String[] values=new String[headers.trim().split(" ").length];
        for(int n=0;n<headers.trim().split(" ").length;n++)
        {
            System.out.print(headers.trim().split(" ")[n]+" > ");
            values[n]=b.readLine();
        }
        Block block=database.getBlock(tableName);
        System.out.println(block.table.insert(values));
        database.updateBlock(block.name,block);       
    }
    public static void formInputDbTable()throws IOException
    {
        InputStreamReader i= new InputStreamReader(System.in);
        BufferedReader b= new BufferedReader(i);
        String tableName;
        String headers[];
        System.out.printf("%15s","Table name >");
        tableName=b.readLine();
        headers=database.getBlock(tableName).table.getHeaders();
        String[] values=new String[headers.length];
        for(int n=0;n<headers.length;n++)
        {
            System.out.printf("%15s",headers[n]+" > ");
            values[n]=b.readLine();
        }
        Block block=database.getBlock(tableName);
        System.out.println(block.table.insert(values));
        database.updateBlock(block.name,block);
       
    }
    public static void customFormInputConsoleTable()throws IOException
    {
        InputStreamReader i= new InputStreamReader(System.in);
        BufferedReader b= new BufferedReader(i);
        System.out.print("Headers >");
        String headers=b.readLine();
        String[] values=new String[headers.trim().split(" ").length];
        for(int n=0;n<headers.trim().split(" ").length;n++)
        {
            System.out.print(headers.trim().split(" ")[n]+" > ");
            values[n]=b.readLine();
        }
        System.out.println(table.insert(headers.trim().split(" "),values));
              
    }
    public static void formInputConsoleTable()throws IOException
    {
        InputStreamReader i= new InputStreamReader(System.in);
        BufferedReader b= new BufferedReader(i);
        String []headers=table.getHeaders();
        String[] values=new String[headers.length];
        for(int n=0;n<headers.length;n++)
        {
            System.out.printf("%15s",headers[n]+" > ");
            values[n]=b.readLine();
        }
        System.out.println(table.insert(values));
    }
    public static void updateTableWhere()throws Exception
    {
        InputStreamReader i= new InputStreamReader(System.in);
        BufferedReader b= new BufferedReader(i);
        String[] values=new String[table.getHeaders().length];
        System.out.println("Input values into the form below ");
        String[] headers=table.getHeaders();
        for(int x=0;x<table.fields;x++)
        {
            
            System.out.printf("%15s",headers[x]+" : ");
            values[x]=b.readLine();
        }
        System.out.print(" Enter number of where clauses :");
        int n=Integer.parseInt(b.readLine());
        String[][] clause=new String[n][3];
        for(int x=0;x<clause.length;x++)
        {
            System.out.println("Input for Clause ["+Integer.toString(x)+"]");
            System.out.printf("%15s","Header :");
            clause[x][0]=b.readLine().trim();
            System.out.printf("%15s","Operator:");
            clause[x][1]=b.readLine().trim();
            System.out.printf("%15s","Value :");
            clause[x][2]=b.readLine().trim();
        }
        System.out.print(" Enter joiner type (AND/OR) :");
        String input=b.readLine();
        int[][] index=table.where(clause);
        if(input.equalsIgnoreCase("OR"))
        System.out.println(table.updateTable(headers,values,index[0]));
        if(input.equalsIgnoreCase("AND"))
        System.out.println(table.updateTable(headers,values,index[1]));          
    }
    public static void updateDbTableWhere()throws IOException
    {
        InputStreamReader i= new InputStreamReader(System.in);
        BufferedReader b= new BufferedReader(i);
        System.out.print("Table name >");
        String tableName=b.readLine();
        String[] values=new String[table.getHeaders().length];
        System.out.println("Input values into the form below ");
        String[] headers=table.getHeaders();
        for(int x=0;x<table.fields;x++)
        {
            
            System.out.printf("%15s",headers[x]+" : ");
            values[x]=b.readLine();
        }
        System.out.print(" Enter number of where clauses :");
        int n=Integer.parseInt(b.readLine());
        String[][] clause=new String[n][3];
        for(int x=0;x<clause.length;x++)
        {
            System.out.println("Input for Clause ["+Integer.toString(x)+"]");
            System.out.printf("%15s","Header :");
            clause[x][0]=b.readLine().trim();
            System.out.printf("%15s","Operator:");
            clause[x][1]=b.readLine().trim();
            System.out.printf("%15s","Value :");
            clause[x][2]=b.readLine().trim();
        }
        System.out.print(" Enter joiner type (AND/OR) :");
        String input=b.readLine();
        int[][] index=table.where(clause);
        if(input.equalsIgnoreCase("OR"))
        {
            Block block=database.getBlock(tableName);
            System.out.println(block.table.updateTable(headers,values,index[0]));
            database.updateBlock(block.name,block);
        }
        if(input.equalsIgnoreCase("AND"))
        {
            Block block=database.getBlock(tableName);
            System.out.println(block.table.updateTable(headers,values,index[1]));
            database.updateBlock(block.name,block);            
        }         
    }
    public static void customUpdateWhere()throws Exception
    {
        InputStreamReader i= new InputStreamReader(System.in);
        BufferedReader b= new BufferedReader(i);
        System.out.print("Input header names for updation >");
        String[] headers=b.readLine().split(" ");
        String[] values=new String[headers.length];
        System.out.println("Input values into the form below ");
        for(int x=0;x<headers.length;x++)
        {
            
            System.out.printf("%15s",headers[x]+" : ");
            values[x]=b.readLine();
        }
        System.out.print(" Enter number of where clauses :");
        int n=Integer.parseInt(b.readLine());
        String[][] clause=new String[n][3];
        for(int x=0;x<clause.length;x++)
        {
            System.out.println("Input for Clause ["+Integer.toString(x)+"]");
            System.out.printf("%15s","Header :");
            clause[x][0]=b.readLine().trim();
            System.out.printf("%15s","Operator:");
            clause[x][1]=b.readLine().trim();
            System.out.printf("%15s","Value :");
            clause[x][2]=b.readLine().trim();
        }
        System.out.print(" Enter joiner type (AND/OR) :");
        String input=b.readLine();
        int[][] index=table.where(clause);
        if(input.equalsIgnoreCase("OR"))
        System.out.println(table.updateTable(headers,values,index[0]));
        if(input.equalsIgnoreCase("AND"))
        System.out.println(table.updateTable(headers,values,index[1]));

    }
    public static void customUpdateDbTableWhere()throws IOException
    {
        InputStreamReader i= new InputStreamReader(System.in);
        BufferedReader b= new BufferedReader(i);
        String tableName;
        System.out.print("Table name >");
        tableName=b.readLine();
        System.out.print("Input header names for updation >");
        String[] headers=b.readLine().split(" ");
        String[] values=new String[headers.length];
        System.out.println("Input values into the form below ");
        for(int x=0;x<headers.length;x++)
        {
            
            System.out.printf("%15s",headers[x]+" : ");
            values[x]=b.readLine();
        }
        System.out.print(" Enter number of where clauses :");
        int n=Integer.parseInt(b.readLine());
        String[][] clause=new String[n][3];
        for(int x=0;x<clause.length;x++)
        {
            System.out.println("Input for Clause ["+Integer.toString(x)+"]");
            System.out.printf("%15s","Header :");
            clause[x][0]=b.readLine().trim();
            System.out.printf("%15s","Operator:");
            clause[x][1]=b.readLine().trim();
            System.out.printf("%15s","Value :");
            clause[x][2]=b.readLine().trim();
        }
        System.out.print(" Enter joiner type (AND/OR) :");
        String input=b.readLine();
        int[][] index=database.getBlock(tableName).table.where(clause);
        if(input.equalsIgnoreCase("OR"))
        {
            Block block=database.getBlock(tableName);
            System.out.println(block.table.updateTable(headers,values,index[0]));
            database.updateBlock(block.name,block);
        }
        if(input.equalsIgnoreCase("AND"))
        {
            Block block=database.getBlock(tableName);
            System.out.println(block.table.updateTable(headers,values,index[1]));
            database.updateBlock(block.name,block);            
        }
    }
    public static void printLogo()
    {
                System.out.println("         ___________  ___                                                        __    __ ");
		        System.out.println("        /   _____   |/  /                                                     _/  /  /  /");
		        System.out.println("       /   /     |  /  /                                                     //  /__/  /");
		        System.out.println("      /   /      / /  /____                                                 //  ___  _/__");
		        System.out.println("     /   /      / /   __   |               -:HANS ENTERPRICES PVT LTD:-    //  /_ / / ___/");
		        System.out.println("    /   /______/ /  /__ /__/_______                                       //__/ //_/ _/_");
		        System.out.println("   /____________/_______/  ____   /                   AUTHOR             /__/  /_ /____/");
		        System.out.println("                       /  /   /__/            JK MOHAN KAPUR HANSDAH            /____/");
		        System.out.println("                      /  /       _____  __ __   ______ _____  __   ______");
		        System.out.println("                     /  /    __ /     // /_  | /  ___//     // /  / ____/");
		        System.out.println("                    /  /___/  //     // / /  //___  //     // /_ / __/_");
		        System.out.println("                   /_________//_____//_/ /__//_____//_____//___//_____/");                           
		        System.out.println("                         --:DATABASE CONSOLE:--                      ");
                System.out.println("-----------------------------------------------------------------------------------------");
                System.out.println("              -:Java Embedded Data Management Architecture:-       Console Draft-III");
                System.out.println("                           -:J-NOSql-EDMA:-                            -:v3.Mk-I:-");
    }
    public static void main(String[] args)throws Exception
    {
        InputStreamReader i= new InputStreamReader(System.in);
        BufferedReader b= new BufferedReader(i);
        String input;
        printLogo();
        boolean run=true;
        
        while(run)
        {
            try{
            if (database!= null)System.out.print(database.db_name);
            System.out.print(" >");
            input=b.readLine();
            switch(input.toUpperCase())
            {
            case "CREATE DB":
            createDb();
            break;
            case "CREATE TABLE":
            createTable();
            break; 
            case "LOAD DB":
            loadDb();
            break;
            case "LOAD CONSOLE TABLE TO DB":
            loadConsoleTableToDb();
            break;
            case "DROP TABLE FROM DB":
            dropBlock();
            break;
            case "SELECT DB TABLE":
            selectDbTable();
            break;
            case "SHOW ALL DB BLOCKS":
            database.blRegistry.showAllBlocks();
            break;
            case "SHOW ALL DB FUNCTIONS":
            database.fnRegistry.showAllFunctions();
            break;
            case "CUSTOM SELECT DB TABLE":
            customSelectDbTable();
            break;
            case "SELECT DB TABLE WHERE":
            selectDbTableWhere();
            break;
            case "SELECT CONSOLE TABLE":
            selectTable();
            break;
            case "SELECT CONSOLE VIEW":
            showView();
            break;
            case "SELECT CONSOLE TABLE WHERE":
            selectWhere();
            break;
            case "CUSTOM SELECT CONSOLE TABLE":
            System.out.print(" Headers names>");
            table.select(b.readLine().trim().split(" "));
            break;
            case "RUN DB FUNCTION":
            runFunction();
            break;
            case "DB COMMIT":
            database.commit();
            break;
            case "FORM INPUT DB TABLE":
            formInputDbTable();
            break;
            case "FORM INPUT CONSOLE TABLE":
            formInputConsoleTable();
            break;
            case "CUSTOM FORM INPUT DB TABLE":
            customFormInputDbTable();
            break;
            case "CUSTOM FORM INPUT CONSOLE TABLE":
            customFormInputConsoleTable();
            break;
            case "DELETE FROM DB TABLE WHERE":
            break;
            case "UPDATE DB TABLE WHERE":
            updateDbTableWhere();
            break;   
            case "CUSTOM UPDATE DB TABLE WHERE":
            customUpdateDbTableWhere();
            break;  
            case "SHOW DB TABLE STRUCTURE":
            System.out.print("Table name >");
            input=b.readLine();
            database.getBlock(input).table.showStructure(); 
            break; 
            case "SHOW CONSOLE TABLE STRUCTURE": 
            table.showStructure();   
            break;
            case "RESET CONSOLE TABLE": 
            table=null; 
            System.out.println("Console table reset"); 
            break;
            case "RESET CONSOLE VIEW": 
            view=null; 
            System.out.println("Console View reset");  
            break;
            case "LOAD VIEW FROM CONSOLE TABLE": 
            view=table.getView(); 
            System.out.println("View loaded from console"); 
            break;
            case "LOAD VIEW FROM DB TABLE": 
            System.out.print(" Table name >");
            input=b.readLine();
            view=database.getBlock(input).table.getView(); 
            System.out.println("View loaded to console");  
            break;
            case "EXIT":
            run=false; 
            break;           
            default:
            System.out.println("Error: Illegal input");
            }
        }
        catch(NullPointerException e){
            System.out.println("Error :No Object loaded");
        }
        catch(IOException e){
            System.out.println("Error :IO Exception");
        }
        }
        }
    
}