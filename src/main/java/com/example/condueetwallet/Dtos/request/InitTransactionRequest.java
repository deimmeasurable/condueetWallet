package com.example.condueetwallet.Dtos.request;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.ValidationException;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class InitTransactionRequest {
    @Digits(integer =9, fraction=0)
    private BigDecimal amount;
    private String email;
    private String plan;
    private String reference;
    private String subAccount;
    private String callback_url;
    private Float quantity;
    private Long walletId;

    private Integer invoice_limit;
    private MetaData MetaData;

    private List<String> channel;

    private Integer Transaction_charge;

    public InitTransactionRequest() {

    }


    public void setChannel(List<String> channel) {
        if (!channel.contains("card") || !channel.contains("bank")) {
            throw new ValidationException("Invalid Channel, channel can only be 'bank' or `card`");
        }
        this.channel = channel;
    }
}
