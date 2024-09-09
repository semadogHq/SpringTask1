package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    /**
     *  Создание таблицы User(ов)
     *  Добавление 4 User(ов) в таблицу с данными на свой выбор. После каждого добавления должен быть вывод в
     *  консоль ( User с именем – name добавлен в базу данных )
     *  Получение всех User из базы и вывод в консоль ( должен быть переопределен toString в классе User)
     *  Очистка таблицы User(ов)
     *  Удаление таблицы
     */
    private final static UserService userService = new UserServiceImpl();
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        userService.createUsersTable();

        userService.saveUser("Vova", "Putin", (byte)70);
        userService.saveUser("Vova", "Zelenski", (byte)50);
        userService.saveUser("Aleksei", "Mishustin", (byte)60);
        userService.saveUser("Djanaluiji", "Donnaruma", (byte)25);

        userService.removeUserById(2);

        userService.getAllUsers();

        userService.cleanUsersTable();

        userService.dropUsersTable();


    }
}
