import java.util.*;

public class ComparadorDeDocumentos {
    private List<Documento> documentos;
    private AVLTree arvore = new AVLTree();

    public ComparadorDeDocumentos(List<Documento> docs) {
        this.documentos = docs;
    }

    public void gerarSimilaridades() {
        for (int i = 0; i < documentos.size(); i++) {
            for (int j = i + 1; j < documentos.size(); j++) {

                Documento A = documentos.get(i);
                Documento B = documentos.get(j);

                double sim = calcularSimilaridade(A, B);

                arvore.inserir(sim, new Resultado(A.getNome(), B.getNome(), sim));
            }
        }
    }

    private double calcularSimilaridade(Documento a, Documento b) {

        List<String> palavras = a.getTabela().keys();
        double numerador = 0, normaA = 0, normaB = 0;

        for (String w : palavras) {
            int fa = a.getTabela().get(w);
            int fb = b.getTabela().get(w);
            numerador += fa * fb;
            normaA += fa * fa;
        }

        for (String w : b.getTabela().keys()) {
            int fb = b.getTabela().get(w);
            normaB += fb * fb;
        }

        if (normaA == 0 || normaB == 0)
            return 0;

        return numerador / (Math.sqrt(normaA) * Math.sqrt(normaB));
    }

    public double calcularSimilaridadeDireta(Documento a, Documento b) {
        return calcularSimilaridade(a, b);
    }

    public AVLTree getAVL() {
        return arvore;
    }
}
