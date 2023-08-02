<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="<%= request.getContextPath()%>"></c:set>
<table border="0" cellspacing="0" cellpadding="0" >
<tr align="left">	
	<td ><img src="${contextPath}/resources/images/accordion_img.png"  alt="accordion logo" /></td>	
</tr>
</table>
<c:set var="clustername" value="<%= System.getenv(\"clustername\")%>"></c:set>
<c:set var="catalogname" value="<%= System.getenv(\"catalogname\")%>"></c:set>
<h1 >WEB APP DEMO:: ${clustername} :: ${catalogname} </h1>
<h1 >HOSTNAME: <%=java.net.InetAddress.getLocalHost().getHostName()%> </h1>
 