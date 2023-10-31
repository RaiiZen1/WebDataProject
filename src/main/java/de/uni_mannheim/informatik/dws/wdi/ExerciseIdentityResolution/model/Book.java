package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model;

import java.util.LinkedList;
import java.util.List;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;

public class Book implements Matchable {

    protected String id;
    protected String provenance;
    private String title;
    private List<String> authors;

    public Book(String identifier, String provenance) {
        id = identifier;
        this.provenance = provenance;
        authors = new LinkedList<>();
    }

    @Override
    public String getIdentifier() {
        return id;
    }

    @Override
    public String getProvenance() {
        return provenance;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }
    
}

