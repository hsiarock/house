package com.kph.demo2;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import oracle.dbtools.raptor.newscriptrunner.ScriptExecutor;
import oracle.dbtools.raptor.newscriptrunner.ScriptRunnerContext;
import oracle.dbtools.db.ResultSetFormatter;
 
public class RunMyScript {
	
  private final static String dbhost = "192.168.1.2";
  private final static String dbport = "1521";
  private final static String dbname = "XE";
  private final static String dbuser = "david";
  private final static String dbpasw = "alexgo12";
  private final static String connStr = "jdbc:oracle:thin:@//" + dbhost + ":" + dbport + "/" + dbname;

  public static void main(String[] args) throws SQLException, UnsupportedEncodingException {
	  

    Connection conn = DriverManager.getConnection(connStr, dbuser, dbpasw);
    conn.setAutoCommit(false);

    // #create sqlcl
    ScriptExecutor sqlcl = new ScriptExecutor(conn);

    // #setup the context
    ScriptRunnerContext ctx = new ScriptRunnerContext();
		    
    // set the output max rows
		ResultSetFormatter.setMaxRows(10000);
    // #set the context
    sqlcl.setScriptRunnerContext(ctx);
    ctx.setBaseConnection(conn);
    
    // Capture the results without this it goes to STDOUT
    ByteArrayOutputStream bout = new ByteArrayOutputStream();
    BufferedOutputStream buf = new BufferedOutputStream(bout);
    sqlcl.setOut(buf);


    // # run a whole file 
    // adjust the path as it needs to be absolute
    //sqlcl.setStmt("@/Users/hsiar/workspace_jboss/oracle-db-tools/sqlcl-java/myfile.sql");
    sqlcl.setStmt("@C:\\Users\\hsiar\\workspace_jboss\\oracle-db-tools\\sqlcl\\java\\myfile.sql");
    sqlcl.run();


    String results = bout.toString("UTF8");
    results = results.replaceAll(" force_print\n", "");
    System.out.println(results);
  }

}
