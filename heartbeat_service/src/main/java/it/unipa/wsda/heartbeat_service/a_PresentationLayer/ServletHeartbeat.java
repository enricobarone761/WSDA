package it.unipa.wsda.heartbeat_service.a_PresentationLayer;

import it.unipa.wsda.heartbeat_service.b_BusinessLogicLayer.DistributoreService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "GestioneHeartbeat", urlPatterns = "/heartbeat")
public class ServletHeartbeat extends HttpServlet {

    private DistributoreService service;

    @Override
    public void init() throws ServletException {
        service = new DistributoreService();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            service.aggiornaUltimoHeartbeat(req.getParameter("id"));
            resp.setStatus(HttpServletResponse.SC_OK); //codice 200 OK
        } catch (SQLException e) {
            // Se l'eccezione contiene "NotFound" significa che l'id non esiste nel DB
            if (e.getMessage() != null && e.getMessage().contains("NotFound:")) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
            } else {
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
            }
        }
    }



}