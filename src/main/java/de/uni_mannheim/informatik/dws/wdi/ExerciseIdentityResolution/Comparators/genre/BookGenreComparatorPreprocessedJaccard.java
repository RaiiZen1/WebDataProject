package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators.genre;

import de.uni_mannheim.informatik.dws.winter.matching.rules.comparators.Comparator;
import de.uni_mannheim.informatik.dws.winter.matching.rules.comparators.ComparatorLogger;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.similarity.string.TokenizingJaccardSimilarity;
import java.util.List;

import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.Book;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.StringPreprocessor;


 
public class BookGenreComparatorPreprocessedJaccard implements Comparator<Book, Attribute> {

	private static final long serialVersionUID = 1L;
	private TokenizingJaccardSimilarity sim = new TokenizingJaccardSimilarity();
	
	private ComparatorLogger comparisonLog;
	
	@Override
	public double compare(
			Book record1,
			Book record2,
			Correspondence<Attribute, Matchable> schemaCorrespondences) {
		
		List<String> l1 = record1.getGenres();
		List<String> l2 = record2.getGenres();

		// calculate similarity
		double maxSimilarity = 0;
		String s1 = "", s2 = "", s1_pre = "", s2_pre = "";
		try {
			for(String genre1 : l1){
				String genre1_pre = StringPreprocessor.preprocess(genre1);
				for(String genre2 : l2){
					String genre2_pre = StringPreprocessor.preprocess(genre2);
					double similarity = sim.calculate(genre1_pre, genre2_pre);
					if(similarity > maxSimilarity){
						maxSimilarity = similarity;
						s1 = genre1;
						s2 = genre2;
						s1_pre = genre1_pre;
						s2_pre = genre2_pre;
					}
				}
			}
		} catch (Exception e) {
			System.out.println(record1.toString());
			System.out.println(record2.toString());
			e.printStackTrace();
		}
		
		// postprocessing
		int postSimilarity = 1;
		if (maxSimilarity <= 0.3) {
			postSimilarity = 0;
		}

		postSimilarity *= maxSimilarity;
		
		if(this.comparisonLog != null){
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
