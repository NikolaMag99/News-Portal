package rs.raf.demo.resources;

import rs.raf.demo.entities.Kategorija;
import rs.raf.demo.services.CategoryService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/category")
public class CategoryResource {

    @Inject
    private CategoryService categoryService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Kategorija> all()
    {
        return this.categoryService.allCategory();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Kategorija create(@Valid Kategorija kategorija) {
        return this.categoryService.addCategory(kategorija);
    }

    @GET
    @Path("/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Kategorija find(@PathParam("name") String name) {
        return this.categoryService.findCategory(name);
    }

    @DELETE
    @Path("/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public void delete(@PathParam("name") String name) {
        this.categoryService.deleteCategory(name);
    }

    @POST
     @Path("/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Kategorija update(@Valid Kategorija kategorija, @PathParam("name") String name)
    {
        return this.categoryService.updateCategory(kategorija, name);
    }

}
