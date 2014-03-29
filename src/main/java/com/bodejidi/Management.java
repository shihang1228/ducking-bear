package com.bodejidi;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import javax.servlet.ServletException;

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
        resp.getWriter().println( username +"   "+ password);
    }
}
