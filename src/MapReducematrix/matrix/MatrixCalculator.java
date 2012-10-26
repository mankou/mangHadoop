package MapReducematrix.matrix;

import java.util.ArrayList;
import java.util.List;

public class MatrixCalculator {

    private double[][] matrix;


    private void swapRows(int row1, int row2) {
        if(row1 == row2) return;
        for (int col = 0; col < matrix[row1].length; col++) {
            double tmp = matrix[row1][col];
            matrix[row1][col] = matrix[row2][col];
            matrix[row2][col] = tmp;
        }
    }

    private void addWithOtherRowMultiNumber(double number, int replacedRow, int otherRow) {
        for (int col = 0; col < matrix[replacedRow].length; col++) {
            matrix[replacedRow][col] += matrix[otherRow][col] * number;
        }
    }

    private void multiRow(double number, int row) {
        for (int col = 0; col < matrix[row].length; col++) {
            if (col == row) {
                matrix[row][col] = 1.0;
            } else {
                matrix[row][col] *= number;
            }
        }

    }

    private boolean normalizeMatrix() {
        for (int col = 0; col < matrix.length; col++) {
            normalizeColumn(col);
        }
        return true;
    }

    private void normalizeColumn(int col) {
        multiRow(1.0 / matrix[col][col], col);
        for (int row = 0; row < matrix.length; row++) {
            if (row != col) {
                addWithOtherRowMultiNumber(-matrix[row][col], row, col);
            }
        }
    }

    private boolean foundNonZeroRowInColAndSwap(int col) {
        for (int row = 0; row < matrix.length; row++) {
            if (matrix[row][col] != 0) {
                swapRows(row, col);
                return true;
            }
        }
        return false;
    }

    public List<Double> calculate(double[][] matrix) {
        this.matrix = matrix;
        if (normalizeMatrix()) {
            System.out.println("----after calculate matrix---- ");
            showMatrix(matrix);
            System.out.println("----after calculate matrix---- end ");
            return getResult(matrix);
        }
        return null;
    }

    private List<Double> getResult(double[][] matrix) {
        List<Double> result = new ArrayList<Double>();
        for (int row = 0; row < matrix.length; row++) {
            result.add(matrix[row][matrix.length]);
        }
        return result;
    }

    public void showMatrix(double matrix[][]) {
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[row].length; col++) {
                System.out.print(matrix[row][col] + "  ");
            }
            System.out.println();
        }
    }
}
