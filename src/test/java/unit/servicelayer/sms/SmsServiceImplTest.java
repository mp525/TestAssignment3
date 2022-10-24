package unit.servicelayer.sms;

import dto.SmsMessage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import servicelayer.notifications.SmsService;
import servicelayer.notifications.SmsServiceImpl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("unit")
class SmsServiceImplTest {

    private SmsService smsService;

    @BeforeAll
    public void setup(){
        smsService = mock(SmsServiceImpl.class);
    }

    @Test
    void shouldSendSmsToCustomer() {
        SmsMessage message = new SmsMessage("88888888", "message");
        Mockito.when(smsService.sendSms(message)).thenReturn(true);

        smsService.sendSms(message);
        Mockito.verify(smsService).sendSms(message);
        Mockito.verifyNoMoreInteractions(smsService);
    }
}