/*
 * create:22:04 2012-11-7
 * last modify:22:04 2012-11-7
 * */
package mang.hadoop.test;

import mang.hadoop.library.DeleteFile;
/**
 * ���ڲ���DeleteFile��
 * <p>��ɾ��hdfs��outputĿ¼��<br>
 * �޸Ĵ����е�hdfs��·��<br>
 * ���з������Ҽ�--run as--run on hadoop--ѡ��hadoop��Ⱥ ����
 * @author mang
 * */
public class DeleteFileTest {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		String str=new String("HDFS://202.201.1.42:9000/user/root/input/helloworld");
		DeleteFile df=new DeleteFile(str);
		df.delete();
	}

}
