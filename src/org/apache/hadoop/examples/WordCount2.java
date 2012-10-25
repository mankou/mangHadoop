/*
 * *˵���������ڡ�hadoopʵս��43ҳ��pdf54ҳ����ĳ����嵥3-3���޸ĵ�wordcount��������
 * 		   �����Դ�����������map ��reduce
 * * ʹ�ã�
 *		��ȷ��hdfs��û�С�/user/root/outputĿ¼�������ɾ��
 *		�ѱ����ļ�������/user/root/inputĿ¼������оͲ��ÿ��ˣ�������eclipse���ò˵��ϴ���Ҳ����linux����hdfs�����ϴ�
 *		��run comfiguration�е��½�java application���ڡ����������롡hdfs://202.201.1.42:9000/user/root/input hdfs://202.201.1.42:9000/user/root/output����һ��������·��һ�������·��
 *		�Ҽ����ļ������С�run as---run on hadoop������ֱ���ڹ����������Ӧ��java��application�����С�
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
