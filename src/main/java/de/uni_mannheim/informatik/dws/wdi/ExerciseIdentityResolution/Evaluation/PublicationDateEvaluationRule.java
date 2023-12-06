package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Evaluation;

import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.Book_DF;
import de.uni_mannheim.informatik.dws.winter.datafusion.EvaluationRule;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;

public class PublicationDateEvaluationRule extends EvaluationRule<Book_DF, Attribute>{
    
    @Override
    public boolean isEqual(Book_DF record1, Book_DF record2, Attribute schemaElement) {
        if(record1.getPublicationDate()==null && record2.getPublicationDate()==null)
            return true;
        else if(record1.getPublicationDate()==null ^ record2.getPublicationDate()==null)
            return false;
        else
            return record1.getPublicationDate().getYear() == record2.getPublicationDate().getYear();
    }

    @Override
    public boolean isEqual(Book_DF record1, Book_DF record2, Correspondence<Attribute, Matchable> schemaCorrespondences) {
        return isEqual(record1, record2, (Attribute)null);
    }
}
