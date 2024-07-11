package com.HansEnterprices;

import java.io.Serializable;

final class Block implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3901043120565153205L;
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
	        System.out.println("New Block Generated");
	        return new Block(blockName,headers,DataType);
	    }
	    public static Block generateBlock(String blockName,String[] headers,String[] DataType, boolean[] noNull, boolean[] noDuplicate)
	    {
	        System.out.println("New Block Generated");
	        return new Block(blockName,headers,DataType,noNull,noDuplicate);
	    }


}
