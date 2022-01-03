package ru.job4j.servlet;

import ru.job4j.dao.DaoImpl;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Обновление задачи
 */

@WebServlet("/UpdateStatus")
public class DoneServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DaoImpl.instOf().update(Integer.parseInt(req.getParameter("id")));
        resp.sendRedirect(req.getContextPath() + "/index.jsp");
    }
}
