import java.io.File;
import java.io.*;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.sql.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.http.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;


public class StegoServlet extends HttpServlet {
    private static float ber=0;
    private String message;
    private final String UPLOAD_DIRECTORY="G:\\";
    private int maxMemSize = 4 * 1024;
    private int maxFileSize = 500 * 1024;
    static int[] m_audio=new int[8];   //storing 8 bytes of audio
    static int[] audio=null;     //storing bits of 1 byte
    static List<Integer> list2=new ArrayList<Integer>();  
    public void init(HttpServletRequest request, HttpServletResponse response){       
    }
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8"); 
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        message =request.getParameter("message");
        final String JDBC_driver="com.microsoft.sqlserver.jdbc.SQLServerDriver";
        final String DB_URL="jdbc:sqlserver://localhost;database=Audio_Steganography";
         
        final String USER="abc";
        final String PASSWORD="123";
        Connection conn=null;
        PreparedStatement pstmt=null;
        ResultSet res=null;
               
        PrintWriter out=response.getWriter();
        out.println(message);
        boolean isMultipartContent = ServletFileUpload.isMultipartContent(request);
		if (!isMultipartContent) {
			out.println("You are not trying to upload<br/>");
			return;
		}
                try{
                Class.forName(JDBC_driver);
                conn=DriverManager.getConnection(DB_URL,USER,PASSWORD);
                }catch (Exception ex){
                    out.println("class not found");
                }
               
                HttpSession session = request.getSession(true);
                String username=(String)session.getAttribute("userID");
                String uname=(String)session.getAttribute("Fname");
                
                
                DiskFileItemFactory factory = new DiskFileItemFactory();
                factory.setSizeThreshold(maxMemSize);
                
		ServletFileUpload upload = new ServletFileUpload(factory);
                upload.setSizeMax( maxFileSize );
                String[] name=new String[2];
                int z=0;
		try {
                        List<FileItem> fields =upload.parseRequest(request) ;
                        for(FileItem item: fields){
                            if(!item.isFormField()){
                                name[z]=new File(item.getName()).getName();
                                item.write(new File(UPLOAD_DIRECTORY+"\\UPLOADED_FILES\\"+ File.separator +name[z]));
                                z++;
                            }
                        }
                String path2=UPLOAD_DIRECTORY+"UPLOADED_FILES\\"+name[0];
                String path= UPLOAD_DIRECTORY+"UPLOADED_FILES\\"+name[1];
         
                 request.getRequestDispatcher("/Home.jsp").forward(request,response);
                int nread=-1,len;
            
                FileInputStream fs2=new FileInputStream(path);
                FileInputStream fs=new FileInputStream(path2);
                
                File file1=new File(path2);
                len=(int)file1.length();      
                int[] data=new int[8]; 
			             
                List<Integer> list=new ArrayList<Integer>();        //for data 
                int i=0,flag=0;
                for(int f=0;f<=5;f++){                           // foe skipping sfirst 48 bytes for wave format
                     m_audio=audio_bits(data,fs2,path,nread);  //bits needs to skip first 8 bytes
                     for(int l=0;l<=7;l++)
                         list2.add(m_audio[l]);  
                }
            int j=0;
           
            nread=(int)len;          //for getting the size of data file and the length is also steganted
            list.add(nread);
            while(list.get(i)!=0){
                    data[j]=list.get(i)%2;
                    list.set(i,list.get(i)/2);
                    j++;    
                 }     
             
             m_audio=audio_bits(data,fs2,path,nread);
             for(int l=0;l<=7;l++)
                     list2.add(m_audio[l]); 
                 j=0;
                 i++;    // increamenting length section of list
            
                 while(true){
                nread=fs.read();
                if(nread==-1){
                    for(int k=7;k>=0;k--)
                        data[k]=0;
                    m_audio=steg.audio_bits(data,fs2,path,nread); 
                  
                    
                    for(int k=0;k<=7;k++)
                          list2.add(m_audio[k]);
                }
                 else{          //as audio bits always need to run 
                j=0;
                list.add(nread);
             
                 while(list.get(i)!=0){
                    data[j]=list.get(i)%2;
                    list.set(i,list.get(i)/2);
                    j++;    
                 }     
           
                 m_audio=audio_bits(data,fs2,path,nread);  //calling function for steg
                     for(int k=0;k<=7;k++){
                          list2.add(m_audio[k]);
                     }
                 i++;
                 for(int k=7;k>=0;k--)
                   data[k]=0;
                 }       
             
                if(m_audio[0]==-1){
                    String sql="select value from counter";
                     pstmt=conn.prepareStatement(sql);
                    res=pstmt.executeQuery();
                
                    String counter=" ";
                    if(res.next())
                    counter=res.getString("value");
                    
                     File file2=new File(UPLOAD_DIRECTORY+"STEG\\"+uname+counter+".wav");
                   
                    FileOutputStream fout=new FileOutputStream(file2);
                    m_audio[0]=0;       //last element of m_audio changes to -1
                    float berr_percentage;
                    String ber_percentage;
                    float size=list2.size();
                    berr_percentage=(ber/size)*100;
                    ber_percentage=String.format("%.2f", berr_percentage);
                    for(int k :list2)
                        fout.write(k); 
                     
                    fout.close();
                    fs2.close();
                    fs.close();
                    savedata(request,response,uname,counter,username,ber_percentage);
                    request.getRequestDispatcher("/Home.jsp").forward(request,response);
                    break;
                } 
            }

    }catch(Exception ex){
        System.out.println(ex);
    }
 }
    public void savedata(HttpServletRequest request, HttpServletResponse response,String uname,String counter,String username,String ber_percentage) throws Exception{
               PrintWriter out=response.getWriter();
               
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
                   String sql="insert into audio_details(username,audioName,ber) values(?,?,?)";
                   
                   pstmt=conn.prepareStatement(sql);
                   
                   pstmt.setString(1,username);
                   pstmt.setString(2,uname+counter+".wav");
                   pstmt.setString(3,ber_percentage);
                   
                   pstmt.executeUpdate();
                   
                   String sql2="update counter set value=value+1";
                   pstmt=conn.prepareStatement(sql2);
                   
                   pstmt.executeUpdate();
                   
                   pstmt.close();
                   conn.close();
               }catch(Exception ex){
                   out.println(ex);
               }
    }
    int[] audio_bits(int[] data,FileInputStream fs2,String path,int nread) throws Exception{ 
         try{
            int i=7,currentValue,flag=0,nread2=0;
            audio=new int[8];
         
            int counter=0,j=0;    
            if(true)
            while((currentValue=fs2.read())!=-1){  // eof of audio_file
                nread2=currentValue;
              
                if(currentValue==0)
                  for(int k=7;k>=0;k--)
                    audio[k]=0;
             counter=0;
                           //there is data in file
                while(currentValue!=0){ 
                   audio[counter]=currentValue%2;
                   currentValue=currentValue/2;
                   counter++;
               } 
               if(nread!=-1){
                   if(audio[0]!=data[i])
                       ber++;
                 audio[0]=data[i];      //steganation process
               }
         i--;                       //decreamenting counter of data array
         int sum=0;
         for(int k=7;k>=0;k--){
 
                sum=sum+(int)(audio[k]*Math.pow(2,k));
         }
      
         flag=1;   // when audio bit =-1
         m_audio[j]=sum;
         j++;
   
        
         if(i==-1){
             flag=1;   // as this function is called every time so need to keep one counter which shows the count of last bit of audio bit
              for(int k=7;k>=0;k--)
                 audio[k]=0;
              break;
         }
            for(int k=7;k>=0;k--)
                audio[k]=0;
    }
             if(flag==0){
                    m_audio[0]=-1;
                return m_audio;
            }


    for(int k=7;k>=0;k--)
        audio[k]=0;
    
    }catch(Exception ex){
        System.out.println("exception from audio_bits\n "+ex.getMessage());
        }
     return m_audio;    
   }
    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold> 

}
