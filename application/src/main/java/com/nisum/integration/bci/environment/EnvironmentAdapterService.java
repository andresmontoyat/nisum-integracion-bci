package com.nisum.integration.bci.environment;

import com.nisum.integration.bci.domain.port.environment.EnvironmentService;
import lombok.AllArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EnvironmentAdapterService implements EnvironmentService {
    private final Environment environment;

    @Override
    public String getPropertyValue(String key) {
        return environment.getProperty(key);
    }
}
