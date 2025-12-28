package it.unipa.wsda.heartbeat_service.a_PresentationLayer;

import com.google.gson.Gson;
import it.unipa.wsda.heartbeat_service.b_BusinessLogicLayer.DistributoreService;
import it.unipa.wsda.heartbeat_service.d_DatabaseLayer.Distributore;
import it.unipa.wsda.heartbeat_service.d_DatabaseLayer.StatiDistributori;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "GestioneDistributoriGuasti" , urlPatterns = {"/distributori-guasti"})
public class ServletDistributoriGuasti extends HttpServlet {


    private DistributoreService service;
    private Gson gson; // libreria google che converte oggetti java <-> json (bidirezionale)

    @Override
    public void init() throws ServletException {
        service = new DistributoreService();
        gson = new Gson();
    }

    private long ultimoControlloGuasti = 0;
    private static final long INTERVALLO_CONTROLLO = 30 * 1000; //30 secondi

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            if (System.currentTimeMillis() - ultimoControlloGuasti > INTERVALLO_CONTROLLO) {
                service.aggiornaStatoDistributoriGuasti();
                ultimoControlloGuasti = System.currentTimeMillis();
            }

            List<Distributore> lista_guasti = service.allDistributori()
                    .stream()
                    .filter(x -> x.getStatus() == StatiDistributori.GUASTO)
                    .toList();

            resp.setContentType("application/json");
            resp.getWriter().write(gson.toJson(lista_guasti));

        } catch (SQLException e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}