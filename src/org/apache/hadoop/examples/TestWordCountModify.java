/*
 * ���ڲ��Ե��ļ�
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
    	//�������ڲ���map�����е���ϵͳ����
    	
   /* 	String cmd="/bin/sh -c cp -r /home/maning/test/test1/* /home/maning/test/test2"; 
		Runtime run = Runtime.getRuntime();//�����뵱ǰ Java Ӧ�ó�����ص�����ʱ���� 
		try {
			Process p = run.exec(cmd);// ������һ��������ִ������   
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
    	
    	
		
		//�������ڲ���map�����е���ϵͳ����
    	
    	/*ԭʼ����Ĭ���Կո�Ϊ�ָ��������ڼ��������ķָ���*/
//    	StringTokenizer itr = new StringTokenizer(value.toString());
      StringTokenizer itr = new StringTokenizer(value.toString()," \t\n\r\f,.;:?");/*ע��˫���ź����и��ո�*/
      while (itr.hasMoreTokens()) {
    	  /*�ѵ���ת��ΪСд������Hello HELLO�ȴ���һ���ʣ�������������*/
    	word.set(itr.nextToken().toLowerCase());/*ÿ�εõ�һ������ ����������������ε���word.set������ɻ���*/
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
      /*�����Ƶ����2�����*/
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
    //���²��Ե���ϵͳ����
    
//    String cmd="/bin/sh -c cp -r /home/maning/test/test1/* /home/maning/test/test2";
   /* String cmd="/bin/sh -c ls";
	Runtime run = Runtime.getRuntime();//�����뵱ǰ Java Ӧ�ó�����ص�����ʱ���� 
	try {
		Process p = run.exec(cmd);// ������һ��������ִ������   
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
  
    
    
    
    //���ϲ��Ե���ϵͳ����
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
