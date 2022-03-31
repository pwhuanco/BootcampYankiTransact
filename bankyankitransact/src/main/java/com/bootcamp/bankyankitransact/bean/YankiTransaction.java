package com.bootcamp.bankyankitransact.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(value = "account")
public class YankiTransaction {

    @Id
    private String id;
    private Double amount;
    private String destinationPhone;
    private String originPhone;
    private String date;
}
