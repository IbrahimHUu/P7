package Hibernate;

import DAO.ProductDAO;
import Domeinklasse.OVChipkaart;
import Domeinklasse.Product;
import factory.sessionFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class ProductDAOHibernate implements ProductDAO {
    @Override
    public boolean save(Product product) {
        Session session = sessionFactory.getSession();

        Transaction tx = session.beginTransaction();
        session.save(product);
        tx.commit();

        return true;
    }

    @Override
    public boolean update(Product product) {
        Session session = sessionFactory.getSession();

        Transaction tx = session.beginTransaction();
        session.update(product);
        tx.commit();

        return true;
    }

    @Override
    public boolean delete(Product product) {
        Session session = sessionFactory.getSession();

        Transaction tx = session.beginTransaction();
        session.delete(product);
        tx.commit();

        return true;
    }

    @Override
    public List<Product> findByOVChipkaart(OVChipkaart ovChipkaart) {
        Session session = sessionFactory.getSession();
        Transaction tx = session.beginTransaction();
        try {
            String hql = ("from Product where alleOvchipkaarten = :kaart_nummer");
            Query query = session.createQuery(hql);
            query.setParameter("kaart_nummer", ovChipkaart.getKaart_nummer());
            tx.commit();
            return query.list();
        }catch (Exception e) {
            tx.rollback();
            throw e;
        }
    }

    @Override
    public List<Product> findAll() {
        Session session = sessionFactory.getSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            List<Product> alleProducten = session.createQuery("from Product ").getResultList();
            tx.commit();
            return alleProducten;
        } catch (HibernateException e) {
            tx.rollback();
        }
        return null;
    }
}
