package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Collection;

import org.apache.commons.lang3.StringUtils;

import de.uni_mannheim.informatik.dws.winter.model.AbstractRecord;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;

public class Book_DF extends AbstractRecord<Attribute> implements Serializable{

    private String title;
    private List<String> authors;
    private List<String> genres;
    private String publisher;
    private LocalDateTime publicationDate;
    private double averageRating = -1.0;
    private int pageNumber = -1;
    private List<String> characters;
    private List<String> awards;
    private String bookFormat;
    private double price = -1.0;
    private String currency;

    public Book_DF(String identifier, String provenance) {
        super(identifier, provenance);
        authors = new LinkedList<>();
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

    private Map<Attribute, Collection<String>> provenance = new HashMap<>();
    private Collection<String> recordProvenance;

    public void setRecordProvenance(Collection<String> provenance) {
        recordProvenance = provenance;
    }

    public Collection<String> getRecordProvenance() {
		return recordProvenance;
	}

	public void setAttributeProvenance(Attribute attribute,
			Collection<String> provenance) {
		this.provenance.put(attribute, provenance);
	}

	public Collection<String> getAttributeProvenance(String attribute) {
		return provenance.get(attribute);
	}

	public String getMergedAttributeProvenance(Attribute attribute) {
		Collection<String> prov = provenance.get(attribute);

		if (prov != null) {
			return StringUtils.join(prov, "+");
		} else {
			return "";
		}
	}

    public static final Attribute TITLE = new Attribute("Title");
    public static final Attribute AUTHORS = new Attribute("Authors");
    public static final Attribute GENRES = new Attribute("Genres");
    public static final Attribute PUBLISHER = new Attribute("Publisher");
    public static final Attribute PUBLICATION_DATE = new Attribute("PublicationDate");
    public static final Attribute AVERAGE_RATING = new Attribute("AverageRating");
    public static final Attribute PAGE_NUMBER = new Attribute("PageNumber");
    public static final Attribute CHARACTERS = new Attribute("Characters");
    public static final Attribute AWARDS = new Attribute("Awards");
    public static final Attribute BOOK_FORMAT = new Attribute("BookFormat");
    public static final Attribute PRICE = new Attribute("Price");
    public static final Attribute CURRENCY = new Attribute("Currency");

    @Override
    public boolean hasValue(Attribute attribute) {
        if (attribute == TITLE)
            return getTitle() != null && !getTitle().isEmpty();
        if (attribute == AUTHORS)
            return getAuthors() != null && getAuthors().size() > 0;
        if (attribute == GENRES)
            return getGenres() != null && getGenres().size() > 0;
        if (attribute == PUBLISHER)
            return getPublisher() != null && !getPublisher().isEmpty();
        if (attribute == PUBLICATION_DATE)
            return getPublicationDate() != null;
        if (attribute == AVERAGE_RATING)
            return getAverageRating() != -1.0;
        if (attribute == PAGE_NUMBER)
            return getPageNumber() != -1;
        if (attribute == CHARACTERS)
            return getCharacters() != null && getCharacters().size() > 0;
        if (attribute == AWARDS)
            return getAwards() != null && getAwards().size() > 0;
        if (attribute == BOOK_FORMAT)
            return getBookFormat() != null && !getBookFormat().isEmpty();
        if (attribute == PRICE)
            return getPrice() != -1.0;
        if (attribute == CURRENCY)
            return getCurrency() != null && !getCurrency().isEmpty();
        else
            return false;
    }

    @Override
    public String toString() {
		return String.format("[Book %s: %s / %s / %s]", getIdentifier(), getTitle(),
				getAuthors(), getGenres());   
    }

    @Override
    public int hashCode() {
        return getIdentifier().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Book) {
            return this.getIdentifier().equals(((Book) obj).getIdentifier());
        }
        else {
            return false;
        }
    }
}
