package com.github.hisener.bbs;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

class P02_MapVsFlatMap extends ReactiveTestCase {

  /**
   * Map 1 to 1 transform T -> V
   *
   * FlatMap 1 to Stream of N elements transform T -> Stream<V>
   */
  @Test
  void streamApi() {
    Stream.of("foo", "bar")
        .flatMap(s -> Stream.of(s, "baz"))
        .map(String::toUpperCase)
        .forEach(System.out::println);
  }

  /**
   * Map 1 to 1 transform T -> V synchronous
   *
   * FlatMap 1 to Flux of N elements transform T -> Flux<V> can be async
   */
  @Test
  void reactor() {
    Flux.just("foo", "bar")
        .flatMap(s -> Flux.just(1, 5))
        .map(Integer::highestOneBit)
        .subscribe(System.out::println);
  }

  @Test
  void completableFuture() {
    CompletableFuture<List<String>> completableFuture = CompletableFuture.completedFuture(
        Arrays.asList("foo", "bar", "baz"));

    Flux<String> flux = Mono.fromFuture(completableFuture)
        .flatMapMany(Flux::fromIterable);
  }
}
