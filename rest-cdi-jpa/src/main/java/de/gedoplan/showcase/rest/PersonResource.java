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
import org.apache.commons.logging.LogFactory;

@ApplicationScoped
@Path("person")
public class PersonResource {
  @Inject
  PersonRepository personRepository;

  Log log = LogFactory.getLog(PersonResource.class);

  /*
   * GET all persons.
   *
   * Sample call: curl -i http://localhost:8080/person
   */
  @GET
  @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
  public List<Person> getAll() {
    return this.personRepository.findAll();
  }

  /*
   * GET person by id.
   *
   * Sample call: curl -i http://localhost:8080/person/2
   */
  @GET
  @Path("{id}")
  @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
  public Person getById(@PathParam("id") Integer id) {
    Person person = this.personRepository.findById(id);
    if (person != null) {
      return person;
    }

    throw new NotFoundException();
  }

  /*
   * PUT (overwrite) person by id.
   *
   * Sample call: curl -i -X PUT -H 'content-type: application/json' -d '{"id":2,"firstname":"Donald","name":"Trump"}' http://localhost:8080/person/2
   */
  @PUT
  @Path("{id}")
  @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
  public void update(@PathParam("id") Integer id, Person Person) {
    if (!id.equals(Person.getId())) {
      throw new BadRequestException("id of updated object must be unchanged");
    }

    this.personRepository.merge(Person);
  }

  /*
   * POST (create) person.
   *
   * Sample call: curl -i -X POST -H 'content-type: application/json' -d '{"firstname":"Tick","name":"Duck"}' http://localhost:8080/person -i
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
        .path(Person.getId().toString())
        .build();
    return Response.created(createdUri).build();
  }

  /*
   * DELETE person.
   *
   * Sample call: curl -i -X DELETE http://localhost:8080/person/3
   */
  @DELETE
  @Path("{id}")
  public void delete(@PathParam("id") Integer id) {
    this.personRepository.removeById(id);
  }
}
