package ee.degeetia.xrtracker.filter.util;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * Utility class for SOAP parsing.
 */
public final class SoapUtil {

  private SoapUtil() {
    throw new UnsupportedOperationException();
  }

  /**
   * Creates a {@link SOAPMessage} object from the specified byte array.
   *
   * @param content     the raw message to parse the <code>SOAPMessage</code> from
   * @param contentType the HTTP Content-Type value that corresponds to the message. Required in order to determine if
   *                    the message should be parsed as a standard XML message or a multipart message with attachments.
   * @return the SOAPMessage object parsed from the given byte array
   * @throws SOAPException if the message is not a valid SOAP message
   */
  public static SOAPMessage parseMessage(byte[] content, String contentType) throws SOAPException {
    ByteArrayInputStream inputStream = new ByteArrayInputStream(content);
    try {
      MessageFactory messageFactory = MessageFactory.newInstance();
      MimeHeaders headers = new MimeHeaders();
      headers.addHeader(HttpUtil.HEADER_CONTENT_TYPE, contentType);
      return messageFactory.createMessage(headers, inputStream);
    } catch (IOException e) {
      // Should never happen - ByteArrayInputStream in this case is always readable and if its contents are invalid,
      // SOAPException will be thrown instead
      throw ExceptionUtil.toUnchecked(e);
    } finally {
      IOUtil.close(inputStream);
    }
  }

}
