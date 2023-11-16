package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Locale;

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

        // PublicationDate publicationDate = new PublicationDateXMLReader().createModelFromElement(node, "publication_date");
        // System.out.println(publicationDate.getPublicationDate());
        //System.out.println(getValueFromChildElement(node, "publication_date");

        try {
            String dateString = getValueFromChildElement(node, "publication_date");
    
            if (dateString != null && !dateString.isEmpty()) {
                String[] lines = getValueFromChildElement(node, "publication_date").split("\n");
                String year = lines[0].trim();
                // Default to January 1st if only the year is provided
                String date = year + "-01-01";
                // If the year does not have 4 digits add 0s to the front
                if (year.length() == 2) {
                    date = "00" + date;
                } else if (year.length() == 3) {
                    date = "0" + date;
                    System.out.println(getValueFromChildElement(node, "title"));
                } else if (year.length() == 1) {
                    date = "000" + date;
                }
                DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                        .appendPattern("yyyy-MM-dd")
                        .parseDefaulting(ChronoField.CLOCK_HOUR_OF_DAY, 0)
                        .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
                        .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
                        .toFormatter(Locale.ENGLISH);
        
                LocalDateTime dt = LocalDateTime.parse(date, formatter);
                book.setPublicationDate(dt);
            }
        } catch (Exception e) {
            e.printStackTrace();
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
