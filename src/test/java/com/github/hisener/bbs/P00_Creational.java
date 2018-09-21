package com.github.hisener.bbs;

import java.time.Duration;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

class P00_Creational extends ReactiveTestCase {

  private Mono<String> mono;

  private Flux<String> flux;

  @Test
  void simpleFactoryMethods() throws InterruptedException {
    mono = Mono.just("foo");
    flux = Flux.just("foo", "bar");

    mono = Mono.empty();
    flux = Flux.empty();

    mono = Mono.justOrEmpty(null);

    mono = Mono.error(new RuntimeException());
    flux = Flux.error(new RuntimeException());

    flux = Flux.never();

    Flux.range(3, 5).doOnNext(System.out::println).subscribe();
    Flux.interval(Duration.ofMillis(100)).doOnNext(System.out::println).subscribe();
  }

  @Test
  void from() {
//    Mono.from();
//    Mono.fromFuture();
//    Mono.fromCallable();
//    Mono.fromCompletionStage();
//    Mono.fromDirect();
//    Mono.fromRunnable();
//    Mono.fromSupplier();

//    Flux.from();
//    Flux.fromArray();
//    Flux.fromIterable();
//    Flux.fromStream();
//    Flux.fromStream();

//    Flux.defer(() -> Flux.just("foo"));
  }

  @Test
  void programmatically() {
    flux = Flux.create(sink -> {
      sink.next("");

      new DummyListener() {
        @Override
        public void onMessage(String message) {
          sink.next(message);
        }

        @Override
        public void onComplete() {
          sink.complete();
        }
      };
    });

//    Flux.push()
//    Flux.generate()
  }

  interface DummyListener {
    void onMessage(String message);
    void onComplete();
  }
}
