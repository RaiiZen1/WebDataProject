package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Evaluation;

import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.Book_DF;
import de.uni_mannheim.informatik.dws.winter.datafusion.EvaluationRule;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;

public class PriceEvaluationRule extends EvaluationRule<Book_DF, Attribute>{
    @Override
	public boolean isEqual(Book_DF record1, Book_DF record2, Attribute schemaElement) {
		if(record1.getPrice()==-1.0 && record2.getPrice()==-1.0)
			return true;
		else if(record1.getPrice()==-1.0 ^ record2.getPrice()==-1.0)
			return false;
		else
			return record1.getPrice() == record2.getPrice();
	}

	/* (non-Javadoc)
	 * @see de.uni_mannheim.informatik.wdi.datafusion.EvaluationRule#isEqual(java.lang.Object, java.lang.Object, de.uni_mannheim.informatik.wdi.model.Correspondence)
	 */
	@Override
	public boolean isEqual(Book_DF record1, Book_DF record2,
			Correspondence<Attribute, Matchable> schemaCorrespondence) {
		return isEqual(record1, record2, (Attribute)null);
	}
}
