package org.activiti.spring.test.email;

import java.io.IOException;
import javax.mail.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**

 */
public class MockEmailTransport extends Transport {

  private static Logger logger = LoggerFactory.getLogger(MockEmailTransport.class);

  public MockEmailTransport(Session smtpSession, URLName urlName) {
    super(smtpSession, urlName);
  }

  @Override
  public void sendMessage(Message message, Address[] addresses) throws MessagingException {
    try {
      logger.info(message.getContent().toString());
    } catch (IOException ex) {
      logger.error("Error occured while sending email" + ex);
    }
  }

  @Override
  public void connect() throws MessagingException {
  }

  @Override
  public void connect(String host, int port, String username, String password) throws MessagingException {
  }

  @Override
  public void connect(String host, String username, String password) throws MessagingException {
  }

  @Override
  public void close() {
  }
}
