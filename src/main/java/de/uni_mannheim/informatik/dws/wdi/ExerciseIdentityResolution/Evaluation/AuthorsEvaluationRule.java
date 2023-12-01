package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Evaluation;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.Book_DF;
import de.uni_mannheim.informatik.dws.winter.datafusion.EvaluationRule;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import org.apache.commons.text.similarity.LevenshteinDistance;

public class AuthorsEvaluationRule extends EvaluationRule<Book_DF, Attribute> {

    @Override

    public boolean isEqual(Book_DF record1, Book_DF record2, Attribute schemaElement) {
        Set<String> authors1 = record1.getAuthors().stream()
                .map(this::normalizeAuthor)
                .collect(Collectors.toSet());

        Set<String> authors2 = record2.getAuthors().stream()
                .map(this::normalizeAuthor)
                .collect(Collectors.toSet());

        LevenshteinDistance levenshteinDistance = new LevenshteinDistance();

        for (String author1 : authors1) {
            boolean matchFound = false;

            for (String author2 : authors2) {
                if (levenshteinDistance.apply(author1, author2) <= 3) {
                    matchFound = true;
                    break;
                }
            }

            if (!matchFound) {
                return false; // No similar author found for author1
            }
        }

        for (String author2 : authors2) {
            boolean matchFound = false;

            for (String author1 : authors1) {
                if (levenshteinDistance.apply(author2, author1) <= 3) {
                    matchFound = true;
                    break;
                }
            }

            if (!matchFound) {
                return false; // No similar author found for author2
            }
        }

        return true;
    }

    // Helper method to normalize author names (remove extra spaces, convert to
    // lowercase, etc.)
    private String normalizeAuthor(String author) {
        // Implement your normalization logic here (e.g., removing extra spaces,
        // converting to lowercase)
        return author.trim().toLowerCase();
    }

    /*
     * public boolean isEqual(Book_DF record1, Book_DF record2, Attribute
     * schemaElement) {
     * Set<String> authors1 = new HashSet<>();
     * 
     * for (String b : record1.getAuthors()) {
     * authors1.add(b);
     * }
     * 
     * Set<String> authors2 = new HashSet<>();
     * 
     * for (String b : record2.getAuthors()){
     * authors2.add(b);
     * }
     * 
     * return authors1.containsAll(authors2) && authors2.containsAll(authors1);
     * }
     */

    @Override
    public boolean isEqual(Book_DF record1, Book_DF record2,
            Correspondence<Attribute, Matchable> schemaCorrespondence) {
        return isEqual(record1, record2, (Attribute) null);
    }
}
