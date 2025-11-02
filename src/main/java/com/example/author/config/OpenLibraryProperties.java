package com.example.finance.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "openlibrary")
public class OpenLibraryProperties {

    private String baseUrl;
    private String authorsSearchPath;
    private String worksPath;

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getAuthorsSearchPath() {
        return authorsSearchPath;
    }

    public void setAuthorsSearchPath(String authorsSearchPath) {
        this.authorsSearchPath = authorsSearchPath;
    }

    public String getWorksPath() {
        return worksPath;
    }

    public void setWorksPath(String worksPath) {
        this.worksPath = worksPath;
    }
}

