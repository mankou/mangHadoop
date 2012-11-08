/*
 * create:17:44 2012-11-8
 * last modify:17:44 2012-11-8
 * */
package mang.hadoop.test;

import java.io.IOException;

import mang.hadoop.library.CreateFile;
/**
 * CreateFile类的测试类
 * <p>单个运行即可，右键--run as-- run on hadoop-选择集群即可</p>
 * @author mang
 * @see CreateFile
 * */
public class CreateFileTest {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		String filePath=new String("HDFS://202.201.1.42:9000/user/root/input/456");//要创建文件的路径 
		String content=new String("hello world HELLO hadoop");//文件内容
		CreateFile cf=new CreateFile(filePath, content);
		cf.create();
	}

}
