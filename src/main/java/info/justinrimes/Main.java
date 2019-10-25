package info.justinrimes;

import com.twilio.twiml.voice.Gather;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        String propFilename = "src/resources/config.properties";
        Properties config = new Properties();
        try (InputStream in = new FileInputStream(propFilename)) {
            config.load(in);
        } catch (IOException e) {
            System.out.println("Could not find file: " + propFilename);
        }

        Gatherer weatherGatherer = new WeatherGatherer("Dallas", config);
        Sender smsSender= new SmsSender(config);


        smsSender.sendMessage(weatherGatherer.getMessage());

    }
}
