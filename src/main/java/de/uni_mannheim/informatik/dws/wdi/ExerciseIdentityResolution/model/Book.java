package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;

public class Book implements Matchable {

    protected String id;
    protected String provenance;
    private String title;
    private List<String> authors;
    private List<String> genres;
    private String publisher;
    private LocalDateTime publicationDate;
    private double averageRating;
    private int pageNumber;
    private List<String> characters;
    private List<String> awards;
    private String bookFormat;
    private double price;
    private String currency;

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
    
    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public LocalDateTime getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(LocalDateTime date) {
        this.publicationDate = date;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(double rating) {
        this.averageRating = rating;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int number) {
        this.pageNumber = number;
    }

    public List<String> getCharacters() {
        return characters;
    }

    public void setCharacters(List<String> characters) {
        this.characters = characters;
    }

    public List<String> getAwards() {
        return awards;
    }

    public void setAwards(List<String> awards) {
        this.awards = awards;
    }

    public String getBookFormat() {
        return bookFormat;
    }

    public void setBookFormat(String format) {
        this.bookFormat = format;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }
    
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Override
    public String toString() {
		return String.format("[Book %s: %s / %s / %s]", getIdentifier(), getTitle(),
				getAuthors(), getGenres());   
    } 
}

