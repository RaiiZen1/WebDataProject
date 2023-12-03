package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Fusers;

import java.util.List;

import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.Book_DF;
import de.uni_mannheim.informatik.dws.winter.datafusion.AttributeValueFuser;
import de.uni_mannheim.informatik.dws.winter.datafusion.conflictresolution.list.Union;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.FusedValue;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.RecordGroup;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.processing.Processable;

public class GenresFuserUnion extends AttributeValueFuser<List<String>, Book_DF, Attribute>{
    public GenresFuserUnion() {
        super(new Union<String, Book_DF, Attribute>());
    }

    @Override
    public boolean hasValue(Book_DF record, Correspondence<Attribute, Matchable> correspondence) {
        return record.hasValue(Book_DF.GENRES);
    }

    @Override
    public List<String> getValue(Book_DF record, Correspondence<Attribute, Matchable> correspondence) {
        return record.getGenres();
    }

    @Override
    public void fuse(RecordGroup<Book_DF, Attribute> group, Book_DF fusedRecord, Processable<Correspondence<Attribute, Matchable>> schemaCorrespondences, Attribute schemaElement) {
        FusedValue<List<String>, Book_DF, Attribute> fused = getFusedValue(group, schemaCorrespondences, schemaElement);
        fusedRecord.setGenres(fused.getValue());
        fusedRecord.setAttributeProvenance(Book_DF.GENRES, fused.getOriginalIds());
    }
}
