/*
 * create:17:44 2012-11-8
 * last modify:17:44 2012-11-8
 * */
package mang.hadoop.test;

import java.io.IOException;

import mang.hadoop.library.CreateFile;
//TODO ¼Ó×¢ÊÍ
public class CreateFileTest {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		String filePath=new String("HDFS://202.201.1.42:9000/user/root/input/456");
		String content=new String("hello world");
		CreateFile cf=new CreateFile(filePath, content);
		cf.create();
	}

}
