package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Fusers;


import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.Book_DF;
import de.uni_mannheim.informatik.dws.winter.datafusion.AttributeValueFuser;
import de.uni_mannheim.informatik.dws.winter.datafusion.conflictresolution.meta.FavourSources;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.FusedValue;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.RecordGroup;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.processing.Processable;

public class CurrencyFuserDummy extends AttributeValueFuser<String, Book_DF, Attribute>{
    
    public CurrencyFuserDummy() {
        super(new FavourSources<String, Book_DF, Attribute>());
    }

    @Override
    public boolean hasValue(Book_DF record, Correspondence<Attribute, Matchable> correspondence) {
        return record.hasValue(Book_DF.CURRENCY);
    }

    @Override
    public String getValue(Book_DF record, Correspondence<Attribute, Matchable> correspondance) {
        return record.getCurrency();
    }

    @Override
	public void fuse(RecordGroup<Book_DF, Attribute> group, Book_DF fusedRecord, Processable<Correspondence<Attribute, Matchable>> schemaCorrespondences, Attribute schemaElement) {
		FusedValue<String, Book_DF, Attribute> fused = getFusedValue(group, schemaCorrespondences, schemaElement);
		if (fused.getValue() != null) {
            fusedRecord.setCurrency(fused.getValue());
        } else {
            fusedRecord.setCurrency(null);
        }
		fusedRecord
				.setAttributeProvenance(Book_DF.AUTHORS, fused.getOriginalIds());
	}
}
