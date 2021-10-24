package ru.shaldin.sd.refactoring.servlet;

import ru.shaldin.sd.refactoring.html.HtmlResponse;

import javax.servlet.http.HttpServlet;
import java.io.IOException;
import java.net.http.HttpResponse;

public class AbstractServlet extends HttpServlet {
    protected final HtmlResponse htmlResponse = new HtmlResponse();

    protected void sendResponse() throws IOException {
        htmlResponse.write().setStatus().setContentType();
    }
}
