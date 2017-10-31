import java.util.*;
import java.io.*;
import java.math.*;

public class steg{
  static int[] m_audio=new int[8];
  static int[] audio=null;
  static List<Integer> list2=new ArrayList<Integer>();   
    public static void main(String[] rags) throws Exception{
       try{
       String path="C:\\Users\\hp1\\Desktop\\Alarm02.wav";
       String path2="C:\\Users\\hp1\\Desktop\\Message.txt";
       File file1=new File(path2);
       FileInputStream fs2=new FileInputStream(path);
       FileInputStream fs=new FileInputStream(path2);
       File file2=new File("C:\\Users\\hp1\\Desktop\\ding.wav");
       FileOutputStream out=new FileOutputStream(file2);
       int nread=-1,len;
       len=(int)file1.length();      
       int[] data=new int[8];  
       
       List<Integer> list=new ArrayList<Integer>();  //for data 
       int i=0,flag=0;
       for(int f=0;f<=5;f++){                           // foe skipping sfirst 48 bytes for wave format
            m_audio=audio_bits(data,fs2,path,nread);  //bits needs to skip first 8 bytes
            for(int l=0;l<=7;l++)
                list2.add(m_audio[l]);  
       }
       int j=0;
       System.out.println("Steganation process starts from here*****");
 
       System.out.print("Characters in dataFile\n: "+len+":");
       nread=(int)len;          //for getting the size of data file and the length is also steganted
       list.add(nread);
       while(list.get(i)!=0){
               data[j]=list.get(i)%2;
               list.set(i,list.get(i)/2);
               j++;    
            }     
            for(int k=7;k>=0;k--)
                System.out.print(data[k]);
            System.out.println();
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
               System.out.println("****"+m_audio[7]+"****");
               m_audio[7]=0;            //last element of m_audio changes to -1
               for(int k=0;k<=7;k++)
                     list2.add(m_audio[k]);
           }
            else{          //as audio bits always need to run 
           j=0;
           list.add(nread);
           System.out.print((char)nread+" "+list.get(i)+" ");
            while(list.get(i)!=0){
               data[j]=list.get(i)%2;
               list.set(i,list.get(i)/2);
               j++;    
            }     
            for(int k=7;k>=0;k--)
                System.out.print(data[k]);
            System.out.println();
            m_audio=audio_bits(data,fs2,path,nread);  //calling function for steg
                for(int k=0;k<=7;k++){
                     list2.add(m_audio[k]);
                }
            i++;
            for(int k=7;k>=0;k--)
              data[k]=0;
         //   System.out.println();
            
           // dsteg.d_bits();
            
            }
           if(m_audio[0]==-1){
                m_audio[0]=0; 
                for(int k :list2)
                    out.write(k);            //writing bits back to audio file
               break;
           }   
        }
     
    }catch(Exception ex){
        System.out.println(ex.getMessage());
    }
 }
   static int[] audio_bits(int[] data,FileInputStream fs2,String path,int nread) throws Exception{ 
         try{
            int i=7,currentValue,flag=0,nread2=0;
         audio=new int[8];
         
            int counter=0,j=0;    
            if(true)
            while((currentValue=fs2.read())!=-1){  // eof of audio_file
                nread2=currentValue;
                System.out.print(currentValue+":");
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
               if(nread!=-1)
                 audio[0]=data[i];      //steganation process
              i--;                       //decreamenting counter of data array
         int sum=0;
         for(int k=7;k>=0;k--){
                System.out.print(audio[k]); 
                sum=sum+(int)(audio[k]*Math.pow(2,k));
         }
        System.out.print(" : "+sum);
         flag=1;   // when audio bit =-1
         m_audio[j]=sum;
         j++;
        System.out.println();
        
         if(i==-1){
             flag=1;   // when audio bit =-1
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

    System.out.print(nread2+":");
    for(int k=7;k>=0;k--)
        audio[k]=0;
  System.out.println();
    }catch(Exception ex){
        System.out.println("exception from audio_bits\n "+ex.getMessage());
        }
     return m_audio;       
   }
}
