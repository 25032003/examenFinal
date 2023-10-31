package org.example;

import org.hibernate.Cache;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class EstudiantesDAO {
    private Cache HibernateUtil;

    public void save(EstudiantesClass estudiante) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(estudiante);
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

    public EstudiantesClass getEstudianteById(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        EstudiantesClass estudiante = session.get(EstudiantesClass.class, id);
        session.close();
        return estudiante;
    }

    public List<EstudiantesClass> getAllEstudiantes() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<EstudiantesClass> estudiantes = session.createQuery("FROM EstudiantesClass", EstudiantesClass.class).list();
        session.close();
        return estudiantes;
    }

    public void update(EstudiantesClass estudiante) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.update(estudiante);
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

    public void delete(EstudiantesClass estudiante) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.delete(estudiante);
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