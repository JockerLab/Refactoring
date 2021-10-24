package ru.shaldin.sd.refactoring.servlet;

import ru.shaldin.sd.refactoring.database.Database;
import ru.shaldin.sd.refactoring.html.HtmlResponse;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class QueryServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String command = request.getParameter("command");

        try {
            HtmlResponse htmlResponse = new HtmlResponse(response);
            switch (command) {
                case "max":
                    htmlResponse.addToBody("<h1>Product with max price: </h1>\r\n").extractList(Database.getMax()).wrapResponse();
                    break;
                case "min":
                    htmlResponse.addToBody("<h1>Product with min price: </h1>\r\n").extractList(Database.getMin()).wrapResponse();
                    break;
                case "sum":
                    htmlResponse.addToBody("Summary price: \r\n").extractLong(Database.getSum()).wrapResponse();
                    break;
                case "count":
                    htmlResponse.addToBody("Number of products: \r\n").extractLong(Database.getCount()).wrapResponse();
                    break;
                default:
                    htmlResponse.addToBody("Unknown command: " + command);
                    break;
            }
            htmlResponse.write().setContentType().setStatus();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
