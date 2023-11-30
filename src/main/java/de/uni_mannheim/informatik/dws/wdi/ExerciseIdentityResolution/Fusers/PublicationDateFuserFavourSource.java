package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Fusers;

import java.time.LocalDateTime;

import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.Book_DF;
import de.uni_mannheim.informatik.dws.winter.datafusion.AttributeValueFuser;
import de.uni_mannheim.informatik.dws.winter.datafusion.conflictresolution.meta.FavourSources;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.FusedValue;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.RecordGroup;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.processing.Processable;

public class PublicationDateFuserFavourSource extends AttributeValueFuser<LocalDateTime, Book_DF, Attribute>{
    
    public PublicationDateFuserFavourSource() {
        super(new FavourSources<LocalDateTime, Book_DF, Attribute>());
    }

   @Override
   public boolean hasValue(Book_DF record, Correspondence<Attribute, Matchable> correspondence) {
        return record.hasValue(Book_DF.PUBLICATION_DATE);
   } 

   @Override
   public LocalDateTime getValue(Book_DF record, Correspondence<Attribute, Matchable> correspondence) {
    return record.getPublicationDate();
   }

   @Override
   public void fuse(RecordGroup<Book_DF, Attribute> group, Book_DF fusedRecord, Processable<Correspondence<Attribute, Matchable>> schemaCorrespondences, Attribute schemaElement) {
    FusedValue<LocalDateTime, Book_DF, Attribute> fused = getFusedValue(group, schemaCorrespondences, schemaElement);
    fusedRecord.setPublicationDate(fused.getValue());
    fusedRecord.setAttributeProvenance(Book_DF.PUBLICATION_DATE, fused.getOriginalIds());
   }
}
