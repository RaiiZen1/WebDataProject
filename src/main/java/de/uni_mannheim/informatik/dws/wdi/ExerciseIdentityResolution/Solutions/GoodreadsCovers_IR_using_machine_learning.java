package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Solutions;

import java.io.File;

import org.slf4j.Logger;

import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Blocking.BookBlockingKeyByTitleAuthorString;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Blocking.BookBlockingKeyByTitleStringGenerator;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators.author.BookAuthorComparatorJaccard;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators.author.BookAuthorComparatorJaro;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators.author.BookAuthorComparatorJaroWinkler;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators.author.BookAuthorComparatorLevenshtein;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators.author.BookAuthorComparatorMongeElkan;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators.author.BookAuthorComparatorPreprocessedJaccard;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators.author.BookAuthorComparatorPreprocessedJaro;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators.author.BookAuthorComparatorPreprocessedJaroWinkler;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators.author.BookAuthorComparatorPreprocessedLevenshtein;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators.author.BookAuthorComparatorPreprocessedMongeElkan;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators.publicationYear.BookPublicationYearComparator10Years;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators.publicationYear.BookPublicationYearComparator2Years;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators.publicationYear.BookPublicationYearComparatorEuclideanDistance;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators.publicationYear.BookPublicationYearComparatorManhattanDistance;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators.publisher.BookPublisherComparatorJaccard;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators.publisher.BookPublisherComparatorJaro;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators.publisher.BookPublisherComparatorJaroWinkler;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators.publisher.BookPublisherComparatorLevenshtein;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators.publisher.BookPublisherComparatorMongeElkan;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators.title.BookTitleComparatorJaccard;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators.title.BookTitleComparatorJaro;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators.title.BookTitleComparatorJaroWinkler;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators.title.BookTitleComparatorLevenshtein;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators.title.BookTitleComparatorMongeElkan;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators.title.BookTitleComparatorPreprocessedJaccard;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators.title.BookTitleComparatorPreprocessedJaro;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators.title.BookTitleComparatorPreprocessedJaroWinkler;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators.title.BookTitleComparatorPreprocessedLevenshtein;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators.title.BookTitleComparatorPreprocessedMongeElkan;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.Book;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.BookXMLReader;
import de.uni_mannheim.informatik.dws.winter.matching.MatchingEngine;
import de.uni_mannheim.informatik.dws.winter.matching.MatchingEvaluator;
import de.uni_mannheim.informatik.dws.winter.matching.algorithms.RuleLearner;
import de.uni_mannheim.informatik.dws.winter.matching.blockers.StandardRecordBlocker;
import de.uni_mannheim.informatik.dws.winter.matching.rules.WekaMatchingRule;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.HashedDataSet;
import de.uni_mannheim.informatik.dws.winter.model.MatchingGoldStandard;
import de.uni_mannheim.informatik.dws.winter.model.Performance;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.model.io.CSVCorrespondenceFormatter;
import de.uni_mannheim.informatik.dws.winter.processing.Processable;
import de.uni_mannheim.informatik.dws.winter.utils.WinterLogManager;

public class GoodreadsCovers_IR_using_machine_learning {
	
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
		HashedDataSet<Book, Attribute> dataGoodreads = new HashedDataSet<>();
		new BookXMLReader().loadFromXML(new File("data/input/goodreads.xml"), "/books/book", dataGoodreads);
		HashedDataSet<Book, Attribute> dataCovers = new HashedDataSet<>();
		new BookXMLReader().loadFromXML(new File("data/input/covers.xml"), "/books/book", dataCovers);
		
		// load the training set
		MatchingGoldStandard gsTraining = new MatchingGoldStandard();
		gsTraining.loadFromCSVFile(new File("data/goldstandard/training/gs_goodreads_covers_train.csv"));

		// create a matching rule
		String options[] = new String[] { "-S" };
		String modelType = "SimpleLogistic"; // use a logistic regression
		WekaMatchingRule<Book, Attribute> matchingRule = new WekaMatchingRule<>(0.5, modelType, options);
		matchingRule.activateDebugReport("data/output/matchingrule/debugResultsMatchingRuleGoodreadsCoversML.csv", 1000, gsTraining);
		
		// add comparators
		matchingRule.addComparator(new BookPublisherComparatorLevenshtein());
		// matchingRule.addComparator(new BookPublisherComparatorPreprocessedLevenshtein());
		matchingRule.addComparator(new BookPublisherComparatorJaccard());
		// matchingRule.addComparator(new BookPublisherComparatorPreprocessedJaccard());
		matchingRule.addComparator(new BookPublisherComparatorJaro());
		// matchingRule.addComparator(new BookPublisherComparatorPreprocessedJaro());
		matchingRule.addComparator(new BookPublisherComparatorJaroWinkler());
		// matchingRule.addComparator(new BookPublisherComparatorPreprocessedJaroWinkler());
		matchingRule.addComparator(new BookPublisherComparatorMongeElkan());
		// matchingRule.addComparator(new BookPublisherComparatorPreprocessedMongeElkan());

		matchingRule.addComparator(new BookTitleComparatorJaccard());
		matchingRule.addComparator(new BookTitleComparatorLevenshtein());
		matchingRule.addComparator(new BookTitleComparatorPreprocessedJaccard());
		matchingRule.addComparator(new BookTitleComparatorPreprocessedLevenshtein());
		// matchingRule.addComparator(new BookTitleComparatorTFIDFCosine(dataGoodreads, dataAmazon, null));
		matchingRule.addComparator(new BookTitleComparatorJaro());
		matchingRule.addComparator(new BookTitleComparatorPreprocessedJaro());
		matchingRule.addComparator(new BookTitleComparatorJaroWinkler());
		matchingRule.addComparator(new BookTitleComparatorPreprocessedJaroWinkler());
		matchingRule.addComparator(new BookTitleComparatorPreprocessedMongeElkan());
		matchingRule.addComparator(new BookTitleComparatorMongeElkan());

		matchingRule.addComparator(new BookAuthorComparatorPreprocessedLevenshtein());
		matchingRule.addComparator(new BookAuthorComparatorLevenshtein());
		matchingRule.addComparator(new BookAuthorComparatorPreprocessedJaccard());
		matchingRule.addComparator(new BookAuthorComparatorJaccard());
		matchingRule.addComparator(new BookAuthorComparatorPreprocessedJaroWinkler());
		matchingRule.addComparator(new BookAuthorComparatorJaroWinkler());
		matchingRule.addComparator(new BookAuthorComparatorPreprocessedJaro());
		matchingRule.addComparator(new BookAuthorComparatorJaro());
		matchingRule.addComparator(new BookAuthorComparatorPreprocessedMongeElkan());
		matchingRule.addComparator(new BookAuthorComparatorMongeElkan());

		matchingRule.addComparator(new BookPublicationYearComparatorEuclideanDistance());
		matchingRule.addComparator(new BookPublicationYearComparatorManhattanDistance());
		matchingRule.addComparator(new BookPublicationYearComparator10Years());
		matchingRule.addComparator(new BookPublicationYearComparator2Years());
		
		
		// train the matching rule's model
		logger.info("*\tLearning matching rule\t*");
		RuleLearner<Book, Attribute> learner = new RuleLearner<>();
		learner.learnMatchingRule(dataGoodreads, dataCovers, null, matchingRule, gsTraining);
		logger.info(String.format("Matching rule is:\n%s", matchingRule.getModelDescription()));
		
		// create a blocker (blocking strategy)
		StandardRecordBlocker<Book, Attribute> blocker = new StandardRecordBlocker<Book, Attribute>(new BookBlockingKeyByTitleAuthorString());
		//SortedNeighbourhoodBlocker<Movie, Attribute, Attribute> blocker = new SortedNeighbourhoodBlocker<>(new MovieBlockingKeyByYearGenerator(), 30);

		blocker.collectBlockSizeData("data/output/blocking/debugResultsBlockingGoodreadsCoversML.csv", 100);
		
		// Initialize Matching Engine
		MatchingEngine<Book, Attribute> engine = new MatchingEngine<>();

		// Execute the matching
		logger.info("*\tRunning identity resolution\t*");
		Processable<Correspondence<Book, Attribute>> correspondences = engine.runIdentityResolution(
				dataGoodreads, dataCovers, null, matchingRule,
				blocker);

		// write the correspondences to the output file
		new CSVCorrespondenceFormatter().writeCSV(new File("data/output/correspondences/goodreads_covers_correspondencesML_new.csv"), correspondences);

		// load the gold standard (test set)
		logger.info("*\tLoading gold standard\t*");
		MatchingGoldStandard gsTest = new MatchingGoldStandard();
		gsTest.loadFromCSVFile(new File(
				"data/goldstandard/test/gs_goodreads_covers_test.csv"));
		
		// evaluate your result
		logger.info("*\tEvaluating result\t*");
		MatchingEvaluator<Book, Attribute> evaluator = new MatchingEvaluator<Book, Attribute>();
		Performance perfTest = evaluator.evaluateMatching(correspondences,
				gsTest);
		
		// print the evaluation result
		logger.info("Goodreads <-> Covers");
		logger.info(String.format(
				"Precision: %.4f",perfTest.getPrecision()));
		logger.info(String.format(
				"Recall: %.4f",	perfTest.getRecall()));
		logger.info(String.format(
				"F1: %.4f",perfTest.getF1()));
    }
}
