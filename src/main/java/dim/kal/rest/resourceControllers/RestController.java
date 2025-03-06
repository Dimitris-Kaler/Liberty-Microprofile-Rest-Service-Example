package dim.kal.rest.resourceControllers;

import dim.kal.rest.models.Greeting;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

import static jakarta.ws.rs.client.Entity.entity;

@Path("/")
public class RestController {

    public RestController() {
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response helloWorld(){
        return Response.ok(new Greeting("Hello World!")).build();
    }



    @GET
    @Path("greet/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response pathParamsGreeting(@PathParam("name") String name){
        if(name == null){
            return Response.status(Response.Status.BAD_REQUEST).entity(new Greeting("Name or age can't be null")).build();
        }

        return Response.ok(new Greeting("Hello "+name+"!")).build();

    }

    @GET
    @Path("/greet")
    @Produces(MediaType.APPLICATION_JSON)
    public Response queryParamsGreeting(@QueryParam("name")String name,@QueryParam("age")Integer age){
        if(name == null || age == null){
            return Response.status(Response.Status.BAD_REQUEST).entity(new Greeting("Name or age can't be null")).build();
        }
        return Response.ok(new Greeting("Hello my name is: "+name+" and my age is: "+age+" old!!")).build();
    }

    @POST
    @Path("/greet")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response bodyParams(Greeting input){
        if(input.getName() == null || input.getAge() == null){
            return Response.status(Response.Status.BAD_REQUEST).entity(new Greeting("Name or age can't be null")).build();
        }

        return Response.ok(new Greeting("Hello my name is: "+input.getName()+" and my age is: "+input.getAge()+" old!!")).build();
    }
}
