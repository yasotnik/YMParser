package ua.edu.ssu.sotnik.controllers;

/**
 * Created by Jabba on 25.03.2015.
 */
public class ParserFactory {

    public static YandexParserAPI getParser(String parserType) {
        if (parserType == null) {
            return null;
        }
        if (parserType.equalsIgnoreCase("YANDEX")) {
            return new YandexParserAPI();
        }
        return null;
    }

}
