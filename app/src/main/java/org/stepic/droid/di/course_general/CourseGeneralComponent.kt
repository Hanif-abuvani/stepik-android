package org.stepic.droid.di.course_general

import dagger.Subcomponent
import org.stepic.droid.di.course.CourseComponent
import org.stepic.droid.di.course_list.CourseGeneralScope
import org.stepic.droid.di.course_list.CourseListComponent

@CourseGeneralScope
@Subcomponent(modules = arrayOf(CourseGeneralModule::class))
interface CourseGeneralComponent {

    @Subcomponent.Builder
    interface Builder {
        fun build(): CourseGeneralComponent
    }

    fun courseComponentBuilder(): CourseComponent.Builder

    fun courseListComponentBuilder(): CourseListComponent.Builder

}
