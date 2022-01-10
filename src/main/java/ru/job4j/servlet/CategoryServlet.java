package ru.job4j.servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.job4j.dao.DaoImpl;
import ru.job4j.entity.Category;
import ru.job4j.entity.Item;
import ru.job4j.entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@WebServlet("/category")
public class CategoryServlet extends HttpServlet {

    private static final Gson GSON = new GsonBuilder().create();

    /** достаем  все категории из БД отправляет в Ajax json  */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Category> categories = DaoImpl.instOf().getAllCategories();
        resp.setContentType("application/json; charset=utf-8");
        OutputStream output = resp.getOutputStream();
        String json = GSON.toJson(categories);
        output.write(json.getBytes(StandardCharsets.UTF_8));
        output.flush();
        output.close();
    }

    /** приходит описание задачи desc, так же id категорий задач "cIds". Присвоить Item категории в БД*/
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String desc = req.getParameter("desc");
        String[] ids = req.getParameterValues("cIds");
        resp.setContentType("text/plain");
        resp.setCharacterEncoding("UTF-8");
        User currentUser = (User) req.getSession().getAttribute("user");
        Item item = new Item(desc, Timestamp.valueOf(LocalDateTime.now()), false, currentUser);
        DaoImpl.instOf().save(item, ids);
        resp.sendRedirect(req.getContextPath() + "/index.jsp");
    }
}


