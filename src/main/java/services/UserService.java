package services;

import entities.User;
import org.codehaus.jackson.map.ObjectMapper;
import servlets.responses.CreateUserResponse;
import servlets.responses.DeleteUserResponse;
import servlets.responses.UpdateUserResponse;
import storage.Storage;

import java.io.IOException;
import java.security.PublicKey;
import java.util.UUID;

/**
 * Created by RENT on 2017-03-04.
 */
public class UserService {
    public CreateUserResponse addUser(String userJson) {
        ObjectMapper mapper = new ObjectMapper();
        CreateUserResponse response = new CreateUserResponse();
        try {
            User user = mapper.readValue(userJson, User.class);
            UUID id = UUID.randomUUID();
            user.setId(id);

            Storage.addUser(user);

            response.setStatus("OK");
            response.setId(id.toString());
        } catch (IOException e) {
            response.setError(e.getMessage());
        }

        return response;
    }

    public DeleteUserResponse removeUserById(String id) {
        DeleteUserResponse result = new DeleteUserResponse();
        result.setMessage("User with id: " + id + "Not found");

        if (id != null && !id.isEmpty()) {
            User tempUser = null;
            UUID uuid = UUID.fromString(id);
            for (User user : Storage.getUsers()) {
                if (uuid.equals(user.getId())) {
                    tempUser = user;
                    break;
                }
            }
            Storage.removeUser(tempUser);
            result.setStatus("OK");
            result.setMessage("User with id: " + id + " Was removed");
        }
        return result;
    }

    public UpdateUserResponse updateUser(User user) {
        UpdateUserResponse response = new UpdateUserResponse();

        Storage.updateUser(user);
        response.setStatus("OK");
        response.setMessage("User Updated");

        return response;
    }
}
