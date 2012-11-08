/*
 * @author mang
 * create:2012-10-29 10:20:10
 * 功能：HDFS上新建文件示例
 * 说明：前期参考自以下两个参考，后期自己重新整合进库。
 * 		参考一：http://blog.csdn.net/nodie/article/details/6386608 
 * 		 参考二：http://blog.csdn.net/nodie/article/details/6386586
 * */
package mang.hadoop.library;

import java.io.IOException;
import java.util.Date;

import org.apache.hadoop.conf.*;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hdfs.*;
import org.apache.hadoop.fs.*;
/**
 * 功能：在hdfs上创建文件，文件名与文件内容以字符串形式传入构造函数
 * <p>调用说明：<br/>
 * String filePath=new String("HDFS://202.201.1.42:9000/user/root/input/456");<br/>
		String content=new String("hello world");<br/>
		CreateFile cf=new CreateFile(filePath, content);<br/>
		cf.create();<br/>
	</p>
 * @author mang
 * */
public class CreateFile {
	Configuration conf;
	FileSystem fs;
	Path filePath;//要创建文件的HDFS路径
	String content;//要创建文件的内容
	public CreateFile(String filePath,String content) throws IOException{
		conf = new Configuration();
		fs = FileSystem.get(conf);
		this.filePath=new Path(filePath);
		this.content=content;
	}
	
/**
 * 在hdfs上创建文件。
 * @author mang
 * 
 * */
	public void create() throws IOException{
		FSDataOutputStream dos = fs.create(filePath);
		String utf8 = "UTF-8";
		dos.write(content.getBytes(utf8));
		//这里添加一些示例，帮助你扩展
		/*dos.write("/r/n".getBytes(utf8));
		dos.write("//ceshi//".getBytes(utf8));
		dos.write("/r/n".getBytes(utf8));
		dos.write("hello world hello hadoop".getBytes(utf8));
		Date dat = new Date();		
		dos.write(dat.toString().getBytes());*/
		
		dos.close();
		
		System.out.println("create file success");		
		
	}

}