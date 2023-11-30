package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Evaluation;

import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.Book_DF;
import de.uni_mannheim.informatik.dws.winter.datafusion.EvaluationRule;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;

public class PublisherEvaluationRule extends EvaluationRule<Book_DF, Attribute>{
    
    @Override
    public boolean isEqual(Book_DF record1, Book_DF record2, Attribute schemaElement) {
        if(record1.getPublisher()==null && record2.getPublisher()==null)
            return true;
        else if(record1.getPublisher()==null ^ record2.getPublisher()==null)
            return false;
        else return record1.getPublisher().equals(record2.getPublisher());
    }

    @Override public boolean isEqual(Book_DF record1, Book_DF record2, Correspondence<Attribute, Matchable> schemaCorrespondence) {
        return isEqual(record1, record2, (Attribute)null);
    }
}
