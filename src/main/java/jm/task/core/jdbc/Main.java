package jm.task.core.jdbc;


import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

public class Main {
    public static void main(String[] args) {
        Util.getConnection();
        UserService impl = new UserServiceImpl();
        impl.createUsersTable();

        impl.saveUser("Джо", "Байден", (byte) 78);
        impl.saveUser("Трамп", "Дональд", (byte) 74);
        impl.saveUser("Барак", "Обама", (byte) 59);
        impl.saveUser("Джордж", "Буш", (byte) 74);

        impl.removeUserById(2);

        impl.getAllUsers();

        impl.cleanUsersTable();

        impl.dropUsersTable();





        // реализуйте алгоритм здесь
    }
}
