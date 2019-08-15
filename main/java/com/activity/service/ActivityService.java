package com.  .activity.service;

import com.  .activity.dao.ActivityResultDao;
import com.  .activity.model.ActivityResultUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by thatikonda on 10/27/16.
 */
@Component
public class ActivityService {

    @Autowired
    ActivityResultDao activityResultDao;

    public boolean updateUserActivityInfo(ActivityResultUpdate activity){

        return activityResultDao.updateUserActivityInfo(activity);
    }

}
