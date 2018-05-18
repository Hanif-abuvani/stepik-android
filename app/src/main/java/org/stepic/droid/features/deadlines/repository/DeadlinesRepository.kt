package org.stepic.droid.features.deadlines.repository

import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import org.stepic.droid.features.deadlines.model.DeadlinesWrapper
import org.stepic.droid.web.storage.model.StorageRecord

interface DeadlinesRepository {

    fun createDeadlinesForCourse(deadlines: DeadlinesWrapper): Single<StorageRecord<DeadlinesWrapper>>
    fun updateDeadlinesForCourse(record: StorageRecord<DeadlinesWrapper>): Single<StorageRecord<DeadlinesWrapper>>

    fun removeDeadlinesForCourse(recordId: Long): Completable
    fun getDeadlinesForCourse(courseId: Long): Maybe<StorageRecord<DeadlinesWrapper>>

    fun fetchAllDeadlines(): Observable<StorageRecord<DeadlinesWrapper>>

}