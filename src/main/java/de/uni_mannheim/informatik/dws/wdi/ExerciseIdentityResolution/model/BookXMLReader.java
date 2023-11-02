package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model;

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

        //book.setGenres(getListFromChildElement(node, "genres"));
        String publisher = getValueFromChildElement(node, "publisher");
        if(publisher != null && !publisher.isEmpty()) {
            book.setPublisher(publisher);
        }
        //book.setPublicationDate(getValueFromChildElement(node, "publicationDate"));
        String averageRating = getValueFromChildElement(node, "averageRating");
        if(averageRating != null && !averageRating.isEmpty()) {
            book.setAverageRating(Double.parseDouble(averageRating));
        }
        //book.setPageNumber(Integer.parseInt(getValueFromChildElement(node, "pageNumber")));
        

        return book;
    }
    
}
