package zhangying;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class SampleProperty {

    private static Properties properties = new Properties();

    public static void loadProperty(String filePath) throws IOException {
        BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(filePath));
        properties.load(inputStream);
    }
    
    public static String readValue(String name) {
        return properties.getProperty(name, "");
    }
}
