import java.util.LinkedList;

public class HashTable {

    LinkedList<Integer>[] tabela;
    int tamanho;

    public HashTable(int tamanho) {
        this.tamanho = tamanho;
        tabela = new LinkedList[tamanho];

        for (int i = 0; i < tamanho; i++) {
            tabela[i] = new LinkedList<>(); // Cria uma lista pra cada posição da tabela
        }
    }

    int hash(int chave) { // Método de divisão
        return chave % tamanho;
    }

    public void inserir(int chave) {
        int indice = hash(chave);
        if (!tabela[indice].contains(chave)) {
            tabela[indice].add(chave);
            System.out.println("Inserir " + chave + ", indice " + indice);
        } else {
            System.out.println(chave + " ja existe");
        }
    }
    
    // Busca uma chave na tabela
    public void buscar(int chave) {
        int indice = hash(chave);
        if (tabela[indice].contains(chave)) {
            System.out.println("Chave " + chave + " esta no indice " + indice);
        } else {
            System.out.println("Chave " + chave + " nao existe\n");
        }
    }

    // Exclui uma chave da tabela
    public void excluir(int chave) {
        int indice = hash(chave);
        if (tabela[indice].remove((Integer) chave)) {
            System.out.println("Chave " + chave + " removida do indice " + indice);
        } else {
            System.out.println("Chave " + chave + " nao existe");
        }
    }
}
