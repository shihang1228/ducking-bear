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
        
           
    }
    public void doPost(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException
    {
        req.setCharacterEncoding(ENCODING);
        resp.setContentType(CONTENT_TYPE);
        String userName = req.getParameter("username");
        String password = req.getParameter("password");
        String action = req.getParameter("action");
        String firstName = req.getParameter("first_name");
        String lastName = req.getParameter("last_name");
        Connection conn = null;
        Statement stmt = null;
        
        Statement stme = connection(resp);    
        try
        {
            if("submit".equals(action))
            {
                
            }
            else if("register".equals(action))
            {
                register(resp);    
            }
            else if("Add".equals(action))
            {
                String sql = "INSERT INTO management(user_name, password, first_name, last_name, date_created, last_updated)"
                             +"VALUES('"+ userName + "', '" + password + "', '" + firstName + "', '" + lastName +"', now(), now())";
                stme.execute(sql);
                System.out.println(sql);
                resp.getWriter().println("add  " + firstName + "  " + lastName + "  success !!");
            }
            
        }
        catch(SQLException ex)
        {
            System.out.println("SQLExcepton: " + ex.getMessage());
            System.out.println("SQLStates: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            resp.getWriter().println("error!");
        }
        
    }
    
    public void register(HttpServletResponse resp)throws ServletException, IOException
    {
        resp.getWriter().println("<html><head><title>register</title></head><body>"
                                        +"<h1>会员注册</h1>"
                                        +"<form action=\"member\" method=\"POST\">UserName:<input type=\"text\" name=\"username\"/></br>"
                                        +"Password:<input type=\"password\" name=\"password\"/></br>"
                                        +"firstName:<input type=\"text\" name=\"first_name\"/></br>"
                                        +"lastName:<input type=\"text\" name=\"last_name\"/></br>"
                                        +"<input type=\"submit\" name=\"action\" value=\"Add\"/>"
                                        +"</form></body></html>");  
    }
    public Statement connection(HttpServletResponse resp)throws ServletException, IOException
    {
        Connection conn = null;
        Statement stmt = null;
        try 
        {
            Class.forName(JDBC_DRIVER).newInstance();
            conn = DriverManager.getConnection(JDBC_CONNECTOR);
            stmt = conn.createStatement();
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
        return stmt;
        
    }
    
}
