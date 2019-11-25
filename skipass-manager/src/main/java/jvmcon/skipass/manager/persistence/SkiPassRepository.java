package jvmcon.skipass.manager.persistence;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import jvmcon.skipass.manager.entity.SkiPass;

@ApplicationScoped
@Transactional(rollbackOn = Exception.class)
public class SkiPassRepository {

  @Inject
  EntityManager entityManager;

  public void persist(SkiPass skiPass) {
    this.entityManager.persist(skiPass);
  }

  public List<SkiPass> findAll() {
    return this.entityManager
        .createQuery("select s from SkiPass s", SkiPass.class)
        .getResultList();
  }

  public SkiPass findById(String id) {
    return this.entityManager.find(SkiPass.class, id);
  }
}
