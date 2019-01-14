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
    public Long id;
    public String likes;
    public String comments;
    public String shares;
    public String propic;
    public String postpic;
    public String name;
    public String time;
    public String message;
    public String url;
}
