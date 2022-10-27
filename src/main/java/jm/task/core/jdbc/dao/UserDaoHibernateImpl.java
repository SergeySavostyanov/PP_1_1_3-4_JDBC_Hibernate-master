package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = null;
        //этапы выполнения запроса

        try {
            transaction = session.beginTransaction();
            Query query = session.createSQLQuery("CREATE TABLE IF NOT EXISTS `mydbstest`.`users` (\n" +
                    "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
                    "  `name` VARCHAR(45) NOT NULL,\n" +
                    "  `last_name` VARCHAR(45) NOT NULL,\n" +
                    "  `age` INT NULL,\n" +
                    "  PRIMARY KEY (`id`));");
            query.executeUpdate();
            transaction.commit();
            
        } catch (Exception e) {
            transaction.rollback();
            System.out.println("Ошибка при создании таблицы users");
        } finally {
            session.close();
        }


    }

    @Override
    public void dropUsersTable() {
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            //этапы выполнения запроса
            Query query = session.createSQLQuery("DROP TABLE IF EXISTS users");

            query.executeUpdate();
            transaction.commit();


        } catch (HibernateException e) {
            transaction.rollback();
            System.out.println("Ошибка при удалении таблицы users  из БД");
        } finally {
            session.close();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction=session.beginTransaction();  //отрытие транзации
            User user = new User(name, lastName, (byte) age);
            session.save(user);                 // фиксация изменений
            session.getTransaction().commit();  // сохранение изменений(транзакция завершается)
        } catch (HibernateException e) {
            transaction.rollback();
            System.out.println("Ошибка при добавлении пользователя в таблицу");
        } finally {
            session.close();                    //закрытие сессию
        }


    }

    @Override
    public void removeUserById(long id) {
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction =session.beginTransaction();                //отрытие транзации
            session.delete(session.get(User.class, id));// фиксация изменений
            transaction.commit();         // сохранение изменений(транзакция завершается)
        } catch (HibernateException e) {
            transaction.rollback();
            System.out.println("Не удалось удалить пользователь с id=" + id);
        } finally {
            session.close();                   //закрытие сессию
        }

    }

    @Override
    public List<User> getAllUsers() {
        Session session = Util.getSessionFactory().openSession();
        List<User> users = null;
        try {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery cq = cb.createQuery(User.class);//обьект с помощбю которого будем формировать запрос
            Root<User> root = cq.from(User.class);//выбор основной таблицы (from в sql)

            cq.select(root);//получение ввсех обьектов
            //этапы выполнения запроса
            Query query = session.createQuery(cq);
            users = query.getResultList();
        } catch (Exception e) {
            System.out.println("Ошибка при создании списка");
        }finally {
            session.close();
        }


        return users;
    }

    @Override
    public void cleanUsersTable() {
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction =session.beginTransaction();
            Query query=session.createSQLQuery("TRUNCATE TABLE users");
            query.executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            transaction.rollback();
            System.out.println("Не удалось очистить таблицу");
        }
        finally {
            session.close();
        }


    }
}
