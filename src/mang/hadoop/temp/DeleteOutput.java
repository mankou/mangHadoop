/*
 * @author mang
 * @version 1.0
 * create: 21:03 2012-11-7
 * last modify:21:03 2012-11-7
 * ����˵����ɾ��hdfs�е�outputĿ¼
 * ����˵���������� "hadoop�ݾ�51ҳ ɾ��HDFS�ϵ��ļ�"   "hadoop�ݾ�52ҳ�鿴ĳ��hdfs�ļ��Ƿ����"
 * */
package mang.hadoop.temp;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

public class DeleteOutput {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		Configuration conf=new Configuration();
		FileSystem hdfs=FileSystem.get(conf);
		String deletePath=new String("hdfs://202.201.1.42:9000/user/root/output");
		Path delef=new Path(deletePath);
		boolean isExists=hdfs.exists(delef);//�ж��ļ��Ƿ����
		if(isExists)
		{
			boolean isDeleted=hdfs.delete(delef, true);//�ݹ�ɾ����������ǵݹ�ɾ�����2������д false
			System.out.println("delete?"+isDeleted);
		}else{
			System.out.println(deletePath+"�ļ�������");
		}
		
	}

}
