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

/** Авторазация пользователя и создание его сесси */

@WebServlet("/auth")
public class AuthServlet extends HttpServlet {

    /**
     *  Вызывается по ссылке http://localhost:8080/todo/auth
     *  doGet() перенаправляет на jsp страницу с формой авторизации
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect(req.getContextPath() + "/auth.jsp");
    }

    /**
     *  вызывается через js Ajax - function auth().
     *  Если пользователь с таким email найден в БД и его пароль совпадет с паролем из формы ввода делаем сессию
     *  с этим пользователем. Иначе отправляем  сообщение в Ajax email/пароль не найден.
     *  Дальнейшее управление производится в Ajax
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        User userByEmail = DaoImpl.instOf().findUserByEmail(email);
        if (userByEmail != null && userByEmail.getPassword().equals(password)) {
            HttpSession sc = req.getSession();
            sc.setAttribute("user", userByEmail);
        } else {
            OutputStream writer = resp.getOutputStream();
            writer.write("email и/или пароль пользователя не найдены".getBytes(StandardCharsets.UTF_8));
            writer.flush();
            writer.close();
        }
    }
}
