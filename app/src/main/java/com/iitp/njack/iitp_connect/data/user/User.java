package com.iitp.njack.iitp_connect.data.user;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.github.jasminb.jsonapi.annotations.Type;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
@Type("user")
@JsonNaming(PropertyNamingStrategy.KebabCaseStrategy.class)
@SuppressWarnings("PMD.TooManyFields")
public class User {
    int id;
}
