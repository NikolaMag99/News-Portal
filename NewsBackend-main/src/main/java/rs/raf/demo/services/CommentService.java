package rs.raf.demo.services;

import rs.raf.demo.entities.Komentar;
import rs.raf.demo.entities.Tag;
import rs.raf.demo.repositories.TagRepository.TagRepository;
import rs.raf.demo.repositories.komentar.CommentRepository;

import javax.inject.Inject;
import java.util.List;

public class CommentService {

    public CommentService() {
        System.out.println(this);
    }

    @Inject
    private CommentRepository tagRepository;

    public Komentar addComment(Komentar komentar) {
        return this.tagRepository.addComment(komentar);
    }

    public List<Komentar> allComments() {
        return this.tagRepository.allComments();
    }

    public Komentar findComment(Integer id) {
        return this.tagRepository.findComment(id);
    }

    public void deleteComment(Integer id) {
        this.tagRepository.deleteComment(id);
    }
}
