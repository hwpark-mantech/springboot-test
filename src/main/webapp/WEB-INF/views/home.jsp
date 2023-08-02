
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<head>
	<title>Home</title>
</head>
<body onload=load()>
<h1>	
	Hello World Welcome   
</h1>

<P>  The time on the server is ${serverTime}. </P>




<script>
var cnt=1;
function input_append()
{
	cnt++;	
	$('#errors').append('<p>4개의 에러가 발견되었습니다.</p>');
	alert(cnt);
	console.log("cnt="+cnt);
}
function load(cnt)
{
	
	
	for(i=0 ; i<cnt ; i++){
      $('#errors').append('<p>4개의 에러가 발견되었습니다.</p>');
    }
    console.log("cnt="+cnt);
    
}	 
</script>
<input type="button" value="라인추가" onclick="input_append()">

<div id="container">
	<div id="errors">
		<h2>ERRORS:</h2>
	</div>
</div>

</body> 
</html>
