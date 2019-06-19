package com.green.dao.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class ConfigBean {
    @Id
    Long id;

    @Generated(hash = 605455490)
    public ConfigBean(Long id) {
        this.id = id;
    }

    @Generated(hash = 1548494737)
    public ConfigBean() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
