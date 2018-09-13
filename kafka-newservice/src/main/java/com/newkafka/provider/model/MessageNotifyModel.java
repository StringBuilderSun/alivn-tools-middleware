package com.newkafka.provider.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * Created by lijinpeng on 2018/9/10.
 */
@Getter
@Setter
@ToString
public class MessageNotifyModel {
    private String userId;
    private String userName;
    private String date;
    private List<String> viewsPages;
    private String viewsTime;

    public MessageNotifyModel(String userId, String userName, String date, List<String> viewsPages, String viewsTime) {
        this.userId = userId;
        this.userName = userName;
        this.date = date;
        this.viewsPages = viewsPages;
        this.viewsTime = viewsTime;
    }
}
