/*
 * Copyright (c) 2017 Data and Web Science Group, University of Mannheim, Germany (http://dws.informatik.uni-mannheim.de/)
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */
package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators.title;

import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Blocking.BookBlockingKeyByTitleStringGenerator;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.Book;
import de.uni_mannheim.informatik.dws.winter.matching.blockers.BlockingKeyIndexer;
import de.uni_mannheim.informatik.dws.winter.matching.rules.comparators.Comparator;
import de.uni_mannheim.informatik.dws.winter.matching.rules.comparators.ComparatorLogger;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.HashedDataSet;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.processing.Processable;
import de.uni_mannheim.informatik.dws.winter.similarity.vectorspace.VectorSpaceCosineSimilarity;
import de.uni_mannheim.informatik.dws.winter.utils.WinterLogManager;
import org.slf4j.Logger;

/**
 * {@link Comparator} for {@link Book}s based on the {@link Book#getTitle()}
 * value and their TF-IDF & Cosine Similarity value.
 * 
 * @author Alexander Brinkmann (alex.brinkmann@informatik.uni-mannheim.de)
 * 
 */
public class BookTitleComparatorTFIDFCosine implements Comparator<Book, Attribute> {

	private static final long serialVersionUID = 1L;
	
	private ComparatorLogger comparisonLog;
	private Processable<Correspondence<Book, Attribute>> correspondences;
	private static final Logger logger = WinterLogManager.activateLogger("default");

	public BookTitleComparatorTFIDFCosine(HashedDataSet<Book, Attribute> dataProductsLeft,
											 HashedDataSet<Book, Attribute> dataProductsRight,
											 Processable<Correspondence<Attribute, Matchable>> schemaCorrespondences){

		logger.info("Calculate TF-IDF score of all correspondences");

		BlockingKeyIndexer<Book, Attribute, Book, Attribute> blockingKeyIndexer = new BlockingKeyIndexer<>(
				new BookBlockingKeyByTitleStringGenerator(),
				new BookBlockingKeyByTitleStringGenerator(), new VectorSpaceCosineSimilarity(),
				BlockingKeyIndexer.VectorCreationMethod.TFIDF, 0.1);

		this.correspondences = blockingKeyIndexer
				.runBlocking(dataProductsLeft, dataProductsRight, schemaCorrespondences);

	}

	@Override
	public double compare(
			Book record1,
			Book record2,
			Correspondence<Attribute, Matchable> schemaCorrespondences) {

		String s1 = record1.getTitle();
		String s2 = record2.getTitle();

		Correspondence<Book, Attribute> cor = this.correspondences
				.where((c)->(c.getFirstRecord().equals(record1)))
				.where((c)->(c.getSecondRecord().equals(record2))).firstOrNull();

		double similarity = 0.0;

		if (cor != null){
			similarity = cor.getSimilarityScore();
		}
    	
		// postprocessing
		double postSimilarity = 1;
		if (similarity <= 0.3) {
			postSimilarity = 0;
		}

		postSimilarity *= similarity;
		
		if(this.comparisonLog != null){
			this.comparisonLog.setComparatorName(getClass().getName());
		
			this.comparisonLog.setRecord1Value(s1);
			this.comparisonLog.setRecord2Value(s2);
    	
			this.comparisonLog.setSimilarity(Double.toString(similarity));
			this.comparisonLog.setPostprocessedSimilarity(Double.toString(postSimilarity));
		}
		return postSimilarity;
	}

	public void initialiseIndices(){

	}

	@Override
	public ComparatorLogger getComparisonLog() {
		return this.comparisonLog;
	}

	@Override
	public void setComparisonLog(ComparatorLogger comparatorLog) {
		this.comparisonLog = comparatorLog;
	}

}
