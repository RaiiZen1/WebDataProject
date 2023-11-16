package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Locale;

import org.w3c.dom.Node;

import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.model.io.XMLMatchableReader;

/**
 * A {@link XMLMatchableReader} for {@link Actor}s.
 * 
 * @author Oliver Lehmberg (oli@dwslab.de)
 * 
 */
public class PublicationDateXMLReader extends XMLMatchableReader<PublicationDate, Attribute> {

	@Override
	public PublicationDate createModelFromElement(Node node, String provenanceInfo) {
		String id = getValueFromChildElement(node, "id");

		// create the object with id and provenance information
		PublicationDate publicationDate = new PublicationDate(id, provenanceInfo);

		// convert the date string into a DateTime object
		try {
			String date = getValueFromChildElement(node, "year");
			if (date != null) {
				DateTimeFormatter formatter = new DateTimeFormatterBuilder()
				        .appendPattern("yyyy-MM-dd")
				        .parseDefaulting(ChronoField.CLOCK_HOUR_OF_DAY, 0)
				        .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
				        .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
				        .toFormatter(Locale.ENGLISH);
				LocalDateTime dt = LocalDateTime.parse(date, formatter);
				publicationDate.setPublicationDate(dt);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return publicationDate;
	}

}
