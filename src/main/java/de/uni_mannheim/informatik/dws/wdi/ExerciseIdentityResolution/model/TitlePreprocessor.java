package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model;

public class TitlePreprocessor {

    public static String preprocess(String inputString) {
        // Index des Trennzeichens ":"
        int colonIndex = inputString.indexOf(':');

        // Überprüfen, ob das Trennzeichnen vorhanden ist
        if (colonIndex != -1) {
            // Extrahiere den Teil vor dem Trennzeichnen (ausschließlich)
            String result = inputString.substring(0, colonIndex).trim();

            // Ausgabe des Ergebnisses
            return result;
        } else {
            // Wenn das Trennzeichnen nicht gefunden wurde, gebe den ursprünglichen String aus
            return inputString;
        }
    }
}
