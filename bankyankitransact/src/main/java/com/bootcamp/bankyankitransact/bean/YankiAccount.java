package com.bootcamp.bankyankitransact.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Builder
@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "account")
public class YankiAccount {
    @Id
    private String idYankiAccount;

    private String documentNumber;
    private String documentType;
    private String phoneNumber;
    private String imei;
    private String email;
    private String debitCard;

    @Builder.Default
    private Double balance = 0.0;

    @Builder.Default
    private LocalDate date = LocalDate.now();

}
