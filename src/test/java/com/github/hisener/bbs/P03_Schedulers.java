package com.github.hisener.bbs;

import java.time.Duration;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

/**
 * @see <a href="https://zoltanaltfatter.com/2018/08/26/subscribeOn-publishOn-in-Reactor/">
 * subscribeOn and publishOn operators in Reactor</a>
 */
class P03_Schedulers extends ReactiveTestCase {

  private static void createSubscribers(Flux<Integer> flux) {
    IntStream.range(1, 5).forEach(value ->
        flux.subscribe(integer ->
            System.out.println(value + " consumer processed " + integer +
                " using thread: " + Thread.currentThread().getName())));
  }

  @Test
  void simple() {
    Flux<Integer> flux = Flux.range(1, 2);

    createSubscribers(flux);
  }

  /**
   * <p>
   * <img src="http://reactivex.io/documentation/operators/images/schedulers.png" />
   * <p>
   */
  @Test
  void schedulers() throws InterruptedException {
    Flux<Integer> flux = Flux.range(1, 2)
        // this is influenced by subscribeOn
        .doOnNext(s -> System.out.println(s + " before publishOn using thread: " +
            Thread.currentThread().getName()))
        .publishOn(Schedulers.elastic())
        // the rest is influenced by publishOn
        .doOnNext(s -> System.out.println(s + " after publishOn using thread: " +
            Thread.currentThread().getName()))
        .subscribeOn(Schedulers.parallel());

    createSubscribers(flux);

    Thread.sleep(1000);
  }

  @Test
  void delayElements() throws InterruptedException {
    Flux<Integer> flux = Flux.range(1, 2)
        // this is influenced by subscribeOn
        .doOnNext(s -> System.out.println(s + " before publishOn using thread: " +
            Thread.currentThread().getName()))
        .publishOn(Schedulers.elastic())
        // influenced by publishOn
        .doOnNext(s -> System.out.println(s + " after publishOn using thread: " +
            Thread.currentThread().getName()))
        .delayElements(Duration.ofMillis(1))
        // influenced by the Schedulers.parallel() caused by the delayElements operator
        .subscribeOn(Schedulers.single());

    createSubscribers(flux);

    Thread.sleep(1000);
  }
}

//.map(s -> {
//    System.out.println(s + " mapped to: " + (s + 1) + " " +
//    Thread.currentThread().getName());
//
//    return s + 1;
//    })
