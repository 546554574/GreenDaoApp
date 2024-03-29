package com.green.dao.greendao;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.green.dao.bean.User;
import com.green.dao.bean.ConfigBean;

import com.green.dao.greendao.UserDao;
import com.green.dao.greendao.ConfigBeanDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig userDaoConfig;
    private final DaoConfig configBeanDaoConfig;

    private final UserDao userDao;
    private final ConfigBeanDao configBeanDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        userDaoConfig = daoConfigMap.get(UserDao.class).clone();
        userDaoConfig.initIdentityScope(type);

        configBeanDaoConfig = daoConfigMap.get(ConfigBeanDao.class).clone();
        configBeanDaoConfig.initIdentityScope(type);

        userDao = new UserDao(userDaoConfig, this);
        configBeanDao = new ConfigBeanDao(configBeanDaoConfig, this);

        registerDao(User.class, userDao);
        registerDao(ConfigBean.class, configBeanDao);
    }
    
    public void clear() {
        userDaoConfig.clearIdentityScope();
        configBeanDaoConfig.clearIdentityScope();
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public ConfigBeanDao getConfigBeanDao() {
        return configBeanDao;
    }

}
