package info.justingrimes;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    final private static Logger log = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("ApplicationContext.xml");
        Gatherer gatherer = ctx.getBean("weatherGatherer", WeatherGatherer.class);
        Sender sender = ctx.getBean("smsSender", SmsSender.class);

        sender.sendMessage(gatherer.getMessage());
    }
}

