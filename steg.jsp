<html>
    <head>
        <title>Audio_steg</title>
<link rel="stylesheet" type="text/css" href="style.css" />
<script language="javascript" type="text/javascript">
    function set(){
        document.getElementById('giff').style.visibility="hidden";
    }
    function validateData(message,textFile,carrier_file){
        
        if (message.value==="" && textFile.value===""){
            alert("please insert the message!");   
            return false;
        }
        else if(carrier_file.value===""){
            alert("choose the carrier file to steganate");
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
    
    <body onload="set()">       
	<div id="headeracc">
            <a href="Home.jsp">Home</a>
            <a href="steg.jsp">Encode</a>
            <a href="dsteg.jsp">Decode</a>
            <a href="login.html">Logout</a>
        </div>
        <div id="header"><p style="font-size:20px">Welcome, <%=session.getAttribute("Fname") %></p></div>
<form name="myform" action="StegoServlet" method="post" enctype="multipart/form-data">
<div  id="header2">
    <section id="content">   
        <p>Select the file that you want to hide
                <input type="file" name="textFile" /><b>(The file selected should only be a .txt file)</b></p>
           <p>Select the audio file that you want to send
                <input type="file" name="carrier_file"/><b>(The file selected should only be a .wav file)</b></p> 

                <input type="submit" value="EnCode Yor File" multiple="multiple" onclick="return validateData(message,textFile,carrier_file);" style="margin: 30px;"/>
                <img id="giff" src="giphy.gif" alt="hello" style="width: 60px; height:60px"/> 
        </div>
        </form>
    </body>
   
</html>