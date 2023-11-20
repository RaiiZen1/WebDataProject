package de.uni_mannheim.informatik.dws.winter.similarity.string;

import com.wcohen.ss.JaroWinkler;

import de.uni_mannheim.informatik.dws.winter.similarity.SimilarityMeasure;

/**
 * {@link SimilarityMeasure}, that calculates the Jaro-Winkler similarity between
 * two strings.
 * 
 * @author Tim Krause (tim.krause@students.uni-mannheim.de)
 * 
 */
public class JaroWinklerSimilarity extends SimilarityMeasure<String> {

    private static final long serialVersionUID = 1L;

    @Override
    public double calculate(String first, String second) {
        if (first == null || second == null) {
            return 0.0;
        } else {
            JaroWinkler jw = new JaroWinkler();

            // Calculate the Jaro-Winkler similarity using the JaroWinkler class
            double similarity = jw.score(first, second);

            // Return the calculated similarity
            return similarity;
        }
    }
}