package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Solutions;

import java.io.File;
import java.time.temporal.ChronoField;
import java.util.Locale;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Evaluation.AuthorsEvaluationRule;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Evaluation.AverageRatingEvaluationRule;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Evaluation.AwardsEvaluationRule;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Evaluation.BookFormatEvaluationRule;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Evaluation.CharactersEvaluationRule;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Evaluation.CurrencyEvaluationRule;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Evaluation.GenresEvaluationRule;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Evaluation.PageNumberEvaluationRule;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Evaluation.PriceEvaluationRule;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Evaluation.PublicationDateEvaluationRule;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Evaluation.PublisherEvaluationRule;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Evaluation.TitleEvaluationRule;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Fusers.AuthorsFuserFavourSource;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Fusers.AuthorsFuserIntersection;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Fusers.AuthorsFuserUnion;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Fusers.AverageRatingFuserAverage;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Fusers.AwardsFuserDummy;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Fusers.BookFormatFuserDummy;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Fusers.CharactersFuserDummy;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Fusers.CurrencyFuserDummy;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Fusers.GenresFuserFavourSource;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Fusers.GenresFuserIntersection;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Fusers.GenresFuserUnion;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Fusers.PageNumberFuserDummy;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Fusers.PriceFuserDummy;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Fusers.PublicationDateFuserFavourSource;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Fusers.PublicationDateFuserMostRecent;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Fusers.PublisherFuserFavourSource;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Fusers.TitleFuserLongestString;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Fusers.TitleFuserShortestString;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.Book_DF;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.Book_DFXMLFormatter;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.Book_DFXMLReader;
import de.uni_mannheim.informatik.dws.winter.datafusion.CorrespondenceSet;
import de.uni_mannheim.informatik.dws.winter.datafusion.DataFusionEngine;
import de.uni_mannheim.informatik.dws.winter.datafusion.DataFusionEvaluator;
import de.uni_mannheim.informatik.dws.winter.datafusion.DataFusionStrategy;
import de.uni_mannheim.informatik.dws.winter.model.DataSet;
import de.uni_mannheim.informatik.dws.winter.model.FusibleDataSet;
import de.uni_mannheim.informatik.dws.winter.model.FusibleHashedDataSet;
import de.uni_mannheim.informatik.dws.winter.model.RecordGroupFactory;
import de.uni_mannheim.informatik.dws.winter.utils.WinterLogManager;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.processing.Processable;

import org.slf4j.Logger;

public class DataFusion_Main {
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

	private static final Logger logger = WinterLogManager.activateLogger("traceFile");
	
	public static void main( String[] args ) throws Exception
    {
		// Load the Data into FusibleDataSet
		logger.info("*\tLoading datasets\t*");
		FusibleDataSet<Book_DF, Attribute> ds1 = new FusibleHashedDataSet<>();
		new Book_DFXMLReader().loadFromXML(new File("data/input/Amazon_final.xml"), "/books/book", ds1);
		ds1.printDataSetDensityReport();

		FusibleDataSet<Book_DF, Attribute> ds2 = new FusibleHashedDataSet<>();
		new Book_DFXMLReader().loadFromXML(new File("data/input/Covers_final.xml"), "/books/book", ds2);
		ds2.printDataSetDensityReport();

		FusibleDataSet<Book_DF, Attribute> ds3 = new FusibleHashedDataSet<>();
		new Book_DFXMLReader().loadFromXML(new File("data/input/Goodreads_final.xml"), "/books/book", ds3);
		ds3.printDataSetDensityReport();

		// Maintain Provenance
		// Scores (e.g. from rating)
		ds1.setScore(3.0);
		ds2.setScore(1.0);
		ds3.setScore(2.0);

		// Date (e.g. last update)
		DateTimeFormatter formatter = new DateTimeFormatterBuilder()
		        .appendPattern("yyyy-MM-dd")
		        .parseDefaulting(ChronoField.CLOCK_HOUR_OF_DAY, 0)
		        .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
		        .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
		        .toFormatter(Locale.ENGLISH);
		
		ds1.setDate(LocalDateTime.parse("2019-01-01", formatter));
		ds2.setDate(LocalDateTime.parse("2004-08-01", formatter));
		ds3.setDate(LocalDateTime.parse("2020-01-01", formatter));

		// load correspondences
		logger.info("*\tLoading correspondences\t*");
		CorrespondenceSet<Book_DF, Attribute> correspondences = new CorrespondenceSet<>();
		correspondences.loadCorrespondences(new File("data/output/correspondences/goodreads_covers_correspondencesML_p0.7.csv"),ds3, ds2);
		correspondences.loadCorrespondences(new File("data/output/correspondences/amazon_covers_correspondencesML_p0.7.csv"),ds2, ds1);
		correspondences.loadCorrespondences(new File("data/output/correspondences/amazon_goodreads_correspondencesML_p0.7.csv"),ds1, ds3);
		

		// write group size distribution
		correspondences.printGroupSizeDistribution();

		// load the gold standard
		logger.info("*\tEvaluating results\t*");
		DataSet<Book_DF, Attribute> gs = new FusibleHashedDataSet<>();
		new Book_DFXMLReader().loadFromXML(new File("data/goldstandard_DF/gs_data_fusion_2.0.xml"), "/books/book", gs);

		for(Book_DF m : gs.get()) {
			logger.info(String.format("gs: %s", m.getIdentifier()));
		}

		// define the fusion strategy
		DataFusionStrategy<Book_DF, Attribute> strategy = new DataFusionStrategy<>(new Book_DFXMLReader());
		// write debug results to file
		strategy.activateDebugReport("data/output_DF/debugResultsDatafusion.csv", -1, gs);
		
		// add attribute fusers
		strategy.addAttributeFuser(Book_DF.TITLE, new TitleFuserShortestString(),new TitleEvaluationRule());
		strategy.addAttributeFuser(Book_DF.AUTHORS,new AuthorsFuserFavourSource(), new AuthorsEvaluationRule());
		strategy.addAttributeFuser(Book_DF.GENRES, new GenresFuserFavourSource(),new GenresEvaluationRule());
		strategy.addAttributeFuser(Book_DF.PUBLISHER,new PublisherFuserFavourSource(),new PublisherEvaluationRule());
		strategy.addAttributeFuser(Book_DF.PUBLICATION_DATE,new PublicationDateFuserMostRecent(),new PublicationDateEvaluationRule());
		strategy.addAttributeFuser(Book_DF.AVERAGE_RATING,new AverageRatingFuserAverage(),new AverageRatingEvaluationRule());
		strategy.addAttributeFuser(Book_DF.PAGE_NUMBER,new PageNumberFuserDummy(), new PageNumberEvaluationRule());
		strategy.addAttributeFuser(Book_DF.CHARACTERS,new CharactersFuserDummy(),new CharactersEvaluationRule());
		strategy.addAttributeFuser(Book_DF.AWARDS,new AwardsFuserDummy(),new AwardsEvaluationRule());
		strategy.addAttributeFuser(Book_DF.BOOK_FORMAT,new BookFormatFuserDummy(),new BookFormatEvaluationRule());
		strategy.addAttributeFuser(Book_DF.PRICE,new PriceFuserDummy(),new PriceEvaluationRule());
		strategy.addAttributeFuser(Book_DF.CURRENCY,new CurrencyFuserDummy(),new CurrencyEvaluationRule());
		
		
		// create the fusion engine
		DataFusionEngine<Book_DF, Attribute> engine = new DataFusionEngine<>(strategy);

		// print consistency report
		engine.printClusterConsistencyReport(correspondences, null);
		
		// print record groups sorted by consistency
		engine.writeRecordGroupsByConsistency(new File("data/output_DF/recordGroupConsistencies.csv"), correspondences, null);

		// run the fusion
		logger.info("*\tRunning data fusion\t*");
		FusibleDataSet<Book_DF, Attribute> fusedDataSet = engine.run(correspondences, null);

		// write the result
		new Book_DFXMLFormatter().writeXML(new File("data/output_DF/fused.xml"), fusedDataSet);
		fusedDataSet.printDataSetDensityReport();

		// evaluate
		DataFusionEvaluator<Book_DF, Attribute> evaluator = new DataFusionEvaluator<>(strategy, new RecordGroupFactory<Book_DF, Attribute>());
		
		double accuracy = evaluator.evaluate(fusedDataSet, gs, null);

		logger.info(String.format("*\tAccuracy: %.2f", accuracy));
    }
}
