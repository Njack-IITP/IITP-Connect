package com.iitp.njack.iitp_connect.data.contest;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@AllArgsConstructor
@Getter
@Setter
public class Contest {
    private String title;
    private String description;
    private String url;
    private Date startTime;
    private Date endTime;
}
