package org.stepic.droid.core.presenters.contracts

import org.stepic.droid.model.Section

interface SectionsView {

    fun onEmptySections()

    fun onConnectionProblem()

    fun onNeedShowSections(sectionList: List<Section>)

    fun onLoading()

    fun updatePosition(position: Int)

}
