/*
 * create:17:13 2012-11-8
 * last modify:17:13 2012-11-8
 * */
package mang.hadoop.test;

import java.io.IOException;

import mang.hadoop.library.DownloadFile;
/**
 * DownloadFile��Ĳ����࣬���ڲ�������ȷ��
 * <p>�����࣬�Ҽ�--run as --run on hadoop--ѡ��Ⱥ ���м���</p>
 * */
public class DownloadFileTest {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		String src =new String("/user/root/input/helloworld");//����Դ·�� ��HDFS·��
		String dst = new String("d:/helloworldmang");//����Ŀ��·�� ������·�� �������windows�ϵ�eclipse���У�����windows��·��
		DownloadFile df=new DownloadFile(src,dst);
		df.getFromHdfs();
	}

}
