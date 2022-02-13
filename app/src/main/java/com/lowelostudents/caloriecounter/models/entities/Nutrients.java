package com.lowelostudents.caloriecounter.models.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Nutrients {
    @EqualsAndHashCode.Include
    protected String name;
    protected int calTotal;
    protected int gramTotal;
}
