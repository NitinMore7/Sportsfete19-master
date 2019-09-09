package spider.app.sportsfete19.FireBaseServices;

/**
 * Created by praba1110 on 1/2/17.
 */
public class Comment {

    String comment, eventId, username;
    Long timestamp;

    public String getEventId() {
        return eventId;
    }

    public String getUsername() {
        return username;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public String getComment() {
        return comment;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    }
