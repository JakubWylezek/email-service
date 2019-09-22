package com.taskservice.emailservice.api.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
public class Task {

    private String name;
    private String description;
    @NotNull
    private List<String> emailList;
}
