package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators.title;

import de.uni_mannheim.informatik.dws.winter.matching.rules.comparators.Comparator;
import de.uni_mannheim.informatik.dws.winter.matching.rules.comparators.ComparatorLogger;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.similarity.string.JaroWinklerSimilarity;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.Book;

public class BookTitleComparatorPreprocessedJaroWinkler implements Comparator<Book, Attribute> {

    private static final long serialVersionUID = 1L;
    private JaroWinklerSimilarity sim = new JaroWinklerSimilarity();

    private ComparatorLogger comparisonLog;

    @Override
    public double compare(
            Book record1,
            Book record2,
            Correspondence<Attribute, Matchable> schemaCorrespondences) {

        // Get the titles from the Book instances
        String s1 = record1.getTitle().toLowerCase().replaceAll("\\p{Punct}", "");
        String s2 = record2.getTitle().toLowerCase().replaceAll("\\p{Punct}", "");

        // Calculate the Jaro-Winkler similarity between the titles
        double similarity = sim.calculate(s1, s2);

        // Log the comparison details if a logger is provided
        if (this.comparisonLog != null) {
            this.comparisonLog.setComparatorName(getClass().getName());
            this.comparisonLog.setRecord1Value(s1);
            this.comparisonLog.setRecord2Value(s2);
            this.comparisonLog.setSimilarity(Double.toString(similarity));
        }

        // Return the calculated similarity
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