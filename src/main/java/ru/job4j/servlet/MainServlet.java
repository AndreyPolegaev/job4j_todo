package ru.job4j.servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;


@WebServlet("/mainServlet")
public class MainServlet extends HttpServlet {

    private static final Gson GSON = new GsonBuilder().create();

    /***
     * сохранение обьекта в БД и передача его в виде json в ajax done
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain");
        resp.setCharacterEncoding("UTF-8");
        String desc = req.getParameter("desc");
        Item item = new Item(desc, Timestamp.valueOf(LocalDateTime.now()), false);
        DaoImpl.instOf().save(item);

        OutputStream output = resp.getOutputStream();
        String itemJson = GSON.toJson(item);
        output.write(itemJson.getBytes(StandardCharsets.UTF_8));
        output.flush();
        output.close();
    }

    /***
     * получение всех записей из БД.
     * вызывается через AJAX при загрузке страницы
     * choiceSelectData - chekBox. Если true показть все задачи, если false - только которые не завершены
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        boolean choiceSelectData = Boolean.parseBoolean(req.getParameter("index"));
        List<Item> itemsList = choiceSelectData ? DaoImpl.instOf().findAll() : DaoImpl.instOf().findUncompleted();
        itemsList.sort(Comparator.comparing(Item::getCreated));
        resp.setContentType("application/json; charset=utf-8");
        OutputStream output = resp.getOutputStream();
        String json = GSON.toJson(itemsList);
        output.write(json.getBytes(StandardCharsets.UTF_8));
        output.flush();
        output.close();
    }
}
