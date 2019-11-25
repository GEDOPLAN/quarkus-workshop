package jvmcon.skipass.gate.service;

import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import jvmcon.skipass.gate.api.SkiPassManager;

@ApplicationScoped
public class GateService {

  ConcurrentMap<String, LocalDateTime> cache = new ConcurrentHashMap<>();

  @Inject
  @RestClient
  SkiPassManager skiPassManager;

  @Fallback(fallbackMethod = "returnTrue")
  public boolean isValid(String skiPassId) {
    // LocalDateTime validTo = fetch(skiPassId);
    LocalDateTime validTo = this.cache.computeIfAbsent(skiPassId, this::fetch);
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

  @Incoming("skipassInvalidation")
  public void invalidate(String skiPassId) {
    this.cache.remove(skiPassId);
  }
}
