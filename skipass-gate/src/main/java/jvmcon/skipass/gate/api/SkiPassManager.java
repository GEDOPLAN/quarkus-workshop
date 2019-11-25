package jvmcon.skipass.gate.api;

import java.time.LocalDateTime;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient
@Path("skipass")
public interface SkiPassManager {
  @Path("{id}/validTo")
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public LocalDateTime getValidTo(@PathParam("id") String id);

}
