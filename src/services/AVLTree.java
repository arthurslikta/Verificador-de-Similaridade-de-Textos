public class AVLTree {
    AVLNo raiz;

    private int rotacoesSimples;
    private int rotacoesDuplas;

    public AVLTree() {
        this.raiz = null;
        this.rotacoesSimples = 0;
        this.rotacoesDuplas = 0;
    }

    int altura(AVLNo N) {
        if (N == null)
            return 0;
        return N.altura;
    }

    int max(int a, int b) {
        return (a > b) ? a : b;
    }


    AVLNo rotacaoDir(AVLNo y) {
        rotacoesSimples++;

        AVLNo x = y.esq;
        AVLNo T2 = x.dir;

        x.dir = y;
        y.esq = T2;

        y.altura = max(altura(y.esq), altura(y.dir)) + 1;
        x.altura = max(altura(x.esq), altura(x.dir)) + 1;

        return x;
    }

    AVLNo rotacaoEsq(AVLNo x) {
        rotacoesSimples++;

        AVLNo y = x.dir;
        AVLNo T2 = y.esq;

        y.esq = x;
        x.dir = T2;

        x.altura = max(altura(x.esq), altura(x.dir)) + 1;
        y.altura = max(altura(y.esq), altura(y.dir)) + 1;

        return y;
    }

    int getBalance(AVLNo N) {
        if (N == null)
            return 0;
        return altura(N.esq) - altura(N.dir);
    }


    AVLNo insert(AVLNo no, int chave) {
        // Inserção normal de BST
        if (no == null)
            return new AVLNo(chave);

        if (chave < no.chave)
            no.esq = insert(no.esq, chave);
        else if (chave > no.chave)
            no.dir = insert(no.dir, chave);
        else
            return no; // chave duplicada

        no.altura = 1 + max(altura(no.esq), altura(no.dir));

        int balance = getBalance(no);


        if (balance > 1 && chave < no.esq.chave)
            return rotacaoDir(no);

        if (balance < -1 && chave > no.dir.chave)
            return rotacaoEsq(no);

        if (balance > 1 && chave > no.esq.chave) {
            no.esq = rotacaoEsq(no.esq);
            rotacoesDuplas++;
            return rotacaoDir(no);
        }

        if (balance < -1 && chave < no.dir.chave) {
            no.dir = rotacaoDir(no.dir);
            rotacoesDuplas++;
            return rotacaoEsq(no);
        }

        return no;
    }

    public void inserir(int chave) {
        raiz = insert(raiz, chave);
    }

    public int getRotacoesSimples() {
        return rotacoesSimples;
    }

    public int getRotacoesDuplas() {
        return rotacoesDuplas;
    }

    public int getTotalRotacoes() {
        return rotacoesSimples + rotacoesDuplas;
    }

    public void imprimirRotacoes() {
        System.out.println("Rotações simples: " + rotacoesSimples);
        System.out.println("Rotações duplas: " + rotacoesDuplas);
        System.out.println("Total de rotações: " + getTotalRotacoes());
    }
}
