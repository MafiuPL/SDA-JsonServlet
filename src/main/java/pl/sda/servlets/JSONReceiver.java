package pl.sda.servlets;

import entities.User;
import org.codehaus.jackson.map.ObjectMapper;
import services.UserService;
import servlets.responses.CreateUserResponse;
import servlets.responses.DeleteUserResponse;
import servlets.responses.UpdateUserResponse;
import storage.Storage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by RENT on 2017-03-04.
 */
public class JSONReceiver extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BufferedReader reader = req.getReader();
        StringBuffer json = new StringBuffer();

        String line = null;
        while ((line = reader.readLine()) != null) {
            json.append(line);
        }
        UserService userService = new UserService();
        CreateUserResponse response = userService.addUser(json.toString());

        ObjectMapper mapper = new ObjectMapper();
        resp.getWriter().write(mapper.writeValueAsString(response));

        System.out.println("");
    }

    public User getUserByUUID(String id) {
        User result = null;
        if (id != null && !id.isEmpty()) {
            UUID uuid = UUID.fromString(id);
            for (User user : Storage.getUsers()) {
                if (uuid.equals(user.getId())) {
                    result = user;
                    break;
                }
            }
        }
        return result;
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId = req.getParameter("id");
        UserService userService = new UserService();
        User user = getUserByUUID(userId);

        ObjectMapper mapper = new ObjectMapper();

        resp.getWriter().write(mapper.writeValueAsString(user));
        resp.setContentType("application/json");
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        UserService userService = new UserService();
        DeleteUserResponse result = userService.removeUserById(id);

        ObjectMapper mapper = new ObjectMapper();

        resp.getWriter().write(mapper.writeValueAsString(result));
        resp.setContentType("application/json");

    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BufferedReader reader = req.getReader();
        StringBuffer json = new StringBuffer();

        String line = null;
        while ((line = reader.readLine()) != null) {
            json.append(line);
        }

        ObjectMapper mapper = new ObjectMapper();
        User user = mapper.readValue(json.toString(), User.class);
        UserService userService = new UserService();
        UpdateUserResponse response = userService.updateUser(user);

        resp.getWriter().write(mapper.writeValueAsString(response));
    }
}
