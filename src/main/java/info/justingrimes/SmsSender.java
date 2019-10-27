package info.justingrimes;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Properties;

public class SmsSender implements Sender {

    final static private Logger log = LogManager.getLogger(SmsSender.class);

    private final String ACCOUNT_SID;
    private final String AUTH_TOKEN;
    private final String toNumber;
    private final String fromNumber;

    SmsSender(Properties config) {
        ACCOUNT_SID = config.getProperty("ACCOUNT_SID");
        AUTH_TOKEN = config.getProperty("AUTH_TOKEN");
        toNumber = config.getProperty("TO_NUMBER");
        fromNumber = config.getProperty("FROM_NUMBER");

        log.debug("SMS Sender created");
    }

    @Override
    public void sendMessage(String msg) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        log.debug("Twilio account initialized");

        Message.creator(
                new PhoneNumber(toNumber), // to
                new PhoneNumber(fromNumber), // from
                msg)
                .create();
        log.debug("Message sent \"" + msg + "\"");
    }
}
