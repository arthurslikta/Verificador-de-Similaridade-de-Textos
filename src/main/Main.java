import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {

        String diretorio = args[0];
        double limiar = Double.parseDouble(args[1]);
        String modo = args[2];


        File pasta = new File(diretorio);
        File[] arquivos = pasta.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String nome) {
                return nome.endsWith(".txt");
            }
        });


        List<Documento> documentos = new ArrayList<Documento>();
        for (int i = 0; i < arquivos.length; i++) {
            File f = arquivos[i];
            Documento doc = new Documento(f.getName(), f.getAbsolutePath());
            documentos.add(doc);
        }


        AVLTree arvoreResultados = new AVLTree();

   

        StringBuilder saida = new StringBuilder();
        saida.append("=== VERIFICADOR DE SIMILARIDADE DE TEXTOS ===\n");
        saida.append("Métrica utilizada: Cosseno\n\n");

        if (modo.equalsIgnoreCase("lista")) {

            saida.append("Pares com similaridade >= ").append(limiar).append("\n");
            saida.append("---------------------------------\n");

            for (int i = 0; i < documentos.size(); i++) {
                for (int j = i + 1; j < documentos.size(); j++) {

                    Documento d1 = documentos.get(i);
                    Documento d2 = documentos.get(j);

                    double similaridade = ComparadorDeDocumentos.calcularSimilaridade(d1, d2);

                    if (similaridade >= limiar) {
                        Resultado r = new Resultado(d1.getNome(), d2.getNome(), similaridade);
                        arvoreResultados.inserir(similaridade, r);
                    }
                }
            }


            List<Resultado> resultados = arvoreResultados.listarDecrescente();
            for (int i = 0; i < resultados.size(); i++) {
                saida.append(resultados.get(i).toString()).append("\n");
            }

        } else if (modo.equalsIgnoreCase("topK")) {

            int K = Integer.parseInt(args[3]);

            for (int i = 0; i < documentos.size(); i++) {
                for (int j = i + 1; j < documentos.size(); j++) {

                    Documento d1 = documentos.get(i);
                    Documento d2 = documentos.get(j);

                    double similaridade = ComparadorDeDocumentos.calcularSimilaridade(d1, d2);
                    Resultado r = new Resultado(d1.getNome(), d2.getNome(), similaridade);
                    arvoreResultados.inserir(similaridade, r);
                }
            }

            saida.append("Top ").append(K).append(" pares mais semelhantes:\n");
            saida.append("---------------------------------\n");

            List<Resultado> todos = arvoreResultados.listarDecrescente();
            for (int i = 0; i < K && i < todos.size(); i++) {
                saida.append(todos.get(i).toString()).append("\n");
            }

        } else if (modo.equalsIgnoreCase("busca")) {

            String nomeDoc1 = args[3];
            String nomeDoc2 = args[4];

            Documento doc1 = null;
            Documento doc2 = null;

            for (int i = 0; i < documentos.size(); i++) {
                Documento atual = documentos.get(i);
                if (atual.getNome().equals(nomeDoc1)) {
                    doc1 = atual;
                } else if (atual.getNome().equals(nomeDoc2)) {
                    doc2 = atual;
                }
            }

            double similaridade = ComparadorDeDocumentos.calcularSimilaridade(doc1, doc2);

            saida.append("Comparando: ").append(nomeDoc1).append(" <-> ").append(nomeDoc2).append("\n");
            saida.append("Similaridade calculada: ").append(String.format("%.2f", similaridade)).append("\n");

        } else {
            System.out.println("Modo inválido! Use: lista | topK | busca");
            return;
        }

        System.out.println(saida.toString());

        FileWriter writer = new FileWriter("resultado.txt");
        BufferedWriter bw = new BufferedWriter(writer);
        bw.write(saida.toString());
        bw.close();

        System.out.println("\nResultados também foram salvos em resultado.txt");
    }
}
