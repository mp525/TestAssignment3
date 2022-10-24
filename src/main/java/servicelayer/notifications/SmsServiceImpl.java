package servicelayer.notifications;

import dto.SmsMessage;

public class SmsServiceImpl implements SmsService{
    @Override
    public boolean sendSms(SmsMessage message) {
        return true;
    }
}
