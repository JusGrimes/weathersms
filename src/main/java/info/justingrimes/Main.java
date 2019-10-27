package info.justingrimes;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

public class Main {
    final private static Logger log = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        String propFilename = "config.properties";
        File filePath = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().getFile());
        filePath = new File(filePath.getParent() + File.separator + propFilename);
        Properties config = new Properties();
        try (InputStream in = new FileInputStream(filePath)) {
            config.load(in);
            log.debug("configuration file loaded");

            Gatherer weatherGatherer = new WeatherGatherer("Dallas", config);
            Sender smsSender = new SmsSender(config);


            smsSender.sendMessage(weatherGatherer.getMessage());
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            System.out.println("Could not find file: " + propFilename);
            System.out.println("Please use " + propFilename + ".template as a template for filling out "
                    + propFilename + " and put it ");
        }

    }
}
