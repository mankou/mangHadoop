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
 * ��HDFS�����ļ�������
 * <p>ʹ�÷���:<br> DownloadFile df=new DownloadFile(src,dst);<br>
 * df.getFromHdfs();</p>
 * <p>�������windows��Զ�̵���linux�ϵ�hadoop,������ı���ָwindows</p>
 * @author mang
 * @see DownloadFileTest
 * */
public class DownloadFile {
	Path srcPath;
	Path dstPath;
	FileSystem fs;
	Configuration conf;
/**
 * ���캯�������ڳ�ʼ��srcPath dstPath conf fs�ȱ���
 * @param src HDFS·��
 * @param dst ����·��
 * */
	public DownloadFile(String src, String dst) throws IOException {
		srcPath = new Path(src);
		dstPath = new Path(dst);
		conf = new Configuration();
		fs = FileSystem.get(conf);
		
//		�����ֻ�ȡHDFS����ķ�������������һ��
		/* Path dstPath = new Path(src);
		 fs = dstPath.getFileSystem(conf);*/

	}
/**
 * ��hdfs�����ļ�����
 * */
	public boolean getFromHdfs() {
		
		try {
			
			
			fs.copyToLocalFile(false, srcPath, dstPath);// ��hdfs�����ļ������ء�false��ʾ��ɾ��hdfs�ϵ��ļ���true��ʾɾ��hdfs�ϵ��ļ�
			System.out.println("copyToLocalFile success!!!   "+"the local paht is "+dstPath.toString());

		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
