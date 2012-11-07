/*
 * @author mang
 * @version 1.0
 * create: 21:03 2012-11-7
 * last modify:21:03 2012-11-7
 * ����˵���������� "hadoop�ݾ�51ҳ ɾ��HDFS�ϵ��ļ�"   "hadoop�ݾ�52ҳ�鿴ĳ��hdfs�ļ��Ƿ����"
 * */
package mang.hadoop.library;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
/**
 * ɾ��HDFS�ϵ��ļ�
 * @author mang
 * 
 * */
public class DeleteFile {
	Configuration conf;
	FileSystem hdfs;
	Path delef;
/**
 * DeleteFile�Ĺ��췽��.
 * @param deletePath Ҫɾ���ļ���·����������Ŀ¼��·��������֧�ֵݹ�ɾ��
 * @return û�з���ֵ
 * */
	public DeleteFile(String deletePath) {
		conf = new Configuration();

		try {

			hdfs = FileSystem.get(conf);
			System.out.println("create hdfs ok");//���ڲ���
			delef=new Path(deletePath);
//			System.out.println(delef.toString());//���ڲ���
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		

	}
	/**
	 * ɾ��HDFS�ϵ��ļ���·����������newʱָ��
	 * <p>�ο��������� "hadoop�ݾ�51ҳ ɾ��HDFS�ϵ��ļ�"   "hadoop�ݾ�52ҳ�鿴ĳ��hdfs�ļ��Ƿ����"
	 * @param û�в���
	 * @return û�з���ֵ
	 */
	public  void delete() throws Exception {
		
		
		boolean isExists=hdfs.exists(delef);//�ж��ļ��Ƿ����
//		System.out.println("�ļ��Ƿ���ڣ�"+isExists);//���ڲ���
		if(isExists)
		{
			boolean isDeleted=hdfs.delete(delef, true);//�ݹ�ɾ����������ǵݹ�ɾ�����2������д false
			System.out.println("delete?"+isDeleted);
		}else{
			System.out.println("�ļ�������");
		}
		
	}

}
