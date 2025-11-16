import java.util.*;

public class HashTable {
    private LinkedList<HashNode>[] tabela;
    private int M;
    private int tipoHash; // 1 = polinomial, 2 = hashCode()
    private int colisoes;

    public HashTable(int M, int tipoHash) {
        this.M = M;
        this.tipoHash = tipoHash;
        tabela = new LinkedList[M];
        for (int i = 0; i < M; i++)
            tabela[i] = new LinkedList<>();
    }

    private int hash(String s) {
        if (tipoHash == 1) {
            int h = 0;
            for (int i = 0; i < s.length(); i++)
                h = (31 * h + s.charAt(i)) % M;
            return h;
        } else {
            return (s.hashCode() & 0x7fffffff) % M;
        }
    }

    public void put(String chave) {
        int idx = hash(chave);
        LinkedList<HashNode> bucket = tabela[idx];

        for (HashNode node : bucket) {
            if (node.chave.equals(chave)) {
                node.valor++;
                return;
            }
        }

        if (!bucket.isEmpty())
            colisoes++;

        bucket.add(new HashNode(chave, 1));
    }

    public int get(String chave) {
        int idx = hash(chave);
        for (HashNode node : tabela[idx]) {
            if (node.chave.equals(chave))
                return node.valor;
        }
        return 0;
    }

    public List<String> keys() {
        List<String> chaves = new ArrayList<>();
        for (LinkedList<HashNode> bucket : tabela)
            for (HashNode n : bucket)
                chaves.add(n.chave);
        return chaves;
    }

    public int getColisoes() {
        return colisoes;
    }
}