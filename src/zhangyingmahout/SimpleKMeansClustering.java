package org.apache.mahout.clustering;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.util.ToolRunner;

import org.apache.mahout.clustering.canopy.CanopyDriver;
import org.apache.mahout.clustering.conversion.InputDriver;
import org.apache.mahout.clustering.kmeans.KMeansDriver;
import org.apache.mahout.clustering.kmeans.RandomSeedGenerator;
import org.apache.mahout.common.AbstractJob;
import org.apache.mahout.common.HadoopUtil;
import org.apache.mahout.common.commandline.DefaultOptionCreator;
import org.apache.mahout.common.distance.DistanceMeasure;
import org.apache.mahout.common.distance.EuclideanDistanceMeasure;
import org.apache.mahout.common.distance.SquaredEuclideanDistanceMeasure;
import org.apache.mahout.math.DenseVector;
import org.apache.mahout.math.RandomAccessSparseVector;
import org.apache.mahout.math.Vector;
import org.apache.mahout.math.VectorWritable;

import org.apache.mahout.clustering.kmeans.Cluster;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SimpleKMeansClustering {
	
	public static final double[][] points = { {1, 1,2}, {2, 1,3}, {1, 2,5}, {2, 2,5}, {3, 3,2}, {8, 8,5},
        {9, 8, 3}, {8, 9,33}, {9, 9,2},{3,4,5}, {4,5,6}, {34,54,43},{33,33,45}};
	private static final int DIMENTION = 11;
	
	
    public static void writePointsToFile(List<Vector> points, String fileName, FileSystem fs, Configuration conf) throws IOException {
    	Path path = new Path(fileName);
    	SequenceFile.Writer writer = new SequenceFile.Writer(fs, conf, path, LongWritable.class, VectorWritable.class); long recNum = 0;
    	VectorWritable vec = new VectorWritable();
    	for (Vector point : points) {
    		vec.set(point);
    	    writer.append(new LongWritable(recNum++), vec);
        }
    	writer.close();
    }
    
    public static List<Vector> getPoints(double[][] raw) {
    	
    	List<Vector> points = new ArrayList<Vector>(); 
    	for (int i = 0; i < raw.length; i++) {
    		double[] fr = raw[i];
    		Vector vec = new RandomAccessSparseVector(fr.length); 
    		vec.assign(fr);
    		points.add(vec);
    	}
    	return points;
    }
    
    public static List<Vector> getPointsFromList(List<double[]> raw) {
    	
    	List<Vector> points = new ArrayList<Vector>(); 
    	for (int i = 0; i < raw.size(); i++) {
    		double[] fr = raw.get(i);
    		Vector vec = new RandomAccessSparseVector(fr.length); 
    		vec.assign(fr);
    		points.add(vec);
    	}
    	return points;
    }
    
    public static void main(String args[]) throws Exception {
    	int k = 5;  	
    	
    	List<double[]> testDoubleData = getDouble("sample.txt");
    	
    	List<Vector> vectors = getPointsFromList(testDoubleData);
    	File testData = new File("testdata"); 
    	if (!testData.exists()) {
    		testData.mkdir();
    	}
    	testData = new File("testdata/points"); 
    	if (!testData.exists()) {
    		 testData.mkdir();
    	}
    	
    	Configuration conf = new Configuration();
    	FileSystem fs = FileSystem.get(conf);
    	writePointsToFile(vectors, "testdata/points/file1", fs, conf);
    	
    	
    	Path path = new Path("testdata/clusters/part-00000");
    	SequenceFile.Writer writer = new SequenceFile.Writer(fs, conf, path, Text.class, Cluster.class);
    	for (int i = 0; i < k; i++) {
    		Vector vec = vectors.get(i);
    		Cluster cluster = new Cluster(vec, i, new EuclideanDistanceMeasure());
    		writer.append(new Text(cluster.getId() + ""), cluster);
    	}
    	writer.close();
    		
    	KMeansDriver.run(conf, new Path("testdata/points"), new Path("testdata/clusters"), new Path("output"), new EuclideanDistanceMeasure(), 0.001, 10, true, false);
    	SequenceFile.Reader reader = new SequenceFile.Reader(fs, new Path("output/" + Cluster.CLUSTERED_POINTS_DIR + "/part-m-00000"), conf);
    	IntWritable key = new IntWritable();
    	WeightedVectorWritable value = new WeightedVectorWritable(); 
    	
    	BufferedWriter out = new BufferedWriter(new FileWriter(createNewFile("result.txt"), true));
    	
    	while (reader.next(key, value)) {
    		out.write(value.toString() + " belongs to cluster: " + key.toString());
            out.newLine();;
    	}
    	reader.close();
    }
    
    private static File createNewFile(String fileName) throws IOException {
        File file = new File(fileName);
        if (file.exists()) {
            file.delete();
        }
        file.createNewFile();
        return file;
    }

	private static List<double[]> getDouble(String dataFile) throws IOException {		
		List<double[]> listData = new ArrayList<double[]>();				
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(dataFile))));
	    String oneLine = "";
	    while ((oneLine = br.readLine()) != null) {
	          String[] arrayResult = oneLine.split("\\s+");
	          if (!isValid(arrayResult)) {
	              continue;
	          }         
	          listData.add(parseLineToDouble(arrayResult));        
	    }			
		return listData;
	}
	
	private static double[] parseLineToDouble(String[] arrayResult) {
		double[] oneLineDouble = new double[DIMENTION];
		for(int index = 0; index < DIMENTION; index++) {
			oneLineDouble[index] = (double) Double.parseDouble(arrayResult[index]);
		}
		return oneLineDouble;
	}

	private static boolean isValid(String[] arrayResult) {
        if (arrayResult.length == (DIMENTION + 1)) return true;
        try {
            Float.parseFloat(arrayResult[0]);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
