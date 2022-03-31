package com.bootcamp.bankyankitransact.dto;

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
public class YankiAccountDto {
    @Id
    private String idYankiAccount;

    private String documentNumber;
    private String documentType;
    private String phoneNumber;
    private String imei;
    private String email;
    private String debitCard;

    private Double balance;

    @Builder.Default
    private LocalDate date = LocalDate.now();

}
