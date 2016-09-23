package messanger.resources;

import messanger.model.Message;
import messanger.resources.beans.MessageFilterBean;
import messanger.service.MessageService;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Path(value = "/messages")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MessageResource {

    static MessageService messageService = new MessageService();

    @GET
    public List<Message> getMessages(@BeanParam MessageFilterBean messageFilterBean) {

        if (messageFilterBean.getStart() > 0 && messageFilterBean.getSize() > 0) {
            return messageService.getAllMessagesPaginated(messageFilterBean.getStart(), messageFilterBean.getSize());
        }
        if (messageFilterBean.getYear() > 0) {
            return messageService.getAllMessagesForYear(messageFilterBean.getYear());
        }
        return messageService.getAllMessages();
    }

    private String getUriForSelf(UriInfo uriInfo, Message message) {
        String uri = String.valueOf(uriInfo.getBaseUriBuilder()
                .path(MessageResource.class)
                .path(Long.toString(message.getId()))
                .build());
        return uri;
    }

    private String getUriForProfile(UriInfo uriInfo, Message message) {
        URI uri = uriInfo.getBaseUriBuilder().path(ProfileResource.class).path(message.getAuthor()).build();
        return uri.toString();
    }

    private String getUriForComments(UriInfo uriInfo, Message message) {
        String uri = String.valueOf(uriInfo.getBaseUriBuilder()
                .path(MessageResource.class)
                .path(MessageResource.class,"getComments")
                .path(CommentResource.class)
                .resolveTemplate("messageId",message.getId())
                .build());
        return uri;
    }

    @GET
    @Path("/{messageId}")
    public Message getMessage(@PathParam("messageId") long messageId, @Context UriInfo uriInfo) {

        Message message = messageService.getMessage(messageId);

        message.addLink(getUriForSelf(uriInfo, message), "self");
        message.addLink(getUriForProfile(uriInfo, message), "profile");
        message.addLink(getUriForComments(uriInfo, message), "comments");
        return message;
    }

    @POST
    public Response addMessage(Message message, @Context UriInfo uriInfo) throws URISyntaxException {
        Message newMessage = messageService.addMessage(message);
        return Response
                .created(uriInfo.getAbsolutePathBuilder().path("" + newMessage.getId()).build())
                .encoding("UTF-8")
                .entity(newMessage)
                .build();
    }

    @PUT
    @Path("/{messageId}")
    public Message updateMessage(@PathParam("messageId") long messageId, Message message) {
        message.setId(messageId);
        return messageService.updateMessage(message);
    }

    @DELETE
    @Path("/{messageId}")
    public void deleteMessage(@PathParam("messageId") long messageId) {
        messageService.removeMessage(messageId);
    }

    @Path("/{messageId}/comments")
    public CommentResource getComments() {
        return new CommentResource();
    }
}
