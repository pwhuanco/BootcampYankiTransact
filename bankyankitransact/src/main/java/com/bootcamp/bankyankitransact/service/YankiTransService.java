package com.bootcamp.bankyankitransact.service;

import com.bootcamp.bankyankitransact.dto.YankiTransactionDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface YankiTransService {

    Flux<YankiTransactionDto> getDeposit();

    Mono<YankiTransactionDto> getDepositById(String id);

    //Mono<YankiTransactionDto> doTransfer(YankiTransactionDto depositDto);

    Mono<YankiTransactionDto> saveDeposit(YankiTransactionDto depositDtoMono);

    Mono<YankiTransactionDto> updateDeposit(Mono<YankiTransactionDto> depositDtoMono, String id);

    Mono<Void> deleteDeposit(String id);

}
