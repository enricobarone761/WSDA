package it.unipa.wsda.heartbeat_service.a_PresentationLayer;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import it.unipa.wsda.heartbeat_service.b_BusinessLogicLayer.DistributorService;
import it.unipa.wsda.heartbeat_service.d_DatabaseLayer.Distributor;
import it.unipa.wsda.heartbeat_service.d_DatabaseLayer.DistributorStatus;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "DistributorServlet", urlPatterns = {"/distributors", "/distributors/status"})
public class DistributorServlet extends HttpServlet {

    private DistributorService service;
    private Gson gson;

    @Override
    public void init() throws ServletException {
        service = new DistributorService();
        gson = new Gson();
    }

    // GET: List of distributors
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<Distributor> list = service.getAllDistributors();
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write(gson.toJson(list));
        } catch (SQLException e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    // POST: Add distributor
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            BufferedReader reader = req.getReader();
            Distributor d = gson.fromJson(reader, Distributor.class);
            service.addDistributor(d);
            resp.setStatus(HttpServletResponse.SC_CREATED);
        } catch (SQLException e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    // PUT: Update status
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        if (path.endsWith("/status")) {
            try {
                BufferedReader reader = req.getReader();
                JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();
                String id = json.get("id").getAsString();
                String statusStr = json.get("status").getAsString();
                DistributorStatus status = DistributorStatus.valueOf(statusStr);

                service.updateStatus(id, status);
                resp.setStatus(HttpServletResponse.SC_OK);
            } catch (IllegalArgumentException e) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid status");
            } catch (SQLException e) {
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
            }
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    // DELETE: Remove distributor
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        if (id == null || id.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing id parameter");
            return;
        }
        try {
            service.removeDistributor(id);
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (SQLException e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}

