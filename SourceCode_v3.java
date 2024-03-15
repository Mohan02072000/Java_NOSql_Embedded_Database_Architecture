final class Entry
{
    private String[] data;
    protected int length;
    protected int index;
    protected Entry prev;
    protected Entry next;
    private Entry(int length)
    {
        this.data=new String[length];
        this.length=length;
    }
    public static Entry generateEntry(int length)
    {
        return new Entry(length);
    }
    public String[] getData()
    {
        return data;
    }
    public void putData(String newData,int i)      //Input only single value into the entry
    {
        this.data[i]=newData;
    }
    public Entry getEntry()
    {
        return this;
    }
    public String[] getEntryArray()
    {
        String[] data=new String[this.length];
        for(int i=0;i<this.length;i++)
        {
            data[i]=this.data[i];
        }
        return data;
    }
    public void display_entry()
    {
        for(int i=0;i<data.length;i++)
        System.out.printf("%-15s",this.data[i]);
    }
    public void display_entry(int[] index)
    {
        for(int i=0;i<index.length;i++)
        {
            System.out.printf("%-15s",this.data[index[i]]);            
        }
    }
    
    public String getData(int index)
    {
        return new String(data[index]);
    }
   
}

final class Table
{
    public String name;
    private Entry tail;
    private Entry head;
    private byte[] datatypeId;
    private boolean[] noNull;
    private boolean[] noDuplicate;
    protected int size;
    protected int fields;
    private String[] datatypes=
    {"Undefined","Boolean","Integer","Date","Character","String"};
    private Table (String name,String [] header, String[] datatype,boolean[] noNull,boolean[] noDuplicate)
    {
        this.name=name;
        this.head=Entry.generateEntry(header.length);
        this.tail=head;
        this.datatypeId=new byte[header.length];
        this.noNull=new boolean[header.length];
        this.noDuplicate=new boolean[header.length];
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
    public static Table generateTable (String name,String[] header, String[] datatype, boolean[] noNull, boolean[] noDuplicate)
    {
        return new Table(name,header,datatype,noNull, noDuplicate);
    }

    public static Table generateTable(String name,String[] header, String[] datatype)
    {
        return new Table(name,header,datatype,new boolean[header.length],new boolean[header.length]);
    }

    public void showStructure()
    {
        Entry node=this.head;
            System.out.print(" --------------  ");
                for(int i=0;i<node.length;i++)
                {
                    System.out.printf("%-15s","--------------");
                }
                System.out.println();
                System.out.print("|FieldName-    |  ");
                
                
                node.display_entry();
                System.out.print("|");
                System.out.println();
                System.out.print("|DataType-     |  ");
                for(int i=0;i<node.length;i++)
                {
                    System.out.printf("%-15s",datatypes[datatypeId[i]]);
                }
                System.out.print("|");
                System.out.println();
                System.out.print("|noNull-       |  ");
                for(int k=0;k<head.length;k++)
                {
                    System.out.printf("%-15s",noNull[k]);                    
                }
                System.out.print("|");
                System.out.println();
                System.out.print("|noDupl-       |  ");
                for(int k=0;k<head.length;k++)
                {
                    System.out.printf("%-15s",noDuplicate[k]);                    
                }
                System.out.print("|");
                System.out.println();
                System.out.print(" --------------  ");
                for(int i=0;i<node.length;i++)
                {
                    System.out.printf("%-15s","--------------");
                }
                System.out.println();        
    }
    public int[] getSequence(String[] header)
    {
        int[] index=new int[header.length];
        for(int i=0;i<header.length;i++)
        for(int j=0;j<head.length;j++)
        {
            if(header[i].equals(head.getData(j)))
            index[i]=j;
        }
        //System.out.print("Node Index Sequence : ");
        //for(int i=0;i<index.length;i++)
        //{
         //   System.out.print(Integer.toString(index[i])+"  ");
        //}
        //System.out.println();
        return index;
    }
    public String insert(String[] entry)
    {
        
        //System.out.print("Insert String   : ");
        //for(int j=0;j<entry.length;j++)
        //{
        //   System.out.print(entry[j]+"  ");
        //}
        //check length of string array
        //insert if only equal to no of fields(head.length)
        //System.out.println();
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
        
            node=node.next;            
        }
        node=null;
            
            
            Entry newNode=Entry.generateEntry(head.length);

            //Insertion Block
            
            try
            {
                
                for(int i=0;i<entry.length;i++)
                    switch(this.datatypes[datatypeId[i]])
                    {
                        case "String":
                        newNode.putData(entry[i],i);
                        break;
                        case "Boolean":
                        newNode.putData(Boolean.toString(Boolean.parseBoolean(entry[i])),i);
                        break;
                        case "Integer":
                        newNode.putData(Integer.toString(Integer.parseInt(entry[i])),i);
                        break;
                        case "Date":
                        newNode.putData(entry[i],i);
                        break;
                        case "Character":
                        if(entry[i].length()==1)
                        newNode.putData(entry[i],i);
                        break;
                        case "Undefined":
                        newNode.putData(entry[i],i);
                        break;
                    }

            }
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
            this.size++;

            return ("Insertion Successful");            
    }
    
    public String insert(String[] header,String[] entry)
    {
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
        return this.insert(insertstring);
    }
    public String delete(int[] index)
    {
        Entry node=this.head;
        int count=0;
        try{
        for(int j=0;j<index.length;j++)
        for(int i=0;i<this.size;i++)
        {
            
            if(node.index==index[j]-1)
            {
                node.next=node.next.next;
                node.next.prev=node; 
                count++;
                break;                               
            }
                
            
            node=node.next;
        }}
        catch(NullPointerException e){}
        this.size=this.size-count;
        //Recaliberate the Indexes
        node=this.head;
        for(int i=0;i<=this.size;i++)
        {
            node.index=i;
            node=node.next;
        }
        return (Integer.toString(count)+" Entries deleted from list");
    }
    public void select()
    {
        System.out.println("Select "+this.name);
        int j;
        Entry node=head;
        for(j=0;j<=size;j++)
        {
            if (j==0)
            {
                System.out.print("-------- ");
                for(int i=0;i<node.length;i++)
                {
                    System.out.printf("%-15s","--------------");
                }
                System.out.println();
                System.out.printf("%8s","|"+Integer.toString(node.index)+"|");
                System.out.print("|");
                
                node.display_entry();
                System.out.println();
                System.out.print("       ||");
                for(int i=0;i<node.length;i++)
                {
                    System.out.printf("%-15s",datatypes[datatypeId[i]]);
                }
                System.out.println();
                
                System.out.print("-------- ");
                for(int i=0;i<node.length;i++)
                {
                    System.out.printf("%-15s","--------------");
                }
                System.out.println();
                node=node.next;

            }
            
            else
            {
                System.out.printf("%8s","|"+Integer.toString(node.index)+"|");
                System.out.print("|");
                node.display_entry();
                System.out.print("|");
                System.out.println();
            node=node.next;
            }
        }
        System.out.println();
        System.out.println("Selected result count: "+Integer.toString(this.size));
        System.out.println();
    }
    public void select(String[] header)
    {
        System.out.println("Custom Select "+this.name);
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
                System.out.print("------  ");
                for(int j=0;j<header.length;j++)
                {
                    System.out.printf("%-15s","--------------");
                }   
                System.out.println();
                System.out.print("|"+Integer.toString(node.index)+"|  ");
                System.out.print("   |");
                node.display_entry(index); 
                
                System.out.println();
                System.out.print("        |");
                for(int j=0;j<header.length;j++)
                {
                    System.out.printf("%-15s",datatypes[datatypeId[i]]);                    
                }
                System.out.println();
                
                System.out.print("------  ");
                for(int j=0;j<header.length;j++)
                {
                    System.out.printf("%-15s","--------------");
                }
                System.out.println();
                node=node.next;
            }
            else
            {
                System.out.printf("%-8s","|"+Integer.toString(node.index)+"|  ");
                node.display_entry(index);
                System.out.print("|");
                System.out.println();
                node=node.next;
            }
           
        }
        System.out.println();
        System.out.println("Selected result count: "+Integer.toString(size));
        System.out.println();
    }
    public Entry getEntry(int index)
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
    
    
}


public class SourceCode_v3 {
    public static void main(String args[])
    {
        System.out.println("                               |-------------------------------------------------------|");
        System.out.println("                               |--------- Java Embedded Database Architecture ---------|");
        System.out.println("                               |------------------Java-NOSql-EDA-----------v2.Mk-III---|");

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
        employee_table.insert(new String[]{"1006","Suman","1/1/2000","e","Manufacturing","23000","9000","4400"});
        employee_table.insert(new String[]{"1007","Tanmay","12/06/2006","c","PM","29000","12000","10000"});
        employee_table.insert(new String[]{"1010","Rajeev","12/08/2016","m","Engg","26000","17000","10000"});
        employee_table.insert(new String[]{"4009","Sanjay","13/09/2021","e","Manufacturing","18000","7500","3000"});
        employee_table.insert(new String[]{"1004","Chahat","29/01/2013","r","Front Desk","12000","6000","2000"});
        employee_table.insert(new String[]{"4009","Sanjay","13/09/2021","e","Manufacturing","18000","7500","3000"});
        System.out.println(
        employee_table.insert
        (   (new String[]{"Emp_No.","Emp_Name"}),
            (new String[]{"1305","Hemant"})));
        employee_table.insert
        ((new String[]{"Join_Date","Design_Code","Dept","Basic","HRA","IT"}),
        new String[]{"13/09/2021","e","Manufacturing","18000","7500","3000"});
        employee_table.insert(new String[]{"3209","Manisha","05/12/2019","i","Manufacturing","helloWorld","7500","3000"});
        employee_table.showStructure();
        employee_table.select();
        System.out.println(employee_table.delete(new int[]{5,8,18}));
        employee_table.select();

        //-------------------------Custom select code --------------------------------//
        //employee_table.select(new String[]{"Emp_No.","Emp_Name","Dept","Join_Date","HRA","IT"});

        // -----------------------------Table 2 insertion----------------------------//
        //Table Design_table=Table.generateTable
        //(new String[]{"Design_Code","Designation","DA"},
        //new String[]{"character","String","Integer"});
        //Design_table.insert(new String[]{"e","Engineer","20000"});
        //Design_table.insert(new String[]{"c","Consultant","32000"});
        //Design_table.insert(new String[]{"k","Clerk","12000"});
        //Design_table.insert(new String[]{"r","Receptionist","15000"});
        //Design_table.insert(new String[]{"m","Manager","40000"});
        //Design_table.select();
        //Design_table.insert(new String[]{"Designation","DA"},
        //new String[]{"Intern","1000"});
        //Design_table.select();
        


        // -----------------------------Join Table logic and insertion----------------------------//
        //Table joint_table = new Table
       // (new String[]{"Emp_No.","Emp_Name","Join_Date","Dept","Basic","HRA","IT","Design_Code","Designation","DA",},
        //new String[]{"string.","string","date","character","integer","integer","Integer","Character","String","Integer",});
        //String[] str=new String[employee_table.fields+Design_table.fields-1];
        //joint_table.select();
    }
}
