package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Evaluation;

import java.util.HashSet;
import java.util.Set;

import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.Book_DF;
import de.uni_mannheim.informatik.dws.winter.datafusion.EvaluationRule;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;

public class AwardsEvaluationRule extends EvaluationRule<Book_DF, Attribute>{
    @Override
    public boolean isEqual(Book_DF record1, Book_DF record2, Attribute schemaElement) {
        Set<String> awards1 = new HashSet<>();

        if (record1.getAwards()== null && record2.getAwards()==null)
            return true;
        else if(record1.getAwards()==null ^ record2.getAwards()==null)
            return false;
        else
            for (String b : record1.getAwards()) {
                awards1.add(b);
            }

            Set<String> awards2 = new HashSet<>();

            for (String b : record2.getAwards()){
                awards2.add(b);
            }

            return awards1.containsAll(awards2) && awards2.containsAll(awards1);
    }

    @Override
	public boolean isEqual(Book_DF record1, Book_DF record2,
			Correspondence<Attribute, Matchable> schemaCorrespondence) {
		return isEqual(record1, record2, (Attribute)null);
	}
}
