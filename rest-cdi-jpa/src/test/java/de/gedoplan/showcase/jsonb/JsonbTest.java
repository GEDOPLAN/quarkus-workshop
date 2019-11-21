package de.gedoplan.showcase.jsonb;

import de.gedoplan.showcase.entity.Person;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

import org.junit.jupiter.api.Test;

public class JsonbTest {

  @Test
  void testToJson() {
    Person person = new Person("Wacker", "Willi");

    Jsonb jsonb = JsonbBuilder.create();
    String personJson = jsonb.toJson(person);

    System.out.println(personJson);
  }
}
