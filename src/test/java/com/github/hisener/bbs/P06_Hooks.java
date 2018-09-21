package com.github.hisener.bbs;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Hooks;
import reactor.core.publisher.Mono;

class P06_Hooks extends ReactiveTestCase {

  @BeforeAll
  static void beforeAll() {
    Hooks.onOperatorDebug();

    Hooks.onEachOperator(objectPublisher -> {
      System.out.println("foo");

      return objectPublisher;
    });
  }

  @AfterAll
  static void afterAll() {
    Hooks.resetOnOperatorDebug();
  }

  @Test
  void stackTrace() {
    try {
      Mono.just("")
          .map(s -> { throw new RuntimeException(); })
          .filter(o -> true)
          .map(o -> o)
          .block();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

}
