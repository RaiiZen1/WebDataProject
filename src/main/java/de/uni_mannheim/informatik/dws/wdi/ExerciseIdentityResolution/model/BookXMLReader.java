package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model;

import java.time.LocalDateTime;

import org.w3c.dom.Node;

import de.uni_mannheim.informatik.dws.winter.model.DataSet;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.model.io.XMLMatchableReader;

public class BookXMLReader extends XMLMatchableReader<Book, Attribute>{

    @Override
    protected void initialiseDataset(DataSet<Book, Attribute> dataset) {
        super.initialiseDataset(dataset);
    }

    @Override
    public Book createModelFromElement(Node node, String provenanceInfo) {
        String id = getValueFromChildElement(node, "id");

        Book book = new Book(id, provenanceInfo);

        book.setTitle(getValueFromChildElement(node, "title"));
        book.setAuthors(getListFromChildElement(node, "authors"));
        book.setGenres(getListFromChildElement(node, "genres"));
        book.setPublisher(getValueFromChildElement(node, "publisher"));

        String date = getValueFromChildElement(node, "publicationn_date");
        if(date != null && !date.isEmpty()) {
            book.setPublicationDate(LocalDateTime.parse(date));
        }

        String averageRating = getValueFromChildElement(node, "averageRating");
        if(averageRating != null && !averageRating.isEmpty()) {
            book.setAverageRating(Double.parseDouble(averageRating));
        }

        String pageNumber = getValueFromChildElement(node, "pageNumber");
        if(pageNumber != null && !pageNumber.isEmpty()) {
            book.setPageNumber(Integer.parseInt(pageNumber));
        }

        book.setCharacters(getListFromChildElement(node, "characters"));
        book.setAwards(getListFromChildElement(node, "awards"));
        book.setBookFormat(getValueFromChildElement(node, "bookFormat"));

        String price = getValueFromChildElement(node, "price");
        if(price != null && !price.isEmpty()) {
            book.setPrice(Double.parseDouble(price));
        }

        book.setCurrency(getValueFromChildElement(node, "currency"));

        return book;
    }
    
}
