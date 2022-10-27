package jm.task.core.jdbc.service;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;

import java.util.List;

public class UserServiceImpl implements UserService {
    UserDao userDao = new UserDaoHibernateImpl();
    public void createUsersTable() {

        userDao.createUsersTable();
        System.out.println("Таблица users создана в БД");
    }

    public void dropUsersTable() {

        userDao.dropUsersTable();
        System.out.println("Таблица users удалена из БД");

    }

    public void saveUser(String name, String lastName, byte age) {

        userDao.saveUser(name,lastName,age);
        System.out.printf("Пользователь %s %s %d добавлен в таблицу users\n",name,lastName,age);
    }

    public void removeUserById(long id) {

        userDao.removeUserById(id);
        System.out.printf("Пользователь с id = %s удален \n",id);

    }

    public List<User> getAllUsers() {

        System.out.println("Создан список");
        return userDao.getAllUsers();

    }

    public void cleanUsersTable() {

        userDao.cleanUsersTable();
        System.out.println("Таблица users очищена");

    }
}
