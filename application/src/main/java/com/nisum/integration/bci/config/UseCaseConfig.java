package com.nisum.integration.bci.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
        basePackages = "com.nisum.integration.bci.domain",
        includeFilters = @ComponentScan.Filter(type = FilterType.REGEX, pattern = ".*[UseCase]")
)
public class UseCaseConfig {
}
