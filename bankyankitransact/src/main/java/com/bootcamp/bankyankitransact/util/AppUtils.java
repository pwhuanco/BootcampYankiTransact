package com.bootcamp.bankyankitransact.util;

import com.bootcamp.bankyankitransact.bean.YankiTransaction;
import com.bootcamp.bankyankitransact.dto.YankiTransactionDto;
import org.springframework.beans.BeanUtils;

public class AppUtils {

    public static YankiTransactionDto entityToDto(YankiTransaction deposit) {
        YankiTransactionDto accDto = new YankiTransactionDto();
        BeanUtils.copyProperties(deposit, accDto);
        return accDto;
    }

    public static YankiTransaction dtoToEntity(YankiTransactionDto accDto) {
        YankiTransaction deposit = new YankiTransaction();
        BeanUtils.copyProperties(accDto, deposit);
        return deposit;
    }
}
