package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators.title;

import de.uni_mannheim.informatik.dws.winter.matching.rules.comparators.Comparator;
import de.uni_mannheim.informatik.dws.winter.matching.rules.comparators.ComparatorLogger;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.similarity.string.SmithWatermanSimilarity;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.Book;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.StringPreprocessor;

public class BookTitleComparatorPreprocessedSmithWaterman implements Comparator<Book, Attribute> {

    private static final long serialVersionUID = 1L;
    private SmithWatermanSimilarity sim = new SmithWatermanSimilarity();

    private ComparatorLogger comparisonLog;

    @Override
    public double compare(
            Book record1,
            Book record2,
            Correspondence<Attribute, Matchable> schemaCorrespondences) {

        String title1 = StringPreprocessor.preprocess(record1.getTitle());
        String title2 = StringPreprocessor.preprocess(record2.getTitle());

        double similarity = sim.calculate(title1, title2);

        if (this.comparisonLog != null) {
            this.comparisonLog.setComparatorName(getClass().getName());
            this.comparisonLog.setRecord1Value(title1);
            this.comparisonLog.setRecord2Value(title2);
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