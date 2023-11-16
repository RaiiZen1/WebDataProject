package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Blocking;

import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.Book;
import de.uni_mannheim.informatik.dws.winter.matching.blockers.generators.RecordBlockingKeyGenerator;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.Pair;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.processing.DataIterator;
import de.uni_mannheim.informatik.dws.winter.processing.Processable;


public class BookBlockingKeyByTitleGenerator2 extends
		RecordBlockingKeyGenerator<Book, Attribute> {

	private static final long serialVersionUID = 1L;


	/* (non-Javadoc)
	 * Generate blocking keys based on the first two letters of the title
	 */
	@Override
	public void generateBlockingKeys(Book record, Processable<Correspondence<Attribute, Matchable>> correspondences,
			DataIterator<Pair<String, Book>> resultCollector) {
			
		String blockingKeyValue = record.getTitle().substring(0, Math.min(2,record.getTitle().length())).toUpperCase();
		resultCollector.next(new Pair<>(blockingKeyValue, record));
		
	}

}
