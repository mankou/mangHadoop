/*
 * create:17:13 2012-11-8
 * last modify:17:13 2012-11-8
 * */
package mang.hadoop.test;

import java.io.IOException;

import mang.hadoop.library.DownloadFile;
/**
 * DownloadFile类的测试类，用于测试其正确性
 * <p>单个类，右键--run as --run on hadoop--选择集群 运行即可</p>
 * */
public class DownloadFileTest {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		String src =new String("/user/root/input/helloworld");//设置源路径 即HDFS路径
		String dst = new String("d:/helloworldmang");//设置目的路径 即本地路径 如果是在windows上的eclipse运行，则是windows的路径
		DownloadFile df=new DownloadFile(src,dst);
		df.getFromHdfs();
	}

}
