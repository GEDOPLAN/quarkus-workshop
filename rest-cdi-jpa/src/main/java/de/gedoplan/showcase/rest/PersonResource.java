package de.gedoplan.showcase.rest;

import de.gedoplan.showcase.entity.Person;
import de.gedoplan.showcase.persistence.PersonRepository;

import java.net.URI;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.logging.Log;

@ApplicationScoped
@Path(PersonResource.PATH)
public class PersonResource {
  public static final String PATH = "person";
  public static final String ID_NAME = "id";
  public static final String ID_TEMPLATE = "{" + ID_NAME + "}";

  @Inject
  PersonRepository personRepository;

  @Inject
  Log log;

  /*
   * GET all persons.
   *
   * Sample call: curl http://localhost:8080/person
   */
  @GET
  @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
  public List<Person> getAll() {
    return this.personRepository.findAll();
  }

  /*
   * GET person by id.
   *
   * Sample call: curl http://localhost:8080/person/2
   */
  @GET
  @Path(ID_TEMPLATE)
  @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
  public Person getById(@PathParam(ID_NAME) Integer id) {
    Person person = this.personRepository.findById(id);
    if (person != null) {
      return person;
    }

    throw new NotFoundException();
  }

  /*
   * PUT (overwrite) person by id.
   *
   * Sample call: curl -X PUT -H 'content-type: application/json' -d '{"id":2,"firstname":"Donald","name":"Trump"}' http://localhost:8080/person/2
   */
  @PUT
  @Path(ID_TEMPLATE)
  @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
  public void update(@PathParam(ID_NAME) Integer id, Person Person) {
    if (!id.equals(Person.getId())) {
      throw new BadRequestException("id of updated object must be unchanged");
    }

    this.personRepository.merge(Person);
  }

  /*
   * POST (create) person.
   *
   * Sample call: curl -X POST -H 'content-type: application/json' -d '{"firstname":"Tick","name":"Duck"}' http://localhost:8080/person -i
   */
  @POST
  @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
  public Response create(Person Person, @Context UriInfo uriInfo) {
    if (Person.getId() != null) {
      throw new BadRequestException("id of new entry must not be set");
    }

    this.personRepository.persist(Person);

    URI createdUri = uriInfo
        .getAbsolutePathBuilder()
        .path(ID_TEMPLATE)
        .resolveTemplate(ID_NAME, Person.getId())
        .build();
    return Response.created(createdUri).build();
  }

  /*
   * DELETE person.
   *
   * Sample call: curl -X DELETE http://localhost:8080/person/4
   */
  @DELETE
  @Path(ID_TEMPLATE)
  public void delete(@PathParam(ID_NAME) Integer id) {
    this.personRepository.removeById(id);
  }
}
