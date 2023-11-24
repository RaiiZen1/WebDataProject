package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Blocking;

import java.util.List;

import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.Book;
import de.uni_mannheim.informatik.dws.winter.matching.blockers.generators.RecordBlockingKeyGenerator;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.Pair;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.processing.DataIterator;
import de.uni_mannheim.informatik.dws.winter.processing.Processable;


public class BookBlockingKeyByTitleAuthorString extends
		RecordBlockingKeyGenerator<Book, Attribute> {

	private static final long serialVersionUID = 1L;


	/* (non-Javadoc)
	 * @see de.uni_mannheim.informatik.wdi.matching.blocking.generators.BlockingKeyGenerator#generateBlockingKeys(de.uni_mannheim.informatik.wdi.model.Matchable, de.uni_mannheim.informatik.wdi.model.Result, de.uni_mannheim.informatik.wdi.processing.DatasetIterator)
	 * Example: The Hobbit; Maximilian Humbold, Franz Maier -> THMA
	 * 			Me, Myself and I; Luigi Giancoli -> MELU
	 */
	@Override
	public void generateBlockingKeys(Book record, Processable<Correspondence<Attribute, Matchable>> correspondences, DataIterator<Pair<String, Book>> resultCollector) {
	
		// Extract the first three letters of the book title
		String title = record.getTitle().replaceAll("[^a-zA-Z0-9]", "");
		List<String> authors = record.getAuthors();

		// Extract the first three letters of the book title
        String titleKey = title.length() > 4 ? title.substring(0, 5).toUpperCase() : title.toUpperCase();

		// Extract the first two letters of the first author's name, if available
		String authorKey = "";
		if (!authors.isEmpty()) {
			String firstAuthor = authors.get(0).replaceAll("[^a-zA-Z0-9]", "");
            authorKey = firstAuthor.length() > 1 ? firstAuthor.substring(0, 2).toUpperCase() : firstAuthor.toUpperCase();
        }
	
		// Combine both keys
		String blockingKeyValue = titleKey + authorKey;
	
		// Add the blocking key and the record to the result collector
		resultCollector.next(new Pair<>(blockingKeyValue, record));
	}	
}