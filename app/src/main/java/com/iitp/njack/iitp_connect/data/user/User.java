package com.iitp.njack.iitp_connect.data.user;

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
import lombok.NoArgsConstructor;

import static com.iitp.njack.iitp_connect.data.user.User.TABLE_NAME;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Type("user")
@JsonNaming(PropertyNamingStrategy.KebabCaseStrategy.class)
@Entity(tableName = TABLE_NAME)
@SuppressWarnings("PMD.TooManyFields")
public class User {
    public static final String TABLE_NAME = "users";
    @Id(LongIdHandler.class)
    @PrimaryKey(autoGenerate = true)
    Long id;
    String userName;
    String email;
    String firstName;
    String lastName;
    String roll;
    public String course;
    public String year;
    public String branch;
}
