<%@page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>  
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">  
<html>
    
<html>
     
<head>
        <title><tiles:getAsString name="title" /></title>
</head>
<c:set var="contextPath" value="<%= request.getContextPath()%>"></c:set>
<script type="text/javascript">
function getPost(mode)
{
	var theForm = document.frmSubmit;
	if(mode == "01")
		{
	    theForm.method = "post"; 
	    theForm.target = "_self";
	    theForm.action = "${contextPath}/query/${queryName}";
		}
		else if(mode == "02")
		{
	 	 theForm.method = "post";
	  	 theForm.target = "_self";
	     theForm.action = "${contextPath}/query/${queryName}/save"
		}
		else if(mode == "03")
		{
	 	 theForm.method = "post";
	 	 theForm.target = "_self";
	     theForm.action = "${contextPath}/query/${queryName}/init"
		}	
	theForm.submit();
}
</script>
<script type="text/javascript">
cnt=0;
function input_append(ff){
  cnt++;
  
  app = document.getElementById("append");
  app.innerHTML += "<label> Name  </label>   <input type=text name=v_name>"  + 
                   "<label> Value </label>   <input type=text name=v_value>" +
                   "<label> Type  </label>" +
                   "<select name=v_type size=1>" +
                   "<option value='String' selected >String</option>" +
                   "<option value='int'    >int</option>" +
                   "<option value='BigDecimal'    >BigDecimal</option>" +
                   "<option value='Date' >Date</option>" +  
                   "<option value='double' >double</option>" +
                   "</select>" + "<br>";
}

function input_result(ff){
  var str = ""; 
  if(cnt == 1){
    str = ff.txt.value;
  }else{
    for(i=0 ; i<cnt ; i++){
      str += ff.txt[i].value + " | ";
    }
  }
  alert(str);
}
</script>	
<body>   
        <div><tiles:insertAttribute name="header" /></div>  
        <hr/> 
        <div style="float:left;padding:10px;width:10%;"><tiles:insertAttribute name="menu" /></div>  
        <div style="float:left;padding:10px;width:86%;border-left:3px solid gray;">  
        <tiles:insertAttribute name="body" />
        </div>                  
        <div style="clear:both"><tiles:insertAttribute name="footer" /></div>  
        
</body>
</html>

