package rs.raf.demo.entities;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


public class Tag {


    private Integer id;

    @NotNull(message = "Main word field is required")
    @NotEmpty(message = "Main word field is required")
    private String mainWord;



    public Tag() {
    }

    public Tag(Integer id, String mainWord) {
        this.id = id;
        this.mainWord = mainWord;
    }




    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMainWord() {
        return mainWord;
    }

    public void setMainWord(String mainWord) {
        this.mainWord = mainWord;
    }
}
