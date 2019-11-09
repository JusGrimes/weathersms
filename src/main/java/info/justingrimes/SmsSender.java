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

    SmsSender(String ACCOUNT_SID, String AUTH_TOKEN, String toNumber, String fromNumber) {

        this.ACCOUNT_SID = ACCOUNT_SID;
        this.AUTH_TOKEN = AUTH_TOKEN;
        this.toNumber = toNumber;
        this.fromNumber = fromNumber;
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
