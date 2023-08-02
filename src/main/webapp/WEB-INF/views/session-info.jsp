<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@page import="java.util.*" %>
<%@page import="java.text.*" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<%
HttpSession sess = request.getSession();
boolean isNew = session.isNew();
String sessionId = session.getId();
long creationTime = session.getCreationTime();
long lastAccessedTime = session.getLastAccessedTime();
int maxInactiveInterval = session.getMaxInactiveInterval();
int rand_M=(int)(Math.random()*10000);
int rand_MS=(int)(Math.random()*10000);
java.util.Enumeration e = sess.getAttributeNames();
%>
<BR>
<table border=1 bordercolor="gray" cellspacing=1 cellpadding=0 width="100%">
<tr bgcolor="gray"><td colspan=2 align="center"><font color="white"><b>Session Info</b></font></td></tr>
<tr><td width="25%">Session ID</td><td width="75%"><%=sessionId%></td></tr>
<tr><td>isNew</td><td><%=isNew%></td></tr>
<tr><td>Creation Time</td><td><%=new Date(creationTime)%></td></tr>
<tr><td>Last Accessed Time</td><td><%=new Date(lastAccessedTime)%></td></tr>
<tr><td>Max Inactive Interval</td><td><%=maxInactiveInterval%></td></tr>
<tr bgcolor="cyan"><td colspan=2 align="center"><b>Session Vaule List<b></td></tr>
<tr><td align="center">NAME</td><td align="center">VAULE</td></tr>
<% 
String name = null;
while ( e.hasMoreElements() ) {
name = (String) e.nextElement();
%>
<tr><td align="left">sessnion name <%=name%></td><td align="left"><%=sess.getAttribute(name)%></td></tr>
<%
}
%>
</table>
<BR>