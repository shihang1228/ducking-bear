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
		resp.getWriter().println("shihanghangxing");
	}
}
