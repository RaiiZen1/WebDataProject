package de.uni_mannheim.informatik.dws.winter.similarity.string;

import com.wcohen.ss.SmithWaterman;

import de.uni_mannheim.informatik.dws.winter.similarity.SimilarityMeasure;

/**
 * {@link SimilarityMeasure}, that calculates the Smith-Waterman similarity between
 * two strings.
 * 
 * @author Tim Krause (tim.krause@students.uni-mannheim.de)
 * 
 */
public class SmithWatermanSimilarity extends SimilarityMeasure<String> {

    private static final long serialVersionUID = 1L;

    @Override
    public double calculate(String first, String second) {
        if (first == null || second == null) {
            return 0.0;
        } else {
            SmithWaterman sw = new SmithWaterman();

            // Calculate the Smith-Waterman similarity
            double similarity = sw.score(first, second);

            return similarity;
        }
    }
}