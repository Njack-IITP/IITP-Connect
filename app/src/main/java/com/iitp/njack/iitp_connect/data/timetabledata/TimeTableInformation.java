package com.iitp.njack.iitp_connect.data.timetabledata;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.github.jasminb.jsonapi.annotations.Type;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Type("timeTableInformation")
@JsonNaming(PropertyNamingStrategy.KebabCaseStrategy.class)
public class TimeTableInformation {
    String course;
    String branch;
    String year;
}
