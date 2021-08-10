package rs.raf.demo.services;

import rs.raf.demo.entities.Tag;
import rs.raf.demo.repositories.TagRepository.TagRepository;

import javax.inject.Inject;
import java.util.List;

public class TagService {

    public TagService() {
        System.out.println(this);
    }

    @Inject
    private TagRepository tagRepository;

    public Tag addTag(Tag tag) {
        return this.tagRepository.addTag(tag);
    }

    public List<Tag> allTags() {
        return this.tagRepository.allTags();
    }

    public Tag findTag(String id) {
        return this.tagRepository.findTag(id);
    }

    public Tag findTagById(Integer id) {
        return this.tagRepository.findTagById(id);
    }


    public void deleteTag(Integer id) {
        this.tagRepository.deleteTag(id);
    }
}
