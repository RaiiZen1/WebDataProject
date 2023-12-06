package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model;

import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.util.Collections;
import java.util.List;
import java.util.LinkedList;
import java.util.Locale;

import de.uni_mannheim.informatik.dws.winter.model.DataSet;
import de.uni_mannheim.informatik.dws.winter.model.FusibleFactory;
import de.uni_mannheim.informatik.dws.winter.model.RecordGroup;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.model.io.XMLMatchableReader;
import de.uni_mannheim.informatik.dws.winter.preprocessing.datatypes.DateJavaTime;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Node;

public class Book_DFXMLReader extends XMLMatchableReader<Book_DF, Attribute> implements FusibleFactory<Book_DF, Attribute>{
    @Override
    protected void initialiseDataset(DataSet<Book_DF, Attribute> dataset) {
        super.initialiseDataset(dataset);

        // the schema is defined in the Movie class and not interpreted from the file, so we have to set the attributes manually
        dataset.addAttribute(Book_DF.TITLE);
        dataset.addAttribute(Book_DF.AUTHORS);
        dataset.addAttribute(Book_DF.GENRES);
        dataset.addAttribute(Book_DF.PUBLISHER);
        dataset.addAttribute(Book_DF.PUBLICATION_DATE);
        dataset.addAttribute(Book_DF.AVERAGE_RATING);
        dataset.addAttribute(Book_DF.PAGE_NUMBER);
        dataset.addAttribute(Book_DF.CHARACTERS);
        dataset.addAttribute(Book_DF.AWARDS);
        dataset.addAttribute(Book_DF.BOOK_FORMAT);
        dataset.addAttribute(Book_DF.PRICE);
        dataset.addAttribute(Book_DF.CURRENCY);
    }


    @Override
    public Book_DF createModelFromElement(Node node, String provenanceInfo) {
        String id = getValueFromChildElement(node, "id");

        Book_DF book = new Book_DF(id, provenanceInfo);

        book.setTitle(getValueFromChildElement(node, "title"));
        book.setAuthors(getListFromChildElement(node, "authors"));
        book.setGenres(getListFromChildElement(node, "genres"));
        book.setPublisher(getValueFromChildElement(node, "publisher"));

        String date = getValueFromChildElement(node, "publication_date");
        try {
            if(date != null) {
                String[] dateString = date.split("\n");
                LocalDateTime dateTime = DateJavaTime.parse(dateString[0]);
                book.setPublicationDate(dateTime);   
            }  
        } catch (Exception e) {
            book.setPublicationDate(LocalDateTime.of(1970, 1, 1, 0, 0));
        }

        String averageRating = getValueFromChildElement(node, "average_rating");
        if(averageRating != null && !averageRating.isEmpty()) {
            book.setAverageRating(Double.parseDouble(averageRating));
        }

        String pageNumber = getValueFromChildElement(node, "page_numbers");
        if(pageNumber != null && !pageNumber.isEmpty()) {
            book.setPageNumber(Integer.parseInt(pageNumber));
        }

        book.setCharacters(getListFromChildElement(node, "characters"));
        book.setAwards(getListFromChildElement(node, "awards"));
        book.setBookFormat(getValueFromChildElement(node, "book_format"));

        String price = getValueFromChildElement(node, "price");
        if(price != null && !price.isEmpty()) {
            book.setPrice(Double.parseDouble(price));
        }

        book.setCurrency(getValueFromChildElement(node, "currency"));

        return book;
    }

    @Override
	public Book_DF createInstanceForFusion(RecordGroup<Book_DF, Attribute> cluster) {
	
	List<String> ids = new LinkedList<>();
	
	for (Book_DF m : cluster.getRecords()) {
		ids.add(m.getIdentifier());
	}
	
	Collections.sort(ids);
	
	String mergedId = StringUtils.join(ids, '+');
	
	return new Book_DF(mergedId, "fused");
	}
}
