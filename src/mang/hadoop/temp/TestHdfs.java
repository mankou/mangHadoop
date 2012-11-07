/*
 * 说明：用于在HDFS进行文件操作的，但没有成功，这里先不删除
 * */
package mang.hadoop.temp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;



public class TestHdfs {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TestHdfs test=new TestHdfs();
		Configuration conf = new Configuration();
		String FileName=new String("input");
		test.GetFileBolckHost(conf, FileName);

	}
	public List<String[]> GetFileBolckHost(Configuration conf, String FileName) {  
        try {  
            List<String[]> list = new ArrayList<String[]>();  
            FileSystem hdfs = FileSystem.get(conf);  
            Path path = new Path(FileName);  
            FileStatus fileStatus = hdfs.getFileStatus(path);  
  
            BlockLocation[] blkLocations = hdfs.getFileBlockLocations(  
                    fileStatus, 0, fileStatus.getLen());  
  
            int blkCount = blkLocations.length;  
            for (int i = 0; i < blkCount; i++) {  
                String[] hosts = blkLocations[i].getHosts();  
                System.out.println(hosts);
                list.add(hosts);  
            }  
            return list;  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        return null;  
    }

}
