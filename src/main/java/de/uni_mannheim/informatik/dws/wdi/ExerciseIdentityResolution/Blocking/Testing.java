package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Blocking;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class Testing {

    public static void main(String[] args) {

        // Extract the first three letters of the book title
        String title = "I Can't Believe You Anymore".replaceAll("[^a-zA-Z0-9]", "");
        LocalDateTime publicationDate = LocalDateTime.of(2010, 1, 1, 0, 0);
        
        // Extract the first three letters of the book title
        String titleKey = title.length() > 3 ? title.substring(0, 4).toUpperCase() : title.toUpperCase();

        // Extract the last two digits of the publication year
        String yearKey = publicationDate != null ? Integer.toString(publicationDate.getYear()).substring(2) : "";

        // Combine both keys
        String blockingKeyValue = titleKey + yearKey;
        System.out.println(blockingKeyValue);

    }
}
