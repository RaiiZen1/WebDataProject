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

public class PageNumberFuserDummy extends AttributeValueFuser<Integer, Book_DF, Attribute>{
    
    public PageNumberFuserDummy() {
        super(new FavourSources<Integer, Book_DF, Attribute>());
    }

    @Override
    public boolean hasValue(Book_DF record, Correspondence<Attribute, Matchable> correspondence) {
        return record.hasValue(Book_DF.PAGE_NUMBER);
    }

    @Override
    public Integer getValue(Book_DF record, Correspondence<Attribute, Matchable> correspondence) {
        return record.getPageNumber();
    }

    @Override 
    public void fuse(RecordGroup<Book_DF, Attribute> group, Book_DF fusedRecord, Processable<Correspondence<Attribute, Matchable>> schemaCorrespondences, Attribute schemaElement) {
        FusedValue<Integer, Book_DF, Attribute> fused = getFusedValue(group, schemaCorrespondences, schemaElement);
        if (fused.getValue() != null) {
            fusedRecord.setPageNumber(fused.getValue());
        } else {
            fusedRecord.setPageNumber(0);
        }
        fusedRecord.setAttributeProvenance(Book_DF.PAGE_NUMBER, fused.getOriginalIds());
    }
}
