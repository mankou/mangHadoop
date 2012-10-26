package MapReducematrix.matrix;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class MatrixApp {

    private static void showMatrix(double matrix[][]) {
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[row].length; col++) {
                System.out.print(matrix[row][col] + "  ");
            }
            System.out.println();
        }
    }
    
    private static void addOneMatrix(double[][] matrix, String string) {
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
				matrix[row][col] += Double.parseDouble(colString);
				col++;
			}
			row++;
		}
	}
    
    private static String parseMatricToString(double[][] array) {
		if(array == null || array.length == 0) return "";
		String result = "";
		for(int row = 0; row < array.length; row++) {
			for(int col = 0; col < array[0].length - 1; col++) {
				result += array[row][col] + ",";
			}
			result += array[row][array[0].length - 1] + ";";
		}
		return result;
	}
    
    public static void main(String[] args) {
    	double[][] a = new double[3][3];
    	for(int row = 0; row < 3; row++) {
    		for(int col = 0; col < 3; col++) {
    			a[row][col] = row + col;
    		}
    	}
    	
    	double[][] b = new double[3][3];
    	
    	String string = parseMatricToString(a);
		System.out.println(string);
    	
		addOneMatrix(b, string);
		System.out.println(parseMatricToString(b));
		
    	
    	
    }
    

   /* public static void main(String[] args) throws IOException {

        MatrixCalculator calculator = new MatrixCalculator();

      //  DataAnalysis dataAnalysis = new DataAnalysis("sample.txt");

        double[][] matrix = dataAnalysis.getMatrix(10);

        System.out.println("-------------matrix--------------");

        showMatrix(matrix);

        System.out.println("-------------matrix end--------------");

        List<Double> result = calculator.calculate(matrix);

        System.out.println("---------R2 = " + dataAnalysis.getRDoubleValue(result));



        if (result == null) {
            System.out.println("No solution or unique solution");
        } else {
            System.out.println("---------Results from b0 ~ bk");
            for (Double value : result) {
                System.out.println(value + "  ");
            }

        }

    }*/
}
