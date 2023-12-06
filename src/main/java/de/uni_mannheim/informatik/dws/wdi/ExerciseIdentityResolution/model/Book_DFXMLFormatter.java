package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import de.uni_mannheim.informatik.dws.winter.model.io.XMLFormatter;

public class Book_DFXMLFormatter extends XMLFormatter<Book_DF> {

    @Override
    public Element createRootElement(Document doc) {
        if (doc == null) {
            return null;
        }
        return doc.createElement("books");
    }

    @Override
    public Element createElementFromRecord(Book_DF record, Document doc) {
        Element book = doc.createElement("book");
    
        appendIfNotNull(book, "id", record.getIdentifier(), doc);
    
        appendIfNotNullWithProvenance(book, "title", record.getTitle(), record.getMergedAttributeProvenance(Book_DF.TITLE), doc);
    
        appendIfNotNullAuthors(book, record, doc);
        // genres
        appendIfNotNullGenres(book, record, doc);
        // publisher
        appendIfNotNullWithProvenance(book, "publisher", record.getPublisher(), record.getMergedAttributeProvenance(Book_DF.PUBLISHER), doc);
        // publication_date
        appendIfNotNull(book, createPublication_DateElement(record, doc));
        // average_rating
        appendIfNotNullWithProvenance(book, "average_rating", Double.toString(record.getAverageRating()), record.getMergedAttributeProvenance(Book_DF.AVERAGE_RATING), doc);
        // page_number
        appendIfNotNull(book, "page_number", Integer.toString(record.getPageNumber()), doc);
        // characters
        appendIfNotNullCharacters(book, record, doc);
        // awards
        appendIfNotNullAwards(book, record, doc);
        // book_format
        appendIfNotNull(book, "book_format", record.getBookFormat(), doc);
        // price
        appendIfNotNull(book, "price", Double.toString(record.getPrice()), doc);
        // currency
        appendIfNotNull(book, "currency", record.getCurrency(), doc);
    
        return book;
    }
    
    protected void appendIfNotNull(Element parent, String name, String value, Document doc) {
        if (value != null && !value.isEmpty()) {
            parent.appendChild(createTextElement(name, value, doc));
        }
    }
    
    protected void appendIfNotNull(Element parent, Element child) {
        if (child != null) {
            parent.appendChild(child);
        }
    }
    
    protected void appendIfNotNullWithProvenance(Element parent, String name, String value, String provenance, Document doc) {
        if (value != null && !value.isEmpty()) {
            Element elem = createTextElement(name, value, doc);
            elem.setAttribute("provenance", provenance);
            parent.appendChild(elem);
        }
    }
    
    protected void appendIfNotNullAuthors(Element parent, Book_DF record, Document doc) {
        String provenance = record.getMergedAttributeProvenance(Book_DF.AUTHORS);
        if (provenance != null && !provenance.isEmpty()) {
            Element authorRoot = doc.createElement("authors");
            authorRoot.setAttribute("provenance", provenance);
    
            for (String a : record.getAuthors()) {
                appendIfNotNull(authorRoot, "author", a, doc);
            }
    
            parent.appendChild(authorRoot);
        }
    }
    
    protected void appendIfNotNullGenres(Element parent, Book_DF record, Document doc) {
        String provenance = record.getMergedAttributeProvenance(Book_DF.GENRES);
        if (provenance != null && !provenance.isEmpty() && record.getGenres() != null) {
            Element genresRoot = doc.createElement("genres");
            genresRoot.setAttribute("provenance", provenance);
    
            for (String a : record.getGenres()) {
                appendIfNotNull(genresRoot, "genre", a, doc);
            }
    
            parent.appendChild(genresRoot);
        }
    }
    
    protected void appendIfNotNullCharacters(Element parent, Book_DF record, Document doc) {
        String provenance = record.getMergedAttributeProvenance(Book_DF.CHARACTERS);
        if (provenance != null && !provenance.isEmpty()) {
            Element charactersRoot = doc.createElement("characters");
            charactersRoot.setAttribute("provenance", provenance);
    
            for (String a : record.getCharacters()) {
                appendIfNotNull(charactersRoot, "character", a, doc);
            }
    
            parent.appendChild(charactersRoot);
        }
    }
    
    protected void appendIfNotNullAwards(Element parent, Book_DF record, Document doc) {
        String provenance = record.getMergedAttributeProvenance(Book_DF.AWARDS);
        if (provenance != null && !provenance.isEmpty()) {
            Element awardsRoot = doc.createElement("awards");
            awardsRoot.setAttribute("provenance", provenance);
    
            for (String a : record.getAwards()) {
                appendIfNotNull(awardsRoot, "award", a, doc);
            }
    
            parent.appendChild(awardsRoot);
        }
    }
    
    protected Element createPublication_DateElement(Book_DF record, Document doc) {
        Element publication_dateRoot = doc.createElement("publication_date");
    
        String provenance = record.getMergedAttributeProvenance(Book_DF.PUBLICATION_DATE);
        if (provenance != null && !provenance.isEmpty()) {
            publication_dateRoot.setAttribute("provenance", provenance);
    
            publication_dateRoot
                    .appendChild(createTextElement("year", Integer.toString(record.getPublicationDate().getYear()), doc));
            publication_dateRoot
                    .appendChild(createTextElement("month", record.getPublicationDate().getMonth().toString(), doc));
            publication_dateRoot
                    .appendChild(createTextElement("day", Integer.toString(record.getPublicationDate().getDayOfMonth()), doc));
        }
    
        return publication_dateRoot;
    }
    
    protected Element createTextElement(String name, String text, Document doc) {
        Element element = doc.createElement(name);
        element.appendChild(doc.createTextNode(text));
        return element;
    }
    

    /*
     * @Override
     * public Element createElementFromRecord(Book_DF record, Document doc) {
     * Element book = doc.createElement("book");
     * 
     * book.appendChild(createTextElement("id", record.getIdentifier(), doc));
     * 
     * book.appendChild(createTextElementWithProvenance("title", record.getTitle(),
     * record.getMergedAttributeProvenance(Book_DF.TITLE), doc));
     * 
     * book.appendChild(createAuthorsElement(record, doc));
     * // genres
     * book.appendChild(createGenresElement(record, doc));
     * // publsiher
     * book.appendChild(createTextElementWithProvenance("publisher",
     * record.getPublisher(),
     * record.getMergedAttributeProvenance(Book_DF.PUBLISHER), doc));
     * // publication_date
     * book.appendChild(createPublication_DateElement(record, doc));
     * // average_rating
     * book.appendChild(createTextElementWithProvenance("average_rating",
     * Double.toString(record.getAverageRating()),
     * record.getMergedAttributeProvenance(Book_DF.AVERAGE_RATING), doc));
     * // page_number
     * book.appendChild(createTextElement("page_number",
     * Integer.toString(record.getPageNumber()), doc));
     * // characters
     * book.appendChild(createCharactersElement(record, doc));
     * // awards
     * book.appendChild(createAwardsElement(record, doc));
     * // bool_format
     * book.appendChild(createTextElement("book_format", record.getBookFormat(),
     * doc));
     * // price
     * book.appendChild(createTextElement("price",
     * Double.toString(record.getPrice()), doc));
     * // currency
     * book.appendChild(createTextElement("currency", record.getCurrency(), doc));
     * 
     * return book;
     * }
     * 
     * protected Element createTextElementWithProvenance(String name,
     * String value, String provenance, Document doc) {
     * Element elem = createTextElement(name, value, doc);
     * elem.setAttribute("provenance", provenance);
     * return elem;
     * }
     * 
     * protected Element createAuthorsElement(Book_DF record, Document doc) {
     * Element authorRoot = doc.createElement("authors");
     * 
     * authorRoot.setAttribute("provenance",
     * record.getMergedAttributeProvenance(Book_DF.AUTHORS));
     * 
     * for (String a : record.getAuthors()) {
     * authorRoot.appendChild(createTextElement("author", a, doc));
     * }
     * 
     * return authorRoot;
     * }
     * 
     * protected Element createGenresElement(Book_DF record, Document doc) {
     * Element genresRoot = doc.createElement("genres");
     * 
     * genresRoot.setAttribute("provenance",
     * record.getMergedAttributeProvenance(Book_DF.GENRES));
     * if (record.getGenres() != null) {
     * for (String a : record.getGenres()) {
     * genresRoot.appendChild(createTextElement("genre", a, doc));
     * }
     * }
     * 
     * return genresRoot;
     * }
     * 
     * protected Element createPublication_DateElement(Book_DF record, Document doc)
     * {
     * Element publication_dateRoot = doc.createElement("publication_date");
     * 
     * publication_dateRoot.setAttribute("provenance",
     * record.getMergedAttributeProvenance(Book_DF.PUBLICATION_DATE));
     * 
     * publication_dateRoot
     * .appendChild(createTextElement("year",
     * Integer.toString(record.getPublicationDate().getYear()), doc));
     * publication_dateRoot
     * .appendChild(createTextElement("month",
     * record.getPublicationDate().getMonth().toString(), doc));
     * publication_dateRoot.appendChild(
     * createTextElement("day",
     * Integer.toString(record.getPublicationDate().getDayOfMonth()), doc));
     * 
     * return publication_dateRoot;
     * }
     * 
     * protected Element createCharactersElement(Book_DF record, Document doc) {
     * Element charactersRoot = doc.createElement("characters");
     * 
     * charactersRoot.setAttribute("provenance",
     * record.getMergedAttributeProvenance(Book_DF.CHARACTERS));
     * 
     * for (String a : record.getCharacters()) {
     * charactersRoot.appendChild(createTextElement("character", a, doc));
     * }
     * 
     * return charactersRoot;
     * }
     * 
     * protected Element createAwardsElement(Book_DF record, Document doc) {
     * Element awardsRoot = doc.createElement("awards");
     * 
     * awardsRoot.setAttribute("provenance",
     * record.getMergedAttributeProvenance(Book_DF.AWARDS));
     * 
     * for (String a : record.getAwards()) {
     * awardsRoot.appendChild(createTextElement("award", a, doc));
     * }
     * 
     * return awardsRoot;
     * }
     */

    /*
     * public Element createElementFromRecord(Book_DF record, Document doc) {
     * 
     * Element book = doc.createElement("book");
     * 
     * appendIfNotNull(book, createTextElement("id", record.getIdentifier(), doc));
     * 
     * Element titleElement = createTextElementWithProvenance("title",
     * record.getTitle(),
     * record.getMergedAttributeProvenance(Book_DF.TITLE), doc);
     * appendIfNotNull(book, titleElement);
     * 
     * appendIfNotNull(book, createAuthorsElement(record, doc));
     * // genres
     * appendIfNotNull(book, createGenresElement(record, doc));
     * // publisher
     * appendIfNotNull(book, createTextElementWithProvenance("publisher",
     * record.getPublisher(),
     * record.getMergedAttributeProvenance(Book_DF.PUBLISHER), doc));
     * // publication_date
     * appendIfNotNull(book, createPublication_DateElement(record, doc));
     * // average_rating
     * appendIfNotNull(book, createTextElementWithProvenance("average_rating",
     * Double.toString(record.getAverageRating()),
     * record.getMergedAttributeProvenance(Book_DF.AVERAGE_RATING), doc));
     * // page_number
     * appendIfNotNull(book, createTextElement("page_number",
     * Integer.toString(record.getPageNumber()), doc));
     * // characters
     * appendIfNotNull(book, createCharactersElement(record, doc));
     * // awards
     * appendIfNotNull(book, createAwardsElement(record, doc));
     * // book_format
     * appendIfNotNull(book, createTextElement("book_format",
     * record.getBookFormat(), doc));
     * // price
     * appendIfNotNull(book, createTextElement("price",
     * Double.toString(record.getPrice()), doc));
     * // currency
     * appendIfNotNull(book, createTextElement("currency", record.getCurrency(),
     * doc));
     * 
     * if (book == null) {
     * return null;
     * }
     * return book;
     * }
     * 
     * private void appendIfNotNull(Element parent, Element child) {
     * if (child != null) {
     * parent.appendChild(child);
     * }
     * }
     * 
     * protected Element createTextElementWithProvenance(String name, String value,
     * String provenance, Document doc) {
     * if (value == null || value.isEmpty()) {
     * return null; // Skip empty elements
     * }
     * 
     * Element elem = createTextElement(name, value, doc);
     * elem.setAttribute("provenance", provenance);
     * return elem;
     * }
     * 
     * protected Element createAuthorsElement(Book_DF record, Document doc) {
     * Element authorRoot = doc.createElement("authors");
     * 
     * String provenance = record.getMergedAttributeProvenance(Book_DF.AUTHORS);
     * if (provenance != null && !provenance.isEmpty()) {
     * authorRoot.setAttribute("provenance", provenance);
     * 
     * for (String a : record.getAuthors()) {
     * if (a != null && !a.isEmpty()) {
     * authorRoot.appendChild(createTextElement("author", a, doc));
     * }
     * }
     * }
     * 
     * return authorRoot;
     * }
     * 
     * protected Element createGenresElement(Book_DF record, Document doc) {
     * Element genresRoot = doc.createElement("genres");
     * 
     * String provenance = record.getMergedAttributeProvenance(Book_DF.GENRES);
     * if (provenance != null && !provenance.isEmpty() && record.getGenres() !=
     * null) {
     * genresRoot.setAttribute("provenance", provenance);
     * 
     * for (String a : record.getGenres()) {
     * if (a != null && !a.isEmpty()) {
     * genresRoot.appendChild(createTextElement("genre", a, doc));
     * }
     * }
     * }
     * 
     * return genresRoot;
     * }
     * 
     * protected Element createPublication_DateElement(Book_DF record, Document doc)
     * {
     * Element publication_dateRoot = doc.createElement("publication_date");
     * 
     * String provenance =
     * record.getMergedAttributeProvenance(Book_DF.PUBLICATION_DATE);
     * if (provenance != null && !provenance.isEmpty()) {
     * publication_dateRoot.setAttribute("provenance", provenance);
     * 
     * publication_dateRoot
     * .appendChild(createTextElement("year",
     * Integer.toString(record.getPublicationDate().getYear()), doc));
     * publication_dateRoot
     * .appendChild(createTextElement("month",
     * record.getPublicationDate().getMonth().toString(), doc));
     * publication_dateRoot
     * .appendChild(createTextElement("day",
     * Integer.toString(record.getPublicationDate().getDayOfMonth()), doc));
     * }
     * 
     * return publication_dateRoot;
     * }
     * 
     * protected Element createCharactersElement(Book_DF record, Document doc) {
     * Element charactersRoot = doc.createElement("characters");
     * 
     * String provenance = record.getMergedAttributeProvenance(Book_DF.CHARACTERS);
     * if (provenance != null && !provenance.isEmpty()) {
     * charactersRoot.setAttribute("provenance", provenance);
     * 
     * for (String a : record.getCharacters()) {
     * if (a != null && !a.isEmpty()) {
     * charactersRoot.appendChild(createTextElement("character", a, doc));
     * }
     * }
     * }
     * 
     * return charactersRoot;
     * }
     * 
     * protected Element createAwardsElement(Book_DF record, Document doc) {
     * Element awardsRoot = doc.createElement("awards");
     * 
     * String provenance = record.getMergedAttributeProvenance(Book_DF.AWARDS);
     * if (provenance != null && !provenance.isEmpty()) {
     * awardsRoot.setAttribute("provenance", provenance);
     * 
     * for (String a : record.getAwards()) {
     * if (a != null && !a.isEmpty()) {
     * awardsRoot.appendChild(createTextElement("award", a, doc));
     * }
     * }
     * }
     * 
     * return awardsRoot;
     * }
     * 
     * protected Element createTextElement(String name, String text, Document doc) {
     * Element element = doc.createElement(name);
     * element.appendChild(doc.createTextNode(text));
     * return element;
     * }
     */

}
