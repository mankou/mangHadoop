/*
 * @author mang
 * @version 1.0
 * create: 21:03 2012-11-7
 * last modify:21:03 2012-11-7
 * 功能说明：删除hdfs中的output目录
 * 其它说明：来自于 "hadoop捷径51页 删除HDFS上的文件"   "hadoop捷径52页查看某个hdfs文件是否存在"
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
		boolean isExists=hdfs.exists(delef);//判断文件是否存在
		if(isExists)
		{
			boolean isDeleted=hdfs.delete(delef, true);//递归删除，如果不是递归删除则第2个参数写 false
			System.out.println("delete?"+isDeleted);
		}else{
			System.out.println(deletePath+"文件不存在");
		}
		
	}

}
