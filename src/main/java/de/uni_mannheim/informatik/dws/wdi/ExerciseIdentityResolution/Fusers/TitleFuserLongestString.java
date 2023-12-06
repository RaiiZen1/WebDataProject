package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Fusers;

import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.Book_DF;
import de.uni_mannheim.informatik.dws.winter.datafusion.AttributeValueFuser;
import de.uni_mannheim.informatik.dws.winter.datafusion.conflictresolution.string.LongestString;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.FusedValue;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.RecordGroup;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.processing.Processable;

public class TitleFuserLongestString extends AttributeValueFuser<String, Book_DF, Attribute>{
    
    public TitleFuserLongestString() {
        super(new LongestString<Book_DF, Attribute>());
    }

    @Override
    public void fuse(RecordGroup<Book_DF, Attribute> group, Book_DF fusedRecord, Processable<Correspondence<Attribute, Matchable>> schemaCorrespondences, Attribute schemaElement) {
         
        // get the fused value
        FusedValue<String, Book_DF, Attribute> fused = getFusedValue(group, schemaCorrespondences, schemaElement);

        // set the value for the fused record
        fusedRecord.setTitle(fused.getValue());

        // add provenance info
        fusedRecord.setAttributeProvenance(Book_DF.TITLE, fused.getOriginalIds());
    }

    @Override
	public boolean hasValue(Book_DF record, Correspondence<Attribute, Matchable> correspondence) {
		return record.hasValue(Book_DF.TITLE);
	}

	@Override
	public String getValue(Book_DF record, Correspondence<Attribute, Matchable> correspondence) {
		return record.getTitle();
	}
}
