/*
 * create:22:04 2012-11-7
 * last modify:22:04 2012-11-7
 * */
package mang.hadoop.test;

import mang.hadoop.library.DeleteFile;
/**
 * 用于测试DeleteFile类
 * <p>会删除hdfs上output目录。<br>
 * 修改代码中的hdfs的路径<br>
 * 运行方法：右键--run as--run on hadoop--选择hadoop集群 即可
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
