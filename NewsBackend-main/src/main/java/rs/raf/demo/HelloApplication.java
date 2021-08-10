package rs.raf.demo;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import rs.raf.demo.repositories.TagRepository.MySqlTagRepository;
import rs.raf.demo.repositories.TagRepository.TagRepository;
import rs.raf.demo.repositories.category.CategoryRepository;
import rs.raf.demo.repositories.category.MySqlCategoryRepository;
import rs.raf.demo.repositories.komentar.CommentRepository;
import rs.raf.demo.repositories.komentar.MySqlCommentRepository;
import rs.raf.demo.repositories.vesti.MySqlVestiRepository;
import rs.raf.demo.repositories.vesti.VestiRepository;
import rs.raf.demo.repositories.user.InMemoryUserRepository;
import rs.raf.demo.repositories.user.UserRepository;
import rs.raf.demo.services.*;

import javax.inject.Singleton;
import javax.ws.rs.ApplicationPath;

@ApplicationPath("/api")
public class HelloApplication extends ResourceConfig {

    public HelloApplication() {
        // Ukljucujemo validaciju
        property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);


        AbstractBinder binder = new AbstractBinder() {
            @Override
            protected void configure() {
                this.bind(MySqlVestiRepository.class).to(VestiRepository.class).in(Singleton.class);
                this.bind(InMemoryUserRepository.class).to(UserRepository.class).in(Singleton.class);
                this.bind(MySqlCategoryRepository.class).to(CategoryRepository.class).in(Singleton.class);
                this.bind(MySqlTagRepository.class).to(TagRepository.class).in(Singleton.class);
                this.bind(MySqlCommentRepository.class).to(CommentRepository.class).in(Singleton.class);

                this.bindAsContract(VestiService.class);
                this.bindAsContract(UserService.class);
                this.bindAsContract(CategoryService.class);
                this.bindAsContract(TagService.class);
                this.bindAsContract(CommentService.class);
            }
        };
        register(binder);

        // Ucitavamo resurse
        packages("rs.raf.demo");
    }
}
