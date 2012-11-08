/*
 * @author mang
 * create:2012-10-29 10:20:10
 * 功能：HDFS上新建文件示例
 * 说明：参考一：http://blog.csdn.net/nodie/article/details/6386608 
 * 		 参考二：http://blog.csdn.net/nodie/article/details/6386586
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
        * 原来参考一上是用java.io的，执行后虽然新建了文件但没有写入内容。
        * 后来看到参考二可以用hdfs的io，就用hdfs的io了
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
      // 获取操作hdfs的对象  
      FileSystem hdfs = dstPath.getFileSystem(conf);  
      // 下載  
      hdfs.copyToLocalFile(false, new Path("hdfs://202.201.1.42:9000" +src),new Path(dst));  
        
    } catch (IOException e) {  
      e.printStackTrace();  
      return false;  
    }  
    return true;  
  }  
    }  