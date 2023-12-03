package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Solutions;

import java.io.File;

import org.slf4j.Logger;

import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Blocking.BookBlockingKeyByTitleAuthorString;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Blocking.BookBlockingKeyByTitleStringGenerator;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators.author.BookAuthorComparatorMongeElkan;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators.author.BookAuthorComparatorPreprocessedJaroWinkler;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators.title.BookTitleComparatorJaccard;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators.title.BookTitleComparatorJaro;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators.title.BookTitleComparatorLevenshtein;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.Book;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.BookXMLReader;
import de.uni_mannheim.informatik.dws.winter.matching.MatchingEngine;
import de.uni_mannheim.informatik.dws.winter.matching.MatchingEvaluator;
import de.uni_mannheim.informatik.dws.winter.matching.blockers.StandardRecordBlocker;
import de.uni_mannheim.informatik.dws.winter.matching.rules.LinearCombinationMatchingRule;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.HashedDataSet;
import de.uni_mannheim.informatik.dws.winter.model.MatchingGoldStandard;
import de.uni_mannheim.informatik.dws.winter.model.Performance;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.model.io.CSVCorrespondenceFormatter;
import de.uni_mannheim.informatik.dws.winter.processing.Processable;
import de.uni_mannheim.informatik.dws.winter.utils.WinterLogManager;

public class AmazonCovers_IR_using_linear_combination {
	/*
	 * Logging Options:
	 * default: level INFO - console
	 * trace: level TRACE - console
	 * infoFile: level INFO - console/file
	 * traceFile: level TRACE - console/file
	 * 
	 * To set the log level to trace and write the log to winter.log and console,
	 * activate the "traceFile" logger as follows:
	 * private static final Logger logger =
	 * WinterLogManager.activateLogger("traceFile");
	 *
	 */

	private static final Logger logger = WinterLogManager.activateLogger("default");

	public static void main(String[] args) throws Exception {
		// loading data
		logger.info("*\tLoading datasets\t*");
		HashedDataSet<Book, Attribute> dataAmazon = new HashedDataSet<>();
		new BookXMLReader().loadFromXML(new File("data/input/Amazon.xml"), "/books/book", dataAmazon);
		HashedDataSet<Book, Attribute> dataCovers = new HashedDataSet<>();
		new BookXMLReader().loadFromXML(new File("data/input/Covers.xml"), "/books/book", dataCovers);

		// load the gold standard (test set)
		logger.info("*\tLoading gold standard\t*");
		MatchingGoldStandard gsTest = new MatchingGoldStandard();
		gsTest.loadFromCSVFile(new File(
				"data/goldstandard/test/gs_covers_amazon_test.csv"));

		// create a matching rule
		LinearCombinationMatchingRule<Book, Attribute> matchingRule = new LinearCombinationMatchingRule<>(
				0.5);
		matchingRule.activateDebugReport("data/output/matchingrule/debugResultsMatchingRuleAmazonCoversLC.csv", 10000,
				gsTest);

		// add comparators
		// matchingRule.addComparator(new BookTitleComparatorEqual(), 1);
		// matchingRule.addComparator(new BookTitleComparatorJaccard(), 0.6);
		matchingRule.addComparator(new BookTitleComparatorLevenshtein(), 0.4);
		// matchingRule.addComparator(new BookTitleComparatorPreprocessedEqual(), 1);
		// matchingRule.addComparator(new BookTitleComparatorPreprocessedJaccard(), 1);
		// matchingRule.addComparator(new BookTitleComparatorPreprocessedLevenshtein(), 1);
		// matchingRule.addComparator(new BookTitleComparatorTFIDFCosine(dataAmazon, dataCovers, null), 0);
		// matchingRule.addComparator(new BookTitleComparatorJaro(), 0.6);
		// matchingRule.addComparator(new BookTitleComparatorPreprocessedJaro(), 0);
		// matchingRule.addComparator(new BookTitleComparatorJaroWinkler(), 0);
		// matchingRule.addComparator(new BookTitleComparatorPreprocessedJaroWinkler(), 0);
		// matchingRule.addComparator(new BookTitleComparatorSmithWaterman(), 0);
		// matchingRule.addComparator(new BookTitleComparatorPreprocessedSmithWaterman(), 0);
		// matchingRule.addComparator(new BookTitleComparatorMongeElkan(), 0.5);
		// matchingRule.addComparator(new BookTitleComparatorPreprocessedMongeElkan(), 1);
		
		// matchingRule.addComparator(new BookAuthorComparatorPreprocessedJaccard(), 0);
		// matchingRule.addComparator(new BookAuthorComparatorJaccard(), 0);
		// matchingRule.addComparator(new BookAuthorComparatorPreprocessedLevenshtein(), 0);
		// matchingRule.addComparator(new BookAuthorComparatorLevenshtein(), 0);
		// matchingRule.addComparator(new BookAuthorComparatorPreprocessedJaroWinkler(), 0.4);
		// matchingRule.addComparator(new BookAuthorComparatorJaroWinkler(), 0);
		// matchingRule.addComparator(new BookAuthorComparatorPreprocessedJaro(), 0);
		// matchingRule.addComparator(new BookAuthorComparatorJaro(), 0);
		// matchingRule.addComparator(new BookAuthorComparatorPreprocessedMongeElkan(), 0);
		matchingRule.addComparator(new BookAuthorComparatorMongeElkan(), 0.6);

		// create a blocker (blocking strategy)
		StandardRecordBlocker<Book, Attribute> blocker = new StandardRecordBlocker<Book, Attribute>(
				new BookBlockingKeyByTitleAuthorString());
		// NoBlocker<Book, Attribute> blocker = new NoBlocker<>();
		// SortedNeighbourhoodBlocker<Book, Attribute, Attribute> blocker = new
		// SortedNeighbourhoodBlocker<>(new BookBlockingKeyByTitleGenerator2(), 100);
		blocker.setMeasureBlockSizes(true);
		// Write debug results to file:
		blocker.collectBlockSizeData("data/output/blocking/debugResultsBlockingAmazonCoversLC.csv", 10000);

		// Initialize Matching Engine
		MatchingEngine<Book, Attribute> engine = new MatchingEngine<>();

		// Execute the matching
		logger.info("*\tRunning identity resolution\t*");
		Processable<Correspondence<Book, Attribute>> correspondences = engine.runIdentityResolution(
				dataAmazon, dataCovers, null, matchingRule,
				blocker);

		// write the correspondences to the output file
		new CSVCorrespondenceFormatter()
				.writeCSV(new File("data/output/correspondences/amazon_covers_correspondencesLC.csv"), correspondences);

		logger.info("*\tEvaluating result\t*");
		// evaluate your result
		MatchingEvaluator<Book, Attribute> evaluator = new MatchingEvaluator<Book, Attribute>();
		Performance perfTest = evaluator.evaluateMatching(correspondences,
				gsTest);

		// print the evaluation result
		logger.info("Amazon <-> Covers");
		logger.info(String.format(
				"Precision: %.4f", perfTest.getPrecision()));
		logger.info(String.format(
				"Recall: %.4f", perfTest.getRecall()));
		logger.info(String.format(
				"F1: %.4f", perfTest.getF1()));
	}
}