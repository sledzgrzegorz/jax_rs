package messanger.resources;

import messanger.model.Comment;
import messanger.service.CommentService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CommentResource {

    private static CommentService commentService=new CommentService();

    @GET
    public List<Comment> getAllComments(@PathParam("messageId") long messageId){
        return commentService.getAllComments(messageId);
    }

    @POST
    public Comment addMessage(@PathParam("messageId") long messageId,Comment comment){
        return commentService.addComment(messageId,comment);
    }

    @GET
    @Path("/{commentId}")
    public Comment getComment(@PathParam("messageId") long messageId,@PathParam("commentId") long commentId){
        return commentService.getComment(messageId,commentId);
    }

    @PUT
    @Path("/{commentId}")
    public Comment updateMessage(@PathParam("messageId") long messageId,@PathParam("commentId") long commentId, Comment comment){
        comment.setId(commentId);
        return commentService.updateComment(messageId,comment);
    }

    @DELETE
    @Path("/{commentId}")
    public Comment getAllComments(@PathParam("messageId")long messageId, @PathParam("commentId") long commentId){
        return commentService.removeComment(messageId,commentId);
    }


}
