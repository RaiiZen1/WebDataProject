package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Fusers;

import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.Book_DF;
import de.uni_mannheim.informatik.dws.winter.datafusion.AttributeValueFuser;
import de.uni_mannheim.informatik.dws.winter.datafusion.conflictresolution.meta.MostRecent;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.FusedValue;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.RecordGroup;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.processing.Processable;

public class AverageRatingFuserMostRecent extends AttributeValueFuser<Double, Book_DF, Attribute>{
    
    public AverageRatingFuserMostRecent() {
        super(new MostRecent<Double, Book_DF, Attribute>());
    }

    @Override
    public boolean hasValue(Book_DF record, Correspondence<Attribute, Matchable> correspondence) {
        return record.hasValue(Book_DF.AVERAGE_RATING);
    }

    @Override
    public Double getValue(Book_DF record, Correspondence<Attribute, Matchable> correspondence) {
        return record.getAverageRating();
    }

    @Override
    public void fuse(RecordGroup<Book_DF, Attribute> group, Book_DF fusedRecord, Processable<Correspondence<Attribute, Matchable>> schemaCorrespondences, Attribute schemaElement) {
        FusedValue<Double, Book_DF, Attribute> fused = getFusedValue(group, schemaCorrespondences, schemaElement);
        fusedRecord.setAverageRating(fused.getValue());
        fusedRecord.setAttributeProvenance(Book_DF.AVERAGE_RATING, fused.getOriginalIds());
    }
}
