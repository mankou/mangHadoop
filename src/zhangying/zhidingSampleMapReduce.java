package zhangying;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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

public class zhidingSampleMapReduce {

  public static class TokenizerMapper 
       extends Mapper<Object, Text, Text, Text>{
    
    private Text one=new Text();
    private Text word=new Text();
	  List<String> list = new ArrayList<String>();
	  SampleDataParser sampleDataParse=new SampleDataParser();
	  int linecount=0;
	  public void loadProperty()
	  {
		  try {
			SampleProperty.loadProperty("/home/zhangying/config.properties");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  }
    public void map(Object key, Text value, Context context
                    ) throws IOException, InterruptedException {
    	
    	String oneLine =value.toString();
      if(oneLine!=null)
      {
    	  if(sampleDataParse.isWantedLine(oneLine))
    	  {
    		  word.set(oneLine);
    		  one.set(String.valueOf(linecount++));
    		  context.write(one, word);
    	  }
      }
    }
  }
  
  public static class IntSumReducer 
       extends Reducer<Text,Text,Text,Text> {
    private Text result = new Text();

    public void reduce(Text key, Text values, 
                       Context context
                       ) throws IOException, InterruptedException {
      result.set(values);
      context.write(key, result);
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
    job.setJarByClass(zhidingSampleMapReduce.class);
    job.setMapperClass(TokenizerMapper.class);
    //job.setCombinerClass(IntSumReducer.class);
    job.setReducerClass(IntSumReducer.class);
    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(Text.class);
    FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
    FileOutputFormat.setOutputPath(job, new Path(otherArgs[1]));
    System.exit(job.waitForCompletion(true) ? 0 : 1);
  }
}
