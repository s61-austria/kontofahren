package rest;

import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("users")
@Stateless
public class UserResource {

    @GET
    public void getThings(){

    }

}
