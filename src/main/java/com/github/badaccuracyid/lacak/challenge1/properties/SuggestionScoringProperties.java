package com.github.badaccuracyid.lacak.challenge1.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "suggestion.scoring")
public class SuggestionScoringProperties {

    private double nameWeight;
    private double locationWeight;

    private boolean usePopulation;
    private double populationWeight;

}
