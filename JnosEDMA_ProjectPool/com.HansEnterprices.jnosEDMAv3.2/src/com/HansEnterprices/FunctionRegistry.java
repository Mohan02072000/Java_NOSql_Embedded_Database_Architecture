package com.HansEnterprices;

import java.io.IOException;
import java.io.Serializable;

final class FunctionRegistry implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -785932920660591122L;
	private FunctionUnit unitHead;
    private FunctionUnit unitTail;
    private String address;
    FunctionRegistry(String address)throws IOException
    {
        this.address=address;
    }
    public void registerNewFunction(Function function)throws IOException
    {
        FunctionUnit functionUnit=FunctionUnit.registerFunction(function,this.address);
        if(this.unitHead==null)
        {
            this.unitHead=functionUnit;
            this.unitTail=functionUnit;
            return;
        }
        this.unitTail.next=functionUnit;
        functionUnit.prev=this.unitTail;
        this.unitTail=functionUnit;
    }
    public Object runFunction(String name,BlockRegistry br)throws IOException
    {
        FunctionUnit fn=this.unitHead;
        while(fn!=null)
        {
            if(fn.name.equalsIgnoreCase(name))return fn.functionReturn(br);
            fn=fn.next;
        }
        System.out.println("Error: Could not find Function");
        return null;
    }
    public String getFunctionReturnType(String name)
    {
        FunctionUnit fn=this.unitHead;
        while(fn!=null)
        {
            if(fn.name.equalsIgnoreCase(name))return fn.return_type;
            fn=fn.next;
        }
        System.out.println("Error: Could not find Function");
        return null;
    }
    public void removeFunction(String name)
    {
        FunctionUnit fn=this.unitHead;
        while(fn!=null)
        {
            if(fn.name.equalsIgnoreCase(name))
            {
                fn.prev.next=fn.next;
                fn.next.prev=fn.prev;
                System.gc();
            }
            fn=fn.next;
        }
    }
    public void showAllFunctions()
    {
        System.out.println();
        System.out.printf("%-30s   ","-------------------------------");
        System.out.printf("%-15s   ","------------");
        System.out.printf("%-12s   ","------------");
        System.out.printf("%-30s   ","-------------------------------");
        System.out.printf("%-30s   ","-------------------------------");
        System.out.println();
        FunctionUnit fn=this.unitHead;
        System.out.printf("%-30s ||","FUNCTION NAME");
        System.out.printf("%-15s ||","FUNCTION TYPE");
        System.out.printf("%-12s ||","RETURN TYPE");
        System.out.printf("%-30s ||","FUNCTION ADDRESS");
        System.out.printf("%-30s ||","FUNCTION FILE NAME");
        System.out.println();
        System.out.printf("%-30s   ","-------------------------------");
        System.out.printf("%-15s   ","------------");
        System.out.printf("%-10s   ","------------");
        System.out.printf("%-30s   ","-------------------------------");
        System.out.printf("%-30s   ","-------------------------------");
        System.out.println();
        while(fn!=null)
        {
            
             System.out.printf("%-30s ||",fn.name);
             System.out.printf("%-15s ||",fn.function_type);
             System.out.printf("%-12s ||",fn.return_type);
             System.out.printf("%-30s ||",fn.address);
             System.out.printf("%-30s ||",fn.functionFileName);
             System.out.println();
             fn=fn.next;     
            
        }        
        System.out.printf("%-30s   ","-------------------------------");
        System.out.printf("%-15s   ","------------");
        System.out.printf("%-12s   ","------------");
        System.out.printf("%-30s   ","-------------------------------");
        System.out.printf("%-30s   ","-------------------------------");
        System.out.println();        
    }


}
