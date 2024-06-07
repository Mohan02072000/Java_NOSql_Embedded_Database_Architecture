import java.io.*;

class Entry implements Serializable
{
    private String[] data;
    protected int size;
    protected int index;
    protected Entry prev;
    protected Entry next;
    Entry(int size)
    {
        this.data=new String[size];
        this.size=size;
    }
    public String[] getData()
    {
        return data;
    }
    public void putData(String[] newData)
    {
        for (int i=0;i<data.length;i++)
        {
            this.data[i]=newData[i];
        }
        
    }
    public Entry getEntry()
    {
        return this;
    }
    public String[] getEntryArray()
    {
        String[] data=new String[size];
        for(int i=0;i<size;i++)
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
        return data[index];
    }
   
}

class Table implements Serializable
{
    private Entry head;
    private byte[] datatypeId;
    protected int size;
    protected int fields;
    private String[] datatypes=
    {"Undefined","Boolean","Integer","Date","Character","String"};
    Table (String [] header, String[] datatype)
    {
        this.head=new Entry(header.length);
        this.datatypeId=new byte[header.length];
        head.putData(header);
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
        System.out.println("Table created Successfully");
    }
    public int[] getSequence(String[] header)
    {
        int[] index=new int[header.length];
        for(int i=0;i<header.length;i++)
        for(int j=0;j<head.size;j++)
        {
            if(header[i].equals(head.getData(j)))
            index[i]=j;
        }
        System.out.print("Node Index Sequence : ");
        for(int i=0;i<index.length;i++)
        {
            System.out.print(Integer.toString(index[i])+"  ");
        }
        System.out.println();
        return index;
    }
    public void insert(String[] entry)
    {
        if(entry.length==head.size)
        {
        Entry node=head;
        Entry newNode=new Entry(head.size);
        newNode.putData(entry);
        while(node.next!=null)
        {
            node=node.next;
        }
        newNode.index=node.index+1;
        node.next=newNode;
        newNode.prev=node;
        this.size++;

        System.out.println("Insertion Successful");
        }
        else{
            System.out.println("Insertion Failed: Array size not in sync with the table size");
        }
    }
    public void insert(String[] header,String[] entry)
    {
        int[] index= getSequence(header);
        String[] insertstring=new String[this.fields];
        System.out.print("Custom Insert Index Sequence : ");
        for(int i=0;i<index.length;i++)
        {
            System.out.print(Integer.toString(index[i])+"  ");
        }
        System.out.println();
        for(int j=0;j<index.length;j++)
        {
            insertstring[index[j]]=entry[j];
        }
        this.insert(insertstring);
        for(int j=0;j<entry.length;j++)
        {
            System.out.print(insertstring[j]+"  ");
        }
        System.out.println("Insertion Successful");
    }
    public void delete(int index)
    {
        Entry node=this.head;
        for(int i=0;i<this.size;i++)
        {
            if(node.index==index-1)
            {
                node.next=node.next.next;
                node.next.prev=node;                
            }
            node=node.next;
        }
        this.size--;
        node=this.head;
        for(int i=0;i<=this.size;i++)
        {
            node.index=i;
            node=node.next;
        }
        System.out.println("Entry deleted from list");
    }
    public void select()
    {
        int j;
        Entry node=head;
        for(j=0;j<=size;j++)
        {
            if (j==0)
            {
                System.out.print("--- ");
                for(int i=0;i<node.size;i++)
                {
                    System.out.printf("%-15s","--------------");
                }
                System.out.println();
                System.out.print("|"+Integer.toString(node.index)+"|  ");
                
                
                node.display_entry();
                System.out.println();
                System.out.print("     ");
                for(int i=0;i<node.size;i++)
                {
                    System.out.printf("%-15s",datatypes[datatypeId[i]]);
                }
                
                System.out.println();
                System.out.print("--- ");
                for(int i=0;i<node.size;i++)
                {
                    System.out.printf("%-15s","--------------");
                }
                System.out.println();
                node=node.next;

            }
            
            else
            {
                System.out.print("|"+Integer.toString(node.index)+"|  ");
                node.display_entry();
                System.out.print("|");
                System.out.println();
            node=node.next;
            }
        }
        System.out.println();
        System.out.println("Selected result count: "+Integer.toString(size));
        System.out.println();
    }
    public void select(String[] header)
    {
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
                System.out.print("--- ");
                for(int j=0;j<header.length;j++)
                {
                    System.out.printf("%-15s","--------------");
                }   
                System.out.println();
                System.out.print("|"+Integer.toString(node.index)+"|  ");
                
                node.display_entry(index); 
                
                System.out.println();
                System.out.print("     ");
                for(int j=0;j<header.length;j++)
                {
                    System.out.printf("%-15s",datatypes[datatypeId[i]]);                    
                }
                System.out.println();
                System.out.print("--- ");
                for(int j=0;j<header.length;j++)
                {
                    System.out.printf("%-15s","--------------");
                }
                System.out.println();
                node=node.next;
            }
            else
            {
                System.out.print("|"+Integer.toString(node.index)+"|  ");
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

        return new Entry(0);
    }
    
    
}


class SourceCodeMain_v1
{
    public static void main(String args[])throws IOException
    {
        System.out.println("                               |-------------------------------------------------------|");
        System.out.println("                               |--------- Java Embedded Database Architecture ---------|");
        System.out.println("                               |---------------------J-NOSql-EDA-----------v2.Mk-I-----|");
        System.out.println();
        System.out.println();
        // -----------------------------Table 1 insertion----------------------------//
        //Table employee_table=new Table(
           // (new String[]{"Emp_No.","Emp_Name","Join_Date","Design_Code","Dept","Basic","HRA","IT"}),
           // (new String[]{"string","String","Date","character","String","integer","integer","integer"}));
           // employee_table.insert(new String[]{"1001","Asish","01/04/2009","e","R&D","20000","8000","3000"});
          //  employee_table.insert(new String[]{"1002","Sushma","23/08/2012","c","PM","30000","12000","9000"});
           // employee_table.insert(new String[]{"1003","Rahul","12/11/2008","k","Acct","10000","8000","1000"});
          //  employee_table.insert(new String[]{"1004","Chahat","29/01/2013","r","Front Desk","12000","6000","2000"});
           // employee_table.insert(new String[]{"1005","Ranjan","16/07/2005","m","Engg","50000","20000","20000"});
           // employee_table.insert(new String[]{"1006","Suman","1/1/2000","e","Manufacturing","23000","9000","4400"});
           // employee_table.insert(new String[]{"1007","Tanmay","12/06/2006","c","PM","29000","12000","10000"});
          //  employee_table.insert(new String[]{"1010","Rajeev","12/08/2016","m","Engg","26000","17000","10000"});
           // employee_table.insert(new String[]{"4009","Sanjay","13/09/2021","e","Manufacturing","18000","7500","3000"});
            //employee_table.showStructure();
           // employee_table.select();
           // employee_table.delete(5);
           // employee_table.select();

        //serialise
        
        //FileOutputStream fos=new FileOutputStream((employee_table.toString())+".to");
        //ObjectOutputStream oos=new ObjectOutputStream(fos);
        
        

        //-------------------------Custom select code --------------------------------//
        //employee_table.select(new String[]{"Emp_No.","Emp_Name","Dept","Join_Date","HRA","IT"});

        // -----------------------------Table 2 insertion----------------------------//
        Table Design_table=new Table
        (new String[]{"Design_Code","Designation","DA"},
        new String[]{"character","String","Integer"});
        Design_table.insert(new String[]{"e","Engineer","20000"});
        Design_table.insert(new String[]{"c","Consultant","32000"});
        Design_table.insert(new String[]{"k","Clerk","12000"});
        Design_table.insert(new String[]{"r","Receptionist","15000"});
        Design_table.insert(new String[]{"m","Manager","40000"});
        Design_table.insert(new String[]{"Designation","DA"},
        new String[]{"Intern","1000"});
        Design_table.select();

        
        //oos.writeObject(employee_table);
        //oos.writeObject(Design_table);

        //fos.close();
        //oos.close();

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
	public static void CreateTable() throws IOException
	{
		String Input;
		int col;
		System.out.print("Number of fields? :");
		col=Integer.parseInt(b.readLine());
		String[][] Headers=new String[2][col];
		System.out.println("Provide headers with datatype. Separete them with space");
		for(int i=0;i<col;i++)
		{
		System.out.print("Create Table >");
    	Input=b.readLine();
    	Headers[0][i]=Input.split(" ")[0];
    	Headers[1][i]=Input.split(" ")[1];
		}
		table = new Table(Headers[0],Headers[1]);
		
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
        
    }
    public static void xportTable()throws IOException
    { 
        FileOutputStream fos=new FileOutputStream(table.toString()+".to");
        ObjectOutputStream oos=new ObjectOutputStream(fos);
        oos.writeObject(table);
        fos.close();
        oos.close();

    }
	public static void main(String args[]) throws IOException
    {
		        System.out.println("============================================================================================");
		        System.out.println("|==========================================================================================|");
		        System.out.println("||========================================================================================||");
		        System.out.println("|||                        _____         _____                                           |||");
		        System.out.println("|||                     _/      /     _/      /                                          |||");
		        System.out.println("|||                    //      /     //      /                                           |||");
		        System.out.println("|||                   //      /     //      /                                            |||");
		        System.out.println("|||                  //      /_____//      /                                             |||");
		        System.out.println("|||                 //                 ___/_____ _                                       |||");
		        System.out.println("|||                //      ________   /          /                                       |||");
		        System.out.println("|||               //      /______//  /    ______/                                        |||");
		        System.out.println("|||              //      /      //  /    /__                                             |||");
		        System.out.println("|||             //      /      //  /       /                                             |||");
		        System.out.println("|||            //______/      //_ /    ___/                                              |||");
		        System.out.println("|||           /______/       /__ /    /______           ::HANS ENTERPRISES PVT LTD::     |||");                           
		        System.out.println("|||                            //           /           :: JK MOHAN KAPUR HANSDAH        |||");
		        System.out.println("|||                           //___________/                                             |||");
		        System.out.println("|||                          /__________/                                                |||");
		        System.out.println("||=============================EMBEDDED DATABASE APPLICATION==============================||");
		        System.out.println("|===========================Java Embedded Database Architecture========Console Draft-I=====|");
		        System.out.println("=======================================J-NOSql-EDA=======================v2.Mk-I============");
        
        System.out.println();
        System.out.println();
       
        boolean run=true;
        
        while(run)
        {
        	
        	String Input;
        	System.out.print(">");
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
                    System.out.print("Field Names >");
                    Input=b.readLine();
                    for(int i=0;i<Input.split(" ").length;i++)
                    System.out.print(Input.split(" ")[i]+" ");
        			table.select(Input.split(" "));
                    break;
        		case "INSERT":
                while(true)
                    {
                        System.out.print("Insert >");
        			    Input=b.readLine();
                        if(Input.equalsIgnoreCase("exit insert")) break;
        			    table.insert(Input.split(" "));
                        
                    }
        			break;
                case "COMMIT":
                    xportTable();
                    break;
        		case "EXIT":
        			run=false;
                    break;
        		default:
        			System.out.println("Illegal command");
        	}
        	
        }
        
    }
}