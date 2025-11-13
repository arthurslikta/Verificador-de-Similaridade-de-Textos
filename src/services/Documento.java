import java.io.*;
import java.text.Normalizer;
import java.util.*;

public class Documento {
    private String nome;
    private String caminho;
    private HashTable tabelaPalavras;

    public Documento(String nome, String caminho) throws IOException {
        this.nome = nome;
        this.caminho = caminho;
        this.tabelaPalavras = new HashTable(500); // tamanho arbitrário da hash
        processar();
    }

    private void processar() throws IOException {
        Set<String> stopwords = carregarStopWords();

        BufferedReader br = new BufferedReader(new FileReader(caminho));
        String linha;
        while ((linha = br.readLine()) != null) {
            // normalização: minúsculas e remoção de acentos
            linha = Normalizer.normalize(linha.toLowerCase(), Normalizer.Form.NFD);
            linha = linha.replaceAll("[^\\p{ASCII}]", "");

            // tokenização: quebra por qualquer caractere não alfanumérico
            String[] tokens = linha.split("\\W+");
            for (String palavra : tokens) {
                if (!palavra.isEmpty() && !stopwords.contains(palavra)) {
                    tabelaPalavras.inserir(palavra.hashCode()); // usar sua HashTable interna (insere o hash da palavra)
                }
            }
        }
        br.close();
    }

    private Set<String> carregarStopWords() {
        // lista básica, mas você pode ler de um arquivo também
        return new HashSet<>(Arrays.asList(
            "de", "a", "o", "e", "do", "da", "que", "em", "um", "uma",
            "para", "com", "no", "na", "por", "os", "as", "dos", "das",
            "se", "ao", "é", "à", "ou", "mas", "como", "quando", "onde"
        ));
    }

    public String getNome() {
        return nome;
    }

    public HashTable getTabelaPalavras() {
        return tabelaPalavras;
    }
}
