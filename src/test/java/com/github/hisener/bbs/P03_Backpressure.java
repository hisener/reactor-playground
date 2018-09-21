package com.github.hisener.bbs;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.Test;
import reactor.core.Exceptions;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.FluxSink.OverflowStrategy;
import reactor.test.StepVerifier;

class P03_Backpressure extends ReactiveTestCase {

  FluxSink.OverflowStrategy backpressure = OverflowStrategy.BUFFER;

  @Test
  void name() {
    AtomicInteger last = new AtomicInteger();

    StepVerifier.create(Flux.range(1, 100)
        .onBackpressureBuffer(3, last::set), 0)
        .then(() -> assertEquals(9, last.get()))
        .thenRequest(8)
        .expectNextCount(8)
        .verifyComplete();
  }

  @Test
  void onBackpressureBufferMax() {
    Flux<Integer> flux = Flux.range(1, 100)
        .onBackpressureBuffer(8);

    StepVerifier.create(flux, 0)
        .thenRequest(7)
        .expectNext(1, 2, 3, 4, 5, 6, 7)
        .thenAwait()
        .verifyErrorMatches(Exceptions::isOverflow);
  }

  @Test
  void onBackpressureBufferMaxCallback() {
    AtomicInteger last = new AtomicInteger();

    StepVerifier.create(Flux.range(1, 100)
        .onBackpressureBuffer(8, last::set), 0)
        .thenRequest(7)
        .expectNext(1, 2, 3, 4, 5, 6, 7)
        .then(() -> assertEquals(16, last.get()))
        .thenRequest(9)
        .expectNextCount(8)
        .verifyErrorMatches(Exceptions::isOverflow);
  }
}
