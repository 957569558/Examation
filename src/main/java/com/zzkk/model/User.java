package com.zzkk.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author warmli
 */
@Data
public class User {
    private String uid;

    @JsonProperty("user")
    private String number;
    @JsonProperty("pwd")
    private String password;

    private String uclass;
}
