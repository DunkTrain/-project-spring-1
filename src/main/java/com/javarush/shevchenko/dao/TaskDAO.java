package com.javarush.shevchenko.dao;

import java.util.List;
import java.util.Optional;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.hibernate.SessionFactory;
import com.javarush.shevchenko.domain.Task;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class TaskDAO {

    private final SessionFactory sessionFactory;

    // Конструктор, принимающий SessionFactory для работы с сессиями Hibernate
    public TaskDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    // Получение списка задач с пагинацией
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public List<Task> getAll(int offset, int limit) {
        Query<Task> query = getSession().createQuery("select t from Task t", Task.class);
        query.setFirstResult(offset);
        query.setMaxResults(limit);
        return query.getResultList();
    }

    // Получение общего количества задач
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public int getAllCount() {
        Query<Long> query = getSession().createQuery("select count(t) from Task t", Long.class);
        return Math.toIntExact(query.uniqueResult());
    }

    // Получение задачи по идентификатору
    @Transactional(propagation = Propagation.REQUIRED)
    public Optional<Task> getById(int id) {
        Query<Task> query = getSession().createQuery("select t from Task t where t.id = :ID", Task.class);
        query.setParameter("ID", id);
        return query.uniqueResultOptional();
    }

    // Сохранение или обновление задачи
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveOrUpdate(Task task) {
        getSession().persist(task);
    }

    // Удаление задачи
    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(Task task) {
        getSession().remove(task);
    }

    // Получение текущей сессии Hibernate из SessionFactory
    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }
}
