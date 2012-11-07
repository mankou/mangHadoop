/*
 * @author mang
 * @version 1.0
 * create: 21:03 2012-11-7
 * last modify:21:03 2012-11-7
 * 其它说明：来自于 "hadoop捷径51页 删除HDFS上的文件"   "hadoop捷径52页查看某个hdfs文件是否存在"
 * */
package mang.hadoop.library;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
/**
 * 删除HDFS上的文件
 * @author mang
 * 
 * */
public class DeleteFile {
	Configuration conf;
	FileSystem hdfs;
	Path delef;
/**
 * DeleteFile的构造方法.
 * @param deletePath 要删除文件的路径。可以是目录的路径。这里支持递归删除
 * @return 没有返回值
 * */
	public DeleteFile(String deletePath) {
		conf = new Configuration();

		try {

			hdfs = FileSystem.get(conf);
			System.out.println("create hdfs ok");//用于测试
			delef=new Path(deletePath);
//			System.out.println(delef.toString());//用于测试
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		

	}
	/**
	 * 删除HDFS上的文件，路径参数已在new时指定
	 * <p>参考自来自于 "hadoop捷径51页 删除HDFS上的文件"   "hadoop捷径52页查看某个hdfs文件是否存在"
	 * @param 没有参数
	 * @return 没有返回值
	 */
	public  void delete() throws Exception {
		
		
		boolean isExists=hdfs.exists(delef);//判断文件是否存在
//		System.out.println("文件是否存在？"+isExists);//用于测试
		if(isExists)
		{
			boolean isDeleted=hdfs.delete(delef, true);//递归删除，如果不是递归删除则第2个参数写 false
			System.out.println("delete?"+isDeleted);
		}else{
			System.out.println("文件不存在");
		}
		
	}

}
