package de.gedoplan.showcase.persistence;

import de.gedoplan.showcase.entity.Person;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@ApplicationScoped
@Transactional(rollbackOn = Exception.class)
public class PersonRepository {

  @Inject
  EntityManager entityManager;

  public long countAll() {
    return this.entityManager
        .createQuery("select count(x) from Person x", Long.class)
        .getSingleResult();
  }

  public Person findById(Integer id) {
    return this.entityManager.find(Person.class, id);
  }

  public List<Person> findAll() {
    return this.entityManager
        .createQuery("select x from Person x", Person.class)
        .getResultList();
  }

  public Person merge(Person entity) {
    return this.entityManager.merge(entity);
  }

  public void persist(Person entity) {
    this.entityManager.persist(entity);
  }

  public boolean removeById(Integer id) {
    Person person = this.entityManager.find(Person.class, id);
    if (person != null) {
      this.entityManager.remove(person);
      return true;
    }

    return false;
  }
}
