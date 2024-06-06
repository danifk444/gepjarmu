
package g�pj�rm�;

import java.util.HashMap;
import java.util.Map;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class G�pj�rm� implements Comparable<G�pj�rm�> {
    private String rendsz�m;
    private T�pus t�pus;
    private int s�ly;

    public enum T�pus {
        BENZINES, D�ZEL, HIBRID, ELEKTROMOS
    }

    public static class RosszG�pj�rm� extends Exception {
        public RosszG�pj�rm�(String message) {
            super(message);
        }
    }

    public G�pj�rm�(String rendsz�m, T�pus t�pus, int s�ly) throws RosszG�pj�rm� {
        if (s�ly <= 0) {
            throw new RosszG�pj�rm�("A s�ly nem lehet negat�v vagy nulla.");
        }
        this.rendsz�m = rendsz�m;
        this.t�pus = t�pus;
        this.s�ly = s�ly;
    }

    public String getRendsz�m() {
        return rendsz�m;
    }

    public T�pus getT�pus() {
        return t�pus;
    }

    public int getS�ly() {
        return s�ly;
    }

    @Override
    public int compareTo(G�pj�rm� m�sik) {
        return Integer.compare(this.s�ly, m�sik.s�ly);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        G�pj�rm� g�pj�rm� = (G�pj�rm�) obj;
        return s�ly == g�pj�rm�.s�ly &&
               rendsz�m.equals(g�pj�rm�.rendsz�m) &&
               t�pus == g�pj�rm�.t�pus;
    }

    @Override
    public int hashCode() {
        int result = rendsz�m.hashCode();
        result = 31 * result + t�pus.hashCode();
        result = 31 * result + s�ly;
        return result;
    }

    @Override
    public String toString() {
        return "G�pj�rm�{" +
               "rendsz�m='" + rendsz�m + '\'' +
               ", t�pus=" + t�pus +
               ", s�ly=" + s�ly +
               '}';
    }

public class Teherg�pj�rm� extends G�pj�rm� {
    private int tengelyekSz�ma;

    public Teherg�pj�rm�(String rendsz�m, T�pus t�pus, int s�ly, int tengelyekSz�ma) throws RosszG�pj�rm� {
        super(rendsz�m, t�pus, s�ly);
        if (tengelyekSz�ma < 2) {
            throw new RosszG�pj�rm�("A tengelyek sz�ma nem lehet kisebb 2-n�l.");
        }
        this.tengelyekSz�ma = tengelyekSz�ma;
    }

    public int getTengelyekSz�ma() {
        return tengelyekSz�ma;
    }

    public double tengelyTerhel�s() {
        return (double) getS�ly() / tengelyekSz�ma;
    }

    @Override
    public String toString() {
        return "Teherg�pj�rm�{" +
               "rendsz�m='" + getRendsz�m() + '\'' +
               ", t�pus=" + getT�pus() +
               ", s�ly=" + getS�ly() +
               ", tengelyekSz�ma=" + tengelyekSz�ma +
               '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        if (!super.equals(obj)) return false;

        Teherg�pj�rm� that = (Teherg�pj�rm�) obj;
        return tengelyekSz�ma == that.tengelyekSz�ma;
    }

    @Override
    public int hashCode() {
        return 31 * super.hashCode() + tengelyekSz�ma;
    }
    
public class J�rm�park {
    private Map<String, G�pj�rm�> j�rm�vek;

    public J�rm�park() {
        this.j�rm�vek = new HashMap<>();
    }

    public J�rm�park(Collection<G�pj�rm�> gy�jtem�ny) {
        this.j�rm�vek = new HashMap<>();
        for (G�pj�rm� j�rm� : gy�jtem�ny) {
            this.j�rm�vek.put(j�rm�.getRendsz�m(), j�rm�);
        }
    }

    public void hozz�ad(G�pj�rm� j�rm�) {
        j�rm�vek.put(j�rm�.getRendsz�m(), j�rm�);
    }

    public G�pj�rm� keresRendsz�mSzerint(String rendsz�m) {
        return j�rm�vek.get(rendsz�m);
    }

    public List<G�pj�rm�> keresT�pusSzerint(G�pj�rm�.T�pus t�pus) {
        return j�rm�vek.values().stream()
                .filter(j�rm� -> j�rm�.getT�pus() == t�pus)
                .collect(Collectors.toList());
    }

    public List<G�pj�rm�> keresG�pj�rm�Szerint(G�pj�rm� keresettJ�rm�) {
        return j�rm�vek.values().stream()
                .filter(j�rm� -> j�rm�.equals(keresettJ�rm�))
                .collect(Collectors.toList());
    }
    public void t�r�lRendsz�mSzerint(String rendsz�m) throws SikertelenT�rl�s {
        if (j�rm�vek.remove(rendsz�m) == null) {
            throw new SikertelenT�rl�s("A t�rl�s sikertelen, nincs ilyen rendsz�m� j�rm�: " + rendsz�m);
        }
    }

    public void t�r�lG�pj�rm�(G�pj�rm� j�rm�) throws SikertelenT�rl�s {
        if (!j�rm�vek.containsKey(j�rm�.getRendsz�m()) || !j�rm�vek.get(j�rm�.getRendsz�m()).equals(j�rm�)) {
            throw new SikertelenT�rl�s("A t�rl�s sikertelen, nincs ilyen j�rm� a parkban: " + j�rm�);
        }
        j�rm�vek.remove(j�rm�.getRendsz�m());
    }

    public void list�zJ�rm�vek() {
        j�rm�vek.values().forEach(System.out::println);
    }

    @Override
    public String toString() {
        return "J�rm�park{" +
               "j�rm�vek=" + j�rm�vek.values() +
               '}';
    }

    public static class SikertelenT�rl�s extends Exception {
        public SikertelenT�rl�s(String message) {
            super(message);
                }
            }
        }
public static void main(String[] args) {
        
        }
    }
}


