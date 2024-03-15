class Table                                                                                                //Class for creating and handling Table data
{
    protected String[][] data;
    Table()                                                                                                //Overridden Constructor to load data into Table object
    {
        this.load_data();
    }
    Table(int size)                                                                                        //Overridden constructor variant to specify number of entries
    {
        this.data=new String[size][];
        this.load_data();
    }
    Table(int row_no,int column_no)                                                                        //Overridden Constructor to create table object with fixed size
    {
        this.data=new String[column_no][row_no];
        this.load_data();
    }
    public void load_data()                                                                                //Function to load data into the Table object, Called during object creation
    {
        //Override this function to insert data into your Table Object
        System.out.println("Override this function to insert data into your Table Object");
    }
    public void display_table()                                                                            //Function to display all the data in the Table object
    {
        for(int i=0;i<this.data.length;i++)
        {
        for(int j=0;j<this.data[i].length;j++)
        {
            System.out.printf("%-15s",data[i][j]);
            
        }
        if(i==0)
        {
            System.out.printf("%n");
            for(int j=0;j<this.data[i].length;j++)
            System.out.printf("----------     ");
        }
        System.out.printf("%n");
        }
    }
    public void display_table(String... args)                                                             //Function to display Table data according to the categories only
    {
        for(int i=0;i<this.data.length;i++)
        {
        for(int j=0;j<this.data[i].length;j++)
        {
            for(int k=0;k<args.length;k++)
            if(this.data[0][j]==args[k])
            System.out.printf("%-15s",data[i][j]);
            

        }
        if(i==0)
        {
            System.out.printf("%n");
            for(int j=0;j<this.data[i].length;j++)
            System.out.printf("----------     ");
        }
        System.out.printf("%n");
        }
        

    }  
    public String[] get_entry(String column, String entry)                                                //Overridden Function returns entry data in an array, returns matchiing column data entry                                         
    {
        int i;
        for(i=0;i<this.data[0].length;i++)
            if(this.data[0][i].equals(column))
            break;
        if(i==this.data[0].length)
        {
            System.out.println("Could not find record.");
            
        }
        for(int j=i;j<this.data.length;j++)
        if(this.data[j][i].equals(entry))
        return this.data[j];  
        return null;      
    }
    public String[] get_entry(int entry)                                                                   //Overridden Function returns entry data in an array takes in entry index            
    {
        return this.data[entry];
    }
    public void display_entry(String[] entry)                                                              //Function to display entry array in the entry form
    {
        for(int j=0;j<entry.length;j++)
        {
            System.out.printf("%-15s",entry[j]);
        }
        System.out.println();
    }
    public String get_data(String column_name, String search_data,String search_column)                    //Overriden Function Extract column data providing column data given, entry data, column data to extract
    {
        int i,j,column=-1,index=-1;
        for(i=0;i<this.data[0].length;i++)
        {
            if(this.data[0][i].equals(search_column))
            {
            column=i;
            }
            if(this.data[0][i].equals(column_name))
            index=i;
        }
        if(index!=-1)
        {
        for(j=0;j<this.data.length;j++)
        if(this.data[j][index].equals(search_data))
        break;

        if(j!=this.data.length && column!=-1)
        {
            return this.data[j][column];
        }
        else if(column==-1)
        {
            System.out.println("Invalid Search column");
        }
        else
        System.out.println("Entry not found");
        }
        else
        System.out.println("Column not found");       
        
        System.out.println("Data not found...");
        return null;
    }
    public String get_data(int row,int column)                                                        //Overriden Function Extract column data providing table row and column
    {
        return this.data[row][column];
    }
    public String get_data(String column_name,int entry)                                             //Overriden Function Extract column data providing column name and entry number
    {
        int i;
        for(i=0;i<this.data[0].length;i++)
        if(this.data[0][i].equals(column_name))
        break;

        if(i!=this.data[0].length)
        {
            return this.data[entry][i];
        }
        System.out.println("Entry not found");
        return null;
    }
    public int[] size()                                                                              //Reurns rows and columns in 1d array
    {
        return new int[]{this.data[0].length,this.data.length};
    }
    
}

class Project_1
{
    public static void main(String[] args)
    {
        Table employee_table=new Table()
        {
            public void load_data()
            {
            this.data=new String[8][];

            this.data[0]=new String[]{"Emp_No.","Emp_Name","Join_Date","Design_Code","Dept","Basic","HRA","IT"};
            this.data[1]=new String[]{"1001","Asish","01/04/2009","e","R&D","20000","8000","3000"};
            this.data[2]=new String[]{"1002","Sushma","23/08/2012","c","PM","30000","12000","9000"};
            this.data[3]=new String[]{"1003","Rahul","12/11/2008","k","Acct","10000","8000","1000"};
            this.data[4]=new String[]{"1004","Chahat","29/01/2013","r","Front Desk","12000","6000","2000"}; 
            this.data[5]=new String[]{"1005","Ranjan","16/07/2005","m","Engg","50000","20000","20000"}; 
            this.data[6]=new String[]{"1006","Suman","1/1/2000","e","Manufacturing","23000","9000","4400"};
            this.data[7]=new String[]{"1007","Tanmay","12/06/2006","c","PM","29000","12000","10000"};
            }
        };                                                                                            //Override load_data() function to insert data into table Employee table
        

        Table Design_table=new Table()
        {
            public void load_data()
            {
            this.data=new String[6][];
            this.data[0]=new String[]{"Design_Code","Designation","DA"};
            this.data[1]=new String[]{"e","Engineer","20000"};
            this.data[2]=new String[]{"c","Consultant","32000"}; 
            this.data[3]=new String[]{"k","Clerk","12000"};
            this.data[4]=new String[]{"r","Receptionist","15000"};
            this.data[5]=new String[]{"m","Manager","40000"};
            }
        };                                                                                            //Override load_data() function to insert data into table Designation table

        Table joint_table=new Table()
        {
            public void load_data()
            {                
                this.data=new String[employee_table.size()[1]][employee_table.size()[0]+Design_table.size()[0]];
                for(int i=0;i<employee_table.size()[0];i++)
                {                     
                    for(int j=0;j<this.size()[1];j++)
                    {
                        this.data[i][j]=employee_table.get_data(i,j);                
                    }    
                }
                for(int i=0;i<this.data.length;i++)
                {
                    String[] entry=Design_table.get_entry("Design_Code",employee_table.get_data("Design_Code",i));
                for(int j=1;j<entry.length;j++)
                this.data[i][j+employee_table.size()[0]-1]=entry[j];
                }
                this.data[0][this.data[0].length-1]="Salary";
                
                for(int i=1;i<this.data.length;i++)
                this.data[i][this.data[0].length-1]=Integer.toString(Integer.parseInt(this.get_data("Basic",i))+Integer.parseInt(this.get_data("HRA",i))+Integer.parseInt(this.get_data("DA",i))-Integer.parseInt(this.get_data("IT",i)));

                
            }
        };                                                                                            //Override load_data() function to insert data into table into joint table.Logic applied to join emp data and designation data and calculate salary
        System.out.println();
        System.out.println("*********************Employee table***************************");
        System.out.println();
        employee_table.display_table();
        System.out.println();
        System.out.println("*********************Design table***************************");
        System.out.println();
        Design_table.display_table();
        System.out.println();
        System.out.println();
        System.out.println("*********************Joint table***************************");
        System.out.println();


        if(args.length==0)
        joint_table.display_table();
        else if(args.length==1)
        {
            try{
            joint_table.display_entry(joint_table.get_entry(0));
            System.out.println();
            joint_table.display_entry(joint_table.get_entry("Emp_No.",args[0]));
            }
            catch(NullPointerException e)
            {
                System.out.println("No records found with data "+args[0]);
            }
        }
        
        joint_table.display_table("Emp_No.","Emp_Name","Designation");
    }
}