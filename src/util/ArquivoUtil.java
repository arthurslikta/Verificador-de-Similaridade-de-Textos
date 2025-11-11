package util;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class ArquivoUtil {
    private static final Set<String> STOPWORDS = new HashSet<>(Arrays.asList(
        "a","ao","aos","as","de","do","da","das","dos","em","no","na","nas","nos",
        "para","por","que","com","como","é","e","o","os","uma","um","se","sua","seu"
    ));

    public static List<String> lerPalavrasDeArquivo(String caminho) {
        List<String> palavras = new ArrayList<>();
        try {
            String texto = Files.readString(Paths.get(caminho))
                    .toLowerCase()
                    .replaceAll("[^a-zà-ú0-9\\s]", " ");

            for (String p : texto.split("\\s+")) {
                if (!p.isBlank() && !STOPWORDS.contains(p))
                    palavras.add(p);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return palavras;
    }
}
