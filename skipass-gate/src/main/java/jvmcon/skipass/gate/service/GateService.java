package jvmcon.skipass.gate.service;

import java.time.LocalDateTime;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import jvmcon.skipass.gate.api.SkiPassManager;

@ApplicationScoped
public class GateService {

  @Inject
  @RestClient
  SkiPassManager skiPassManager;

  @Fallback(fallbackMethod = "returnTrue")
  public boolean isValid(String skiPassId) {
    LocalDateTime validTo = fetch(skiPassId);
    // LocalDateTime validTo = this.cache.computeIfAbsent(skiPassId, this::fetch);
    return validTo != null && validTo.isAfter(LocalDateTime.now());
  }

  private LocalDateTime fetch(String skiPassId) {
    return this.skiPassManager.getValidTo(skiPassId);
  }

  @SuppressWarnings("unused")
  private boolean returnTrue(String skiPassId) {
    System.out.println("Using default value!!!!");
    return true;
  }
}
