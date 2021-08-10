package rs.raf.demo.services;

import rs.raf.demo.entities.Komentar;
import rs.raf.demo.entities.Tag;
import rs.raf.demo.entities.Vesti;
import rs.raf.demo.repositories.vesti.VestiRepository;

import javax.inject.Inject;
import java.util.List;

public class VestiService {

    public VestiService() {
        System.out.println(this);
    }

    @Inject
    private VestiRepository vestiRepository;

    public Vesti addNews(Vesti vesti) {
        return this.vestiRepository.addNews(vesti);
    }

    public Vesti updateNews(Vesti vesti) {
        return this.vestiRepository.updateNews(vesti);
    }

    public List<Vesti> allNews() {
        return this.vestiRepository.allNews();
    }

    public List<Vesti> allNewsByVisits() {
        return this.vestiRepository.allNewsByVisits();
    }

    public Vesti findNews(Integer id) {
        return this.vestiRepository.findNews(id);
    }

    public void deleteNews(Integer id) {
        this.vestiRepository.deleteNews(id);
    }

    public List<Vesti> allByCategory(String name) {
        return this.vestiRepository.allByCategory(name);
    }

    public List<Vesti> allByTag(Integer id) {
        return this.vestiRepository.allByTag(id);
    }

    public List<Tag> allTagByNews(Integer id) {
        return this.vestiRepository.allTagByNews(id);
    }

    public List<Komentar> allCommentsByNews(Integer id) {
        return this.vestiRepository.allCommentsByNews(id);
    }

}
