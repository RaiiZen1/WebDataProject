package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators.publicationYear;

import de.uni_mannheim.informatik.dws.winter.matching.rules.comparators.Comparator;
import de.uni_mannheim.informatik.dws.winter.matching.rules.comparators.ComparatorLogger;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;

import java.time.LocalDateTime;

import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.Book;

public class BookPublicationYearComparatorEuclideanDistance implements Comparator<Book, Attribute> {

	private static final long serialVersionUID = 1L;
	
	private ComparatorLogger comparisonLog;

	@Override
	public double compare(
            Book record1,
            Book record2,
            Correspondence<Attribute, Matchable> schemaCorrespondences) {

        // Assuming publication years are stored as LocalDateTime objects in the Book class
        LocalDateTime year1 = record1.getPublicationDate();
        LocalDateTime year2 = record2.getPublicationDate();

        // Extract the year from LocalDateTime and compare
        double yearDifference = Math.sqrt(Math.pow((year1.getYear() - year2.getYear()), 2));

        // Use a weighting factor

        if (this.comparisonLog != null) {
            this.comparisonLog.setComparatorName(getClass().getName());

            this.comparisonLog.setRecord1Value(year1.toString());
            this.comparisonLog.setRecord2Value(year2.toString());

            this.comparisonLog.setSimilarity(Double.toString(1.0 / (1.0 + yearDifference)));
        }

        return 1.0 / (1.0 + yearDifference);
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
