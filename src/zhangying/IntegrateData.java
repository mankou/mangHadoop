package zhangying;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.KeyValueTextInputFormat;
import org.apache.hadoop.mapred.TextInputFormat;
import org.apache.hadoop.mapred.lib.*;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;
import org.openscience.cdk.exception.CDKException;

public class IntegrateData {

  public class MoleculeMapper extends Mapper<Object, Text, Text, Text> {
		private Text setS = new Text();
		private Text setZincId = new Text();
		Property p;
		String ZincId;
		StringBuffer STR=new StringBuffer("");
		int filenum=0;
	    String s="";
	    int j=0;
	    String crlf = System.getProperty("line.separator");
	    public void map(Object key, Text value, Context context)
				throws IOException, InterruptedException {
	    		
	    		 String str=value.toString();    
		          j++;  
		          STR.append(str);
		          STR.append(crlf) ;
		        	  if(STR.equals("@<TRIPOS>MOLECULE"))
					  {
					   ZincId=str;
					  }
					  if(str.equals(""))
		        	  {  
		        		  s=s+"a";
		        		  if(s.equals("a")&&(!STR.equals(crlf)))
		        		  {   
		        		      try {
		        		    	  p=new Property(STR.toString());
		        		    	  setS.set(p.getXLogP().toString()
											+ "\t"
											+ p.getRotatableBonds().toString()
											+ "\t"
											+ p.getH_Donor1().toString()
											+ "\t"
											+ p.getHB_Acceptor1().toString()
											+ "\t"
											+ String.valueOf(p.getMolecule_weight())
											+ "\t"
											+ String.valueOf(p.getNaturalMass())
											+ "\t"
											+ String.valueOf(p.getAtomCount())
											+ "\t"
											+ String.valueOf(p.getBondCount())
											+ "\t"
											+ p.getPolar_surface_area()
													.toString() + "\t"
											+ p.getComplexity().toString());
				        	     
		        		    	  setZincId.set(ZincId);
		        		    	  context.write(setZincId, setS);
							} catch (CDKException e) {
								e.printStackTrace();
							}
		        	      s="";
		        	      STR.replace(0, STR.length()-1, "") ;
		        		  }
		        	  }
				}
	}

  
  public static class OutMapper 
       extends Mapper<Object, Text, Text, Text>{
    
    private Text setZincID = new Text();
    private Text setGridSore = new Text();
	private String ZincId;
	private String Gridscore;
	int n=0;
    public void map(Object key, Text value, Context context
                    ) throws IOException, InterruptedException {
      String word=value.toString();
	  n++;
	  if(n<2)
	  {
		  if(word.startsWith("Molecule"))
		  {
			  int index = word.indexOf(":");
			  ZincId=word.substring(index + 1).trim();
		  }
		  if(word.startsWith("Grid Score"))
		  {
			  int index = word.indexOf(":");
			  Gridscore=word.substring(index + 1).trim();
		  }
	  
	  }
	  if(n==2)
	  {
		  setZincID.set(ZincId);
		  setGridSore.set(Gridscore);
		  context.write(setZincID, setGridSore);
		  n=0;
	  }
	}
  }
  
  public static class IntegerReducer 
       extends Reducer<Text,Text,Text,Text> {
    private Text result = new Text();

    public void reduce(Text key, Iterable<Text> values, 
                       Context context
                       ) throws IOException, InterruptedException {
      String propertyvalue = null;
      for (Text val : values) {
        propertyvalue+=val.toString();
		}
      result.set(propertyvalue);
      context.write(key, result);
    } 
  }

  public static void main(String[] args) throws Exception {
    Configuration conf = new Configuration();
    String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
    if (otherArgs.length != 2) {
      System.err.println("Usage: IntegrateData <in> <out>");
      System.exit(2);
    }
    Job job = new Job(conf, "IntegrateData");
    job.setJarByClass(IntegrateData.class);
    job.setReducerClass(IntegerReducer.class);
    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(Text.class);
    MultipleInputs.addInputPath(conf, new Path("/home/zhangying/molecule"), KeyValueTextInputFormat.class, MoleculeMapper.class);
    MultipleInputs.addInputPath(conf,new Path("/home/zhangying/resultout"),KeyValueTextInputFormat.class,OutMapper.class); 
    FileOutputFormat.setOutputPath(job, new Path("/home/zhangying/output"));
    System.exit(job.waitForCompletion(true) ? 0 : 1);
  }
}
