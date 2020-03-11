package by.gmar.emailservices;

public interface IEmailable {

    String getContent();

    String getSubject();

    String getRecipient();

    String getSender();
}
