package Domeinklasse;


import factory.DAOFactory;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product")
public class Product {
    @Id
    private int product_nummer;
    private String naam;
    private String beschrijving;
    private double prijs;

    @ManyToMany(mappedBy="alleProducten")
    private List<OVChipkaart> alleOvchipkaarten = new ArrayList<>();

    @Transient
    static
    DAOFactory df = DAOFactory.newInstance();


    public Product(int product_nummer, String naam, String beschrijving, double prijs) {
        this.product_nummer = product_nummer;
        this.naam = naam;
        this.beschrijving = beschrijving;
        this.prijs = prijs;
    }

    public Product() {

    }

    public static Product createNewProduct(int product_nummer, String naam, String beschrijving, double prijs) {
        Product product = new Product(product_nummer, naam, beschrijving, prijs);
        df.getPdao().save(product);
        return product;
    }

    public void deleteProduct() {
        for (OVChipkaart ov : getAlleOvchipkaarten()) {
            ov.getAlleProducten().remove(this);
        }
    }

    public void voegOVChipkaartToe(OVChipkaart ovChipkaart) {
        alleOvchipkaarten.add(ovChipkaart);
//        df.getPdao().update(this);
    }

    public void verwijderOVChipkaart(OVChipkaart ovChipkaart) {
        alleOvchipkaarten.remove(ovChipkaart);
    }

    public int getProduct_nummer() {
        return product_nummer;
    }

    public void setProduct_nummer(int product_nummer) {
        this.product_nummer = product_nummer;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getBeschrijving() {
        return beschrijving;
    }

    public void setBeschrijving(String beschrijving) {
        this.beschrijving = beschrijving;
    }

    public double getPrijs() {
        return prijs;
    }

    public void setPrijs(int prijs) {
        this.prijs = prijs;
    }

    public List<OVChipkaart> getAlleOvchipkaarten() {
        return alleOvchipkaarten;
    }

    public void setAlleOvchipkaarten(List<OVChipkaart> alleOvchipkaarten) {
        this.alleOvchipkaarten = alleOvchipkaarten;
    }

    @Override
    public String toString() {
        String s = "Productnummer:"+ product_nummer + ", Naam:" + " " + naam + ", Beschrijving:" + " " + beschrijving  + " Prijs:" + " " + prijs;
        return s;
    }
}
