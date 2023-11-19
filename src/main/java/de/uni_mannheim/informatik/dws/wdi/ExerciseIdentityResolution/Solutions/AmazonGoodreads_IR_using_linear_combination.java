package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Solutions;

import java.io.File;

import org.slf4j.Logger;

import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Blocking.BookBlockingKeyByTitleStringGenerator;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators.author.BookAuthorComparatorJaccard;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators.title.BookTitleComparatorTFIDFCosine;
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

public class AmazonGoodreads_IR_using_linear_combination 
{
	/*
	 * Logging Options:
	 * 		default: 	level INFO	- console
	 * 		trace:		level TRACE     - console
	 * 		infoFile:	level INFO	- console/file
	 * 		traceFile:	level TRACE	- console/file
	 *  
	 * To set the log level to trace and write the log to winter.log and console, 
	 * activate the "traceFile" logger as follows:
	 *     private static final Logger logger = WinterLogManager.activateLogger("traceFile");
	 *
	 */

	private static final Logger logger = WinterLogManager.activateLogger("default");
	
    public static void main( String[] args ) throws Exception
    {
		// loading data
		logger.info("*\tLoading datasets\t*");
		HashedDataSet<Book, Attribute> dataAmazon = new HashedDataSet<>();
		new BookXMLReader().loadFromXML(new File("data/input/Amazon.xml"), "/books/book", dataAmazon);
		HashedDataSet<Book, Attribute> dataGoodreads = new HashedDataSet<>();
		new BookXMLReader().loadFromXML(new File("data/input/Goodreads.xml"), "/books/book", dataGoodreads);
		
		// load the gold standard (test set)
		logger.info("*\tLoading gold standard\t*");
		MatchingGoldStandard gsTest = new MatchingGoldStandard();
		gsTest.loadFromCSVFile(new File(
				"data/goldstandard/gs_amazon_goodreads.csv"));

		// create a matching rule
		LinearCombinationMatchingRule<Book, Attribute> matchingRule = new LinearCombinationMatchingRule<>(
				0.4);
		matchingRule.activateDebugReport("data/output/matchingrule/debugResultsMatchingRuleAmazonGoodreads.csv", 10000, gsTest);
		
		// add comparators
		matchingRule.addComparator(new BookTitleComparatorTFIDFCosine(dataGoodreads, dataAmazon, null), 0.8);
		matchingRule.addComparator(new BookAuthorComparatorJaccard(), 0.2);
		// matchingRule.addComparator(new BookPublisherComparatorLevenshtein(), 0.2);

		// create a blocker (blocking strategy)
		StandardRecordBlocker<Book, Attribute> blocker = new StandardRecordBlocker<Book, Attribute>(new BookBlockingKeyByTitleStringGenerator());
		// NoBlocker<Book, Attribute> blocker = new NoBlocker<>();
		// SortedNeighbourhoodBlocker<Book, Attribute, Attribute> blocker = new SortedNeighbourhoodBlocker<>(new BookBlockingKeyByTitleGenerator2(), 30);
		blocker.setMeasureBlockSizes(true);
		//Write debug results to file:
		blocker.collectBlockSizeData("data/output/blocking/debugResultsBlockingAmazonGoodreads.csv", 10000);

		// Initialize Matching Engine
		MatchingEngine<Book, Attribute> engine = new MatchingEngine<>();

		// Execute the matching
		logger.info("*\tRunning identity resolution\t*");
		Processable<Correspondence<Book, Attribute>> correspondences = engine.runIdentityResolution(
				dataGoodreads, dataAmazon, null, matchingRule,
				blocker);

		// write the correspondences to the output file
		new CSVCorrespondenceFormatter().writeCSV(new File("data/output/correspondences/amazon_goodreads_correspondences.csv"), correspondences);
		
		logger.info("*\tEvaluating result\t*");
		// evaluate your result
		MatchingEvaluator<Book, Attribute> evaluator = new MatchingEvaluator<Book, Attribute>();
		Performance perfTest = evaluator.evaluateMatching(correspondences,
				gsTest);

		// print the evaluation result
		logger.info("Goodreads <-> Amazon");
		logger.info(String.format(
				"Precision: %.4f",perfTest.getPrecision()));
		logger.info(String.format(
				"Recall: %.4f",	perfTest.getRecall()));
		logger.info(String.format(
				"F1: %.4f",perfTest.getF1()));
    }
    
}