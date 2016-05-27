package com.thinkcoo.mobile.model.entity;


import com.thinkcoo.mobile.model.entity.nullentities.NullUser;
import com.thinkcoo.mobile.model.entity.nullentities.Nullable;
import com.thinkcoo.mobile.model.entity.serverresponse.LoginResponse;
import com.thinkcoo.mobile.model.entity.serverresponse.UserResponse;

import java.util.List;

public class User implements Nullable {

    public static final User NULL_USER = new NullUser();

    public static User fromServerResponse(LoginResponse loginResponse){
        User user = new User();
        //TODO 转换
        return user;
    }

    private boolean isLogined;
    private String cert;
    private String name;
    private String userId;
    private String portraitURL;
    private String type;
    private List<WebNode> webNodeList;

    @Override
    public boolean isEmpty() {
        return false;
    }
    public boolean isLogined() {
        return isLogined;
    }
    public void setIsLogined(boolean isLogined) {
        this.isLogined = isLogined;
    }
    public static User getNullUser() {
        return NULL_USER;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCert() {
        return cert;
    }

    public void setCert(String cert) {
        this.cert = cert;
    }

    public String getPortraitURL() {
        return portraitURL;
    }

    public void setPortraitURL(String portraitURL) {
        this.portraitURL = portraitURL;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<WebNode> getWebNodeList() {
        return webNodeList;
    }

    public void setWebNodeList(List<WebNode> webNodeList) {
        this.webNodeList = webNodeList;
    }
}
