/*
 * create:17:06 2012-11-8
 * last modify:17:06 2012-11-8
 * */
package mang.hadoop.library;

import java.io.IOException;

import mang.hadoop.test.DownloadFileTest;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
/**
 * 从HDFS拷备文件到本地
 * <p>使用方法:<br> DownloadFile df=new DownloadFile(src,dst);<br>
 * df.getFromHdfs();</p>
 * <p>如果是在windows下远程调用linux上的hadoop,则这里的本地指windows</p>
 * @author mang
 * @see DownloadFileTest
 * */
public class DownloadFile {
	Path srcPath;
	Path dstPath;
	FileSystem fs;
	Configuration conf;
/**
 * 构造函数，用于初始化srcPath dstPath conf fs等变量
 * @param src HDFS路径
 * @param dst 本地路径
 * */
	public DownloadFile(String src, String dst) throws IOException {
		srcPath = new Path(src);
		dstPath = new Path(dst);
		conf = new Configuration();
		fs = FileSystem.get(conf);
		
//		有两种获取HDFS对象的方法，这里是另一种
		/* Path dstPath = new Path(src);
		 fs = dstPath.getFileSystem(conf);*/

	}
/**
 * 从hdfs拷备文件本地
 * */
	public boolean getFromHdfs() {
		
		try {
			
			
			fs.copyToLocalFile(false, srcPath, dstPath);// 从hdfs下载文件到本地。false表示不删除hdfs上的文件，true表示删除hdfs上的文件
			System.out.println("copyToLocalFile success!!!   "+"the local paht is "+dstPath.toString());

		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
