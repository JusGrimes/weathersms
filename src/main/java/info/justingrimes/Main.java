package info.justingrimes;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        String propFilename = "config.properties";
        File filePath = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().getFile());
        filePath = new File(filePath.getParent() + File.separator + propFilename);
        Properties config = new Properties();
        try (InputStream in = new FileInputStream(filePath)) {
            config.load(in);
        } catch (IOException e) {
            System.out.println("Could not find file: " + propFilename);
        }
        Gatherer weatherGatherer = new WeatherGatherer("Dallas", config);
        Sender smsSender= new SmsSender(config);


        smsSender.sendMessage(weatherGatherer.getMessage());

    }
}
