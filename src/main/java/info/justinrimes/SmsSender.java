package info.justinrimes;


import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class SmsSender implements Sender {
    private final String ACCOUNT_SID =
            System.getenv("ACCOUNT_SID");
    private final String AUTH_TOKEN =
            System.getenv("AUTH_TOKEN");
    private final String toNumber = System.getenv("TO_NUMBER");
    private final String fromNumber = System.getenv("PHONE_NUMBER");

    public void sendMessage(String msg) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message.creator(
                new PhoneNumber(toNumber), // to
                new PhoneNumber(fromNumber), // from
                msg)
                .create();
    }
}
