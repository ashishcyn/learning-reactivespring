package com.ashi.tech.learningreactivespring.fluxandmonoplayground;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class FluxAndMonoTests {

    @Test
    public void fluxTest(){
        Flux<String> stringFlux = Flux.just("Spring","Spring Boot","Reactive Spring")
                    //.concatWith(Flux.error(new RuntimeException("Exception occured")))
                    .concatWith(Flux.just("After Error. This wont print incase of exception"))
                    .log();
        stringFlux
                .subscribe(
                        System.out::println,
                        (e)->System.err.println("Exception is "+e),
                        ()-> System.out.println("Completed"));

    }

    @Test
    public void fluxTestElements_WithoutError(){
        Flux<String> stringFlux = Flux.just("Spring","Spring Boot","Reactive Spring")
                .log();
        StepVerifier.create(stringFlux)
                .expectNext("Spring")
                .expectNext("Spring Boot")
                .expectNext("Reactive Spring")
                .verifyComplete();
    }

    @Test
    public void fluxTestElements_WithError(){
        Flux<String> stringFlux = Flux.just("Spring","Spring Boot","Reactive Spring")
                .concatWith(Flux.error(new RuntimeException("ashi,Exception occured")))
                .log();
        StepVerifier.create(stringFlux)
                .expectNext("Spring")
                .expectNext("Spring Boot")
                .expectNext("Reactive Spring")
                //.expectError(RuntimeException.class)
                .expectErrorMessage("ashi,Exception occured")
                .verify();
    }

    @Test
    public void fluxTestElementsCount_WithError(){
        Flux<String> stringFlux = Flux.just("Spring","Spring Boot","Reactive Spring")
                .concatWith(Flux.error(new RuntimeException("ashi,Exception occured")))
                .log();
        StepVerifier.create(stringFlux)
                .expectNextCount(3)
                .expectErrorMessage("ashi,Exception occured")
                .verify();
    }

    @Test
    public void fluxTestElements_WithError1(){
        Flux<String> stringFlux = Flux.just("Spring","Spring Boot","Reactive Spring")
                .concatWith(Flux.error(new RuntimeException("ashi,Exception occured")))
                .log();
        StepVerifier.create(stringFlux)
                .expectNext("Spring","Spring Boot","Reactive Spring")
                //.expectError(RuntimeException.class)
                .expectErrorMessage("ashi,Exception occured")
                .verify();
    }

    @Test
    public void monoTest(){
        Mono<String>  stringMono = Mono.just("Spring");
        StepVerifier.create(stringMono.log())
                    .expectNext("Spring")
                    .verifyComplete();
    }

    @Test
    public void monoTest_Error(){
        StepVerifier.create(Mono.error(new RuntimeException("Exp occured")).log())
                    .expectError(RuntimeException.class)
                    .verify();
    }

}
