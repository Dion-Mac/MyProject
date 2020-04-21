package com.leon.secondkill.vo;

import com.leon.authentication.entity.UserInfo;
import com.leon.item.pojo.SecondKillGoods;

/**
 * @author LeonMac
 * @description 秒杀信息
 */

public class SecondKillMessage {

    /**
     * 用户信息
     */
    private UserInfo userInfo;

    /**
     * 秒杀商品
     */
    private SecondKillGoods secondKillGoods;

    public SecondKillMessage() {
    }

    public SecondKillMessage(UserInfo userInfo, SecondKillGoods secondKillGoods) {
        this.userInfo = userInfo;
        this.secondKillGoods = secondKillGoods;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public SecondKillGoods getSecondKillGoods() {
        return secondKillGoods;
    }

    public void setSecondKillGoods(SecondKillGoods secondKillGoods) {
        this.secondKillGoods = secondKillGoods;
    }
}
