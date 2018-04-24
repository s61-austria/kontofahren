package service

import dao.ActivityDao
import domain.Activity
import utils.Open
import javax.ejb.Stateless
import javax.inject.Inject

@Open
@Stateless
class ActivityService @Inject constructor(
    val activityDao: ActivityDao
) {

    fun create(activity: Activity) = activityDao.createActivity(activity)
    fun update(activity: Activity) = activityDao.updateActivity(activity)
}
