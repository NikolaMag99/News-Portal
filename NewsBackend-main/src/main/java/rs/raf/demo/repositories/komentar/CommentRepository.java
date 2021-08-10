package rs.raf.demo.repositories.komentar;

import rs.raf.demo.entities.Komentar;


import java.util.List;

public interface CommentRepository {
    public Komentar addComment(Komentar komentar);
    public List<Komentar> allComments();
    public Komentar findComment(Integer id);
    public void deleteComment(Integer id);
}
