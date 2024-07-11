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

final class FunctionUnit implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8116477595016176379L;
	protected FunctionUnit prev;
    protected FunctionUnit next;
    String name;
    String function_type;
    String address;
    String return_type;
    String functionFileName;
    private FunctionUnit(String name,String function_type,String address, String functionFileName,String return_type)
    {
        this.name=name;
        this.function_type=function_type;
        this.address=address+"/Functions/";
        this.functionFileName=functionFileName;
        this.return_type=return_type;
    }
    public static FunctionUnit registerFunction(Function function, String address)throws IOException
    {
        FunctionUnit newFunctionUnit=new FunctionUnit(function.getName(),function.getFunctionType(),address,function.toString(),function.getReturnType());
        FileOutputStream fos=new FileOutputStream(newFunctionUnit.address+newFunctionUnit.functionFileName+".fno");
        ObjectOutputStream oos=new ObjectOutputStream(fos);
        oos.writeObject(function);
        oos.close();
        fos.close();
        return newFunctionUnit;
    }
    public Object functionReturn(BlockRegistry br)throws IOException
    {
        Function function;
        try{
            FileInputStream fos=new FileInputStream(this.address+this.functionFileName+".fno");
            ObjectInputStream ois = new ObjectInputStream(fos);
                function=(Function)ois.readObject();
                ois.close();
                return function.run(br);
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

}
