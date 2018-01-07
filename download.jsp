<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.io.*" %>
<%@ page import="java.sql.*" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Audio_Steg</title>
    </head>
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
                          
                String fileName=request.getParameter("fname");

                String filePath="G:\\STEG\\";
                response.setContentType("APPLICATION/OCTET-STREAM");
                response.setHeader("Content-disposition","attachment;filename=\""+fileName+"\"");
                FileInputStream fin= new FileInputStream(filePath+fileName);
                OutputStream fout = response.getOutputStream();
                int data;

                while((data=fin.read())!=-1){
                    fout.write(data);
                }
               }catch(Exception ex){
                   out.println(ex);
               }
            %>
            
    </body>
</html>
