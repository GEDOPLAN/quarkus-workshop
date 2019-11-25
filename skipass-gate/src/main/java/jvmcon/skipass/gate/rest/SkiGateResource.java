package jvmcon.skipass.gate.rest;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import jvmcon.skipass.gate.service.GateService;

@Path("gate")
public class SkiGateResource {

  @Inject
  GateService gateService;

  @Path("{id}")
  @GET
  @Produces(MediaType.TEXT_PLAIN)
  public String get(@PathParam("id") String id) {
    return this.gateService.isValid(id) ? "GREEN" : "RED";
  }
}
