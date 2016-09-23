package messanger.service;

import messanger.database.DatabaseClass;
import messanger.exception.DataNotFoundException;
import messanger.model.Comment;
import messanger.model.Message;

import java.util.*;
import java.util.stream.Collectors;

public class MessageService {

    private Map<Long, Message> messages = DatabaseClass.getMessages();

    public MessageService() {
        Message msg1 = new Message(1, "karol", "Hello!");
        Message msg2 = new Message(2, "grzes", "Hello JAS!");
        Map<Long, Comment> msg1Comments = new HashMap<>();
        msg1Comments.put(1L, new Comment(1L, "welcome abroad!", new Date(), "grzes"));
        msg1Comments.put(2L, new Comment(2L, "thanks grzes!", new Date(), "karol"));
        msg1.setComments(msg1Comments);
        Map<Long, Comment> msg2Comments = new HashMap<>();
        msg2Comments.put(1L, new Comment(1L, "OMG, JAS?!", new Date(), "karol"));
        msg2Comments.put(1L, new Comment(2L, "shit happens!", new Date(), "grzes"));
        msg2.setComments(msg2Comments);
        messages.put(msg1.getId(), msg1);
        messages.put(msg2.getId(), msg2);
    }

    public List<Message> getAllMessages() {
        return new ArrayList<>(messages.values());
    }

    public List<Message> getAllMessagesForYear(int year) {
        Calendar calendar = Calendar.getInstance();

        return messages.values().stream().filter(message -> {
            calendar.setTime(message.getCreated());
            return calendar.get(Calendar.YEAR) == year;
        }).collect(Collectors.toList());
    }

    public List<Message> getAllMessagesPaginated(int start, int size) {
        List<Message> messages = new ArrayList<>(this.messages.values());
        start--;
        if (start > messages.size()) {
            return Collections.emptyList();
        }
        if (start + size >= messages.size()) {
            return messages.subList(start, messages.size());
        }
        return messages.subList(start, start + size);
    }

    public Message getMessage(long id)
    {
        Message message= messages.get(id);
        if (message==null){
            throw new DataNotFoundException("Message with id "+id+" not found");
        }
        return message;
    }

    public Message addMessage(Message message) {
        message.setId(messages.size() + 1);
        messages.put(message.getId(), message);
        return message;
    }

    public Message updateMessage(Message message) {
        if (message.getId() <= 0) {
            return null;
        }
        messages.put(message.getId(), message);
        return message;
    }

    public Message removeMessage(long id) {
        return this.messages.remove(id);
    }


    public Map<Long, Message> getMessages() {
        return this.messages;
    }
}
