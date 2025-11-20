import java.util.ArrayList;
import java.util.List;

public class AVLNode {
    public double chave;
    public List<Resultado> resultados;
    public AVLNode esq, dir;
    public int altura;

    public AVLNode(double chave, Resultado r) {
        this.chave = chave;
        this.resultados = new ArrayList<>();
        this.resultados.add(r);
        this.esq = null;
        this.dir = null;
        this.altura = 1;
    }
}
