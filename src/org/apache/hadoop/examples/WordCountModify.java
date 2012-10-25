/**
 * 功能：wordcount程序的改进，如不区分大小字与，对分隔符的自定义等．
 * 说明：来自于hadoop实战书14页　pdf25页
 * 使用：
 *		先确保hdfs上没有　/user/root/output目录　如果有删除
 *		把本地文件拷到　/user/root/input目录　如果有就不用拷了．可以在eclipse中用菜单上传，也可在linux下用hdfs命令上传
 *		在run comfiguration中的新建java application　在　参数中填入　hdfs://202.201.1.42:9000/user/root/input hdfs://202.201.1.42:9000/user/root/output　　一个是输入路径一个是输出路径
 *		右键该文件　运行　run as---run on hadoop　或者直接在工具栏点击相应的java　application名运行　
 * 
 * */
package org.apache.hadoop.examples;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;
import org.apache.hadoop.util.ToolRunner;
import org.apache.hadoop.fs.*;

public class WordCountModify {

  public static class TokenizerMapper 
       extends Mapper<Object, Text, Text, IntWritable>{
    
    private final static IntWritable one = new IntWritable(1);
    private Text word = new Text();
      
    public void map(Object key, Text value, Context context
                    ) throws IOException, InterruptedException {
    	
    	/*原始程序默认以空格为分隔符，现在加入其它的分隔符*/
//    	StringTokenizer itr = new StringTokenizer(value.toString());
      StringTokenizer itr = new StringTokenizer(value.toString()," \t\n\r\f,.;:?");/*注意双引号后面有个空格*/
      while (itr.hasMoreTokens()) {
    	  /*把单词转换为小写，这样Hello HELLO等词算一个词，而不是两个词*/
    	word.set(itr.nextToken().toLowerCase());/*每次得到一个单词 就输出，所以这里多次调用word.set不会造成混乱*/
//        word.set(itr.nextToken());
        context.write(word, one);
      }
    }
  }
  
  public static class IntSumReducer 
       extends Reducer<Text,IntWritable,Text,IntWritable> {
    private IntWritable result = new IntWritable();

    public void reduce(Text key, Iterable<IntWritable> values, 
                       Context context
                       ) throws IOException, InterruptedException {
      int sum = 0;
      for (IntWritable val : values) {
        sum += val.get();
      }
//      result.set(sum);
//      context.write(key, result);
      /*如果词频大于2才输出*/
      if(sum>2) { 
    	  result.set(sum);
    	  context.write(key, result);
    	  }
    }
  }

  public static void main(String[] args) throws Exception {
    Configuration conf = new Configuration();
    String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
    if (otherArgs.length != 2) {
      System.err.println("Usage: wordcount <in> <out>");
      System.exit(2);
    }
    Job job = new Job(conf, "word count");
    job.setJarByClass(WordCountModify.class);
    job.setMapperClass(TokenizerMapper.class);
    job.setCombinerClass(IntSumReducer.class);
    job.setReducerClass(IntSumReducer.class);
    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(IntWritable.class);
    FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
    FileOutputFormat.setOutputPath(job, new Path(otherArgs[1]));
    System.exit(job.waitForCompletion(true) ? 0 : 1);
  }
}
