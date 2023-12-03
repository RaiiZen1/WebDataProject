package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model;

public class StringPreprocessor {

    public static String preprocess(String s) {
        return s.toLowerCase().replaceAll("\\p{Punct}", "").replaceAll("ö", "oe").replaceAll("ü", "ue").replaceAll("ä",
                "ae").replaceAll("ß", "ss");
    }

}
