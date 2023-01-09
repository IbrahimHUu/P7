package Hibernate;

import DAO.AdresDAO;
import Domeinklasse.Adres;
import Domeinklasse.Reiziger;
import factory.sessionFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class AdresDAOHibernate implements AdresDAO {
    @Override
    public boolean save(Adres adres) {
        Session session = sessionFactory.getSession();

        Transaction tx = session.beginTransaction();
        session.save(adres);
        tx.commit();

        return true;
    }

    @Override
    public boolean update(Adres adres) {
        Session session = sessionFactory.getSession();

        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.update(adres);
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
        }
        return true;
    }

    @Override
    public boolean delete(Adres adres) {
        Session session = sessionFactory.getSession();

        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.delete(adres);
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
        }
        return true;
    }

    @Override
    public List<Adres> findByReiziger(Reiziger reiziger) {
        Session session = sessionFactory.getSession();

        Transaction tx = null;
        try {
            String hql = "from Adres A where A.reiziger.reiziger_id =:reiziger_id";
            tx = session.beginTransaction();
            Query query = session.createQuery(hql);
            query.setParameter("reiziger_id", reiziger.getReiziger_id());

            tx.commit();

            return query.list();
        } catch (HibernateException e) {
            tx.rollback();
        }
        return null;
    }

    @Override
    public List<Adres> findAll() {
        Session session = sessionFactory.getSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            List<Adres> alleAdressen = session.createQuery("from Adres").getResultList();
            tx.commit();
            return alleAdressen;
        } catch (HibernateException e) {
            tx.rollback();
        }
        return null;
    }

}
