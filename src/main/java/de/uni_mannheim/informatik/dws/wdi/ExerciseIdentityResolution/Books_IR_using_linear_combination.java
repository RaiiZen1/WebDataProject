package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution;

import java.io.File;

import org.slf4j.Logger;

import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Blocking.BookBlockingKeyByTitleGenerator;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators.BookTitleComparatorEqual;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.Book;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.BookXMLReader;
import de.uni_mannheim.informatik.dws.winter.matching.MatchingEngine;
import de.uni_mannheim.informatik.dws.winter.matching.blockers.StandardRecordBlocker;
import de.uni_mannheim.informatik.dws.winter.matching.rules.LinearCombinationMatchingRule;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.HashedDataSet;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.model.io.CSVCorrespondenceFormatter;
import de.uni_mannheim.informatik.dws.winter.processing.Processable;
import de.uni_mannheim.informatik.dws.winter.utils.WinterLogManager;

public class Books_IR_using_linear_combination 
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
		HashedDataSet<Book, Attribute> dataCovers = new HashedDataSet<>();
		new BookXMLReader().loadFromXML(new File("data/input/Covers.xml"), "/books/book", dataCovers);
		HashedDataSet<Book, Attribute> dataGoodreads = new HashedDataSet<>();
		new BookXMLReader().loadFromXML(new File("data/input/Goodreads.xml"), "/books/book", dataGoodreads);
		
		// Print a block of data to see if everything works in the console limit it to 20 elements
		int i = 0;
		for(Book b : dataCovers.get()) {
			i++;
			if(i > 20) {
				break;
			}
			// Print all attributes of the current book
			System.out.println(b.toString());
		}


		// create a matching rule
		LinearCombinationMatchingRule<Book, Attribute> matchingRule = new LinearCombinationMatchingRule<>(
				0.7);
		
		// add comparators
		matchingRule.addComparator(new BookTitleComparatorEqual(), 1);

		// create a blocker (blocking strategy)
		StandardRecordBlocker<Book, Attribute> blocker = new StandardRecordBlocker<Book, Attribute>(new BookBlockingKeyByTitleGenerator());
//		NoBlocker<Movie, Attribute> blocker = new NoBlocker<>();
//		SortedNeighbourhoodBlocker<Movie, Attribute, Attribute> blocker = new SortedNeighbourhoodBlocker<>(new MovieBlockingKeyByTitleGenerator(), 1);
		blocker.setMeasureBlockSizes(true);
		//Write debug results to file:
		blocker.collectBlockSizeData("data/output/debugResultsBlocking.csv", 100);

		// Initialize Matching Engine
		MatchingEngine<Book, Attribute> engine = new MatchingEngine<>();

		// Execute the matching
		logger.info("*\tRunning identity resolution\t*");
		Processable<Correspondence<Book, Attribute>> correspondences = engine.runIdentityResolution(
				dataAmazon, dataGoodreads, null, matchingRule,
				blocker);

		// write the correspondences to the output file
		new CSVCorrespondenceFormatter().writeCSV(new File("data/output/covers_goodreads_correspondences.csv"), correspondences);

    }
}
