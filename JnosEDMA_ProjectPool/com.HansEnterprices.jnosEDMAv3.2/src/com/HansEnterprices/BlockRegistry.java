package com.HansEnterprices;

import java.io.IOException;
import java.io.Serializable;

final class BlockRegistry implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1381463081391426604L;
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
                return;
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
        System.out.printf("%-15s ||","BLOCK FILE NAME");
        System.out.printf("%-30s ||","BLOCK ADDRESS");
        System.out.println();
        System.out.printf("%-15s   ","---------------");
        System.out.printf("%-15s   ","---------------");
        System.out.printf("%-15s   ","---------------");
        System.out.printf("%-30s   ","-------------------------------");
        
        System.out.println();
        while(bUnit!=null)
        {
            
             System.out.printf("%-15s ||",bUnit.name);
             System.out.printf("%-15s ||",bUnit.tableName);
             System.out.printf("%-15s ||",bUnit.blockFileName);
             System.out.printf("%-30s ||",bUnit.address);
             System.out.println();
             bUnit=bUnit.next;     
            
        }        
        System.out.printf("%-15s   ","---------------");
        System.out.printf("%-15s   ","---------------");
        System.out.printf("%-15s   ","---------------");
        System.out.printf("%-30s   ","-------------------------------");
        
        System.out.println();
    }    

}
