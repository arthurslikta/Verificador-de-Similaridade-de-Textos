public class Resultado {
    public String doc1;
    public String doc2;
    public double similaridade;

    public Resultado(String d1, String d2, double similaridade) {
        this.doc1 = d1;
        this.doc2 = d2;
        this.similaridade = similaridade;
    }

    @Override
    public String toString() {
        return doc1 + " <-> " + doc2 + " = " + String.format("%.4f", similaridade);
    }
}
