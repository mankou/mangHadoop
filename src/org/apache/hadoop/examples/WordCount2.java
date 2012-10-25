/*
 * *说明：来自于　hadoop实战书43页　pdf54页　书的程序清单3-3　修改的wordcount程序例程
 * 		   用了自带的类来设置map 和reduce
 * * 使用：
 *		先确保hdfs上没有　/user/root/output目录　如果有删除
 *		把本地文件拷到　/user/root/input目录　如果有就不用拷了．可以在eclipse中用菜单上传，也可在linux下用hdfs命令上传
 *		在run comfiguration中的新建java application　在　参数中填入　hdfs://202.201.1.42:9000/user/root/input hdfs://202.201.1.42:9000/user/root/output　　一个是输入路径一个是输出路径
 *		右键该文件　运行　run as---run on hadoop　或者直接在工具栏点击相应的java　application名运行　
 * 
 * */
package org.apache.hadoop.examples;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.lib.TokenCountMapper;
import org.apache.hadoop.mapred.lib.LongSumReducer;

public class WordCount2 {
    public static void main(String[] args) {
        JobClient client = new JobClient();
        JobConf conf = new JobConf(WordCount2.class);

        FileInputFormat.addInputPath(conf, new Path(args[0]));
        FileOutputFormat.setOutputPath(conf, new Path(args[1]));

        conf.setOutputKeyClass(Text.class);
        conf.setOutputValueClass(LongWritable.class);
        conf.setMapperClass(TokenCountMapper.class);
        conf.setCombinerClass(LongSumReducer.class);
        conf.setReducerClass(LongSumReducer.class);

        client.setConf(conf);
        try {
            JobClient.runJob(conf);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
