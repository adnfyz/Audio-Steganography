<%@page import="java.io.FileInputStream"%>
<%@page import="java.io.File"%>
<%@page import="java.io.InputStreamReader"%>
<%@page import="java.net.URL"%>
<%@page import="java.io.FileReader"%>
<%@page import="java.io.BufferedReader"%>
<html>
    <head>
        <title>Audio_steg</title>
<link rel="stylesheet" type="text/css" href="style.css" />
<script language="javascript" type="text/javascript">
    function set(){
         document.getElementById('giff').style.visibility="hidden";
    }
    function validateData(carrier_file){
         if(carrier_file.value===""){
            alert("choose the Audio file to decode the data");
            return false;
            }
        else {
            document.getElementById('giff').style.visibility="visible";
            return true;
        }
    }
   
    </script>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0"> 
        <title>Audio_Steg</title>
    </head>
   <body  onload="set()">
	<div id="headeracc">
            <a href="Home.jsp">Home</a>
            <a href="steg.jsp">Encode</a>
            <a href="dsteg.jsp">Decode</a>
            <a href="login.html">Logout</a>
        </div>
        <div id="header"><p>Welcome, <%=session.getAttribute("Fname")%></p></div>
        
<form name="myform" action="StegoServlet2" method="post" enctype="multipart/form-data" accept-charset="UTF-8">
    <div  id="header2">
    <section id="content"> 
    <div id="header2">
        <textarea name="message" rows="10" cols="70"> <%=request.getAttribute("file") %>
        </textarea>
              <p>Select the audio file that you want to Decode 
			<input type="file" name="carrier_file"></br></p> 
              <input type="submit" value="DeCode Your File" multiple="multiple" onclick="return validateData(carrier_file);" style="margin: 30px;"/>
              <img id="giff" src="giphy.gif" alt="hello" style="width: 60px; height:60px"> 
    </div>
    </form>
      
</body>
</html>