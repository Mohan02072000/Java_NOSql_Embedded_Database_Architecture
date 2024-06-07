import java.io.*;


final class Entry implements Serializable          //Node class
{
    private String[] data;                         //Data Array
    protected int length;                          //Length of Data Array
    protected int index;                           //Entry index in table
    protected Entry prev;                          //Pointer to previous entry
    protected Entry next;                          //Pointer to next index
    private Entry(int length)                      //Private constructor
    {
        this.data=new String[length];
        this.length=length;
    }
    public static Entry generateEntry(int length)  //Factory function
    {
        return new Entry(length);
    }
    public void putData(String newData,int i)      //Input only single value into the entry
    {
        this.data[i]=newData;
    }
    public String[] getEntryArray()                //Return Entry data as a new Array
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
    private Entry tail;                                                              //Tail of the entry linked list
    private Entry head;                                                              //head of the Entry linked list
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
        this.head=Entry.generateEntry(header.length);
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
        return head.getEntryArray();
    }
    public void recaliberate()throws NullPointerException                                                                       // Recaliberate indexes and Pointers
    {
        Entry node=head;
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
        Entry node=this.head;
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
        Entry node=this.head;
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
            
            
            Entry newNode=Entry.generateEntry(head.length);

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
                        if(entry[i].length()!=0) newNode.putData(entry[i].trim(),i);
                        break;
                        case "Character":
                        if(entry[i].length()==1)
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
        Entry node=this.head;
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
        select(this.head.getEntryArray());
    }
    public void select(String[] header)throws NullPointerException                                                              //Custom select
    {
        System.out.println("Select "+this.name);
        int[] index= getSequence(header);
        Entry node=head;
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
                    if(index[j]>=0)System.out.printf(" %-"+Integer.toString(this.indent[j])+"s |",node.getEntryArray()[index[j]]);
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
                    if(index[j]!=-1)System.out.printf(" %-"+Integer.toString(this.indent[j])+"s |",node.getEntryArray()[index[j]]);
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
        select(this.head.getEntryArray(), index);

    }
    public void select(String[] header,int[] indexes)throws NullPointerException                                                //Custom filter select
    {
        System.out.println("Custom Filter Select "+this.name);
        int[] index= getSequence(header);
        Entry node=head;
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
    public Entry getEntry(int index)throws NullPointerException                                                                 //find entry as per index
    {
        if(index>0 && index<=size)
        {
        Entry node=head;
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

        return Entry.generateEntry(0);
    }
    public String updateTable(String[] headers,String[] values, int[] indexes)                                                  //Update values in the entry as per 
    {
        if(headers.length!=values.length) return "Table Update failed : different header and index lengths";
        Entry node;
        for(int i=0;i<indexes.length;i++)                                 //access index record
        {
            if(indexes[i]>this.size) continue;
            node=getEntry(indexes[i]);
            int[] fieldsIndexSequence=getSequence(headers); 
            for(int j=0;j<fieldsIndexSequence.length;j++)                //access each fields
            {
                node.putData(values[j], fieldsIndexSequence[j]);
            }
        }
        return "Table update complete";
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
        Entry node=head;
        for(int i=0;i<=this.size;i++)
        {
            view[i]=node.getEntryArray();
            node=node.next;
        }
        return view;
        
    }
    public String[][] getView(String[] headers)                                                                                 //Return table data of mentioned headers only
    {
        String[][] view=new String[this.size+1][headers.length];
        Entry node=head;
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
        Entry node;
        for(int i=0;i<=index.length;i++)
        {
            if(i==0)
            {
                view[i]=this.head.getEntryArray();
                continue;
            }
            node=this.getEntry(index[i-1]);
            view[i]=node.getEntryArray();
            //node=node.next;
        }
        return view;

    }
    public String[][] getView(String[] headers,int[] index)                                                                     //Return table data of filtered index of mentioned headers only
    {
        String[][] view=new String[index.length+1][headers.length];
        Entry node;
        int[] headersIndex=this.getSequence(headers);
        for(int i=0;i<=index.length;i++)
        {
            for(int j=0;j<headers.length;j++)
            {
            if(i==0)
            {
                view[i][j]=this.head.getEntryArray()[headersIndex[j]];
                continue;
            }
            node=this.getEntry(index[i-1]);
            view[i][j]=node.getEntryArray()[headersIndex[j]];
            //node=node.next;
            }
        }
        return view;
    }
    public int[] where(String[][] clause)                                                                                       //Return indexes where conditions satisfy in the [n][3] clause matrix
    {
        if(clause[0].length!=3 )
        {
            System.out.println("Error: Clause matrix not in synk with the Function requirement");
            return new int[0];
        }
        else{
            boolean[] indexFlag=new boolean[this.size+1];
            
            for(int j=1;j<=this.size;j++)
            for(int i=0;i<clause.length;i++)
            {
                int headerIndex=this.getSequence(new String[]{clause[i][0]})[0];                                                //get Header index for comparision
                String compareValue=clause[i][2];
                String tableValue=this.getEntry(j).getEntryArray()[headerIndex];
                try{
                switch (clause[i][1])
                {
                    
                    case "string.equals":
                        //System.out.println("string.equals in If block in table index: "+Integer.toString(j)+" in header : "+this.getHeaders()[headerIndex]);
                        if(tableValue.trim().equals(compareValue.trim()))indexFlag[j]=true;
                        break;
                    case "string.equalsIgnoreCase":
                        //System.out.println("string.equalsIgnoreCase in If block in table index: "+Integer.toString(j)+" in header :"+this.getHeaders()[headerIndex]);
                        if(tableValue.trim().equalsIgnoreCase(compareValue.trim()))indexFlag[j]=true;
                        break;
                    case "Integer.lessThen":
                         //System.out.println("Integer.lessThen in If block in table index: "+Integer.toString(j)+" in header :"+this.getHeaders()[headerIndex]);
                         if(Integer.parseInt(tableValue.trim())<Integer.parseInt(compareValue.trim()))indexFlag[j]=true;
                         break;
                    case "Integer.greaterThen":
                         //System.out.println("Integer.greaterThen in If block in table index: "+Integer.toString(j)+" in header :"+this.getHeaders()[headerIndex]);
                         if(Integer.parseInt(tableValue.trim())>Integer.parseInt(compareValue.trim()))indexFlag[j]=true;
                         break;
                    case "Integer.equals":
                         //System.out.println("Integer.equals in If block in table index: "+Integer.toString(j)+" in header :"+this.getHeaders()[headerIndex]);
                         if(Integer.parseInt(tableValue.trim())==Integer.parseInt(compareValue.trim()))indexFlag[j]=true;
                         break;
                    case "Integer.lessThenEquals":
                         //System.out.println("Integer.lessThenEquals in If block in table index: "+Integer.toString(j)+" in header :"+this.getHeaders()[headerIndex]);
                         if(Integer.parseInt(tableValue.trim())<=Integer.parseInt(compareValue.trim()))indexFlag[j]=true;
                         break;
                    case "Integer.greaterThenEquals":
                         //System.out.println("Integer.greaterThenEquals in If block in table index: "+Integer.toString(j)+" in header :"+this.getHeaders()[headerIndex]);
                         if(Integer.parseInt(tableValue.trim())>=Integer.parseInt(compareValue.trim()))indexFlag[j]=true;
                         break;
                    
                }                
                }
                catch (NullPointerException e){};
            }
            int indexSize=0;
            for(int i=0;i<indexFlag.length;i++)
                if(indexFlag[i]==true)indexSize++;
            int[] index=new int[indexSize];
            for(int i=0,j=0;i<indexFlag.length && j<index.length;i++)
                if(indexFlag[i]==true)
                {
                    index[j]=i;
                    j++;
                }
            
            return index;
        }
    }
}


class SourceCode_v4testScript {
    public static void main(String args[])throws IOException
    {
        System.out.println("                               |-------------------Script Block------------------------|");
        System.out.println("                               |--------- Java Embedded Database Architecture ---------|");
        System.out.println("                               |------------------Java-NOSql-EDMA----------v2.Mk-IV----|");

        // -----------------------------Table 1 insertion----------------------------//
        Table employee_table=Table.generateTable("Employee Table",
        (new String[]{"Emp_No.","Emp_Name","Join_Date","Design_Code","Dept","Basic","HRA","IT"}),
        (new String[]{"string","String","Date","character","String","integer","integer","integer"}),
        (new boolean[]{true,true,false,false,false,false,false,false}),
        (new boolean[]{true,true,false,false,false,false,false,false}));
        employee_table.insert(new String[]{"1001","Asish","01/04/2009","e","R&D","20000","8000","3000"});
        employee_table.insert(new String[]{"1002","Sushma","23/08/2012","c","PM","30000","12000","9000"});
        employee_table.insert(new String[]{"1003","Rahul","12/11/2008","k","Acct","10000","8000","1000"});
        employee_table.insert(new String[]{"1004","Chahat","29/01/2013","r","Front Desk","12000","6000","2000"});
        employee_table.insert(new String[]{"1005","Ranjan","16/07/2005","m","Engg","50000","20000","20000"});
        employee_table.insert(new String[]{"1006","Suman","01/01/2000","e","Manufacturing","23000","9000","4400"});
        employee_table.insert(new String[]{"1007","Tanmay","12/06/2006","c","PM","29000","12000","10000"});
        employee_table.insert(new String[]{"1010","Rajeev","12/08/2016","m","Engg","26000","17000","10000"});
        employee_table.insert(new String[]{"4009","Sanjay","13/09/2021","e","Manufacturing","18000","7500","3000"});
        employee_table.insert(new String[]{"1004","Chahat","29/01/2013","r","Front Desk","12000","6000","2000"});
        employee_table.insert(new String[]{"4009","Sanjay","13/09/2021","e","Manufacturing","18000","7500","3000"});
        System.out.println(
        employee_table.insert
        (   (new String[]{"Emp_No.","Emp_Name"}),
            (new String[]{"1305","Hemant"})));
        //employee_table.insert                                                                          //Test for insert() (Null insertion test)
        //((new String[]{"Join_Date","Design_Code","Dept","Basic","HRA","IT"}),
       // new String[]{"13/09/2021","e","Manufacturing","18000","7500","3000"});
        employee_table.insert(new String[]{"3209","Manisha","05/12/2019","i","Manufacturing","helloWorld","7500","3000"});
        employee_table.showStructure();
        employee_table.select();  //test for select()
        //System.out.println(employee_table.delete(new int[]{5,8,18}));
        //employee_table.select(new int[]{3,5,7,8});  //test for Select(int[] index)
        
        //employee_table.select(new String[]{"Emp_No.","Emp_Name","Design_Code","Dept","Basic"},new int[]{3,5,7,8}); //test for select(String headers[],int index[]);
        //employee_table.select(new String[]{"Emp_No","Emp_Name","Design_Code","Dept","Basic"});
        employee_table.updateTable(new String[]{"Design_Code","Dept","Basic"},new String[]{"e","Manufacturing","30000"},new int[]{10});  //test for update(String[] header,String[] values,int[] index) 
        employee_table.select(); 
        //String[][] tableView =employee_table.getView();                                                                                                               //Test for getView()
        //String[][] tableView =employee_table.getView(new String[]{"Emp_No.","Emp_Name","Design_Code"});                                                               //Test for getView(headers)
        String[][] tableView=employee_table.getView(new String[]{"Emp_No.","Emp_Name","Design_Code","Dept","Basic"},new int[]{3,5,7,8});                                //Test for getView(headers,Indexes)
        //String[][] tableView=employee_table.getView(new int[]{3,5,7,8});                                                                                              //Test for getView(indexes)
        System.out.print("---View- ");
            for(int j=0;j<tableView[0].length;j++)
            {
                
                System.out.printf(" %-20s","------------------");
            }
            System.out.println();
        for(int i=0;i<tableView.length;i++)                                                      //Display view
         
        {
            
            System.out.print(" ");
            System.out.printf("|%-8s",Integer.toString(i)+"|");
            for(int j=0;j<tableView[i].length;j++)
            {
                try{
                System.out.printf("|  %-15s|  ",tableView[i][j]);
                }
                catch(NullPointerException e){}
            }
            System.out.println();
            System.out.print("-------- ");
            for(int j=0;j<tableView[i].length;j++)
            {
                
                System.out.printf(" %-20s","------------------");
            }
            System.out.println();

            

        }
        System.out.println("View length is:"+Integer.toString(tableView.length));
        int[] indexes=employee_table.where(new String[][]{                                            //test for where(String[][]) part 2
            new String[]{"emp_Name","string.equals","Chahat"},
            new String[]{"Basic","Integer.greaterThenEquals","20000"}}            
        );
        //for(int i=0;i<indexes.length;i++)
        //System.out.println(indexes[i]);
        employee_table.select(indexes);                                                                //test for where(String[][]) part 2

        FileOutputStream fos=new FileOutputStream((employee_table.toString())+".jnos");              //create jnos file
        ObjectOutputStream oos=new ObjectOutputStream(fos);
                oos.writeObject(employee_table);
                //oos.writeObject(Design_table);

        fos.close();
        oos.close();
        //-------------------------Custom select code --------------------------------//
        //employee_table.select(new String[]{"Emp_No.","Emp_Name","Dept","Join_Date","HRA","IT"}); 
        
        
        // -----------------------------Table 2 insertion----------------------------//                              //Lookup table creation 
        //Table Design_table=Table.generateTable
        //(new String[]{"Design_Code","Designation","DA"},
        //new String[]{"character","String","Integer"});
        //Design_table.insert(new String[]{"e","Engineer","20000"});                                                 //Lookup table insertion
        //Design_table.insert(new String[]{"c","Consultant","32000"});
        //Design_table.insert(new String[]{"k","Clerk","12000"});
        //Design_table.insert(new String[]{"r","Receptionist","15000"});
        //Design_table.insert(new String[]{"m","Manager","40000"});
        //Design_table.select();
        //Design_table.insert(new String[]{"Designation","DA"},
        //new String[]{"Intern","1000"});
        //Design_table.select();
        


        // -----------------------------Join Table logic and insertion----------------------------//                //Join table creation
        //Table joint_table = new Table
       // (new String[]{"Emp_No.","Emp_Name","Join_Date","Dept","Basic","HRA","IT","Design_Code","Designation","DA",},
        //new String[]{"string.","string","date","character","integer","integer","Integer","Character","String","Integer",});
        //String[] str=new String[employee_table.fields+Design_table.fields-1];
        //joint_table.select();
    }
}

class Console {
	private static InputStreamReader i=new InputStreamReader(System.in);
	private static BufferedReader b= new BufferedReader(i);
	private static Table table;
    private static String[][] view;
	public static void CreateTable() throws IOException,NullPointerException
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
		table = Table.generateTable(name,Headers[0],Headers[1]);
		
	}
    public static void customFormInsert(String[] headers)throws IOException
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
    public static void formInput()throws IOException
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
    public static void updateTable()throws IOException
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
    public static void customUpdate()throws IOException
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
    public static void showView()throws IOException                                                 //Display view
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
    public static void selectWhere()throws IOException
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
        int[] indexes=table.where(clause);
        table.select(indexes);
    }
    public static void getIndexWhere()throws IOException
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
        int[] indexes=table.where(clause);
        System.out.print("  Indexes with the following data :");
        for(int i=0;i<indexes.length;i++)
        System.out.print(Integer.toString(indexes[i])+"   ");
    }
    public static void LoadTable(String filename)throws IOException
    {
        try{
        FileInputStream fos=new FileInputStream(filename+".jnos");
        ObjectInputStream ois = new ObjectInputStream(fos);
            table=(Table)ois.readObject();
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
    }
    public static void LoadViewFromFile(String filename)throws IOException
    {
        try{
        FileInputStream fos=new FileInputStream(filename+".view");
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
    public static void xportTable()throws IOException
    { 
        FileOutputStream fos=new FileOutputStream(table.toString()+".jnos");
        ObjectOutputStream oos=new ObjectOutputStream(fos);
        oos.writeObject(table);
        System.out.println("   Table exported to present directory as "+table.toString()+".jnos");
        //System.out.println("SerialVersionUID :"+Long.toString(Table.SerialVersionUID));
        fos.close();
        oos.close();

    }
    public static void xportView()throws IOException
    { 
        FileOutputStream fos=new FileOutputStream(table.toString()+".view");
        ObjectOutputStream oos=new ObjectOutputStream(fos);
        oos.writeObject(view);
        System.out.println("    View exported to present directory as "+table.toString()+".view");
        //System.out.println("SerialVersionUID :"+Long.toString(Table.SerialVersionUID));
        fos.close();
        oos.close();

    }
    public static void showCatalog()
    {
        
        System.out.println("|==================================================================================================================|");
        System.out.println("|:::::::::::::::::::::::::::::::::::::::::: Commands Catalog ::::::::::::::::::::::::::::::::::::::::::::::::::::::|");
        System.out.println("|==================================================================================================================|");
        System.out.println("|______________________________________________________________________________________________________________    |");
        System.out.println("||                                                                                                             |V.2|");
        System.out.println("|| CREATE TABLE : Create and load new table                                                                    |   |");
        System.out.println("|| LOAD TABLE : Load a table from file                                                                         |   |");
        System.out.println("|| LOAD VIEW FROM TABLE: load a view from a table                                                              |   |");
        System.out.println("|| LOAD VIEW FROM FILE : Load a 2D String[][] from an object file                                              |   |");
        System.out.println("|| INSERT INTO TABLE FROM VIEW: Insert into table from the loaded view                                         |   |");
        System.out.println("|| SELECT VIEW (new): Show data in the loaded view                                                             |   |");
        System.out.println("|| SELECT TABLE: Show all fields Table data                                                                    | M |");
        System.out.println("|| CUSTOM SELECT TABLE: input field names to view their data                                                   | A |");
        System.out.println("|| FILTER SELECT TABLE: Input index values to view them (Glitchy)                                              | R |");
        System.out.println("|| CUSTOM FILTER SELECT TABLE: input Field and indexes to view their data (Glitchy)                            | K |");
        System.out.println("|| WHERE SELECT TABLE(new): input where clause to view table data (Glitchy)                                    |   |");
        System.out.println("|| INSERT TABLE : enter data as per the table structure to insert                                              |   |");
        System.out.println("|| CUSTOM INSERT TABLE: enter field names and corresponding data to insert                                     |-IV|");
        System.out.println("|| FORM INPUT TABLE: insert data from a form structure                                                         |   |");
        System.out.println("|| CUSTOM FORM INPUT TABLE: select fields for insert and insert values in a form structure                     |   |");
        System.out.println("|| UPDATE TABLE (new) : Update entry in table  as per index                                                    |   |");
        System.out.println("|| CUSTOM UPDATE TABLE : Update entry in table  as per index                                                   |   |");
        System.out.println("|| DELETE FROM TABLE: delete records from index values                                                         |   |");
        System.out.println("|| SHOW TABLE STRUCTURE : Show structure of the table                                                          |   |");
        System.out.println("|| RECALIBERATE : run to recaliberate table object                                                             |   |");
        System.out.println("|| EXPORT TABLE : save and export table in the directory                                                       |   |");
        System.out.println("|| EXPORT VIEW (new):  save and export loaded view into the directory as 2D array                              |   |");
        System.out.println("|| EXIT : Quit application                                                                                     |   |");
        System.out.println("||                                                                                                             |   |");
        System.out.println("||_____________________________________________________________________________________________________________|   |");
        System.out.println("|                                                                New features to be added in upcomming versions    |");
        System.out.println("|==================================================================================================================|");

    }
	public static void main(String args[]) throws IOException
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
		        System.out.println("                                              -:Java Embedded Data Management Architecture:-  Console Draft-II");
		        System.out.println("                                                          -:J-NOSql-EDMA:-                       -:v2.Mk-IV:-");
        
        System.out.println();
        System.out.println();
       
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
                
                case "LOAD TABLE":
                      System.out.print("File name >");
                      Input=b.readLine();
                      LoadTable(Input);
                      break;
        		case "CREATE TABLE":
        			CreateTable();
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
                case "WHERE SELECT TABLE":
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
                     
                    
        		case "INSERT TABLE":
                    if(table!=null) System.out.print(table.name);
                while(true)
                    {
                        System.out.print("Insert >");
        			    Input=b.readLine();
                        if(Input.equalsIgnoreCase("exit insert")) break;
        			    System.out.println(table.insert(Input.split(" ")));
                        
                    }
        			break;
                case "CUSTOM INSERT TABLE":
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
                        System.out.print("Indexes >");
        			    Input=b.readLine();
                        int[] arr=new int[Input.split(" ").length];
                        
                        
                            for(int i=0;i<Input.split(" ").length;i++)
                            arr[i]=Integer.parseInt(Input.split(" ")[i]);

                            System.out.println(table.delete(arr));
                        
                        break;
                }
                case "EXPORT TABLE":
                    xportTable();
                    break;
                case "EXPORT VIEW" :
                     xportView();
                     break;
                case "SHOW TABLE STRUCTURE":
                     table.showStructure();
                     break; 

                case "UPDATE TABLE":
                     updateTable();
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
        	
        }
        
    }
}