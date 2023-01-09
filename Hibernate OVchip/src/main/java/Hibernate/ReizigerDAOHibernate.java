package Hibernate;

import DAO.ReizigerDAO;
import Domeinklasse.Reiziger;
import factory.DAOFactory;
import factory.sessionFactory;
import org.hibernate.*;

import java.sql.Date;
import java.util.List;

public class ReizigerDAOHibernate implements ReizigerDAO {
    DAOFactory df;

    public ReizigerDAOHibernate(DAOFactory df) {
        this.df = df;
    }

    @Override
    public boolean save(Reiziger reiziger) {
        Session session = sessionFactory.getSession();

        Transaction tx = session.beginTransaction();
        session.save(reiziger);
        tx.commit();
//        session.flush();

        return true;

    }

    @Override
    public boolean update(Reiziger reiziger) {
        Session session = sessionFactory.getSession();

        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.update(reiziger);
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
        }
        return true;
    }

    @Override
    public boolean delete(Reiziger reiziger) {
        Session session = sessionFactory.getSession();

        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.delete(reiziger);
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
        }
        return true;
    }

    @Override
    public Reiziger findById(int reiziger_id) {
        Session session = sessionFactory.getSession();

        Transaction tx = null;
        Reiziger reiziger = null;
        try {
            tx = session.beginTransaction();
            reiziger = session.get(Reiziger.class, reiziger_id);
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
        }
        return reiziger;
    }

    @Override
    public List<Reiziger> findByGbdatum(String datum) {
        Session session = sessionFactory.getSession();

        Transaction tx = null;
        try {
            String hql = "from Reiziger R where R.geboortedatum=:date";
            tx = session.beginTransaction();
            Query query = session.createQuery(hql);
            query.setParameter("date", Date.valueOf(datum));

            tx.commit();

            return query.list();
        } catch (HibernateException e) {
            tx.rollback();
        }
        return null;
    }

    @Override
    public List<Reiziger> findAll() {
        Session session = sessionFactory.getSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            List<Reiziger> alleReizigers = session.createQuery("from Reiziger").getResultList();
            tx.commit();
            return alleReizigers;
        } catch (HibernateException e) {
            tx.rollback();
        }
        return null;
    }
}
