/*
 * @author mang
 * create:2012-10-29 10:20:10
 * 功能：HDFS上新建文件示例
 * 说明：参考一：http://blog.csdn.net/nodie/article/details/6386608 
 * 		 参考二：http://blog.csdn.net/nodie/article/details/6386586
 * */
package mang.hadoop.examples;

import java.io.IOException;
import java.util.Date;

import org.apache.hadoop.conf.*;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hdfs.*;
import org.apache.hadoop.fs.*;

public class CreateFile {
	Configuration conf;
	FileSystem fs;
	Path filePath;
	String content;
	public CreateFile(String filePath,String content) throws IOException{
		conf = new Configuration();
		fs = FileSystem.get(conf);
		this.filePath=new Path(filePath);
		this.content=content;
	}
	public void create() throws IOException{
		FSDataOutputStream dos = fs.create(filePath);
		String utf8 = "UTF-8";
		dos.write(content.getBytes(utf8));
		dos.close();
		
		System.out.println("create file success");
		
		
	}
	public static void main(String[] args) throws Exception {
		/*try {
			String str = "Hello world";
			Configuration configuration = new Configuration();
			FileSystem fs = FileSystem.get(configuration);
			
			 * 原来参考一上是用java.io的，执行后虽然新建了文件但没有写入内容。 后来看到参考二可以用hdfs的io，就用hdfs的io了
			 
			// java.io.OutputStream out = fs.create(new Path("/user/root/input/helloworld"));
			
			
			FSDataOutputStream dos = fs.create(new Path(
					"/user/root/input/helloworld"));
			String utf8 = "UTF-8";
			
			
//			dos.write("/r/n".getBytes(utf8));
//			dos.write("//ceshi//".getBytes(utf8));
//			dos.write("/r/n".getBytes(utf8));
			dos.write("hello world hello hadoop".getBytes(utf8));
			Date dat = new Date();
			
			
			
			dos.write(dat.toString().getBytes());
			dos.close();
			
			System.out.println("create file success");
		} catch (Exception e) {
			System.out.println(e.toString());
		}*/
		String filePath=new String("HDFS://202.201.1.42:9000/user/root/input/456");
		String content=new String("hello world");
		CreateFile cf=new CreateFile(filePath, content);
		cf.create();

	}
}