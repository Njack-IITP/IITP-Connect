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
    public HashMap<String, String> monday;
    public HashMap<String, String> tuesday;
    public HashMap<String, String> wednesday;
    public HashMap<String, String> thursday;
    public HashMap<String, String> friday;
}
