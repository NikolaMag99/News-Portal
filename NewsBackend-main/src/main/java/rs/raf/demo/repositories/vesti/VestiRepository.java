package rs.raf.demo.repositories.vesti;

import rs.raf.demo.entities.Komentar;
import rs.raf.demo.entities.Tag;
import rs.raf.demo.entities.Vesti;

import java.util.List;

public interface VestiRepository {
    public Vesti addNews(Vesti vesti);
    public Vesti updateNews(Vesti vesti);
    public List<Vesti> allNews();
    public List<Vesti> allNewsByVisits();
    public Vesti findNews(Integer id);
    public void deleteNews(Integer id);
    public List<Vesti> allByCategory(String name);
    public List<Vesti> allByTag(Integer id);
    public List<Tag> allTagByNews(Integer id);
    public List<Komentar> allCommentsByNews(Integer id);
}
