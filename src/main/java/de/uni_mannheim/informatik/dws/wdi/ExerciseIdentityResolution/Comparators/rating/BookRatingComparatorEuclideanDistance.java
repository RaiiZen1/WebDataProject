package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators.rating;

import de.uni_mannheim.informatik.dws.winter.matching.rules.comparators.Comparator;
import de.uni_mannheim.informatik.dws.winter.matching.rules.comparators.ComparatorLogger;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.Book;

public class BookRatingComparatorEuclideanDistance implements Comparator<Book, Attribute> {

	private static final long serialVersionUID = 1L;
	
	private ComparatorLogger comparisonLog;

	@Override
	public double compare(
            Book record1,
            Book record2,
            Correspondence<Attribute, Matchable> schemaCorrespondences) {

        // Assuming numerical values are stored in some attributes of the Book class
        double value1 = record1.getAverageRating();
        double value2 = record2.getAverageRating();

        // Calculate Euclidean distance
        double distance = Math.sqrt(Math.pow((value1 - value2), 2));

        if (this.comparisonLog != null) {
            this.comparisonLog.setComparatorName(getClass().getName());

            this.comparisonLog.setRecord1Value(Double.toString(value1));
            this.comparisonLog.setRecord2Value(Double.toString(value2));

            this.comparisonLog.setSimilarity(Double.toString(distance));
        }

        return distance;
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
