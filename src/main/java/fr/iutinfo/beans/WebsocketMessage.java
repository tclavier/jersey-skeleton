package fr.iutinfo.beans;

public class WebsocketMessage extends WebsocketObject {

    private String content;
    private String from;

    /**
     * Constructor of a Websocket Message (Type 2)
     */
    public WebsocketMessage() { setType(2); }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getFrom() { return from; }
    public void setFrom(String from) { this.from = from; }

}
