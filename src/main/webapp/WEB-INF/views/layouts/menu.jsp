<%@ page language="java" contentType="text/html; charset=UTF-8" %>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<ul>
<c:set var="contextPath" value="<%= request.getContextPath()%>"></c:set>
    <li><a href="${contextPath}/query/Query1">쿼리 테스트</a></li>
    <li><a href="${contextPath}/query/BindQuery">바인드 쿼리 테스트</a></li>  
    <li><a href="${contextPath}/jsp/session-info">세션</a></li>
    <li><a href="${contextPath}/jsp/sleep?second=1">TPS 테스트(Sleep1)</a></li>    
       
    <br>
    <br>
    


</ul>