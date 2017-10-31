import java.util.*;
import java.io.*;
import java.math.*;
import javax.imageio.*;
import java.awt.image.BufferedImage;
public class dsteg{
    
  static int[] dataRead=new int[8];
  static int[] audio=null;
  static List<Character> list2=new ArrayList<Character>();   
    public static void main(String[] rags) throws Exception{
       String path2="C:\\Users\\hp1\\Desktop\\Retrieved.txt";
       File dataFile=new File(path2);
       
       
       
       FileOutputStream fout=new FileOutputStream(dataFile);
       FileInputStream in=new FileInputStream("C:\\Users\\hp1\\Desktop\\ding.wav");
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
     dstegg(in,fout,path2,len);
    }
    static void dstegg(FileInputStream in,FileOutputStream fout,String path2,int len) throws Exception{
        
        int[] audio=new int[8];
        int bitread=0,counter=0,sum,byteCount;
        int loopCount=0;
        System.out.println(len);
        System.out.println("From here");
        //System.out.println(len);
        while(loopCount<len){
            byteCount=0;            //for counting databits when equal to 8
            sum=0;                  // calculating ascii va;ue of character in data
            counter=7;              //counting array of data array
            while(byteCount<8){
                bitread=in.read();
                System.out.print(bitread+":");
                    if(bitread==0)
                        dataRead[counter]=0;
                    else
                        dataRead[counter]=bitread%2;
                System.out.print(dataRead[counter]);
                sum=sum+(int)(dataRead[counter]*Math.pow(2,counter));
                
                System.out.println("   "+sum);
                counter--;
                byteCount++;
            }
            System.out.println((char)sum);
            fout.write((char)sum);
            loopCount++;
            System.out.println();
        }
    }
}
