package com.roomreservation.management.model;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Builder
public class WorkHistory{

    private String workTitle;
    private String companyName;
    private String description;
}

