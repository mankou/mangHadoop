package zhangying;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SampleDataParser {


    private String sourceFile;
    private String destinationFile;
    private int SAMPLE_AMOUNT;
    private Random random;

    public SampleDataParser() {

    }
    //
    public boolean isWantedLine(String oneLine) {
        String[] array = oneLine.split("\\s+");
        if(array == null || array.length == 0) return false;
        String scoreString = array[0];
        try {
            float score = Float.parseFloat(scoreString);
            return isScoreInRange(score, SampleProperty.readValue("MIN_GRID_SCORE"), SampleProperty.readValue("MAX_GRID_SCORE"));
        } catch (Exception e) {
            return false;
        }
    }
    //
    public boolean isScoreInRange(float score, String minScoreString, String maxScoreString) {
        if(minScoreString.equals("") && maxScoreString.equals("")) return true;
        try {
            float minScore;
            float maxScore;

            if (!minScoreString.equals("") && maxScoreString.equals("")) {
                minScore = Float.parseFloat(minScoreString);
                return score >= minScore;
            }

            if (minScoreString.equals("") && !maxScoreString.equals("")) {
                maxScore = Float.parseFloat(maxScoreString);
                return score <= maxScore;
            }
            minScore = Float.parseFloat(minScoreString);
            maxScore = Float.parseFloat(maxScoreString);
            return minScore <= score && score <= maxScore;

        } catch (Exception e) {
//          Configuration error
            return true;
        }
    }
   //
   
}
