package mang.hadoop.examples;
/**
 * create:21:29 2012-8-24
 * last modify:21:29 2012-8-24
 * @author mang
 * 说明：该程序来自于《hadoop实战56页》
 * 功能：对citing cited进行反转  形成 cited citing的形式
 * */
import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
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
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class ReverseCiting extends Configured implements Tool {//为什么要实现Tool接口
    
    public static class MapClass extends MapReduceBase
        implements Mapper<Text, Text, Text, Text> {
        
        public void map(Text key, Text value,
                        OutputCollector<Text, Text> output,
                        Reporter reporter) throws IOException {
                        
            output.collect(value, key);
        }
    }
    
    public static class Reduce extends MapReduceBase
        implements Reducer<Text, Text, Text, Text> {
        
        public void reduce(Text key, Iterator<Text> values,
                           OutputCollector<Text, Text> output,
                           Reporter reporter) throws IOException {
                           
            String csv = "";
            while (values.hasNext()) {
                if (csv.length() > 0) csv += ",";
                csv += values.next().toString();
            }
            output.collect(key, new Text(csv));
        }
    }
    
    public int run(String[] args) throws Exception {
        Configuration conf = getConf();
        
        JobConf job = new JobConf(conf, ReverseCiting.class);
        
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
        int res = ToolRunner.run(new Configuration(), new ReverseCiting(), args);
        
        System.exit(res);
    }
}
