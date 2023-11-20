package de.uni_mannheim.informatik.dws.winter.similarity.string;

import com.wcohen.ss.Jaro;

import de.uni_mannheim.informatik.dws.winter.similarity.SimilarityMeasure;

/**
 * {@link SimilarityMeasure}, that calculates the Jaro similarity between
 * two strings.
 * 
 * @author Tim Krause (tim.krause@students.uni-mannheim.de)
 * 
 */
public class JaroSimilarity extends SimilarityMeasure<String> {

    private static final long serialVersionUID = 1L;

    @Override
    public double calculate(String first, String second) {
        if (first == null || second == null) {
            return 0.0;
        } else {
            Jaro j = new Jaro();

            // Calculate the Jaro-Winkler similarity using the JaroWinkler class
            double similarity = j.score(first, second);

            // Return the calculated similarity
            return similarity;
        }
    }
}