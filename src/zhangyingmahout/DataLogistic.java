import java.io.*;

public class DataLogistic {

    private static final int DIMENTION = 11;

    public void parseDataToLogisticData(String inputFile, String outFile) throws IOException {

        BufferedWriter out = new BufferedWriter(new FileWriter(createNewFile(outFile), true));
        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(new File(inputFile))));

        String oneLine;

        while ((oneLine = in.readLine()) != null) {
            String[] arrayResult = oneLine.split("\\s+");
	          if (!isValid(arrayResult)) {
	              continue;
	          }
            replaceWithLogicData(arrayResult);
            writeArrayToFile(arrayResult, out);

        }
    }

    private void writeArrayToFile(String[] arrayResult, BufferedWriter out) throws IOException {
        String line = "";
        for (String field : arrayResult) {
            line += field + ",";
        }

        out.write(line.substring(0, line.length() - 1));
        out.newLine();
    }

    private void replaceWithLogicData(String[] arrayResult) {
        Float gridScore = Math.abs(Float.parseFloat(arrayResult[0]));
        int rate = getRate(gridScore);
        arrayResult[0] = rate + "";
    }

    private int getRate(Float gridScore) {
        if(gridScore < 45) return 1;
//        if( 20 <= gridScore && gridScore < 25) return 2;
//        if( 25 <= gridScore && gridScore < 30) return 3;
//        if( 30 <= gridScore && gridScore < 35) return 4;
//        if( 35 <= gridScore && gridScore < 40) return 5;
//        if( 40 <= gridScore && gridScore < 45) return 6;
//        if( 45 <= gridScore && gridScore < 50) return 7;
        if( gridScore >= 45) return 2;
        return 1;
    }

    private File createNewFile(String fileName) throws IOException {
        File file = new File(fileName);
        if (file.exists()) {
            file.delete();
        }
        file.createNewFile();
        return file;
    }

    private boolean isValid(String[] arrayResult) {
        if (arrayResult.length != DIMENTION) return false;
        try {
            Float.parseFloat(arrayResult[0]);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
