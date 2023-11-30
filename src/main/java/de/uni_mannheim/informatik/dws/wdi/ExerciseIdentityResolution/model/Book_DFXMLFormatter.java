package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import de.uni_mannheim.informatik.dws.winter.model.io.XMLFormatter;

public class Book_DFXMLFormatter extends XMLFormatter<Book_DF>{
    
    @Override
    public Element createRootElement(Document doc) {
        return doc.createElement("books");
    }

    @Override
    public Element createElementFromRecord(Book_DF record, Document doc) {
        Element book = doc.createElement("book");

        book.appendChild(createTextElement("id", record.getIdentifier(), doc));

        book.appendChild(createTextElementWithProvenance("title", record.getTitle(), record.getMergedAttributeProvenance(Book_DF.TITLE), doc));

        book.appendChild(createAuthorsElement(record, doc));
        // genres
        // publsiher
        // publication_date
        // average_rating
        //page_number
        // characters
        // awards
        // bool_format
        // price
        // currency

        return book;
    }

    protected Element createTextElementWithProvenance(String name,
			String value, String provenance, Document doc) {
		Element elem = createTextElement(name, value, doc);
		elem.setAttribute("provenance", provenance);
		return elem;
	}

    protected Element createAuthorsElement(Book_DF record, Document doc) {
        Element authorRoot = doc.createElement("authors");

        authorRoot.setAttribute("provenance", record.getMergedAttributeProvenance(Book_DF.AUTHORS));

        for (String a: record.getAuthors()) {
            authorRoot.appendChild(createTextElement("author", a, doc));
        }

        return authorRoot;
    }
}
