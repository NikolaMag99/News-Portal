package rs.raf.demo.entities;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.xml.crypto.Data;
import java.util.Date;


public class Vesti {

    private Integer id;

    @NotNull(message = "Title field is required")
    @NotEmpty(message = "Title field is required")
    private String title;

    @NotNull(message = "Content field is required")
    @NotEmpty(message = "Content field is required")
    private String content;


    // TODO
    private Date createdAt;

    // TODO
    private Integer visits;

    // TODO
    private User author;

    // TODO
    private Kategorija kategorija;


    public Vesti() {
    }

    public Vesti(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public Vesti(int id, String title, String content, Date createdAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
    }

    public Vesti(Integer id, String title, String content, Date createdAt, Integer visits) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.visits = visits;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public void setVisits(Integer visits) {
        this.visits = visits;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Kategorija getKategorija() {
        return kategorija;
    }

    public void setKategorija(Kategorija kategorija) {
        this.kategorija = kategorija;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getVisits() {
        return visits;
    }

    public void setVisits(int visits) {
        this.visits = visits;
    }


    public Date getCreatedAt() {
        return createdAt;
    }
}


