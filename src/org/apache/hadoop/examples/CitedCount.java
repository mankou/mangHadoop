package org.apache.hadoop.examples;

/**
 * create:21:24 2012-8-24
 * lastmodiyf:21:24 2012-8-24
 * @author mang
 * 说明：该程序来自《hadoop实战书60页》
 * Function:对被引用专利进行计数，计算专利被引用了多少次
 * 
 * */
import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.KeyValueTextInputFormat;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.mapred.TextOutputFormat;
import org.apache.hadoop.mapred.lib.LongSumReducer;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class CitedCount extends Configured implements Tool {//为什么要实现Tool接口
    
    public static class MapClass extends MapReduceBase
        implements Mapper<Text, Text, Text, Text> {
        
        public void map(Text key, Text value,
                        OutputCollector<Text, Text> output,
                        Reporter reporter) throws IOException {
                        
            output.collect(value, key);
        }
    }
    
    public static class Reduce extends MapReduceBase
        implements Reducer<Text, Text, Text, IntWritable> {//这里与ReverseCiting.java中不一样
        
        public void reduce(Text key, Iterator<Text> values,
                           OutputCollector<Text, IntWritable> output,
                           Reporter reporter) throws IOException {
                           
//            String csv = "";
//            while (values.hasNext()) {
//                if (csv.length() > 0) csv += ",";
//                csv += values.next().toString();
//            }
//            output.collect(key, new Text(csv));
        	int count=0;
        	while(values.hasNext()){
        		values.next();
        		count++;       		
        		}
        	output.collect(key, new IntWritable(count));
        }
    }
    
    public int run(String[] args) throws Exception {
        Configuration conf = getConf();
        
        JobConf job = new JobConf(conf, CitedCount.class);
        
        Path in = new Path(args[0]);
        Path out = new Path(args[1]);
        FileInputFormat.setInputPaths(job, in);
        FileOutputFormat.setOutputPath(job, out);
        
        job.setJobName("MyJob");
        job.setMapperClass(MapClass.class);
        job.setReducerClass(Reduce.class);
        
        job.setInputFormat(KeyValueTextInputFormat.class);//肯定在这里指定了k1 k2的类型
        job.setOutputFormat(TextOutputFormat.class);
        job.setOutputKeyClass(Text.class);//指定k2 类型 见 实战pdf71页 书60页
        job.setOutputValueClass(Text.class);//指定v2类型 见实战pdf71页 书60页
        job.set("key.value.separator.in.input.line", ",");
        
        JobClient.runJob(job);
        
        return 0;
    }
    
    public static void main(String[] args) throws Exception { 
        int res = ToolRunner.run(new Configuration(), new CitedCount(), args);
        
        System.exit(res);
    }
}
