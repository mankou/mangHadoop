package zhangying;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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

public class reservoirSampleMapReduce {

  public static class TokenizerMapper 
       extends Mapper<Object, Text, IntWritable, Text>{
    
   private IntWritable one;
   private Text word = new Text();
   private int index=0;
   private static int SAMPLE_AMOUNT=20;
   List<String> list=new ArrayList<String>();
   Random random =new Random();
    public void map(Object key, Text value, Context context
                    ) throws IOException, InterruptedException {
      String oneLine = value.toString();
      if((index<SAMPLE_AMOUNT) && (oneLine!=null))
      {
    	  word.set(oneLine);
    	  one=one = new IntWritable(index++);
    	  context.write(one, word);
      }else if((index>=SAMPLE_AMOUNT) &&(oneLine!=null))
      {
    	  int ramdomnumber=random.nextInt(index);
    	  if(ramdomnumber<SAMPLE_AMOUNT)
    	  {
    		  word.set(oneLine);
        	  one=one = new IntWritable(ramdomnumber);
    		  context.write(one, word);
    	  }
      } 
     /* if(oneLine==null)
      {
    	  int linecount=0;
    	  for(String oneLinevalue : list)
    	  {
    		  linecount++;
    		  one=one = new IntWritable(linecount);
    		  word.set(oneLinevalue);
    		  context.write(one, word);
    	  }
      }*/
     /* int linecount=0;
	  for(String oneLinevalue : list)
	  {
		  linecount++;
		  one=one = new IntWritable(linecount);
		  word.set(oneLinevalue);
		  context.write(one, word);
	  }*/
      
    }
  }
   
  
  public static class IntSumReducer 
       extends Reducer<IntWritable,Text,IntWritable,Text> {
    private Text result = new Text();

    public void reduce(IntWritable key, Iterable<Text> values, 
                       Context context
                       ) throws IOException, InterruptedException {
     
    	String value=values.toString();
    	for (Text val : values) {
    		value = val.toString();
          }
    	result.set(value);
      context.write(key, result);
    }
  }

  public static void main(String[] args) throws Exception {
    Configuration conf = new Configuration();
    String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
    /*if (otherArgs.length != 2) {
      System.err.println("Usage: wordcount <in> <out>");
      System.exit(2);
    }*/
    Job job = new Job(conf, "word count");
    job.setJarByClass(reservoirSampleMapReduce.class);
    job.setMapperClass(TokenizerMapper.class);
    job.setCombinerClass(IntSumReducer.class);
    job.setReducerClass(IntSumReducer.class);
    job.setOutputKeyClass(IntWritable.class);
    job.setOutputValueClass(Text.class);
    FileInputFormat.addInputPath(job,  new Path("/home/zhangying/sample.txt"));
    FileOutputFormat.setOutputPath(job, new Path("/home/zhangying/outsampletest2"));
    System.exit(job.waitForCompletion(true) ? 0 : 1);
  }
}
  
