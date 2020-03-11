package by.gmar.emailservices.impl;

import by.gmar.emailservices.IEmailable;
import by.gmar.emailservices.IEmailable;

public class ODEmail implements IEmailable {

    private String subject;

    private String content;

    private String sender;

    private String recipient;

    ODEmail() {
        super();
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

}
