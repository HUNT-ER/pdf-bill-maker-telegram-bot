package com.boldyrev.billmakertelegrambot.dto;

import lombok.Data;

@Data
public class BillOwnerDetails {

    private String recipientCredentials;
    private String bankCredentials;
    private String carrier;
    private String signatory;

    public boolean setProperty(String property, String value) {
        boolean isSet = true;
        switch (property) {
            case "recipientCredentials":
                recipientCredentials = value;
                break;
            case "bankCredentials":
                bankCredentials = value;
                break;
            case "carrier":
                carrier = value;
                break;
            case "signatory":
                signatory = value;
                break;
            default:
                isSet = false;
        }
        return isSet;
    }

    public boolean isCorrect() {
        if (recipientCredentials != null && bankCredentials != null && carrier != null
            && signatory != null) {
            return true;
        }
        return false;
    }
}
