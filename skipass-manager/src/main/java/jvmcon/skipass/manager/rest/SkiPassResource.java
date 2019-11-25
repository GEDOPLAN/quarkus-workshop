package jvmcon.skipass.manager.rest;

import java.time.LocalDateTime;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import jvmcon.skipass.manager.entity.SkiPass;

@Path("skipass")
public class SkiPassResource {

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

    return skiPass.getId();
  }
}
