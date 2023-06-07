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

    @NotNull(message = "Не указан номер счёта")
    @Min(message = "Номер счёта должен быть больше 1", value = 1)
    @JsonProperty("number")
    private Long billNumber;

    @NotNull(message = "Не указана дата")
    @JsonProperty("date")
    private LocalDate billDate;

    @NotNull(message = "Не указана организация")
    @JsonProperty("customer")
    private String customer;

    @NotNull(message = "Не указан маршрут")
    @JsonProperty("route")
    private String route;

    @NotNull(message = "Не указана цена")
    @Min(message = "Цена должна быть больше 0", value = 1)
    @JsonProperty("cost")
    private Integer cost;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;
}
