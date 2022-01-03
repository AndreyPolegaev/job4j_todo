package ru.job4j.servlet;

import ru.job4j.dao.DaoImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * удаление всех задач из БД
 */

@WebServlet("/deleteAll")
public class DeleteAllServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DaoImpl.instOf().deleteAll();
        resp.sendRedirect(req.getContextPath() + "/index.jsp");
    }
}
