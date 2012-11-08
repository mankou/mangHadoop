/*
 * @author mang
 * create:2012-10-29 10:20:10
 * ���ܣ�HDFS���½��ļ�ʾ��
 * ˵����ǰ�ڲο������������ο��������Լ��������Ͻ��⡣
 * 		�ο�һ��http://blog.csdn.net/nodie/article/details/6386608 
 * 		 �ο�����http://blog.csdn.net/nodie/article/details/6386586
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
 * ���ܣ���hdfs�ϴ����ļ����ļ������ļ��������ַ�����ʽ���빹�캯��
 * <p>����˵����<br/>
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
	Path filePath;//Ҫ�����ļ���HDFS·��
	String content;//Ҫ�����ļ�������
	public CreateFile(String filePath,String content) throws IOException{
		conf = new Configuration();
		fs = FileSystem.get(conf);
		this.filePath=new Path(filePath);
		this.content=content;
	}
	
/**
 * ��hdfs�ϴ����ļ���
 * @author mang
 * 
 * */
	public void create() throws IOException{
		FSDataOutputStream dos = fs.create(filePath);
		String utf8 = "UTF-8";
		dos.write(content.getBytes(utf8));
		//�������һЩʾ������������չ
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