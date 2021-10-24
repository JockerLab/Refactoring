package ru.shaldin.sd.refactoring.servlet;

import ru.shaldin.sd.refactoring.database.Database;
import ru.shaldin.sd.refactoring.html.HtmlResponse;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddProductServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter("name");
        long price = Long.parseLong(request.getParameter("price"));

        try {
            Database.insert(name, price);
            HtmlResponse htmlResponse = new HtmlResponse(response);
            htmlResponse.addToBody("OK").write().setStatus().setContentType();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
