package com.bootcamp.bankyankitransact.service.impl;

import com.bootcamp.bankyankitransact.dto.AccountDto;
import com.bootcamp.bankyankitransact.dto.YankiAccountDto;
import com.bootcamp.bankyankitransact.dto.YankiTransactionDto;
import com.bootcamp.bankyankitransact.repository.YankiTransRepository;
import com.bootcamp.bankyankitransact.service.YankiTransService;
import com.bootcamp.bankyankitransact.util.AppUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.Optional;

@Slf4j
@Service
public class YankiTransServiceImpl implements YankiTransService {

    @Value("${microservice-accounts.uri}")
    private String urlAccounts;
    @Value("${apiclient.uri}")
    private String urlApigateway;


    @Autowired
    private YankiTransRepository depositRepository;

    @Autowired
    private WebClient.Builder webClient;
    @Autowired
    private RestTemplate restTemplate;

    public Flux<YankiTransactionDto> getDeposit() {
        log.debug("In getDeposit()");
        return depositRepository.findAll().map(AppUtils::entityToDto);
    }

    @Override
    public Mono<YankiTransactionDto> getDepositById(String id) {
        return depositRepository.findById(id)
                .map(AppUtils::entityToDto);
    }

    /*@Override
    public Mono<YankiAccountDto> doTransfer(YankiTransactionDto depositDto) {
        YankiAccountDto accountOutgoing = restTemplate.getForObject(urlApigateway + urlAccounts + depositDto.getFromAccountId(), YankiAccountDto.class);
        YankiAccountDto accountDestination = restTemplate.getForObject(urlApigateway + urlAccounts + depositDto.getToAccountId(), YankiAccountDto.class);
        if (accountOutgoing.getBalance() >= depositDto.getAmount() && accountOutgoing.getCurrency() == accountDestination.getCurrency()) {
            accountOutgoing.setBalance(accountDestination.getBalance() - depositDto.getAmount());
            accountDestination.setBalance(accountDestination.getBalance() + depositDto.getAmount());
            return Mono.just(accountDestination);
        } else {
            return null;
        }

    }*/

    public Mono<YankiTransactionDto> saveDeposit(YankiTransactionDto yankiTransDto) {
        log.debug("url a invocar:" + urlApigateway + urlAccounts);

        try {
            Optional<AccountDto> account = haveAsociatedAccount(yankiTransDto);
            if(account.isPresent()){
                updateBalanceAccount(account.get());
            }
            YankiAccountDto yankiAccount = obtainYankiAccountToDeposit(yankiTransDto.getDestinationPhone());

            if (approveDeposit(yankiAccount, yankiTransDto)) {
                log.debug("calculateBalance:");
                calculateBalance(yankiAccount, yankiTransDto);
                log.debug("updateBalanceAccount:");
                updateBalanceYankiAccount(yankiAccount);
                log.debug("savingDeposit:");
                return savingDeposit(yankiTransDto);
            } else {
                throw new Exception("Error: Deposito no permitido");
            }
        } catch (Exception e) {
            log.error("TransactionError", e);
            //rolback transaction
            return Mono.just(new YankiTransactionDto());
        }
    }

    //TODO implementar consulta de cuenta asociada
    private Optional<AccountDto> haveAsociatedAccount(YankiTransactionDto yankiTransDto) {

        return Optional.of(new AccountDto());
    }

    //TODO implementar la consulta de cuenta yanki
    private YankiAccountDto obtainYankiAccountToDeposit(String toAccount) {
        YankiAccountDto account = restTemplate
                .getForObject(urlApigateway + urlAccounts +
                                toAccount,
                        YankiAccountDto.class);
        log.debug("restTemplate:" + account.getIdYankiAccount());
        return account;
    }

    private Mono<YankiTransactionDto> savingDeposit(YankiTransactionDto depositDto) {
        log.debug("Service.savingDeposit");
        return Mono.just(depositDto).map(AppUtils::dtoToEntity)
                .flatMap(depositRepository::insert)
                .map(AppUtils::entityToDto);
    }
    //TODO: Implementar la actualizacion del balance de la cuenta yanki. servicio cuentas
    private void updateBalanceYankiAccount(YankiAccountDto account) {
        //account.setMovementPerMonth(account.getMovementPerMonth() + 1);
        //restTemplate.put(urlApigateway + urlAccounts + account.getId(), account);
    }
    //TODO: Implementar la actualizacion del balance de la cuenta de ahorro. servicio cuentas
    private void updateBalanceAccount(AccountDto account) {
        //account.setMovementPerMonth(account.getMovementPerMonth() + 1);
        //restTemplate.put(urlApigateway + urlAccounts + account.getId(), account);
    }

    /**
     *
     */
    private boolean approveDeposit(YankiAccountDto account, YankiTransactionDto depositDto) {
        /*if (Constant.TIPO_CUENTA_PLAZO.equalsIgnoreCase(account.getAccountType())) {
            if (Constant.CAN_BE_DEPOSIT.equalsIgnoreCase(account.getCanBeDeposit())) {
                return true;
            }
        } else if (Constant.TIPO_CUENTA_AHORRO.equalsIgnoreCase(account.getAccountType())) {
            if (account.getMovementPerMonth() <= account.getMaxLimitMovementPerMonth()) {
                return true;
            }
        } else if (Constant.TIPO_CUENTA_CORRIENTE.equalsIgnoreCase(account.getAccountType())) {
            return true;
        }
        return false;*/
        return true;
    }

    private void calculateBalance(YankiAccountDto account, YankiTransactionDto depositDto) throws NumberFormatException {
        BigDecimal balance = BigDecimal.valueOf(account.getBalance());
        BigDecimal amount = BigDecimal.valueOf(depositDto.getAmount());
        BigDecimal newBalance = balance.add(amount);
        account.setBalance(newBalance.doubleValue());
    }

    public Mono<YankiTransactionDto> updateDeposit(Mono<YankiTransactionDto> YankiTransactionDtoMono, String id) {
        return depositRepository.findById(id)
                .flatMap(p -> YankiTransactionDtoMono.map(AppUtils::dtoToEntity)
                        .doOnNext(e -> e.setId(id)))
                .flatMap(depositRepository::save)
                .map(AppUtils::entityToDto);
    }

    public Mono<Void> deleteDeposit(String id) {
        return depositRepository.deleteById(id);
    }
}
