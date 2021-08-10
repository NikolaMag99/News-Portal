package rs.raf.demo.resources;

import rs.raf.demo.entities.Komentar;
import rs.raf.demo.services.CommentService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/comments")
public class CommentResource {

    @Inject
    private CommentService commentService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Komentar> all()
    {
        return this.commentService.allComments();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Komentar create(@Valid Komentar komentar) {
        return this.commentService.addComment(komentar);
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Komentar find(@PathParam("id") Integer id) {
        return this.commentService.findComment(id);
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public void delete(@PathParam("id") Integer id) {
        this.commentService.deleteComment(id);
    }

}
