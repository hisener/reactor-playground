package com.github.hisener.bbs;

import java.lang.reflect.Constructor;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

/**
 * https://image.slidesharecdn.com/springone2016reactor3060mn-160909143255/95/reactor-30-a-reactive-foundation-for-java-8-and-spring-35-638.jpg
 */
class P01_BooleanVsVoid extends ReactiveTestCase {

  @Test
  void monoBoolean() {
    Mono<Boolean> mono;

    mono = Mono.just(true);
    mono = Mono.just(false);

    mono = Mono.error(new RuntimeException());
    mono = Mono.empty();
  }

  @Test
  void monoVoid() {
    Mono<Void> mono;

    mono = Mono.just("foo").then();
    StepVerifier.create(mono)
        .expectComplete()
        .verify();

    mono = Mono.error(new RuntimeException());
    mono = Mono.empty();
  }

  @Test
  void reflection() throws ReflectiveOperationException{
    Constructor<Void> constructor = Void.class.getDeclaredConstructor();
    constructor.setAccessible(true);
    Void aVoid = constructor.newInstance();

    StepVerifier.create(Mono.just(aVoid))
        .expectNext(aVoid)
        .verifyComplete();
  }
}
