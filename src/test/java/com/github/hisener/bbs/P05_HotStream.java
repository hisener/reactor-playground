package com.github.hisener.bbs;

import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;

class P05_HotStream extends ReactiveTestCase {

  @Test
  void streamApi() {
    Stream.of("Foo", "Bar", "Baz")
        .filter(s -> s.startsWith("B"))
        .map(String::toLowerCase)
        .forEach(System.out::println);
  }

  @Test
  void reactor() {
    Flux<String> coldFlux = Flux.just("Foo", "Bar", "Baz")
        .filter(s -> s.startsWith("B"))
        .map(String::toLowerCase)
        .doOnNext(System.out::println);

    ConnectableFlux<String> connectableFlux = coldFlux.publish();

    connectableFlux.connect();

    connectableFlux.subscribe();
  }
}
