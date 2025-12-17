/*
package it.unipa.wsda.heartbeat_service.a_PresentationLayer;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import it.unipa.wsda.heartbeat_service.b_BusinessLogicLayer.kjsahdkjashd;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;

@WebServlet(name = "HeartbeatServlet", urlPatterns = {"/heartbeat"})
public class HeartbeatServlet extends HttpServlet {

    private kjsahdkjashd service;

    @Override
    public void init() throws ServletException {
        service = new kjsahdkjashd();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            BufferedReader reader = req.getReader();
            JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();
            String id = json.get("id").getAsString();

            service.receiveHeartbeat(id);
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}

*/
