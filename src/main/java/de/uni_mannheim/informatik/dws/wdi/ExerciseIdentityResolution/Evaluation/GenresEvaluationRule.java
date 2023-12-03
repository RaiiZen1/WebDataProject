package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Evaluation;

import java.util.HashSet;
import java.util.Set;

import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.Book_DF;
import de.uni_mannheim.informatik.dws.winter.datafusion.EvaluationRule;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;

public class GenresEvaluationRule extends EvaluationRule<Book_DF, Attribute>{
    
    @Override
    public boolean isEqual(Book_DF record1, Book_DF record2, Attribute schemaElement) {
        Set<String> authors1 = new HashSet<>();

        for (String b : record1.getGenres()) {
            authors1.add(b);
        }

        Set<String> authors2 = new HashSet<>();

        for (String b : record2.getGenres()){
            authors2.add(b);
        }

        return authors1.containsAll(authors2) && authors2.containsAll(authors1);
    }
    

    @Override
	public boolean isEqual(Book_DF record1, Book_DF record2,
			Correspondence<Attribute, Matchable> schemaCorrespondence) {
		return isEqual(record1, record2, (Attribute)null);
	}
}
