package jvmcon.skipass.manager.rest;

import java.time.LocalDateTime;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import jvmcon.skipass.manager.entity.SkiPass;
import jvmcon.skipass.manager.persistence.SkiPassRepository;

@Path("skipass")
public class SkiPassResource {

  @Inject
  SkiPassRepository skiPassRepository;

  @POST
  @Produces(MediaType.TEXT_PLAIN)
  public String create(@QueryParam("validTo") LocalDateTime validTo, @QueryParam("validFrom") LocalDateTime validFrom) {
    if (validFrom == null) {
      validFrom = LocalDateTime.now();
    }

    if (validTo == null) {
      throw new BadRequestException("validTo must be specified");
    }

    if (validTo.isBefore(validFrom)) {
      throw new BadRequestException("validFrom must be after validTo");
    }

    SkiPass skiPass = new SkiPass(validFrom, validTo);
    this.skiPassRepository.persist(skiPass);
    return skiPass.getId();
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public List<SkiPass> getAll() {
    return this.skiPassRepository.findAll();
  }

  @GET
  @Path("{id}/validTo")
  @Produces(MediaType.APPLICATION_JSON)
  public LocalDateTime getValidTo(@PathParam("id") String id) {
    SkiPass skiPass = this.skiPassRepository.findById(id);
    LocalDateTime now = LocalDateTime.now();
    return skiPass != null && skiPass.getValidFrom().isBefore(now) && skiPass.getValidTo().isAfter(now) && !skiPass.isBlocked()
        ? skiPass.getValidTo()
        : LocalDateTime.MIN;

  }
}
