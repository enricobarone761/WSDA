package it.unipa.wsda.prove_da_prof;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "Registrazione", value = "/registrazione")
public class Registrazione extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        // Simula verifica utente già presente nel DB
        if (Math.random() < 0.5) {
            out.println("{\"codice\":-1}");
            return;
        } else {
            String comb=request.getParameter("nome")+" "+request.getParameter("cognome")+" "+request.getParameter("email");
            out.println("{\"codice\":" +  Math.abs(comb.hashCode()) + "}");
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doGet(request, response);
    }

    public void destroy() {
    }
}