package entites;


public class Categorie {

    private int Catid;
    private String Catlib;

    public Categorie() {
    }

    public Categorie(int Catid, String Catlib) {
        this.Catid = Catid;
        this.Catlib = Catlib;
    }

    
    public Categorie(String Catlib) {
        this.Catlib = Catlib;
    }

    public int getCatid() {
        return Catid;
    }

    public void setCatid(int Catid) {
        this.Catid = Catid;
    }

    public String getCatlib() {
        return Catlib;
    }

    public void setCatlib(String Catlib) {
        this.Catlib = Catlib;
    }

    @Override
    public String toString() {
        return "Catégorie  "   + Catlib.toUpperCase ()+ "\n";
    }
}




