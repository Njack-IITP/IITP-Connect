package com.iitp.njack.iitp_connect.data.contest;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.github.jasminb.jsonapi.annotations.Type;

import org.threeten.bp.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
@Type("contest")
@JsonNaming(PropertyNamingStrategy.KebabCaseStrategy.class)
@Entity(tableName = "contests")
public class Contest {
    @PrimaryKey(autoGenerate = true)
    int id;
    String title;
    String description;
    String url;
    LocalDateTime startDate;
    LocalDateTime endDate;
}
