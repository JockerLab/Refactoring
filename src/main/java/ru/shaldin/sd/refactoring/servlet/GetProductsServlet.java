package ru.shaldin.sd.refactoring.servlet;

import ru.shaldin.sd.refactoring.database.Database;
import ru.shaldin.sd.refactoring.html.HtmlResponse;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GetProductsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            HtmlResponse htmlResponse = new HtmlResponse(response);
            htmlResponse.extractList(Database.selectAll()).wrapResponse().write().setStatus().setContentType();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
