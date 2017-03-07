package servlets.responses;

import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * Created by RENT on 2017-03-04.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class CreateUserResponse {
    private String status;
    private String id;
    private String error;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
