package com.bootcamp.bankyankitransact.repository;

import com.bootcamp.bankyankitransact.bean.YankiTransaction;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

@Configuration
public interface YankiTransRepository extends ReactiveMongoRepository<YankiTransaction, String> {

}
