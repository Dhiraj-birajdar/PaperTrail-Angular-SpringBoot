package com.papertrail.email;

import lombok.Getter;

@Getter
@Deprecated(forRemoval = true, since = "1.0.0")
public enum EmailTemplateName {

    ACTIVATE_ACCOUNT("activate_account");

    private final String name;

    EmailTemplateName(String name) {
        this.name = name;
    }
}
