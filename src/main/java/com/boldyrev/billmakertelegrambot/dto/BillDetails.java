package com.boldyrev.billmakertelegrambot.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import lombok.Data;

@Data
public class BillDetails {

    @JsonProperty("number")
    private Long billNumber;

    @JsonProperty("date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate billDate;

    @JsonProperty("recipient_cred")
    private String recipientCredentials;

    @JsonProperty("bank_cred")
    private String bankCredentials;

    @JsonProperty("carrier")
    private String carrier;

    @JsonProperty("customer")
    private String customer;

    @JsonProperty("customer_cred")
    private String customerCredentials;

    @JsonProperty("route")
    private String route;

    @JsonProperty("cost")
    private Integer cost;

    @JsonProperty("signatory")
    private String signatory;

    @JsonProperty("signed")
    private Boolean signed;

    public void setBillOwnerDetails(BillOwnerDetails billOwnerDetails) {
        this.recipientCredentials = billOwnerDetails.getRecipientCredentials();
        this.bankCredentials = billOwnerDetails.getBankCredentials();
        this.carrier = billOwnerDetails.getCarrier();
        this.signatory = billOwnerDetails.getSignatory();
    }

    public boolean setProperty(String property, Object value) {
        boolean isSet = true;
        switch (property) {
            case "billNumber":
                billNumber = Long.parseLong((String) value);
                break;
            case "billDate":
                billDate = LocalDate.parse((String) value,
                    DateTimeFormatter.ofPattern("dd.MM.yyyy"));
                break;
            case "customer":
                customer = (String) value;
                break;
            case "customerCredentials":
                customerCredentials = (String) value;
                break;
            case "route":
                route = (String) value;
                break;
            case "cost":
                cost = Integer.parseInt((String) value);
                break;
            case "signed":
                if (((String) value).equalsIgnoreCase("Да")) {
                    signed = true;
                } else {
                    signed = false;
                }
                break;
            default:
                isSet = false;
        }
        return isSet;
    }

    @JsonIgnore
    public boolean isCorrect() {
        if (billNumber != null && billDate != null && recipientCredentials != null
            && bankCredentials != null && carrier != null && customer != null
            && customerCredentials != null && route != null && cost != null && signatory != null
            && signed
            != null) {
            return true;
        }
        return false;
    }

    public void resetDetails() {
        billNumber = null;
        billDate = null;
        customer = null;
        customerCredentials = null;
        route = null;
        cost = null;
        signed = null;
    }

}
