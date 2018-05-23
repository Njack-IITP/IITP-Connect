package com.iitp.njack.iitp_connect.data.models;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
@Getter
public class Contest {
    private String title;
    private String description;
    private String url;
    private Date startTime;
    private Date endTime;
}
