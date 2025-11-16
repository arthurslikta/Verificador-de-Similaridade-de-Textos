import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) {

        if (args.length < 3) {
            System.out.println("java Main <diretorio_documentos> <limiar> <modo> [args]");
            return;
        }

        String diretorio = args[0];
        double limiar = Double.parseDouble(args[1]);
        String modo = args[2].toLowerCase();

        File pasta = new File(diretorio);
        File[] arquivos = pasta.listFiles((dir, name) -> name.endsWith(".txt"));

        if (arquivos == null || arquivos.length == 0) {
            System.out.println("Nenhum arquivo .txt encontrado no diretório informado.");
            return;
        }

        List<Documento> documentos = new ArrayList<>();
        for (File f : arquivos) {
            Documento doc = new Documento(f.getName());
            doc.processarTexto(f);
            documentos.add(doc);
        }

        ComparadorDeDocumentos comp = new ComparadorDeDocumentos(documentos);

        if (!modo.equals("busca")) {
            comp.gerarSimilaridades();
        }

        StringBuilder sb = new StringBuilder();
        sb.append("=== VERIFICADOR DE SIMILARIDADE DE TEXTOS ===\n");
        sb.append("Total de documentos processados: ").append(documentos.size()).append("\n");

        int totalPares = modo.equals("busca")
                ? 1
                : (documentos.size() * (documentos.size() - 1)) / 2;

        sb.append("Total de pares comparados: ").append(totalPares).append("\n");
<<<<<<< HEAD

  
        int tipoHash = documentos.get(0).getTabela().getTipoHash(); 

        if (tipoHash == 1)
            sb.append("Função hash utilizada: soma\n");
        else if (tipoHash == 2)
            sb.append("Função hash utilizada: divisão (base 128)\n");

        sb.append("Métrica de similaridade: Cosseno\n\n");


=======
        sb.append("Função hash utilizada: polinomial (base 31) ou multiplicativo (hashCode)\n");
        sb.append("Métrica de similaridade: Cosseno\n\n");

>>>>>>> 7edfa1ec2694ab4814cd5e15821cbb0c3212ae1e
        if (modo.equals("lista")) {

            sb.append("Pares com similaridade >= ").append(limiar).append("\n");
            sb.append("-----------------------------------------\n");

            List<Resultado> lista = comp.getAVL().listarDecrescente();
            for (Resultado r : lista) {
                if (r.similaridade >= limiar) {
                    sb.append(String.format("%s <-> %s = %.4f\n", r.doc1, r.doc2, r.similaridade));
                }
            }

        } else if (modo.equals("topk")) {

            if (args.length < 4) {
                System.out.println("Modo topK exige: java Main <dir> <limiar> topK <K>");
                return;
            }

            int K = Integer.parseInt(args[3]);
            sb.append("Top ").append(K).append(" pares mais semelhantes:\n");
            sb.append("-----------------------------------------\n");

            List<Resultado> lista = comp.getAVL().listarDecrescente();
            for (int i = 0; i < K && i < lista.size(); i++) {
                Resultado r = lista.get(i);
                sb.append(String.format("%s <-> %s = %.4f\n", r.doc1, r.doc2, r.similaridade));
            }

        } else if (modo.equals("busca")) {

            if (args.length < 5) {
                System.out.println("Uso: java Main <dir> <limiar> busca <arquivo1> <arquivo2>");
                return;
            }

            String d1 = args[3];
            String d2 = args[4];

            Documento A = null, B = null;
            for (Documento d : documentos) {
                if (d.getNome().equals(d1)) A = d;
                if (d.getNome().equals(d2)) B = d;
            }

            if (A == null || B == null) {
                System.out.println("Erro: um dos arquivos não foi encontrado.");
                return;
            }

            double sim = comp.calcularSimilaridadeDireta(A, B);

            sb.append("Comparando: ").append(d1).append(" <-> ").append(d2).append("\n");
            sb.append(String.format("Similaridade calculada: %.4f\n", sim)).append("\n");
<<<<<<< HEAD
        }
=======
        } 
>>>>>>> 7edfa1ec2694ab4814cd5e15821cbb0c3212ae1e
        else {
            System.out.println("Modo inválido. Use: lista | topK | busca");
            return;
        }

<<<<<<< HEAD

=======
>>>>>>> 7edfa1ec2694ab4814cd5e15821cbb0c3212ae1e
        int totalColisoes = 0;
        for (Documento d : documentos) totalColisoes += d.getTabela().getColisoes();
        sb.append("\nColisões totais da HashTable: ").append(totalColisoes).append("\n");

<<<<<<< HEAD
        sb.append("Rotações simples AVL: ").append(comp.getAVL().getRotSimples()).append("\n");
        sb.append("Rotações duplas AVL: ").append(comp.getAVL().getRotDupla()).append("\n");
=======
        sb.append("Rotações simples AVL: ")
                .append(comp.getAVL().getRotSimples()).append("\n");

        sb.append("Rotações duplas AVL: ")
                .append(comp.getAVL().getRotDupla()).append("\n");
>>>>>>> 7edfa1ec2694ab4814cd5e15821cbb0c3212ae1e

        System.out.println(sb.toString());

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("resultado.txt"))) {
            bw.write(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Resultados salvos em resultado.txt");
    }
}
