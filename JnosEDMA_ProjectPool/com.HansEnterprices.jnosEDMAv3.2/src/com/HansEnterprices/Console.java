package com.HansEnterprices;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;

public final class Console {

    private static Database database=null;
    private static Table table=null;
    private static String[][] view=null;
    private static Function function=null;
    public static void createDb()throws IOException
    {
        InputStreamReader i= new InputStreamReader(System.in);
        BufferedReader b= new BufferedReader(i);
        System.out.printf("%15s"," Database name >");
        String db_name=b.readLine();
        System.out.printf("%15s"," Address > ");
        String address=b.readLine();
        database=new Database(db_name,address);
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
        database=(Database)ois.readObject();
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
    public static void importView()throws IOException
    {
        InputStreamReader i= new InputStreamReader(System.in);
        BufferedReader b= new BufferedReader(i);
        System.out.print("Give file name >");
        String input=b.readLine();
        try{
            FileInputStream fis=new FileInputStream(input);
            ObjectInputStream ois=new ObjectInputStream(fis);
            view=(String[][])ois.readObject();
            System.out.println("View loaded to console");
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
    public static void selectDbTableWhere()throws Exception
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
        int[][] index=database.getBlock(tableName).table.where(clause);
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
    public static void selectWhere()throws Exception
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
        try{
        if(database.getFunctionReturnType(functionName).equalsIgnoreCase("Table"))
        {
            table=(Table)database.runFunction(functionName);
            System.out.println("Function output table has been loaded in Console table");
        }
        else if(database.getFunctionReturnType(functionName).equalsIgnoreCase("View"))
        {
            view=(String[][])database.runFunction(functionName);
            System.out.println("Function output View has been loaded in Console View");
        }
        }
        catch(NullPointerException e)
        {
            database.runFunction(functionName);            
        }
    }
    public static void runConsoleFunction()throws IOException
    {
        if(function.getReturnType().equalsIgnoreCase("Table"))
        {
            table=(Table)function.run(database.blRegistry);
            System.out.println("Function output table has been loaded in Console table");
        }
        else if(function.getReturnType().equalsIgnoreCase("View"))
        {
            view=(String[][])function.run(database.blRegistry);
            System.out.println("Function output View has been loaded in Console View");
        }
    }
    public static void loadConsoleTableToDb()throws IOException
    {
        database.newBlock(Block.generateBlock(table.name,table.getHeaders(),table.getDataTypes(),table.noNull,table.noDuplicate));
        database.getBlock(table.name).table.insert(table.getView());
    }
    public static void loadConsoleFunctionToDb()throws IOException
    {
        database.newFunction(function);
    }
    public static void loadFunctionToConsole()throws IOException
    {
        System.out.print(" Give file path >");
        InputStreamReader i= new InputStreamReader(System.in);
        BufferedReader b= new BufferedReader(i);
        String input=b.readLine();
        try{
            FileInputStream fis=new FileInputStream(input);
            ObjectInputStream ois=new ObjectInputStream(fis);
            function=(Function)ois.readObject();
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
    public static void deleteFromTableWhere()throws Exception
    {
        InputStreamReader i= new InputStreamReader(System.in);
        BufferedReader b= new BufferedReader(i);    
        System.out.print("Table name >");
        String tableName=b.readLine();    
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
        Block block=database.getBlock(tableName);
        if(input.equalsIgnoreCase("OR"))
        System.out.println(block.table.delete(index[0]));
        if(input.equalsIgnoreCase("AND"))
        System.out.println(block.table.delete(index[1]));
        block.table.select();
        database.updateBlock(block.name, block);
    }
    public static void formInputConsoleTable()throws Exception
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
    public static void updateDbTableWhere()throws Exception
    {
        InputStreamReader i= new InputStreamReader(System.in);
        BufferedReader b= new BufferedReader(i);
        System.out.print("Table name >");
        String tableName=b.readLine();
        String[] values=new String[table.getHeaders().length];
        System.out.println("Input values into the form below ");
        String[] headers=database.getBlock(tableName).table.getHeaders();
        for(int x=0;x<database.getBlock(tableName).table.fields;x++)
        {
            
            System.out.printf("%15s",headers[x]+" : ");
            values[x]=b.readLine();
        }
        System.out.print(" Enter number of where clauses :");
        int n=Integer.parseInt(b.readLine());
        String[][] clause=new String[n][3];
        for(int x=0;x<clause.length;x++)
        {
            System.out.println("Input for Clause ["+Integer.toString(x+1)+"]");
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
    public static void customUpdateDbTableWhere()throws Exception
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
    public static void insertIntoDBTableFromViewFile()throws IOException
    {
        InputStreamReader i= new InputStreamReader(System.in);
        BufferedReader b= new BufferedReader(i);
        System.out.print("Table name in ./Source/ >");
        String tableName="./Source/"+b.readLine();
        Block block=database.getBlock(tableName);
        block.table.insert(view);
        database.updateBlock(block.name,block);
    }
    public static void insertIntoDBTableFromView()throws IOException
    {
        InputStreamReader i= new InputStreamReader(System.in);
        BufferedReader b= new BufferedReader(i);
        System.out.print("Give table name >");
        String tableName=b.readLine();
        Block block=database.getBlock(tableName);
        block.table.insert(view);
        database.updateBlock(block.name,block);
    }
    public static void addFunction()throws IOException
    {
        InputStreamReader i= new InputStreamReader(System.in);
        BufferedReader b= new BufferedReader(i);
        System.out.print("Function file name in ./Source/ >");
        String tableName="./Source/"+b.readLine();
        try{
            FileInputStream fis=new FileInputStream(tableName);
            ObjectInputStream ois=new ObjectInputStream(fis);
            Function function=(Function)ois.readObject();
            database.newFunction(function);
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
    public static void printLogo()
    {
                System.out.println("         ___________  ___                                                               __    __ ");
		        System.out.println("        /   _____   |/  /                                                            _/  /  /  /");
		        System.out.println("       /   /     |  /  /                                                            //  /__/  /");
		        System.out.println("      /   /      / /  /____                                                        //  ___  _/__");
		        System.out.println("     /   /      / /   __   |               -:HANS ENTERPRICES PVT LTD:-           //  /_ / / ___/");
		        System.out.println("    /   /______/ /  /__ /__/_______                                              //__/ //_/ _/_");
		        System.out.println("   /____________/_______/  ____   /                   AUTHOR                    /__/  /_ /____/");
		        System.out.println("                       /  /   /__/            JK MOHAN KAPUR HANSDAH                   /____/");
		        System.out.println("                      /  /       _____  __ __   ______ _____  __   ______");
		        System.out.println("                     /  /    __ /     // /_  | /  ___//     // /  / ____/");
		        System.out.println("                    /  /___/  //     // / /  //___  //     // /_ / __/_");
		        System.out.println("                   /_________//_____//_/ /__//_____//_____//___//_____/");                           
		        System.out.println("                         --:DATABASE CONSOLE:--                      ");
                System.out.println("-----------------------------------------------------------------------------------------");
                System.out.println("              -:Java Embedded Data Management Architecture:-       Console Draft-III");
                System.out.println("                           -:J-NOSql-EDMA:-                         -:v3.Mk-III.II.II:-");
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
            case "LOAD CONSOLE FUNCTION TO DB":
            loadConsoleFunctionToDb();
            break;
            case "LOAD FUNCTION TO CONSOLE FROM FILE":
            loadFunctionToConsole();
            break;
            case "LOAD VIEW FROM FILE":
            importView();
            break;
            case "DROP TABLE FROM DB":
            dropBlock();
            break;
            case "DROP FUNCTION FROM DB":
            System.out.print("Give function name >");
            input=b.readLine();
            database.removeFunction(input);
            break;
            case "SELECT DB TABLE":
            selectDbTable();
            break;
            case "SHOW ALL DB TABLES":
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
            case "RUN CONSOLE FUNCTION":
            runConsoleFunction();
            break;
            case "DB COMMIT":
            database.commit();
            break;
            case "INSERT INTO DB FROM CONSOLE VIEW":
            insertIntoDBTableFromView();
            break;
            case "ADD NEW FUNCTION":
            addFunction();
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
            deleteFromTableWhere();
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
            case "LOAD DB TABLE TO CONSOLE":
            System.out.print("Table name >");
            input=b.readLine(); 
            table=database.getBlock(input).table; 
            System.out.println("Table loaded from console"); 
            break;
            case "LOAD CUSTOM VIEW FROM CONSOLE TABLE":
            System.out.print("header names >");
            input=b.readLine();
            view=table.getView(input.trim().split(" "));
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
            e.printStackTrace();
        }
        catch(IOException e){
            e.printStackTrace();
            System.out.println("Error :IO Exception");
        }
        }
        }
}
