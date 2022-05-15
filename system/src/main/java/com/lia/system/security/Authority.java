package com.lia.system.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;


@Data
@AllArgsConstructor
public class Authority implements GrantedAuthority {

    private static final long serialVersionUID = 1L;

    private String authority;

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return this.authority;
    }
}
