package com.github.hisener.bbs;

import java.time.Duration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class ReactiveTestCase {

  @BeforeAll
  static void beforeAll() {
    StepVerifier.setDefaultTimeout(Duration.ofSeconds(5));
  }

  @AfterAll
  static void afterAll() {
    StepVerifier.resetDefaultTimeout();
  }

  @Test
  void name() {
    StepVerifier.create(Mono.never())
        .expectComplete()
        .verify();

//    StepVerifier.create(Mono.create(monoSink -> {}))
//        .expectComplete()
//        .verify();
  }
}
