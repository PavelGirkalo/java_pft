package ru.stqa.pft.mantis.model;

public class User {
  String name;
  String email;

  public User(String name, String email) {
    this.name = name;
    this.email = email;
  }

  public String getName() {
    return name;
  }

  public User withName(String name) {
    this.name = name;
    return this;
  }

  public String getEmail() {
    return email;
  }

  public User withEmail(String email) {
    this.email = email;
    return this;
  }
}
