package MapReducematrix.matrix;

import java.io.IOException;
import java.util.Iterator;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

public class CaculatorLinearregression {

	public static class TokenizerMapper extends
			Mapper<Object, Text, Text, Text> {

		private static final String AVERAGE_KEY = "average";
		private static final String MATRIX_KEY = "matrix";
		private static final int ATTRIBUTE_COUNT = 10;

		private DataAnalysis dataanalysis = new DataAnalysis();

		private boolean isValid(String[] arrayResult) {
			if (arrayResult.length == (ATTRIBUTE_COUNT + 1))
				return true;
			try {
				Float.parseFloat(arrayResult[0]);
				return true;
			} catch (Exception e) {
				return false;
			}
		}

		public void map(Object key, Text value, Context context)
				throws IOException, InterruptedException {
			
			double[][] matrix = new double[ATTRIBUTE_COUNT + 1][ATTRIBUTE_COUNT + 2];
			double[] averageX = new double[ATTRIBUTE_COUNT];
			
			String oneLine = value.toString();
			if (oneLine == null || oneLine.trim() == "")
				return;

			String[] words = oneLine.split("\\s+");

			if (!isValid(words))
				return;

			dataanalysis.parseStringValueToDoubleMatrix(words, matrix, averageX, 1);

			Text matrixKey = new Text(MATRIX_KEY);
			context.write(matrixKey, new Text(parseMatricToString(matrix)));
			
//			Text averageKey = new Text(AVERAGE_KEY);
//			context.write(averageKey, new Text(parseAverageXToString(averageX)));
		}

		private String parseAverageXToString(double[] array) {
			if (array == null || array.length == 0)
				return "";
			String result = "";
			for (int index = 0; index < array.length; index++) {
				result += array[index] + ",";
			}
			return result;
		}

		private String parseMatricToString(double[][] array) {
			if (array == null || array.length == 0)
				return "";
			String result = "";
			for (int row = 0; row < array.length; row++) {
				for (int col = 0; col < array[0].length - 1; col++) {
					result += array[row][col] + ",";
				}
				result += array[row][array[0].length - 1] + ";";
			}
			return result;
		}
	}

	public static class TextSumReducer extends
			Reducer<Text, Text, Text, Text> {

		private static final String AVERAGE_KEY = "average";
		private static final String MATRIC_KEY = "matrix";
		private static final int ATTRIBUTE_COUNT = 10;
		
		private double[][] matrix = new double[ATTRIBUTE_COUNT + 1][ATTRIBUTE_COUNT + 2];
		private double[] averageX = new double[ATTRIBUTE_COUNT];

		public void reduce(Text key, Iterable<Text> values, Context context)
				throws IOException, InterruptedException {
			
			addEachMapValueToMatrix(matrix, values);
			context.write(new Text(MATRIC_KEY), new Text(parseMatricToString(matrix)));
			
			
//			addEachAverageXToAverage(averageX, values);
//			context.write(new Text(AVERAGE_KEY), new Text(parseAverageXToString((averageX))));
		}

		private void addEachAverageXToAverage(double[] averageX,
				Iterable<Text> values) {
			Iterator<Text> iterator = values.iterator();
			while (iterator.hasNext()) {
				addOneAverage(averageX, iterator.next().toString());
			}
			
		}

		private void addOneAverage(double[] averageX, String string) {
			String[] attributeStrings = string.split(",");
			for(int attributeIndex = 0, index = 0; attributeIndex < attributeStrings.length; attributeIndex ++) {
				String attributeString = attributeStrings[attributeIndex];
				if(attributeString == null || attributeString.trim() == "") continue;
				
				///////////////////////////////////////////////  sum all the average from each map
				averageX[index] += Double.parseDouble(attributeString);
				index++;
			}
			
		}

		private void addEachMapValueToMatrix(double[][] matrix,
				Iterable<Text> values) {
			Iterator<Text> iterator = values.iterator();
			while (iterator.hasNext()) {
				addOneMatrix(matrix, iterator.next().toString());
			}
		}
		
		private String parseAverageXToString(double[] array) {
			if (array == null || array.length == 0)
				return "";
			String result = "";
			for (int index = 0; index < array.length; index++) {
				result += array[index] + ",";
			}
			return result;
		}

		private String parseMatricToString(double[][] array) {
			if (array == null || array.length == 0)
				return "";
			String result = "";
			for (int row = 0; row < array.length; row++) {
				for (int col = 0; col < array[0].length - 1; col++) {
					result += array[row][col] + ",";
				}
				result += array[row][array[0].length - 1] + ";";
			}
			return result;
		}
		
		private void addOneMatrix(double[][] matrix, String string) {
			if(string == null || string.trim() == "") return;
			String[] rows = string.split(";");
			if (rows == null || rows.length == 0)
				return;
			for (int rowIndex = 0, row = 0; rowIndex < rows.length; rowIndex++) {
				String rowString = rows[rowIndex];
				if (rowString == null || rowString.trim() == "")
					continue;
				String[] cols = rowString.split(",");
				for (int colIndex = 0, col = 0; colIndex < cols.length; colIndex++) {
					String colString = cols[colIndex];
					if (colString == null || colString.trim() == "")
						continue;
					
					///////////////////////////////////////////////  sum all the matrix from each map
					matrix[row][col] += Double.parseDouble(colString);
					col++;
				}
				row++;
			}
		}
	}

	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		conf.set(Config.UGI, Config.USERINFO);
		String[] otherArgs = new GenericOptionsParser(conf, args)
				.getRemainingArgs();
		// if (otherArgs.length != 2) {
		// System.err.println("Usage: wordcount <in> <out>");
		// System.exit(2);
		// }
		Job job = new Job(conf, "word count");
		job.setJarByClass(CaculatorLinearregression.class);
		job.setMapperClass(TokenizerMapper.class);
		job.setCombinerClass(TextSumReducer.class);
		job.setReducerClass(TextSumReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		FileInputFormat.addInputPath(job, new Path("/home/zhangying/sample.txt"));
		FileOutputFormat.setOutputPath(job, new Path("/home/zhangying/out0"));
		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
}
