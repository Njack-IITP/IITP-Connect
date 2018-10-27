package com.iitp.njack.iitp_connect.data.timetabledata;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.github.jasminb.jsonapi.annotations.Type;

import java.util.HashMap;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Type("timeTableDatabase")
@JsonNaming(PropertyNamingStrategy.KebabCaseStrategy.class)
public class TimeTableDatabase {
    HashMap<String, String> monday;
    HashMap<String, String> tuesday;
    HashMap<String, String> wednesday;
    HashMap<String, String> thursday;
    HashMap<String, String> friday;
}
