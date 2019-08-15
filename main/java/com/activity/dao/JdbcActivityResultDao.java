package com.  .activity.dao;

import com.  .activity.model.ActivityResultUpdate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

/**
 * Created by thatikonda on 10/27/16.
 */
@Component
public class JdbcActivityResultDao  implements ActivityResultDao {

    private static Logger log = LoggerFactory.getLogger(JdbcActivityResultDao.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public boolean updateUserActivityInfo(ActivityResultUpdate activity) {

        try {
            int update = getJdbcTemplate().update(
                    "update activity_user_attribute set value = ? where ACTIVITY_RESULT_ID = ? and  ATTRIBUTE_ID = ? ",
                    activity.getAttributeValue(), activity.getActivityResultId(), activity.getUserAttribute()
            );

            if (update == 0) {
                getJdbcTemplate().update(
                        "insert into activity_user_attribute(ACTIVITY_RESULT_ID ,ATTRIBUTE_ID ,VALUE ) values (?,?,?)",
                        activity.getActivityResultId(), activity.getUserAttribute(), activity.getAttributeValue()
                );
            }

        }catch (Exception e){
            log.error("ERROR while insert/updating activity_user_attribute ",e);
            return false;
        }
        return true;
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
