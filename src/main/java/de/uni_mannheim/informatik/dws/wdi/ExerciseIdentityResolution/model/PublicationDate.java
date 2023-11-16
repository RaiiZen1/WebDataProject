package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model;

import java.time.LocalDateTime;
import de.uni_mannheim.informatik.dws.winter.model.AbstractRecord;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;


public class PublicationDate extends AbstractRecord<Attribute> {

	/*
	 * example entry <actor> <name>Janet Gaynor</name>
	 * <birthday>1906-01-01</birthday> <birthplace>Pennsylvania</birthplace>
	 * </actor>
	 */

	private static final long serialVersionUID = 1L;
	private LocalDateTime publicationDate;

	public PublicationDate(String identifier, String provenance) {
		super(identifier, provenance);
	}

	public LocalDateTime getPublicationDate() {
		return publicationDate;
	}

	public void setPublicationDate(LocalDateTime publicationDate) {
		this.publicationDate = publicationDate;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int result = 31 + ((publicationDate == null) ? 0 : publicationDate.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PublicationDate other = (PublicationDate) obj;
		if (publicationDate == null) {
			if (other.publicationDate != null)
				return false;
		} else if (!publicationDate.equals(other.publicationDate))
			return false;
		return true;
	}

	public static final Attribute PUBLICATIONDATE = new Attribute("Publicationdate");
	
	/* (non-Javadoc)
	 * @see de.uni_mannheim.informatik.wdi.model.Record#hasValue(java.lang.Object)
	 */
	@Override
	public boolean hasValue(Attribute attribute) {
		if(attribute==PUBLICATIONDATE)
			return publicationDate!=null;
		return false;
	}
}
