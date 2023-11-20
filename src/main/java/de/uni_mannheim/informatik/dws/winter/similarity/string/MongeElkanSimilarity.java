package de.uni_mannheim.informatik.dws.winter.similarity.string;

import com.wcohen.ss.MongeElkan;
import de.uni_mannheim.informatik.dws.winter.similarity.SimilarityMeasure;

/**
 * {@link SimilarityMeasure}, that calculates the Monge-Elkan similarity between
 * two strings.
 * 
 * @author Tim Krause (tim.krause@students.uni-mannheim.de)
 * 
 */
public class MongeElkanSimilarity extends SimilarityMeasure<String> {

    private static final long serialVersionUID = 1L;

    @Override
    public double calculate(String first, String second) {
        if (first == null || second == null) {
            return 0.0;
        } else {
            MongeElkan me = new MongeElkan();

            // Calculate the Monge-Elkan similarity
            double similarity = me.score(first, second);

            return similarity;
        }
    }
}