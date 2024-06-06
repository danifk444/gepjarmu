
package gépjármû;

import java.util.HashMap;
import java.util.Map;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class Gépjármû implements Comparable<Gépjármû> {
    private String rendszám;
    private Típus típus;
    private int súly;

    public enum Típus {
        BENZINES, DÍZEL, HIBRID, ELEKTROMOS
    }

    public static class RosszGépjármû extends Exception {
        public RosszGépjármû(String message) {
            super(message);
        }
    }

    public Gépjármû(String rendszám, Típus típus, int súly) throws RosszGépjármû {
        if (súly <= 0) {
            throw new RosszGépjármû("A súly nem lehet negatív vagy nulla.");
        }
        this.rendszám = rendszám;
        this.típus = típus;
        this.súly = súly;
    }

    public String getRendszám() {
        return rendszám;
    }

    public Típus getTípus() {
        return típus;
    }

    public int getSúly() {
        return súly;
    }

    @Override
    public int compareTo(Gépjármû másik) {
        return Integer.compare(this.súly, másik.súly);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Gépjármû gépjármû = (Gépjármû) obj;
        return súly == gépjármû.súly &&
               rendszám.equals(gépjármû.rendszám) &&
               típus == gépjármû.típus;
    }

    @Override
    public int hashCode() {
        int result = rendszám.hashCode();
        result = 31 * result + típus.hashCode();
        result = 31 * result + súly;
        return result;
    }

    @Override
    public String toString() {
        return "Gépjármû{" +
               "rendszám='" + rendszám + '\'' +
               ", típus=" + típus +
               ", súly=" + súly +
               '}';
    }

public class Tehergépjármû extends Gépjármû {
    private int tengelyekSzáma;

    public Tehergépjármû(String rendszám, Típus típus, int súly, int tengelyekSzáma) throws RosszGépjármû {
        super(rendszám, típus, súly);
        if (tengelyekSzáma < 2) {
            throw new RosszGépjármû("A tengelyek száma nem lehet kisebb 2-nél.");
        }
        this.tengelyekSzáma = tengelyekSzáma;
    }

    public int getTengelyekSzáma() {
        return tengelyekSzáma;
    }

    public double tengelyTerhelés() {
        return (double) getSúly() / tengelyekSzáma;
    }

    @Override
    public String toString() {
        return "Tehergépjármû{" +
               "rendszám='" + getRendszám() + '\'' +
               ", típus=" + getTípus() +
               ", súly=" + getSúly() +
               ", tengelyekSzáma=" + tengelyekSzáma +
               '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        if (!super.equals(obj)) return false;

        Tehergépjármû that = (Tehergépjármû) obj;
        return tengelyekSzáma == that.tengelyekSzáma;
    }

    @Override
    public int hashCode() {
        return 31 * super.hashCode() + tengelyekSzáma;
    }
    
public class Jármûpark {
    private Map<String, Gépjármû> jármûvek;

    public Jármûpark() {
        this.jármûvek = new HashMap<>();
    }

    public Jármûpark(Collection<Gépjármû> gyûjtemény) {
        this.jármûvek = new HashMap<>();
        for (Gépjármû jármû : gyûjtemény) {
            this.jármûvek.put(jármû.getRendszám(), jármû);
        }
    }

    public void hozzáad(Gépjármû jármû) {
        jármûvek.put(jármû.getRendszám(), jármû);
    }

    public Gépjármû keresRendszámSzerint(String rendszám) {
        return jármûvek.get(rendszám);
    }

    public List<Gépjármû> keresTípusSzerint(Gépjármû.Típus típus) {
        return jármûvek.values().stream()
                .filter(jármû -> jármû.getTípus() == típus)
                .collect(Collectors.toList());
    }

    public List<Gépjármû> keresGépjármûSzerint(Gépjármû keresettJármû) {
        return jármûvek.values().stream()
                .filter(jármû -> jármû.equals(keresettJármû))
                .collect(Collectors.toList());
    }
    public void törölRendszámSzerint(String rendszám) throws SikertelenTörlés {
        if (jármûvek.remove(rendszám) == null) {
            throw new SikertelenTörlés("A törlés sikertelen, nincs ilyen rendszámú jármû: " + rendszám);
        }
    }

    public void törölGépjármû(Gépjármû jármû) throws SikertelenTörlés {
        if (!jármûvek.containsKey(jármû.getRendszám()) || !jármûvek.get(jármû.getRendszám()).equals(jármû)) {
            throw new SikertelenTörlés("A törlés sikertelen, nincs ilyen jármû a parkban: " + jármû);
        }
        jármûvek.remove(jármû.getRendszám());
    }

    public void listázJármûvek() {
        jármûvek.values().forEach(System.out::println);
    }

    @Override
    public String toString() {
        return "Jármûpark{" +
               "jármûvek=" + jármûvek.values() +
               '}';
    }

    public static class SikertelenTörlés extends Exception {
        public SikertelenTörlés(String message) {
            super(message);
                }
            }
        }
public static void main(String[] args) {
        
        }
    }
}


