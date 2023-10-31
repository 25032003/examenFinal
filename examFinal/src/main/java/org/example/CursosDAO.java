package org.example;

import org.hibernate.Cache;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class CursosDAO {
    private Cache HibernateUtil;

    public void save(CursosClass curso) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(curso);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public CursosClass getCursoById(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        CursosClass curso = session.get(CursosClass.class, id);
        session.close();
        return curso;
    }

    public List<CursosClass> getAllCursos() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<CursosClass> cursos = session.createQuery("FROM CursosClass", CursosClass.class).list();
        session.close();
        return cursos;
    }

    public void update(CursosClass curso) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.update(curso);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public void delete(CursosClass curso) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.delete(curso);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}