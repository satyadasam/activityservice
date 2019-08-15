package com.  .activity.dao;

import com.  .activity.model.ActivityResultUpdate;
import org.springframework.stereotype.Repository;

/**
 * Created by thatikonda on 10/27/16.
 */
@Repository
public interface ActivityResultDao {
    public boolean updateUserActivityInfo(ActivityResultUpdate activity);

}
