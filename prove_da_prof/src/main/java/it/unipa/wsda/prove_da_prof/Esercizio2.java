package it.unipa.wsda.prove_da_prof;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

@WebServlet(name = "Esercizio2", value = "/esercizio2")
public class Esercizio2 extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        // Hello
        int n = Integer.parseInt(request.getParameter("n"));
        int k = Integer.parseInt(request.getParameter("k"));
        Random rand = new Random();
        PrintWriter out = response.getWriter();
        out.println("<html><body>");

        out.println("<ul>");
        for (int i = 0; i < n; i++) {
            int num = rand.nextInt(k) + 1; // genera tra 1 e k
            out.println("<li>" + num + "</li>");
        }
        out.println("</ul>");
        out.println("</body></html>");
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doGet(request, response);
    }

    public void destroy() {
    }
}