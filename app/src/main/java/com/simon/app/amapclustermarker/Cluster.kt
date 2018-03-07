package com.simon.app.amapclustermarker

import com.amap.api.maps.model.LatLng
import com.amap.api.maps.model.Marker

import java.util.ArrayList

/**
 * Created by yiyi.qi on 16/10/10.
 */

data class Cluster(val centerLatLng: LatLng) {

    var mClusterItems: ArrayList<ClusterItem> = ArrayList()
    var marker: Marker? = null


    fun getClusterCount(): Int {
        return mClusterItems.size
    }

    fun addClusterItem(clusterItem: ClusterItem) {
        mClusterItems.add(clusterItem)
    }

    fun getClusterItems(): List<ClusterItem> {
        return mClusterItems
    }
}
