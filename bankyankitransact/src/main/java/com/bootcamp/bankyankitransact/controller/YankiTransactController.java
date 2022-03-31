package com.bootcamp.bankyankitransact.controller;

import com.bootcamp.bankyankitransact.dto.YankiTransactionDto;
import com.bootcamp.bankyankitransact.service.YankiTransService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

//@Slf4j
@RestController
@RequestMapping(path = "/api/yankitrans")
public class YankiTransactController {
    private static final Logger LOGGER = LoggerFactory.getLogger(YankiTransactController.class);

    @Resource
    private YankiTransService depositService;

    @CircuitBreaker(name = "getDepositCB", fallbackMethod = "fallbackGetDeposit")
    @TimeLimiter(name = "getDepositCB", fallbackMethod = "fallbackGetDeposit")
    @GetMapping
    public Flux<YankiTransactionDto> getDeposit() {
        LOGGER.debug("Getting Deposit!");
        return depositService.getDeposit();
    }

    @GetMapping("/{id}")
    public Mono<YankiTransactionDto> getDeposit(@PathVariable String id) {
        LOGGER.debug("Getting a deposit!");
        return depositService.getDepositById(id);
    }

    @PostMapping
    public Mono<YankiTransactionDto> saveDeposit(@RequestBody YankiTransactionDto depositDtoMono) {
        LOGGER.debug("Saving deposit!");
        return depositService.saveDeposit(depositDtoMono);
    }

    @PutMapping("/{id}")
    public Mono<YankiTransactionDto> updateDeposit(@RequestBody Mono<YankiTransactionDto> depositDtoMono, @PathVariable String id) {
        LOGGER.debug("Updating deposit!");
        return depositService.updateDeposit(depositDtoMono, id);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteDeposit(@PathVariable String id) {
        LOGGER.debug("Deleting deposit!");
        return depositService.deleteDeposit(id);
    }

    /**
     * Fallback method
     *
     * @param re is a RuntimeException
     * @return Flux vacio
     */
    private Flux<YankiTransactionDto> fallbackGetDeposit(RuntimeException re) {

        return Flux.just(new YankiTransactionDto());
    }
}
