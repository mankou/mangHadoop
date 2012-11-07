/*
 *创建文件测试
 * */
package mang.hadoop.temp;

import java.util.Date;

import org.apache.hadoop.conf.*;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hdfs.*;
import org.apache.hadoop.fs.*;

public class CreateFileTest {
	public static void main(String[] args) throws Exception {
		try{
			String key=new String("123");
			String value=new String("hello world");
    		Configuration configuration = new Configuration();
			FileSystem fs = FileSystem.get(configuration);
			byte[] buff=value.getBytes();
			Path dfs=new Path("HDFS://202.201.1.42:9000/user/root/input/"+key.toString());
			FSDataOutputStream outputStream=fs.create(dfs);
			outputStream.write(buff,0,buff.length);
			System.out.println("success");
    		
    	}catch(Exception e){
    		System.out.println(e.toString());
    		
    	}

	}
}