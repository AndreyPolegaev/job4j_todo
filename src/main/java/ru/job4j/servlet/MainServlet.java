package ru.job4j.servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.log4j.Log4j2;
import ru.job4j.dao.DaoImpl;
import ru.job4j.entity.Item;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Comparator;
import java.util.List;


@WebServlet("/mainServlet")
@Log4j2
public class MainServlet extends HttpServlet {

    private static final Gson GSON = new GsonBuilder().create();

    /***
     * получение всех записей из БД.
     * вызывается через AJAX при загрузке страницы
     * choiceSelectData - chekBox. Если true показть все задачи, если false - только которые не завершены
     * $(document).ready(function ()
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        boolean choiceSelectData = Boolean.parseBoolean(req.getParameter("index"));
        List<Item> itemsList = choiceSelectData ? DaoImpl.instOf().findAll() : DaoImpl.instOf().findUncompleted();
        itemsList.sort(Comparator.comparing(Item::getCreated));
        resp.setContentType("application/json; charset=utf-8");
        req.setAttribute("user", req.getSession().getAttribute("user"));
        OutputStream output = resp.getOutputStream();
        String json = GSON.toJson(itemsList);
        output.write(json.getBytes(StandardCharsets.UTF_8));
        output.flush();
        output.close();
    }

    /***
     * сохранение ЗАДАЧИ в БД. Ajax function uploadInDB()
     * Получение пользователя из текущей сессии
     * Сохранение заявки Item с привязкой пользователя
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain");
        resp.setCharacterEncoding("UTF-8");
        String desc = req.getParameter("desc");
    }
}
