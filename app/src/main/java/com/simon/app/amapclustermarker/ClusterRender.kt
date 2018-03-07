package com.simon.app.amapclustermarker

import android.graphics.drawable.Drawable

/**
 * Created by yiyi.qi on 16/10/10.
 */

interface ClusterRender {
    /**
     * 根据聚合点的元素数目返回渲染背景样式
     *
     * @param clusterNum
     * @return
     */
    fun getDrawAble(clusterNum: Int): Drawable
}
