import java.io.*;
import java.text.Normalizer;
import java.util.HashSet;
import java.util.Set;

public class Documento {
    private String nome;
    private HashTable tabela;

    private static final Set<String> STOPWORDS = new HashSet<>();

    static {
        String[] palavras = {
             "de","a","o","que","e","do","da","em","um","para","e","com","nao","uma","os","no","se","na","por",
             "mais","as","dos","como","mas","foi","ao","ele","das","tem","a","seu","sua","ou","ser","quando","muito",
             "ha","nos","ja","esta","eu","tambem","so","pelo","pela","ate","isso","ela","entre","era","depois","sem",
             "mesmo","aos","ter","seus","quem","nas","me","esse","eles","estao","voce","tinha","foram","essa","num","nem",
             "suas","meu","as","minha","tem","numa","pelos","elas","havia","seja","qual","sera","nos","tenho","lhe","deles",
             "essas","esses","pelas","este","fosse","dele","tu","te","voces","vos","lhes","meus","minhas","teu","tua","teus",
             "tuas","nosso","nossa","nossos","nossas","dela","delas","esta","estes","estas","aquele","aquela","aqueles","aquelas",
             "isto","aquilo","estou","esta","estamos","estao","estive","esteve","estivemos","estiveram","estava","estavamos",
             "estavam","estivera","estiveramos","esteja","estejamos","estejam","estivesse","estivessemos","estivessem","estiver",
             "estivermos","estiverem","hei","ha","havemos","hao","houve","houvemos","houveram","houvera","houveramos","haja","hajamos",
             "hajam","houvesse","houvessemos","houvessem","houver","houvermos","houverem","houverei","houvera","houveremos","houverao",
             "houveria","houveriamos","houveriam","sou","somos","sao","era","eramos","eram","fui","foi","fomos","foram","fora","foramos",
             "seja","sejamos","sejam","fosse","fossemos","fossem","for","formos","forem","serei","sera","seremos","serao","seria","seriamos","seriam","tenho",
             "tem","temos","tem","tinha","tinhamos","tinham","tive","teve","tivemos","tiveram","tivera","tiveramos","tenha","tenhamos",
             "tenham","tivesse","tivessemos","tivessem","tiver","tivermos","tiverem","terei","tera","teremos","terao","teria","teriamos","teriam"
        };
        for (String p : palavras) STOPWORDS.add(p);
    }

    public Documento(String nome) {
        this.nome = nome;
        this.tabela = new HashTable(101, 1);
    }

    public String getNome() { return nome; }
    public HashTable getTabela() { return tabela; }

    public void processarTexto(File arquivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                linha = normalizarTexto(linha);
                String[] palavras = linha.split("\\s+");
                for (String p : palavras) {
                    if (p.isBlank()) continue;
                    if (STOPWORDS.contains(p)) continue;
                    tabela.put(p);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String normalizarTexto(String texto) {
        texto = texto.toLowerCase();
        texto = Normalizer.normalize(texto, Normalizer.Form.NFD);
        texto = texto.replaceAll("[^\\p{L}\\s]", ""); 
        texto = texto.replaceAll("\\p{M}", ""); 
        return texto;
    }
}
