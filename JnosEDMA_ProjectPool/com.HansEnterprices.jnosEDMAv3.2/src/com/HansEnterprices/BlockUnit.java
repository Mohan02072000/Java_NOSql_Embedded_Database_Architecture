package com.HansEnterprices;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.StreamCorruptedException;

final class BlockUnit implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1032318994747733246L;
	protected BlockUnit prev;
    protected BlockUnit next;
    String name;
    String tableName;
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
        bUnit.tableName=block.name;
        FileOutputStream fos=new FileOutputStream(bUnit.address+bUnit.blockFileName+".blo");
        ObjectOutputStream oos=new ObjectOutputStream(fos);
        oos.writeObject(block);
        oos.close();
        fos.close();
        return bUnit;
    } 

}
