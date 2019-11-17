package jvmcon;

import java.time.LocalDateTime;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/hello")
public class GreetingResource {

  /*
   * Simple GET-Methode.
   * 
   * @Produces bestimmt Serialisierung des Returnwerts im Response-Body.
   */
  @GET
  @Produces(MediaType.TEXT_PLAIN)
  public String hello() {
    return "hello";
  }

  /*
   * Zusätzlicher Pfad-Anteil (ergänzend zum Pfad der Klasse).
   * Im Pfad enthaltene Templates {identifier} erlauben Übernahme von URL-Anteilen in Methodenparameter.
   */
  @Path("{name}")
  @GET
  @Produces(MediaType.TEXT_PLAIN)
  public String hello(@PathParam("name") String name) {
    return "hello " + name;
  }

  /*
   * Die andere Richtung: PUT statt GET.
   * 
   * @Consumes bestimmt Deserialisierung des Methodenparameters aus dem Request-Body.
   * Die Methode darf weitere Parameter haben, wenn diese mit @PathParam, @QueryParam etc. annotiert sind.
   */
  @PUT
  @Consumes(MediaType.TEXT_PLAIN)
  public void put(String value) {
    System.out.println("put(" + value + ")");
  }

  /*
   * POST wird i. A. für Neuanlage von Einträgen etc. genutzt.
   * Funktioniert ansonsten wie PUT.
   * Achtung, Schwierigkeit: Für Parameter des Typs LocalDateTime wird ein Parameter-Converter benötigt
   * (s. LocalDateTimeParamConverterProvider).
   */
  @POST
  @Produces(MediaType.TEXT_PLAIN)
  public String post(@QueryParam("timestamp") LocalDateTime timestamp) {
    System.out.println("post(" + timestamp + ")");
    return "It was " + timestamp;
  }
}