package com.bootcamp.bankyankitransact.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class YankiTransactionDto {

    private String id;
    private Double amount;
    private String destinationPhone;
    private String originPhone;
    private String date;
}
