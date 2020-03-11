package by.gmar.emailservices;

/**
 * Implementations if this interface need to provide protocol specific transport for email messages.
 */
public interface IEmailTransport {

    void sendMimeEmail(String sender, String recipient, String subjectStr, String contentAsStr);
}
