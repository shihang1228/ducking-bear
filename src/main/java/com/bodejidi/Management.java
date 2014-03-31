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
import java.sql.ResultSet;
import java.lang.AutoCloseable;
import javax.servlet.http.HttpSession;

public class Management extends HttpServlet
{
    static final String JDBC_DRIVER= "com.mysql.jdbc.Driver";
    static final String JDBC_CONNECTOR = "jdbc:mysql://localhost/test?user=root&password=";
    static final String ENCODING = "UTF-8";
    static final String CONTENT_TYPE = "text/html;charset=UTF-8";
    
    
    public void doGet(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException
    {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String action = req.getParameter("action");
        String sql = "SELECT * FROM management";
        req.setCharacterEncoding(ENCODING);
        resp.setContentType(CONTENT_TYPE);
        String pid = req.getParameter("id");
        HttpSession session = req.getSession();
      
        if("logout".equals(action))
        {
            session.removeAttribute("memberId");
        }
        
        Long memberId = (Long)session.getAttribute("memberId");
        
        if(memberId == null)
        {
            login(req,resp);
            return;
        }
        
        if(pid==null)
        {
            memberList(req,resp);
        }
        else
        {
            selectMember(req,resp);            
        }      
    }
    public void doPost(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException
    {
        req.setCharacterEncoding(ENCODING);
        resp.setContentType(CONTENT_TYPE);     
        String action = req.getParameter("action");
        Connection conn = null;
        Statement stmt = null;
         
        if("login".equals(action))
        {
            String username = req.getParameter("username");       
            String password = req.getParameter("password");
            
            if(username.equals("shihang")&&password.equals("sh1228sh"))
            {
                HttpSession session = req.getSession();
                session.setAttribute("memberId",0L);
                resp.getWriter().println("<html><head><title>Login Success</title></head>"
                                        +"<body><p>Welcome  " + username + "!!!</p>"
                                        +"<a href=\"member\">Member List</a></br>"
                                        +"<a href=\"?action=logout\">logout</a>"
                                        +"</body></html>");
            }
            else
            {
                resp.getWriter().println("<html><head><title>Login Success</title></head>"
                                        +"<body><p>login failed!!!</p>"
                                        +"<a href=\"member\">会员登录</a>"
                                        +"</body></html>");
            }
        }
        else if("register".equals(action))
        {
            register(resp);
            
        }
        else if("Add".equals(action))
        {              
            add(req, resp);
        }
        else if("delete".equals(action))
        {
            deleteMember(req,resp);
        }
        else if("update".equals(action))
        {
            updateMember(req,resp);
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
                                        +"</form><a href=\"member\">会员登录</a></body></html>");  
    }
    public Connection connection(HttpServletResponse resp)throws ServletException, IOException
    {
        Connection conn = null;
        Statement stmt = null;
        try 
        {
            Class.forName(JDBC_DRIVER).newInstance();
            conn = DriverManager.getConnection(JDBC_CONNECTOR);
           // stmt = conn.createStatement();
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
        return conn;
        
    }
    public void add(HttpServletRequest req,HttpServletResponse resp) throws ServletException,IOException
    {
        String userName = req.getParameter("username");
        String password = req.getParameter("password");
        String action = req.getParameter("action");
        String firstName = req.getParameter("first_name");
        String lastName = req.getParameter("last_name");
        Connection conn = null;
        Statement stmt = null;
        try
        {
            conn = connection(resp); 
            stmt = conn.createStatement();
        
            String sql = "INSERT INTO management(user_name, password, first_name, last_name, date_created, last_updated)"
                       + "VALUES('"+ userName + "', '" + password + "', '" + firstName + "', '" + lastName +"', now(), now())";
            stmt.execute(sql);
            System.out.println(sql);
            resp.getWriter().println("add  " + firstName + "  " + lastName + "  success !!");
            resp.getWriter().println("<html><body><a href=\"member\">Member List</body></html>");
        }
        catch(SQLException ex)
        {
            System.out.println("SQLExcepton: " + ex.getMessage());
            System.out.println("SQLStates: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            resp.getWriter().println("error!");  
        }
        finally
        {
            close(conn);
            close(stmt);
        }
         
    }
    public void close(AutoCloseable obj)
    {
        if(obj!=null)
        {
            try
            {
                obj.close();
            }
            catch(Exception ex)
            {
                //ignore
            }
        }
    }
    public void memberList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM management";
        req.setCharacterEncoding(ENCODING);
        resp.setContentType(CONTENT_TYPE);     
        try
        {   
            conn = connection(resp); 
            stmt = conn.createStatement();
            rs = stmt.executeQuery((sql));
            System.out.println(sql);
            
            resp.getWriter().println("<html><head><title>会员列表</title></head>");
            resp.getWriter().println("<body><h1>会员列表</h1></h1>");
            resp.getWriter().println("<table border=1><tr><th>Id</th><th>Name</th></tr>");         
            while(rs.next())
            {
                Long id = rs.getLong("id");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");                               
                resp.getWriter().println("<tr><td><a href=\"?id=" + id + "\">" + id + "</td><td>" + firstName + lastName + "</td></tr>");
                
            }
            resp.getWriter().println("</table></body></html>");
        }
        catch(SQLException ex)
            {
                System.out.println("SQLExcepton: " + ex.getMessage());
                System.out.println("SQLStates: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
                resp.getWriter().println("error!");
            }
        finally
        {
            close(conn);
            close(stmt);
        }
    }
    public void selectMember(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String pid = req.getParameter("id");
        String sql = "SELECT * FROM management";
        req.setCharacterEncoding(ENCODING);
        resp.setContentType(CONTENT_TYPE);     
        try
            {           
                conn = connection(resp); 
                stmt = conn.createStatement();
                sql = sql + " where id=" + pid;
                rs = stmt.executeQuery((sql));
                System.out.println(sql);
                rs.next();
                Long id = rs.getLong("id");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                resp.getWriter().println("<html><head><title>指定会员</title></head>"
                                    +"<body><h1>第" + pid + "号会员</h1><form action=\"member\" method=\"POST\">"
                                    +"<table border=1><tr><th>ID</th><th>firstName</th><th>lastName</th></tr>"
                                    +"<tr><td>" + id + "</td>"
                                    +"<td><input type=\"text\" name=\"first_name\" value=\"" + firstName + "\"/></td>"
                                    +"<td><input type=\"text\" name=\"last_name\" value=\""+lastName+"\"/></td></tr>"
                                    +"</table><input type=\"submit\" name=\"action\" value=\"update\"/>"
                                    +"<input type=\"submit\" name=\"action\" value=\"delete\"/>"
                                    +"<input type=\"hidden\" name=\"id\" value=\"" + pid + "\"/>"
                                    +"</form><a href=\"member\">Member List</a></body></html>");
            
            }        
            catch(SQLException ex)
            {
                System.out.println("SQLExcepton: " + ex.getMessage());
                System.out.println("SQLStates: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
                resp.getWriter().println("error!");
            }
            finally
            {
                close(conn);
                close(stmt);
            }
    }
    public void deleteMember(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        Connection conn = null;
        Statement stmt = null;
        String firstName = req.getParameter("first_name");
        String lastName = req.getParameter("last_name");
        String id = req.getParameter("id");
        String sql = "DELETE FROM management WHERE ID=" + id;
        try
        {
            conn = connection(resp); 
            stmt = conn.createStatement();
            stmt.execute(sql);
            System.out.println(sql);
            resp.getWriter().println("delete 第 " + id + " 号 success!!!"); 
            resp.getWriter().println("<html><body><a href=\"member\">Member List</body></html>");
        }
         catch(SQLException ex)
        {
            System.out.println("SQLExcepton: " + ex.getMessage());
            System.out.println("SQLStates: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            resp.getWriter().println("error!");
        }
        finally
        {
            close(conn);
            close(stmt);
        }
    }
    public void updateMember(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        Connection conn = null;
        Statement stmt = null;
        String firstName = req.getParameter("first_name");
        String lastName = req.getParameter("last_name");
        String id = req.getParameter("id");
        String sql = "UPDATE management SET first_name='" +  firstName + "',last_name='" + lastName + "' WHERE ID='" + id + "'";
        try
        {
            conn = connection(resp); 
            stmt = conn.createStatement();
            stmt.execute(sql);
            System.out.println(sql);
            resp.getWriter().println("update " + firstName + lastName + "  success!!!"); 
            resp.getWriter().println("<html><body><a href=\"member\">Member List</body></html>");
        }
         catch(SQLException ex)
        {
            System.out.println("SQLExcepton: " + ex.getMessage());
            System.out.println("SQLStates: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            resp.getWriter().println("error!");
        }
        finally
        {
            close(conn);
            close(stmt);
        }
    }
    public void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        resp.getWriter().println("<html>");
        resp.getWriter().println("<head>");
        resp.getWriter().println("<title>index</title>");
        resp.getWriter().println("<meta http-equiv=\"Content-Type\" content=\"text/html\"; charset=\"UTF-8\">");
        resp.getWriter().println("</head>");
        resp.getWriter().println("<body>");
        resp.getWriter().println("<h1>会员登录</h1>");
        resp.getWriter().println("<form action=\"member\" method=\"POST\">");
        resp.getWriter().println("<label>用户名:<input type=\"text\" name=\"username\"/></label></br>");
        resp.getWriter().println("<label>密  码:<input type=\"password\" name=\"password\"/></label></br>");
        resp.getWriter().println("<input type=\"submit\" name=\"action\" value=\"login\"/>");
        resp.getWriter().println("<input type=\"submit\" name=\"action\" value=\"register\"/>");
        resp.getWriter().println("</form>");
        resp.getWriter().println("</body>");
        resp.getWriter().println("</html>");
    }
    
    
    
    
}
