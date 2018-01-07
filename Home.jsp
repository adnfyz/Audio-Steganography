<%@ page import="java.sql.*" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
    <link rel="stylesheet" type="text/css" href="style.css" />
  
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Audio_Steg</title>
        <div id="headeracc">
            <a href="Home.jsp">Home</a>
            <a href="steg.jsp">Encode</a>
            <a href="dsteg.jsp">Decode</a>
            <a href="login.html">Logout</a>
        </div>
    </head>
    <body>
        <div id="header"><p>Welcome, <%=session.getAttribute("Fname")%></p></div>
        <table  border="2" style="color: black; margin: 60px"> 
            <col width="5%"/> 
            <col width="30%"/>
            <col width="30%"/>
            
            <tr>
                <td><h3>SNO</h3></td>
                <td><h3>File Name</h3></td>
                <td><h3>Degradation of original file</h3></td>
               </tr>
            <% final String JDBC_driver="com.microsoft.sqlserver.jdbc.SQLServerDriver";
               final String DB_URL="jdbc:sqlserver://localhost;database=Audio_Steganography";
               
               final String USER="abc";
               final String PASSWORD="123";
               Connection conn=null;
               PreparedStatement pstmt=null;
               ResultSet res=null;
              
               try{
                  
                   Class.forName(JDBC_driver);
                   conn=DriverManager.getConnection(DB_URL,USER,PASSWORD);
                   
                   String username=(String)session.getAttribute("userID");
                   String sql="select * from audio_details where username=?";
                   pstmt=conn.prepareStatement(sql);
                   pstmt.setString(1,username);
                   
                   res=pstmt.executeQuery();
                   int Sno=0;
                   while(res.next())
                   { 
                    Sno++;%>
                       <tr>
                           <td style="color:blue"><%=Sno %></td>
                           <td style="color:blue"><a href="download.jsp?fname=<%=res.getString("audioName")%>"><%=res.getString("audioName") %></a></td>
                           <td style="color:black"><%=res.getString("BER")%>%</td>
                       </tr>
                   <%}
                       
               }catch(Exception ex){
                   out.println(ex);
               }
                %>
            </table>
    </body>
</html>
