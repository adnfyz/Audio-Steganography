<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.io.*" %>
<%@page import="java.sql.*" %>
<%@page import="java.util.*" %>
 <html>
     <script language="javascript" type="text/javascript">
         function validate(){
         alert("user name already exists");
         window.location="register.html";
     }
     </script>
     <body>
<%   
    final String JDBC_driver="com.microsoft.sqlserver.jdbc.SQLServerDriver";
    final String DB_URL="jdbc:sqlserver://localhost;database=Audio_Steganography";
    
    final String USER="abc";
    final String PASSWORD="123";
    Connection conn=null;
    PreparedStatement pstmt=null;
    ResultSet res=null;
   try{
        
        Class.forName(JDBC_driver);
        conn=DriverManager.getConnection(DB_URL,USER,PASSWORD);
        String Fname=request.getParameter("Fname");
        String Lname=request.getParameter("Lname");
        String userid=request.getParameter("userID");
        String password=request.getParameter("password");
        String Country=request.getParameter("country");
        String Gender=request.getParameter("Gender");
        
        String sql="insert into registration(Fname,Lname,userID,password,country,Gender)values(?,?,?,?,?,?)";
      
        pstmt=conn.prepareStatement(sql);
        pstmt.setString(1,Fname);
        pstmt.setString(2,Lname);
        pstmt.setString(3,userid);
        pstmt.setString(4,password);
        
        pstmt.setString(5,Country);
        pstmt.setString(6,Gender);    
        pstmt.executeUpdate();
        pstmt.close();
        conn.close();
      
       response.sendRedirect("login.html");   
       
    }
   catch(Exception e)
    { 
        %>
        <script>
           validate();
        </script>
                
   <% } 
   %> 
  
     </body>
 </html> 