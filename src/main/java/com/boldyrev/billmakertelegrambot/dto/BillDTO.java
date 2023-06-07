package com.boldyrev.billmakertelegrambot.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BillDTO {

    @JsonProperty("url")
    private String url;

    @NotNull(message = "�� ������ ����� �����")
    @Min(message = "����� ����� ������ ���� ������ 1", value = 1)
    @JsonProperty("number")
    private Long billNumber;

    @NotNull(message = "�� ������� ����")
    @JsonProperty("date")
    private LocalDate billDate;

    @NotNull(message = "�� ������� �����������")
    @JsonProperty("customer")
    private String customer;

    @NotNull(message = "�� ������ �������")
    @JsonProperty("route")
    private String route;

    @NotNull(message = "�� ������� ����")
    @Min(message = "���� ������ ���� ������ 0", value = 1)
    @JsonProperty("cost")
    private Integer cost;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;
}
