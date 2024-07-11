package com.HansEnterprices;

import java.io.IOException;
import java.io.Serializable;

public interface Function extends Serializable {
	String name=null;
    String Return_Type=null;
    String function_type=null;
    public abstract Object run(BlockRegistry block_Registry)throws IOException;
    public String getName();
    public String getReturnType();
    public String getFunctionType();
    
}
