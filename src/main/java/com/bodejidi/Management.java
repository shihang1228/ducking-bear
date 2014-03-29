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
    public void doGet(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException
    {
		//resp.getWriter().println("shihanghangxing");
    }
    public void doPost(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException
    {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset = UTF-8");
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        //resp.getWriter().println( username +"   "+ password + " success!!!");
        
        try 
        {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        }catch(Exception ex)
        {
          //ignore;
        }
            Connection conn = null;
            Statement stmt = null;
        
        try
        {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/test?"
                                                 + "user=root"
                                                 + "&password=");
            stmt = conn.createStatement();
            resp.getWriter().println("haha!hehe!");
        
        }catch(SQLException ex)
        {
            System.out.println("SQLExcepton: " + ex.getMessage());
            System.out.println("SQLStates: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            resp.getWriter().println("error!");
        }
    }
}
