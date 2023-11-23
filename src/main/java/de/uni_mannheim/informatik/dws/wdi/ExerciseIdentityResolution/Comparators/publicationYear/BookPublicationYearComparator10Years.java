package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators.publicationYear;

import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.Book;
import de.uni_mannheim.informatik.dws.winter.matching.rules.comparators.Comparator;
import de.uni_mannheim.informatik.dws.winter.matching.rules.comparators.ComparatorLogger;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.similarity.date.YearSimilarity;

/**
 * {@link Comparator} for {@link Book}s based on the {@link Book#getPublicationDate()}
 * value. With a maximal difference of 10 years.
 * 
 * @author Oliver Lehmberg (oli@dwslab.de)
 * 
 */
public class BookPublicationYearComparator10Years implements Comparator<Book, Attribute> {

	private static final long serialVersionUID = 1L;
	private YearSimilarity sim = new YearSimilarity(10);

	private ComparatorLogger comparisonLog;

	@Override
	public double compare(
			Book record1,
			Book record2,
			Correspondence<Attribute, Matchable> schemaCorrespondences) {

		double similarity = sim.calculate(record1.getPublicationDate(), record2.getPublicationDate());

		if (this.comparisonLog != null) {
			this.comparisonLog.setComparatorName(getClass().getName());

			this.comparisonLog.setRecord1Value(record1.getPublicationDate().toString());
			this.comparisonLog.setRecord2Value(record2.getPublicationDate().toString());

			this.comparisonLog.setSimilarity(Double.toString(similarity));
		}
		return similarity;
	}

	@Override
	public ComparatorLogger getComparisonLog() {
		return this.comparisonLog;
	}

	@Override
	public void setComparisonLog(ComparatorLogger comparatorLog) {
		this.comparisonLog = comparatorLog;
	}

}
