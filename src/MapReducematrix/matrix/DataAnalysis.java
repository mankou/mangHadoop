package MapReducematrix.matrix;

import java.io.*;
import java.util.List;

public class DataAnalysis {
    private double[][] matrix;
    private String dataFile;
    private int dimension;

    private double[] averageX;
    private int lineCount;

    public DataAnalysis() {
       
    }

    //  The first column of dataFile is GridScore
    public double[][] getMatrix(int attributesAmount, String str) throws IOException {
        this.dimension = attributesAmount;
        matrix = new double[attributesAmount + 1][attributesAmount + 2];
        averageX = new double[attributesAmount];

        lineCount = 0;
        String oneLine = "";
        while ((oneLine =str) != null) {
            String[] arrayResult = oneLine.split("\\s+");
            if (!isValid(arrayResult)) {
                continue;
            }
            lineCount++;
            parseStringValueToDoubleMatrix(arrayResult, matrix, averageX, lineCount);
        }

        
        return copyMetrix(attributesAmount);
    }

   //?
    public double[][] copyMetrix(int attributesAmount) {
        double[][] returnedResult = new double[attributesAmount + 1][attributesAmount + 2];
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[0].length; col++) {
                returnedResult[row][col] = matrix[row][col];
            }
        }
        return returnedResult;
    }


    public double getRDoubleValue(List<Double> result) throws IOException {
        double TSS = 0;
        double RSS = 0;
        double averageY = getAverageY(result, averageX);

        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(dataFile))));
        String oneLine = "";
        while ((oneLine = br.readLine()) != null) {
            String[] arrayResult = oneLine.split("\\s+");
            if (!isValid(arrayResult)) {
                continue;
            }
            double y = Float.parseFloat(arrayResult[0]);
            double estimatedY = getEstimatedY(arrayResult, result);

            TSS += (y - averageY) * (y - averageY);
            RSS += (y - estimatedY) * (y - estimatedY);

            parseStringValueToDoubleMatrix(arrayResult, matrix, averageX, lineCount);
        }

        return 1 - (RSS / TSS);

    }

    public double getAverageY(List<Double> result, double[] averageX) {
        double sumValue = result.get(0); // b0
        for (int index = 1; index < result.size(); index++) {
            sumValue += result.get(index) * averageX[index - 1] / lineCount;
        }
        return sumValue;
    }

    public double getEstimatedY(String[] arrayResult, List<Double> result) {
        double sumValue = result.get(0); // b0
        for (int index = 1; index < arrayResult.length; index++) {
            sumValue += Float.parseFloat(arrayResult[index]) * result.get(index);
        }
        return sumValue;
    }


    public boolean isValid(String[] arrayResult) {
        if (arrayResult.length == (dimension + 1)) return true;
        try {
            Float.parseFloat(arrayResult[0]);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void parseStringValueToDoubleMatrix(String[] arrayResult, double[][] matrix, double[] averageX, int lineCount) {
        float score = Float.parseFloat(arrayResult[0]);//The first column of dataFile is GridScore
        for (int row = 0; row < matrix.length; row++) {

            int col = 0;

            for (; col < matrix.length; col++) {
                if (row == 0 && col == 0) {
                    matrix[row][col] = lineCount;
                    continue;
                }
                if (row == 0) {
                    float attributeValue = Float.parseFloat(arrayResult[col]);
                    matrix[0][col] += attributeValue;
                    averageX[col - 1] += attributeValue;
                } else if (col == 0) {
                    matrix[row][col] = matrix[col][row];
                } else {
                    matrix[row][col] += Float.parseFloat(arrayResult[row]) * Float.parseFloat(arrayResult[col]);
                }
            }

            if (col == matrix.length) {
                if (row == 0) {
                    matrix[row][col] += score;
                } else {
                    matrix[row][col] += score * Float.parseFloat(arrayResult[row]);
                }
            }
        }
    }
}
