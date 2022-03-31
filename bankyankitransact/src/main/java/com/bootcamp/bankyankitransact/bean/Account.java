package com.bootcamp.bankyankitransact.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(value = "account")
@JsonInclude(JsonInclude.Include.NON_NULL)

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Account {
    @Id
    private String id;

    private String accountNumber;

    private double balance;

    private String currency;

    private String accountType;

    private String canBeDeposit;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime operationDate = LocalDateTime.now();

    private int movementPerMonth;

    private int maxLimitMovementPerMonth;

    private String clientIdNumber;

    private Double minimumOpeningAmount;

    private Double minimumDailyAverageAmountEachMonth;

    private int maxLimitTransaction;

}
