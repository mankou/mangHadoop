/*
 * @author mang
 * create:2012-10-29 10:20:10
 * ���ܣ�HDFS���½��ļ�ʾ��
 * ˵�����ο�һ��http://blog.csdn.net/nodie/article/details/6386608 
 * 		 �ο�����http://blog.csdn.net/nodie/article/details/6386586
 * */
package mang.hadoop.examples;
import java.util.Date;

import org.apache.hadoop.conf.*;  
import org.apache.hadoop.fs.FileSystem;  
import org.apache.hadoop.hdfs.*;  
import org.apache.hadoop.fs.*;  
public class CreateFile {  
public static void main(String[] args) throws Exception{  
       String str = "Hello world";  
       Configuration configuration = new Configuration();  
       FileSystem fs = FileSystem.get(configuration);  
       /*
        * ԭ���ο�һ������java.io�ģ�ִ�к���Ȼ�½����ļ���û��д�����ݡ�
        * ���������ο���������hdfs��io������hdfs��io��
        * */
//       java.io.OutputStream out = fs.create(new Path("/user/root/input/helloworld"));  
       FSDataOutputStream dos = fs.create(new Path("/user/root/input/helloworld")); 
       String utf8 = "UTF-8";
       dos.write("/r/n".getBytes(utf8));
       dos.write("//ceshi//".getBytes(utf8));
       dos.write("/r/n".getBytes(utf8));
       Date dat = new Date();
       dos.write(dat.toString().getBytes());
       dos.close();  
    }  
    }  