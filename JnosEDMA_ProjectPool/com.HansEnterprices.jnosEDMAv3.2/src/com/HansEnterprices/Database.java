package com.HansEnterprices;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public final class Database implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5918189635576084920L;
	String Address;
    String db_name;
    FunctionRegistry fnRegistry;
    BlockRegistry blRegistry;
    Database(String db_name,String address)throws IOException
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
        FileOutputStream fosdb=new FileOutputStream(Address+"/"+db_name+".dbo");
        ObjectOutput Oosdb =new ObjectOutputStream(fosdb);
        Oosdb.writeObject(this);
        fosdb.close();
    }
    public void newFunction(Function function)throws IOException
    {
        fnRegistry.registerNewFunction(function);
        FileOutputStream fosdb=new FileOutputStream(Address+"/"+db_name+".dbo");
        ObjectOutput Oosdb =new ObjectOutputStream(fosdb);
        Oosdb.writeObject(this);        
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
    public void removeFunction(String function_name)
    {
        fnRegistry.removeFunction(function_name);
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
    public String getFunctionReturnType(String name)
    {
        return fnRegistry.getFunctionReturnType(name);
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
        fosdb.close();
        Oosdb.close();
    }

}
