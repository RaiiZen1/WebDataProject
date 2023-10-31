package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution;

import java.io.File;

import org.slf4j.Logger;

import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.Book;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.BookXMLReader;
import de.uni_mannheim.informatik.dws.winter.model.HashedDataSet;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.utils.WinterLogManager;

public class IR_using_linear_combination2 
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
		
		// Print dataAmazon 
		for (Book book : dataAmazon.get()) {
			System.out.println(String.format("Amazon:\t[%s] %s", book.getIdentifier(), book.getTitle()));
		}
    }
}
