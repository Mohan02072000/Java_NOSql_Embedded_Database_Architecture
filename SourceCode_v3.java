import java.io.*;

final class Entry implements Serializable
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
    public void display_entry(int[] index)
    {
        for(int i=0;i<index.length;i++)
        {
            if(index[i]!=-1)
            System.out.printf("%-15s",this.data[index[i]]);            
        }
    }
    public String getData(int index)
    {
        return new String(data[index]);
    }
    public void updateData(String value,int index)
    {
        data[index]=new String(value);
    }
   
}

final class Table implements Serializable
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
    public static Table generateTable (String name,String[] header, String[] datatype, boolean[] noNull, boolean[] noDuplicate) //factory function for setting no null and no dupl
    {
        return new Table(name,header,datatype,noNull, noDuplicate);
    }
    public static Table generateTable(String name,String[] header, String[] datatype)        //factory function dupl and null set to false
    {
        return new Table(name,header,datatype,new boolean[header.length],new boolean[header.length]);
    }
    public String[] getHeaders()                                                     //get headers array
    {
        return head.getEntryArray();
    }
    public void recaliberate()throws NullPointerException                            // Recaliberate indexes and linkedlist
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
    public void showStructure()                                                      //Show table structure
    {
        Entry node=this.head;
            System.out.print(" --------------  ");
                for(int i=0;i<node.length;i++)
                {
                    System.out.printf("%-15s","--------------");
                }
                System.out.println();
                System.out.print("|FieldName-    |  ");
                
                
                node.display_entry(getSequence(node.getEntryArray()));
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
    public int[] getSequence(String[] header)                                         //get headers sequence for custom insert and select
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
    public String insert(String[] entry)throws NullPointerException                   //Insert
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
    public String insert(String[] header,String[] entry)throws NullPointerException  //custom insert
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
    public String delete(int[] index)                                             //Delete
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
    public void select()throws NullPointerException                               //Select 
    {
        System.out.println("Select "+this.name);
        select(this.head.getEntryArray());
    }
    public void select(String[] header)throws NullPointerException                //Custom select
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
                System.out.print("=======  ");
                for(int j=0;j<header.length;j++)
                {
                    if(index[i]<0) continue;
                    System.out.printf("%-15s","==============");
                }   
                System.out.print("== ");
                System.out.println();
                System.out.printf("%8s","|"+Integer.toString(node.index)+"|");
                System.out.print("|");
                node.display_entry(index); 
                System.out.print("|| ");
                System.out.println();
                System.out.print("        |");
                for(int j=0;j<header.length;j++)
                {
                    if(index[i]!=-1) System.out.printf("%-15s",datatypes[datatypeId[j]]);                    
                }
                System.out.print("|| ");
                System.out.println();
                
                System.out.print("=======  ");
                for(int j=0;j<header.length;j++)
                {
                    if(index[i]<0) continue; 
                    System.out.printf("%-15s","==============");
                }
                System.out.print("=| ");
                System.out.println();
                node=node.next;
            }
            else
            {
                System.out.printf("%8s","|"+Integer.toString(node.index)+"|");
                System.out.print("|");
                node.display_entry(index);
                System.out.print("||");
                System.out.println();
                node=node.next;
            }
            
        }
                System.out.print("=======  ");
                for(int j=0;j<header.length;j++)
                {
                    if(index[j]>=0)System.out.printf("%-15s","==============");
                }
                System.out.print("== ");
        System.out.println();
        System.out.println("Selected result count: "+Integer.toString(size));
        System.out.println();
    }
    public void select(int[] index)throws NullPointerException                    //Filter select
    {
        System.out.println("Select "+this.name);
        select(this.head.getEntryArray(), index);

    }
    public void select(String[] header,int[] indexes)throws NullPointerException  //Custom filter select
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
                System.out.print("=======  ");
                for(int j=0;j<header.length;j++)
                {
                    System.out.printf("%-15s","==============");
                }  
                System.out.print("== "); 
                System.out.println();
                System.out.printf("%8s","|"+Integer.toString(node.index)+"|  ");
                System.out.print("|");
                node.display_entry(index);
                System.out.print("||"); 
                
                System.out.println();
                System.out.print("        |");
                for(int j=0;j<header.length;j++)
                {
                    if(index[j]>=0)System.out.printf("%-15s",datatypes[datatypeId[index[j]]]);                    
                }
                System.out.print("||");
                System.out.println();
                System.out.print("=======  ");
                for(int j=0;j<header.length;j++)
                {
                    System.out.printf("%-15s","==============");
                }
                System.out.print("=| ");
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
                node.display_entry(index);
                System.out.print("||");
                System.out.println();
                }
                node=node.next;
            }
           
        }
                System.out.print("=======  ");
                for(int j=0;j<header.length;j++)
                {
                    System.out.printf("%-15s","==============");
                } 
                System.out.print("== ");
        System.out.println();
        System.out.println("Selected result count: "+Integer.toString(indexes.length));
        System.out.println();
        

    }
    public Entry getEntry(int index)throws NullPointerException                   //find entry as per indexWPP
    
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


class SourceCode_v3Main {
    public static void main(String args[])throws IOException
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
        employee_table.select();  //test for select()
        //System.out.println(employee_table.delete(new int[]{5,8,18}));
        employee_table.select(new int[]{3,5,7,8});  //test for Select(int[] index)
        
        employee_table.select(new String[]{"Emp_No","Emp_Name","Design_Code","Dept","Basic"},new int[]{3,5,7,8}); //test for select(String headers[],int index[]);
        FileOutputStream fos=new FileOutputStream((employee_table.toString())+".to");
        ObjectOutputStream oos=new ObjectOutputStream(fos);
                oos.writeObject(employee_table);
                //oos.writeObject(Design_table);

        fos.close();
        oos.close();
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

class MainClass {
	private static InputStreamReader i=new InputStreamReader(System.in);
	private static BufferedReader b= new BufferedReader(i);
	private static Table table;
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
		System.out.print("Create Table >");
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
    public static void LoadTable(String filename)throws IOException
    {
        try{
        FileInputStream fos=new FileInputStream(filename);
        ObjectInputStream ois = new ObjectInputStream(fos);
            table=(Table)ois.readObject();
            System.out.println("Table Loaded");
            ois.close();        
        }
        catch(FileNotFoundException e)
        {
            System.out.println("File not found");
        }
        catch(ClassNotFoundException e)
        {
            System.out.println("Incompatible file");
        }
        catch(InvalidClassException e)
        {
            System.out.println("File version incompatible");
        }
        catch(StreamCorruptedException e)
        {
            System.out.println("File Corrupted");
        }
    }
    public static void xportTable()throws IOException
    { 
        FileOutputStream fos=new FileOutputStream(Integer.toString(table.hashCode())+".jnos");
        ObjectOutputStream oos=new ObjectOutputStream(fos);
        oos.writeObject(table);
        System.out.println("Table exported to present directory as "+Integer.toString(table.hashCode())+".jnos");
        //System.out.println("SerialVersionUID :"+Long.toString(Table.SerialVersionUID));
        fos.close();
        oos.close();

    }
    public static void showCatalog()
    {
        
        System.out.println("|==============================================================================================|");
        System.out.println("|::::::::::::::::::::::::::::::::: Commands Catalog :::::::::::::::::::::::::::::::::::::::::::|");
        System.out.println("|==============================================================================================|");
        System.out.println("|___________________________________________________________________________________________   |");
        System.out.println("||                                                                                         | V2|");
        System.out.println("|| CREATE TABLE : Create and load new table                                                |   |");
        System.out.println("|| LOAD TABLE : Load a table from file                                                     |   |");
        System.out.println("|| SELECT : Show all fields Table data                                                     | M |");
        System.out.println("|| CUSTOM SELECT : input field names to view their data                                    | A |");
        System.out.println("|| FILTER SELECT : Input index values to view them (Glitchy)                               | R |");
        System.out.println("|| CUSTOM FILTER SELECT : input Field and indexes to view their data (Glitchy)             | K |");
        System.out.println("|| INSERT : enter data as per the table to insert                                          |   |");
        System.out.println("|| CUSTOM INSERT: enter field names and corresponding data to insert                       |III|");
        System.out.println("|| FORM INPUT(new) : insert data from a form structure                                          |   |");
        System.out.println("|| CUSTOM FORM INPUT(new) : select fields for insert and insert values in a form structure      |   |");
        System.out.println("|| DELETE : delete records from index values                                               |   |");
        System.out.println("|| STRUCTURE (new): Show structure of the table                                                 |   |");
        System.out.println("|| RECALIBERATE (new): run to recaliberate table object                                         |   |");
        System.out.println("|| COMMIT : save and export table in the directory                                         |   |");
        System.out.println("|| EXIT : Quit application                                                                 |   |");
        System.out.println("||_________________________________________________________________________________________|   |");
        System.out.println("|                                            New features to be added in upcomming versions    |");
        System.out.println("|==============================================================================================|");

    }
	public static void main(String args[]) throws IOException
    {
		        System.out.println("================================================================================================================");
		        System.out.println("||============================================================================================================||");
		        System.out.println("||||========================================================================================================||||");
		        System.out.println("|||||                         _____         _____                                                          |||||");
		        System.out.println("|||||                      _/      /     _/      /                                                         |||||");
		        System.out.println("|||||                     //      /     //      /                                                          |||||");
		        System.out.println("|||||                    //      /     //      /                                                           |||||");
		        System.out.println("|||||                   //      /_____//      /                                                            |||||");
		        System.out.println("|||||                  //                 ___/_____ _                                                      |||||");
		        System.out.println("|||||                 //      ________   /          /                                                      |||||");
		        System.out.println("|||||                //      /______//  /    ______/                                                       |||||");
		        System.out.println("|||||               //      /      //  /    /__                                                            |||||");
		        System.out.println("|||||              //      /      //  /       /                                                            |||||");
		        System.out.println("|||||             //______/      //_ /    ___/                                                             |||||");
		        System.out.println("|||||            /______/       /__ /    /______                         ::HANS ENTERPRISES PVT LTD::      |||||");                           
		        System.out.println("|||||                             //           /                                                           |||||");
		        System.out.println("|||||                            //___________/                           ::JK MOHAN KAPUR HANSDAH         |||||");
		        System.out.println("|||||                           /__________/                                                               |||||");
		        System.out.println("||||====================================EMBEDDED DATABASE APPLICATION=======================================||||");
		        System.out.println("||===================================Java Embedded Database Architecture=============Console Draft-I==========||");
		        System.out.println("===============================================J-NOSql-EDA=============================v2.Mk-III================");
        
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
        		case "SELECT":
                    
        			table.select();
        			break;
        		case "CUSTOM SELECT":
                    if(table!=null) System.out.print(table.name+" ");
                    System.out.print("Field Names >");
                    Input=b.readLine();
                    for(int i=0;i<Input.split(" ").length;i++)
                    System.out.print(Input.split(" ")[i]+" ");
        			table.select(Input.split(" "));
                    break;
                case "FILTER SELECT":
                    
                    if(table!=null) System.out.print(table.name+" ");
                    System.out.print("Indexes >");
                    Input=b.readLine();
                    index=new int[Input.split(" ").length];
                    for(int i=0;i<index.length;i++)
                    index[i]=Integer.parseInt(Input.split(" ")[i]);
                    table.select(index);
                    break;

                case "CUSTOM FILTER SELECT":
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
                     
                    
        		case "INSERT":
                    if(table!=null) System.out.print(table.name);
                while(true)
                    {
                        System.out.print("Insert >");
        			    Input=b.readLine();
                        if(Input.equalsIgnoreCase("exit insert")) break;
        			    System.out.println(table.insert(Input.split(" ")));
                        
                    }
        			break;
                case "CUSTOM INSERT":
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
                case "FORM INPUT":
                        formInput();
                        break;

                case "CUSTOM FORM INPUT":
                        System.out.print("Headers >");
        			    Input=b.readLine();
                        if(Input.equalsIgnoreCase("exit insert") || Input.equalsIgnoreCase("cancel")) break;
                        customFormInsert(Input.split(" "));
                        break;
                case "DELETE":
                {
                        System.out.print("Indexes >");
        			    Input=b.readLine();
                        int[] arr=new int[Input.split(" ").length];
                        
                        
                            for(int i=0;i<Input.split(" ").length;i++)
                            arr[i]=Integer.parseInt(Input.split(" ")[i]);

                            System.out.println(table.delete(arr));
                        
                        break;
                }
                case "COMMIT":
                    xportTable();
                    break;
                case "STRUCTURE":
                     table.showStructure();
                     break; 
        		case "EXIT":
        			run=false;
                    break;
                case "SHOW CATALOG":
                    showCatalog();
                    break;

                case "RECALIBERATE":
                    table.recaliberate();
        		default:
        			System.out.println("Illegal command : Type SHOW CATALOG to view commands list");
                    table.recaliberate();
            }
        	}
            catch(NullPointerException e)
            {
                System.out.println("Err : no table loaded. Load a Table object file.");
            }
        	
        }
        
    }
}