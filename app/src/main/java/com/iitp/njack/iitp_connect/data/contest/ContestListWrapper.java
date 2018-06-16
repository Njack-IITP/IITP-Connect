package com.iitp.njack.iitp_connect.data.contest;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.github.jasminb.jsonapi.LongIdHandler;
import com.github.jasminb.jsonapi.annotations.Id;
import com.github.jasminb.jsonapi.annotations.Type;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Type("contestWrapper")
@EqualsAndHashCode
@JsonDeserialize(using = ContestDeserializer.class)
public class ContestListWrapper {
    @Id(LongIdHandler.class)
    public Long id;
    private List<Contest> contests;
}
