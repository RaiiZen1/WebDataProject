package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Evaluation;

import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.Book_DF;
import de.uni_mannheim.informatik.dws.winter.datafusion.EvaluationRule;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.similarity.SimilarityMeasure;
import de.uni_mannheim.informatik.dws.winter.similarity.string.TokenizingJaccardSimilarity;

public class BookFormatEvaluationRule extends EvaluationRule<Book_DF, Attribute>{
    SimilarityMeasure<String> sim = new TokenizingJaccardSimilarity();
    
    @Override
    public boolean isEqual(Book_DF record1, Book_DF record2, Attribute schemaElement) {
        // the title is correct if all tokens are there, but the order does not matter
        return sim.calculate(record1.getBookFormat(), record2.getBookFormat()) == 1.0;
    }

    @Override
    public boolean isEqual(Book_DF record1, Book_DF record2, Correspondence<Attribute, Matchable> schemaCorrespondence) {
        return isEqual(record1, record2, (Attribute)null);
    }
}
