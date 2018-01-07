<%@page import="java.sql.*" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
    <head>  
        <title>Audio_steg</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Audio_Steg</title><title>Audio_Steg</title>
        <link rel="stylesheet" type="text/css" href="style.css"/>
        
        <script language="javascript" type="text/javascript">
            function Redirect(){
                window.location="reg.html";
            }
            </script>
    </head>
  
<% 
    
     final String JDBC_driver="com.microsoft.sqlserver.jdbc.SQLServerDriver";
     final String DB_URL="jdbc:sqlserver://localhost;database=Audio_Steganography";
     String userdb,passdb,Fnamedb;
     final String USER="abc";
    final String PASSWORD="123";
    Connection conn=null;
    PreparedStatement pstmt=null;
    ResultSet res=null;
    try{
        Class.forName(JDBC_driver);
        conn=DriverManager.getConnection(DB_URL,USER,PASSWORD);
        String username=request.getParameter("Username");
        String pass=request.getParameter("Password");
        String sql="select * from registration where userID=? and password=?";
        pstmt=conn.prepareStatement(sql);
        pstmt.setString(1,username);
        pstmt.setString(2,pass);
        res=pstmt.executeQuery();
        if(res.next()){
           userdb=res.getString("userID");
           passdb=res.getString("password");
           Fnamedb=res.getString("Fname");
           if(username.equals(userdb) && pass.equals(passdb)){  
               session.setAttribute("userID", userdb);
               session.setAttribute("password", passdb);
               session.setAttribute("Fname", Fnamedb);
               response.sendRedirect("Home.jsp");
           }
       }
       else{
           %><script>
           alert("Username OR Password not found!");
           window.location="login.html";
            </script>
            <%
       }
       res.close();
       conn.close();
    }catch(Exception ex){
        out.println(ex);
    }
    %>
</div>
</body>
</html>

