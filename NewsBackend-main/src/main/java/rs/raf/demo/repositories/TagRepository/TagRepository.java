package rs.raf.demo.repositories.TagRepository;

import rs.raf.demo.entities.Tag;

import java.util.List;

public interface TagRepository {
    public Tag addTag(Tag tag);
    public List<Tag> allTags();
    public Tag findTag(String id);
    public Tag findTagById(Integer id);
    public void deleteTag(Integer id);
}
