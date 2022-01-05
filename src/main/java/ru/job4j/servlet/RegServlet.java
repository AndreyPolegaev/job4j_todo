package ru.job4j.servlet;

import ru.job4j.dao.DaoImpl;
import ru.job4j.entity.User;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

/**
 * Регистрация пользователе.
 * По ссылке http://localhost:8080/todo/reg
 * запрос идет в doGet(), перенаправляем на страницу http://localhost:8080/todo/reg.jsp с формой регистрации
 */
@WebServlet("/reg")
public class RegServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect(req.getContextPath() + "/reg.jsp");
    }

    /**
     * doPost() вызывается из js Ajax function reg().
     * Регистрация нового пользователя, проверка что такого email еще нет в базе
     * 1. Из Ajax получает значения полей name, email, password.
     * 2. По email ищем пользователя в базе. Если такого нет, создаем сессию и сохраняем его в БД, Ajax перенаправляет на index.jsp
     * 3. Если такой пользователь уже есть в БД, потоком вывода resp.getOutputStream() отправляет ответ в Ajax.
     * 4. После того, как сервлет отработал, управление переходит в Ajax блок done.
     *    Сессия: sc.setAttribute("user", user) - атрибут "user" - доступен на всех jsp страницах
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        User userByEmail = DaoImpl.instOf().findUserByEmail(email);
        if (userByEmail == null) {
            User user = new User(name, email, password);
            HttpSession sc = req.getSession();
            sc.setAttribute("user", user);
            DaoImpl.instOf().save(user);
        } else {
            OutputStream writer = resp.getOutputStream();
            writer.write("Пользователь с таким email уже зарегистрирован".getBytes(StandardCharsets.UTF_8));
            writer.flush();
            writer.close();
        }
    }
}
