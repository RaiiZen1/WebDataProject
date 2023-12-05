package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators.author;

import de.uni_mannheim.informatik.dws.winter.matching.rules.comparators.Comparator;
import de.uni_mannheim.informatik.dws.winter.matching.rules.comparators.ComparatorLogger;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.similarity.string.MongeElkanSimilarity;

import java.util.List;

import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.Book;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.StringPreprocessor;

public class BookAuthorComparatorPreprocessedMongeElkan implements Comparator<Book, Attribute> {

    private static final long serialVersionUID = 1L;
    private MongeElkanSimilarity sim = new MongeElkanSimilarity();

    private ComparatorLogger comparisonLog;

    @Override
    public double compare(
            Book record1,
            Book record2,
            Correspondence<Attribute, Matchable> schemaCorrespondences) {

        List<String> l1 = record1.getAuthors();
        List<String> l2 = record2.getAuthors();

        // calculate similarity
        double maxSimilarity = 0;
		String s1 = "", s2 = "", s1_pre = "", s2_pre = "";
		try {
			for(String author1 : l1){
				String author1_pre = StringPreprocessor.preprocess(author1);
				for(String author2 : l2){
					String author2_pre = StringPreprocessor.preprocess(author2);
					double similarity = sim.calculate(author1_pre, author2_pre);
					if(similarity > maxSimilarity){
						maxSimilarity = similarity;
						s1 = author1;
						s2 = author2;
						s1_pre = author1_pre;
						s2_pre = author2_pre;
					}
				}
			}
        } catch (Exception e) {
            System.out.println(record1.toString());
            System.out.println(record2.toString());
            e.printStackTrace();
        }

        // postprocessing
        double postSimilarity = 1;
        if (maxSimilarity <= 0.3) {
            postSimilarity = 0;
        }

        postSimilarity *= maxSimilarity;

        if (this.comparisonLog != null) {
            this.comparisonLog.setComparatorName(getClass().getName());

            this.comparisonLog.setRecord1Value(s1);
            this.comparisonLog.setRecord2Value(s2);
            this.comparisonLog.setRecord1PreprocessedValue(s1_pre);
			this.comparisonLog.setRecord2PreprocessedValue(s2_pre);

            this.comparisonLog.setSimilarity(Double.toString(maxSimilarity));
            this.comparisonLog.setPostprocessedSimilarity(Double.toString(postSimilarity));
        }
        return postSimilarity;
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