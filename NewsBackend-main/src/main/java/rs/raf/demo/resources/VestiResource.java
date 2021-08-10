package rs.raf.demo.resources;

import rs.raf.demo.entities.Komentar;
import rs.raf.demo.entities.Tag;
import rs.raf.demo.entities.Vesti;
import rs.raf.demo.services.TagService;
import rs.raf.demo.services.VestiService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;

@Path("/news")
public class VestiResource {

    @Inject
    private VestiService vestiService;
    @Inject
    private TagService tagServiceService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Vesti> all()
    {
        return this.vestiService.allNews();
    }

    @GET
    @Path("/visits")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Vesti> allNewsByVisits()
    {
        return this.vestiService.allNewsByVisits();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Vesti create(@Valid Vesti vesti) {
        return this.vestiService.addNews(vesti);
    }


    @POST
    @Path("/update")
    @Produces(MediaType.APPLICATION_JSON)
    public Vesti updateNews(@Valid Vesti vesti) {
        return this.vestiService.updateNews(vesti);
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Vesti find(@PathParam("id") Integer id) {
        return this.vestiService.findNews(id);
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public void delete(@PathParam("id") Integer id) {
        this.vestiService.deleteNews(id);
    }

    @GET
    @Path("/kategorija/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Vesti> allByCategory(@PathParam("name") String name) {
        return this.vestiService.allByCategory(name);
    }

    @GET
    @Path("/tag/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Vesti> allByTag(@PathParam("id") Integer id) {
        return this.vestiService.allByTag(id);
    }

    @GET
    @Path("/newsTag/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Tag> allTagByNews(@PathParam("id") Integer id) {
        return this.vestiService.allTagByNews(id);
    }

    @GET
    @Path("/comments/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Komentar> allCommentsByNews(@PathParam("id") Integer id) {
        return this.vestiService.allCommentsByNews(id);
    }
}
