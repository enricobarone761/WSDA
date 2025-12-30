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

@WebServlet(name = "GestioneDistributori" , urlPatterns = {"/distributori", "/distributori/status"})
public class ServletDistributore extends HttpServlet {


    private DistributoreService service;
    private Gson gson; // libreria google che converte oggetti java <-> json (bidirezionale)

    @Override
    public void init() throws ServletException {
        service = new DistributoreService();
        gson = new Gson();
    }

    private long ultimoControlloGuasti = 0;
    private static final long INTERVALLO_CONTROLLO = 30 * 1000; //30 secondi


    //lista tutti i distributori
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //righe necessarie per mitigare il fastidioso CORS
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");

        try {
            if (System.currentTimeMillis() - ultimoControlloGuasti > INTERVALLO_CONTROLLO) {
                service.aggiornaStatoDistributoriGuasti();
                ultimoControlloGuasti = System.currentTimeMillis();
            }

            List<Distributore> list = service.allDistributori();

            resp.setContentType("application/json");
            resp.getWriter().write(gson.toJson(list));

        } catch (SQLException e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    //Aggiunta distributore
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            var richiesta = req.getReader();
            service.aggiungiDistributore( gson.fromJson(richiesta, Distributore.class) );
            resp.setStatus(HttpServletResponse.SC_CREATED); //codice 201 successo
        }catch(SQLException e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage()); //codice 500 errore del server
        }

    }

    //Rimozione distributore
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            service.rimuoviDistributore(req.getParameter("id"));
            resp.setStatus(HttpServletResponse.SC_OK);
        }catch(SQLException e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());

        }

    }

    //Aggiornamento stato distributore
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getServletPath().endsWith("/status")) {
            try {
                service.aggiornaStato(req.getParameter("id") , StatiDistributori.valueOf(req.getParameter("stato")));
                resp.setStatus(HttpServletResponse.SC_OK);
                System.out.println("Cambio stato ricevuto "+req.getParameter("id"));
            }catch(SQLException e) {
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());

            }
        }


    }
}
