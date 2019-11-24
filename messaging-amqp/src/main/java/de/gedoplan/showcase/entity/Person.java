package de.gedoplan.showcase.entity;

import java.io.Serializable;

public class Person implements Serializable {

  private String name;
  private String firstname;

  public Person(String name, String firstname) {
    this.name = name;
    this.firstname = firstname;
  }

  @Override
  public String toString() {
    return "Person [name=" + this.name + ", firstname=" + this.firstname + "]";
  }

}
