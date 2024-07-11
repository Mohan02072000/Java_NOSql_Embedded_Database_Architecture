package com.HansEnterprices;

import java.io.IOException;

public class create_database {

	public static void main(String[] args) throws IOException {
		if(args.length ==2)
        {
            Database database=new Database(args[0], args[1]);
            System.out.println("Database creation Successfull. Database name:"+database.db_name);
            System.out.println("Database address :"+database.Address);
            System.out.println("Block repository address :"+database.Address+"/Registries/"+database.db_name+".blr");
            System.out.println("Function repository address :"+database.Address+"/Registries/"+database.db_name+".fnr");
        }
        else{
            System.out.println("Database creation failed. Illegal arguments");
            System.out.println("Syntax: create_database <database_name> <File_Location>");
        }

	}

}
