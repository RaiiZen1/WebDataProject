package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model;

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

        return book;
    }
    
}
