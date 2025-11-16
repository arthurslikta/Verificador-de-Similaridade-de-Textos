import java.util.*;

public class AVLTree {
    private AVLNode raiz;
    private int rotSimples = 0;
    private int rotDupla = 0;

    public AVLTree() { this.raiz = null; }

    private int altura(AVLNode n) { return n == null ? 0 : n.altura; }

    private void atualizarAltura(AVLNode n) {
        if (n != null) n.altura = 1 + Math.max(altura(n.esq), altura(n.dir));
    }

    private int balance(AVLNode n) {
        return n == null ? 0 : altura(n.esq) - altura(n.dir);
    }

    private AVLNode rotacaoDireita(AVLNode y) {
        rotSimples++;
        AVLNode x = y.esq;
        AVLNode T2 = x.dir;
        x.dir = y;
        y.esq = T2;
        atualizarAltura(y);
        atualizarAltura(x);
        return x;
    }

    private AVLNode rotacaoEsquerda(AVLNode x) {
        rotSimples++;
        AVLNode y = x.dir;
        AVLNode T2 = y.esq;
        y.esq = x;
        x.dir = T2;
        atualizarAltura(x);
        atualizarAltura(y);
        return y;
    }

    private AVLNode rotacaoDuplaDireita(AVLNode p) {
        rotDupla++;
        p.esq = rotacaoEsquerda(p.esq);
        return rotacaoDireita(p);
    }

    private AVLNode rotacaoDuplaEsquerda(AVLNode p) {
        rotDupla++;
        p.dir = rotacaoDireita(p.dir);
        return rotacaoEsquerda(p);
    }

    private AVLNode inserirRec(AVLNode node, double chave, Resultado r) {
        if (node == null) return new AVLNode(chave, r);

        if (Math.abs(chave - node.chave) < 1e-9) {
            node.resultados.add(r);
            return node;
        } else if (chave < node.chave) {
            node.esq = inserirRec(node.esq, chave, r);
        } else {
            node.dir = inserirRec(node.dir, chave, r);
        }

        atualizarAltura(node);
        int balance = balance(node);

        if (balance > 1 && chave < node.esq.chave)
            return rotacaoDireita(node);

        if (balance < -1 && chave > node.dir.chave)
            return rotacaoEsquerda(node);

        if (balance > 1 && chave > node.esq.chave)
            return rotacaoDuplaDireita(node);

        if (balance < -1 && chave < node.dir.chave)
            return rotacaoDuplaEsquerda(node);

        return node;
    }

    public void inserir(double chave, Resultado r) {
        raiz = inserirRec(raiz, chave, r);
    }

    public List<Resultado> listarDecrescente() {
        List<Resultado> out = new ArrayList<>();
        listarRec(raiz, out);
        return out;
    }

    private void listarRec(AVLNode n, List<Resultado> out) {
        if (n == null) return;
        listarRec(n.dir, out);
        out.addAll(n.resultados);
        listarRec(n.esq, out);
    }

    public void imprimirTopK(int k) {
        imprimirTopKRec(raiz, new int[]{0}, k);
    }

    private void imprimirTopKRec(AVLNode p, int[] count, int k) {
        if (p == null || count[0] >= k) return;
        imprimirTopKRec(p.dir, count, k);
        for (Resultado r : p.resultados) {
            if (count[0] >= k) break;
            System.out.printf("%.4f: %s vs %s\n", p.chave, r.doc1, r.doc2);
            count[0]++;
        }
        imprimirTopKRec(p.esq, count, k);
    }

    public AVLNode getRaiz() { return raiz; }
    public int getRotSimples() { return rotSimples; }
    public int getRotDupla() { return rotDupla; }
}
