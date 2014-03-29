package com.bodejidi;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import javax.servlet.ServletException;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.DriverManager;

public class Management extends HttpServlet
{
    static final String JDBC_DRIVER= "com.mysql.jdbc.Driver";
    static final String JDBC_CONNECTOR = "jdbc:mysql://localhost/test?user=root&password=";
    static final String ENCODING = "UTF-8";
    static final String CONTENT_TYPE = "text/html;charset=UTF-8";
    
    public void doGet(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException
    {
		//resp.getWriter().println("shihanghangxing");
    }
    public void doPost(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException
    {
        req.setCharacterEncoding(ENCODING);
        resp.setContentType(CONTENT_TYPE);
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        
        Connection conn = null;
        Statement stmt = null;
        resp.getWriter().println( username +"   "+ password + " success!!!");
        
        try 
        {
            Class.forName(JDBC_DRIVER).newInstance();
            conn = DriverManager.getConnection(JDBC_CONNECTOR);
            stmt = conn.createStatement();
            resp.getWriter().println("haha!hehe!");
        }  
        catch(SQLException ex)
        {
            System.out.println("SQLExcepton: " + ex.getMessage());
            System.out.println("SQLStates: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            resp.getWriter().println("error!");
        }
        catch(Exception ex)
        {  
            //ignore;
        }  
    }
}
