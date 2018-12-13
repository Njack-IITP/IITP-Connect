package com.iitp.njack.iitp_connect.data.facebook;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.github.jasminb.jsonapi.LongIdHandler;
import com.github.jasminb.jsonapi.annotations.Id;
import com.github.jasminb.jsonapi.annotations.Type;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import static com.iitp.njack.iitp_connect.data.facebook.FacebookPost.TABLE_NAME;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Type("facebookPost")
@EqualsAndHashCode
@JsonNaming(PropertyNamingStrategy.KebabCaseStrategy.class)
@Entity(tableName = TABLE_NAME)
public class FacebookPost {
    public static final String TABLE_NAME = "facebookPosts";
    @Id(LongIdHandler.class)
    @PrimaryKey(autoGenerate = true)
    private Long id;
    private String likes;
    private String comments;
    private String shares;
    private String propic;
    private String postpic;
    private String name;
    private String time;
    private String message;
    private String url;
}
