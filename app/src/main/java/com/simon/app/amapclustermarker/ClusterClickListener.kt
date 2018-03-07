package com.simon.app.amapclustermarker

import com.amap.api.maps.model.Marker

/**
 * Created by yiyi.qi on 16/10/10.
 */

interface ClusterClickListener {
    /**
     * 点击聚合点的回调处理函数
     *
     * @param marker
     * 点击的聚合点
     * @param clusterItems
     * 聚合点所包含的元素
     */
    fun onClick(marker: Marker, clusterItems: List<ClusterItem>)
}
