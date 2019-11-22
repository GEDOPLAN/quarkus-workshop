package de.gedoplan.showcase.restcountries.eu;

public class Country {
  private String alpha2Code;
  private String name;
  private String capital;

  public String getAlpha2Code() {
    return this.alpha2Code;
  }

  public void setAlpha2Code(String alpha2Code) {
    this.alpha2Code = alpha2Code;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCapital() {
    return this.capital;
  }

  public void setCapital(String capital) {
    this.capital = capital;
  }

  @Override
  public String toString() {
    return "Country [alpha2Code=" + this.alpha2Code + ", name=" + this.name + ", capital=" + this.capital + "]";
  }

}
