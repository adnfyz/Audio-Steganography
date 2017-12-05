import java.io.File;
import java.io.*;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;


public class StegoServlet2 extends HttpServlet {
    
    static int[] dataRead=new int[8];
    static int[] audio=null;
    private final String UPLOAD_DIRECTORY="G:\\";
    private int maxMemSize = 400 * 1024;
    private int maxFileSize = 5000 * 1024;
    
    public void init(){
        
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
        PrintWriter out=response.getWriter();
        boolean isMultipartContent = ServletFileUpload.isMultipartContent(request);
		if (!isMultipartContent) {
			out.println("You are not trying to upload<br/>");
			return;
		}
                DiskFileItemFactory factory = new DiskFileItemFactory();
                factory.setSizeThreshold(maxMemSize);
              
		ServletFileUpload upload = new ServletFileUpload(factory);
                upload.setSizeMax( maxFileSize );
                
                String name=null;
                try{
                    List<FileItem> fields =upload.parseRequest(request) ;
                    for(FileItem item: fields){
                        if(!item.isFormField()){
                            name=new File(item.getName()).getName();
                            item.write(new File(UPLOAD_DIRECTORY+"STEG\\"+File.separator +name));
                            }  
                    }
                    
                    String path2=UPLOAD_DIRECTORY+"STEG\\"+name;
                    
                    File dataFile=new File(UPLOAD_DIRECTORY+"DSTEG\\Retreived.txt");
                    out.println(dataFile);
                    
                    FileOutputStream fout=new FileOutputStream(dataFile);
                    FileInputStream in=new FileInputStream(path2);
                    int bitread,len=0;
       for(int k=0;k<48;k++)
           in.read();
       for(int counter=7;counter>=0;counter--){
           bitread=in.read();
          
           if(bitread==0)
               dataRead[counter]=0;
           else
               dataRead[counter]=bitread%2;
            len=len+(int)(dataRead[counter]*Math.pow(2,counter)); 
       }
     dstegg(in,fout,len,request,response);
        }catch(Exception ex){      
            out.println(ex.getMessage());
    }
}
    private void dstegg(FileInputStream in,FileOutputStream fout,int len,HttpServletRequest request,HttpServletResponse response) throws Exception{
        StringBuilder sb = new StringBuilder();
        int bitread=0,counter=0,sum,byteCount;
        int loopCount=0;
       
        while(loopCount<len){
            byteCount=0;            //for counting databits when equal to 8
            sum=0;                  // calculating ascii va;ue of character in data
            counter=7;              //counting array of data array
            while(byteCount<8){
                bitread=in.read();
               
                    if(bitread==0)
                        dataRead[counter]=0;
                    else
                        dataRead[counter]=bitread%2;       //taking only lsb
                System.out.print(dataRead[counter]);
                sum=sum+(int)(dataRead[counter]*Math.pow(2,counter));  //coverting array into integer
               
                counter--;
                byteCount++;
            }         
            sb.append((char)sum);
            fout.write((char)sum);
            loopCount++;
            
        }
        
        fout.close();
       
        request.setAttribute("file", sb);
        request.getRequestDispatcher("/dsteg.jsp").forward(request,response);
        
    }
}