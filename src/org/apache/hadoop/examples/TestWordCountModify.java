/*
 * 用于测试的文件
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

public class TestWordCountModify {

  public static class TokenizerMapper 
       extends Mapper<Object, Text, Text, IntWritable>{
    
    private final static IntWritable one = new IntWritable(1);
    private Text word = new Text();
      
    public void map(Object key, Text value, Context context
                    ) throws IOException, InterruptedException {
    	//以下用于测试map过程中调用系统命令
    	
   /* 	String cmd="/bin/sh -c cp -r /home/maning/test/test1/* /home/maning/test/test2"; 
		Runtime run = Runtime.getRuntime();//返回与当前 Java 应用程序相关的运行时对象 
		try {
			Process p = run.exec(cmd);// 启动另一个进程来执行命令   
		} catch (IOException e) {
			
			e.printStackTrace();
		}*/
    	
    	/*FsShell shell = new FsShell();
        String[] argv = {"-cp","-r","/home/maning/test/test1/*","/home/maning/test/test2"};
        
        int res;
        try {
          try {
			res = ToolRunner.run(shell, argv);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        } finally {
          shell.close();
        }*/
    	
    	
		
		//以上用于测试map过程中调用系统命令
    	
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
    //以下测试调用系统命令
    
//    String cmd="/bin/sh -c cp -r /home/maning/test/test1/* /home/maning/test/test2";
   /* String cmd="/bin/sh -c ls";
	Runtime run = Runtime.getRuntime();//返回与当前 Java 应用程序相关的运行时对象 
	try {
		Process p = run.exec(cmd);// 启动另一个进程来执行命令   
	} catch (IOException e) {
		
		e.printStackTrace();
	}*/
    
    FsShell shell = new FsShell();
//    String[] argv = {"-cp","/home/maning/test/test1/source","/home/maning/test/test2"};
//      String[] argv={"touch","/home/maning/test/test2/newfile"};
    String[] argv = {"-ls","/home/hadoop/bin"};
    
    int res;
    try {
      res = ToolRunner.run(shell, argv);
    } finally {
      shell.close();
    }
//    System.exit(res);
  
    
    
    
    //以上测试调用系统命令
    Job job = new Job(conf, "word count");
    job.setJarByClass(TestWordCountModify.class);
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
