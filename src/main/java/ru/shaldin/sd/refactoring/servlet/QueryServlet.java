package ru.shaldin.sd.refactoring.servlet;

import ru.shaldin.sd.refactoring.Product;
import ru.shaldin.sd.refactoring.database.Database;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

public class QueryServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String command = request.getParameter("command");

        try {
            switch (command) {
                case "max":
                    List<Product> data = Database.getMax();
                    response.getWriter().println("<html><body>");
                    response.getWriter().println("<h1>Product with max price: </h1>");
                    for (Product product : data) {
                        response.getWriter().println(product.getName() + "\t" + product.getPrice() + "</br>");
                    }
                    response.getWriter().println("</body></html>");
                    break;
                case "min":
                    List<Product> data1 = Database.getMin();
                    response.getWriter().println("<html><body>");
                    response.getWriter().println("<h1>Product with min price: </h1>");
                    for (Product product : data1) {
                        response.getWriter().println(product.getName() + "\t" + product.getPrice() + "</br>");
                    }
                    response.getWriter().println("</body></html>");
                    break;
                case "sum":
                    Optional<Long> sum = Database.getSum();
                    response.getWriter().println("<html><body>");
                    response.getWriter().println("Summary price: ");
                    if (sum.isPresent()) {
                        response.getWriter().println(sum.get());
                    }
                    response.getWriter().println("</body></html>");
                    break;
                case "count":
                    Optional<Long> count = Database.getCount();
                    response.getWriter().println("<html><body>");
                    response.getWriter().println("Number of products: ");
                    if (count.isPresent()) {
                        response.getWriter().println(count.get());
                    }
                    response.getWriter().println("</body></html>");
                    break;
                default:
                    response.getWriter().println("Unknown command: " + command);
                    break;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }

}
