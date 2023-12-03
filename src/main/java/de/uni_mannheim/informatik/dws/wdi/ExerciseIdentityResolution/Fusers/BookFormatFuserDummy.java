package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Fusers;

import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.Book_DF;
import de.uni_mannheim.informatik.dws.winter.datafusion.AttributeValueFuser;
import de.uni_mannheim.informatik.dws.winter.datafusion.conflictresolution.string.ShortestString;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.FusedValue;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.RecordGroup;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.processing.Processable;

public class BookFormatFuserDummy extends AttributeValueFuser<String, Book_DF, Attribute>{
    
    public BookFormatFuserDummy() {
        super(new ShortestString<Book_DF, Attribute>());
    }

    @Override
    public void fuse(RecordGroup<Book_DF, Attribute> group, Book_DF fusedRecord, Processable<Correspondence<Attribute, Matchable>> schemaCorrespondences, Attribute schemaElement) {
         
        // get the fused value
        FusedValue<String, Book_DF, Attribute> fused = getFusedValue(group, schemaCorrespondences, schemaElement);

        if (fused.getValue() != null) {
            fusedRecord.setBookFormat(fused.getValue());
        } else {
            fusedRecord.setPageNumber(0);
        }

        // add provenance info
        fusedRecord.setAttributeProvenance(Book_DF.BOOK_FORMAT, fused.getOriginalIds());
    }

    @Override
	public boolean hasValue(Book_DF record, Correspondence<Attribute, Matchable> correspondence) {
		return record.hasValue(Book_DF.BOOK_FORMAT);
	}

	@Override
	public String getValue(Book_DF record, Correspondence<Attribute, Matchable> correspondence) {
		return record.getBookFormat();
	}
}
