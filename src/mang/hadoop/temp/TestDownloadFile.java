/*
 * @author mang
 * create:2012-10-29 10:20:10
 * ���ܣ�HDFS���½��ļ�ʾ��
 * ˵�����ο�һ��http://blog.csdn.net/nodie/article/details/6386608 
 * 		 �ο�����http://blog.csdn.net/nodie/article/details/6386586
 * */
package mang.hadoop.temp;
import java.io.IOException;
import java.util.Date;

import org.apache.hadoop.conf.*;  
import org.apache.hadoop.fs.FileSystem;  
import org.apache.hadoop.hdfs.*;  
import org.apache.hadoop.fs.*;  
public class TestDownloadFile {  
public static void main(String[] args) throws Exception{  
       String str = "Hello world";  
       Configuration configuration = new Configuration();  
       FileSystem fs = FileSystem.get(configuration);  
       /*
        * ԭ���ο�һ������java.io�ģ�ִ�к���Ȼ�½����ļ���û��д�����ݡ�
        * ���������ο���������hdfs��io������hdfs��io��
        * */
//       java.io.OutputStream out = fs.create(new Path("/user/root/input/helloworld"));
       
       
//       FSDataOutputStream dos = fs.create(new Path("/user/root/input/helloworld")); 
//       String utf8 = "UTF-8";
//       dos.write("/r/n".getBytes(utf8));
//       dos.write("//ceshi//".getBytes(utf8));
//       dos.write("/r/n".getBytes(utf8));
//       Date dat = new Date();
//       dos.write(dat.toString().getBytes());
//       dos.close();  
       
       
       String srcFile=new String("/user/root/input/helloworld");
//       String dstFile=new String("/home/maning/test/helloworld.txt");
//       String dstFile=new String("/test/helloworld.txt");
       String dstFile=new String("d:/helloworldmang");
       TestDownloadFile.getFromHdfs(srcFile, dstFile, configuration);
       System.out.println("download success");
    }  


static boolean getFromHdfs(String src,String dst, Configuration conf) {  
    Path dstPath = new Path(src);  
    try {  
      // ��ȡ����hdfs�Ķ���  
      FileSystem hdfs = dstPath.getFileSystem(conf);  
      // ���d  
      hdfs.copyToLocalFile(false, new Path("hdfs://202.201.1.42:9000" +src),new Path(dst));  
        
    } catch (IOException e) {  
      e.printStackTrace();  
      return false;  
    }  
    return true;  
  }  
    }  