package com.github.hisener.bbs;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

/**
 * https://github.com/reactor/reactor-core/blob/master/src/docs/asciidoc/advancedFeatures.adoc#advanced-mutualizing-operator-usage
 */
class P04_TransformVsCompose extends ReactiveTestCase {

  @Test
  void transform() {
    AtomicInteger opCount = new AtomicInteger(0);

    Function<Flux<String>, Flux<String>> function =
        f -> {
          opCount.incrementAndGet();

          return f
              .filter(s -> !s.equalsIgnoreCase("purple"))
              .map(String::toUpperCase);
        };

    Flux<String> flux = Flux.just("blue", "green", "orange", "purple")
        .transform(function);

    System.out.println(opCount.get());

    flux.subscribe(System.out::println);
    flux.subscribe(System.out::println);
    flux.subscribe(System.out::println);

    System.out.println(opCount.get());
  }

  @Test
  void compose() {
    AtomicInteger opCount = new AtomicInteger(0);

    Function<Flux<String>, Flux<String>> function =
        f -> {
          opCount.incrementAndGet();

          return f
              .filter(s -> opCount.get() > 2)
              .filter(s -> !s.equalsIgnoreCase("purple"))
              .map(String::toUpperCase);
        };

    Flux<String> flux = Flux.just("blue", "green", "orange", "purple")
        .compose(function)
        .subscribeOn(Schedulers.parallel());

    System.out.println(opCount.get());

    flux.subscribe(System.out::println);
    flux.subscribe(System.out::println);
    flux.subscribe(System.out::println);

    System.out.println(opCount.get());
  }

//  class FooOperator extends Function<Flux<String>, Flux<Integer>> {
//
//  }

}
