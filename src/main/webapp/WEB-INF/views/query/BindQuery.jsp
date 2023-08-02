<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" contentType="text/html; charset=UTF-8"%>
<html>
<head>
	<title>Quest Test</title>
</head>
<body>

<h1>
	바인드 쿼리 테스트
	
</h1>
<form name=frmSubmit>

<c:set var="v_dataSource" value="${param.dataSource}" />
<input type="radio" name="dataSource" value="oracleDS" <%="oracleDS".equals(pageContext.getAttribute("v_dataSource"))?"checked":"" %>>오라클 </input>
<input type="radio" name="dataSource" value="tiberoDS" <%="tiberoDS".equals(pageContext.getAttribute("v_dataSource"))?"checked":"" %>>티베로 </input>
<input type="radio" name="dataSource" value="db2DS"    <%="db2DS".equals(pageContext.getAttribute("v_dataSource"))?"checked":"" %>>DB2 </input>
<input type="radio" name="dataSource" value="H2DS"     <%="H2DS".equals(pageContext.getAttribute("v_dataSource"))?"checked":"" %>>H2DS </input>
<input type="radio" name="dataSource" value="MariaDB"     <%="MariaDB".equals(pageContext.getAttribute("v_dataSource"))?"checked":"" %>>MariaDB </input>
<br/>
 

<Table align=left border="1" width=100%>
	<tr>
		<input type="button" value="바인드변수 추가" onclick="input_append(this.form)"><br>		
		<div id="append" >				 
		
		<c:forEach  var="i" begin="1" end="${bindCount}">
			<label> Name   </label> <input type=text name="v_name"  value="${v_name.get(i-1)}">
			<label> Value  </label> <input type=text name="v_value" value="${v_value.get(i-1)}">			
			<label> Type   </label> <select name=v_type size=1>
										<option value='String' ${v_type.get(i-1) eq "String" ?"selected":""}  >String</option>
										<option value='BigDecimal'   ${v_type.get(i-1) eq "BigDecimal" ?"selected":""}  >BigDecimal</option>  
                   						<option value='int'    ${v_type.get(i-1) eq "int" ?"selected":""}     >int</option>                                         
                   						<option value='Date'   ${v_type.get(i-1) eq "Date" ?"selected":""}  >Date</option>
                   						<option value='double'   ${v_type.get(i-1) eq "double" ?"selected":""}  >double</option>
                   						    
                                    </select><br> 
		</c:forEach> 
		</div>				
	</tr>

	<tr>
	    <td align=center width=80>query</td>
	    <td colspan="2" align=center> <textarea id="querytextarea" name="querytextarea"  rows=15 style="width:100%">${query}</textarea>
	</tr>
	<tr>
	    <td align=center width=80>result</td>
   	    <td colspan="2" align=center> 
	   	    <textarea id="resulttextarea" readonly="readonly" rows=8 style="width:100%">
${columnsName} 	    
	   	    
				<c:forEach  items="${queryResult}" var="row">
${row}
		    	</c:forEach>

${resultCode}		    	 
		    </textarea></td>		    
	</tr>
	<tr>
		    <td align=center width=100>elapsed time</td>
		    <td colspan="1" align=center> ${etime} </td>
	
</Table>

<input type=submit name=excute value="excute"   onClick="getPost('01')">
<input type=submit name=save   value="save"     onClick="getPost('02')">
<input type=submit name=init   value="init"     onClick="getPost('03')">
</form>
   
</body>
</html>
