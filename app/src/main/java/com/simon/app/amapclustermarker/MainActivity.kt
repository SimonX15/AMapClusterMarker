package com.simon.app.amapclustermarker

import android.app.Activity
import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import com.amap.api.maps.AMap
import com.amap.api.maps.CameraUpdateFactory
import com.amap.api.maps.MapView
import com.amap.api.maps.model.LatLng
import com.amap.api.maps.model.LatLngBounds
import com.amap.api.maps.model.Marker
import com.simon.app.amapclustermarker.demo.RegionItem
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : Activity(), ClusterRender, AMap.OnMapLoadedListener, ClusterClickListener {


    private var mMapView: MapView? = null
    private var mAMap: AMap? = null

    private val clusterRadius = 100

    private val mBackDrawAbles = HashMap<Int, Drawable>()

    private var mClusterOverlay: ClusterOverlay? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mMapView = map_view
        mMapView!!.onCreate(savedInstanceState)

        init()

    }

    private fun init() {
        if (mAMap == null) {
            // 初始化地图
            mAMap = mMapView!!.map
            mAMap!!.setOnMapLoadedListener(this)
            //点击可以动态添加点
            mAMap!!.setOnMapClickListener {
                val lat = Math.random() + 39.474923
                val lon = Math.random() + 116.027116

                val latLng1 = LatLng(lat, lon, false)
                val regionItem = RegionItem(latLng1,
                        "test")
                mClusterOverlay!!.addClusterItem(regionItem)
            }

            val uiSettings = mAMap!!.uiSettings
            uiSettings.isScaleControlsEnabled = true
        }
    }


    override fun onResume() {
        super.onResume()
        mMapView!!.onResume()
    }

    override fun onPause() {
        super.onPause()
        mMapView!!.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        //销毁资源
        mClusterOverlay!!.onDestroy()
        mMapView!!.onDestroy()
    }

    override fun onMapLoaded() {
        //添加测试数据
        object : Thread() {
            override fun run() {

                val items = ArrayList<ClusterItem>()

                //随机10000个点
                for (i in 0..9999) {

                    val lat = Math.random() + 39.474923
                    val lon = Math.random() + 116.027116

                    val latLng = LatLng(lat, lon, false)
                    val regionItem = RegionItem(latLng,
                            "test" + i)
                    items.add(regionItem)

                }
                mClusterOverlay = ClusterOverlay(mAMap!!, items,
//                        600,
                        dp2px(applicationContext, clusterRadius.toFloat()),
                        applicationContext)
                mClusterOverlay!!.setClusterRenderer(this@MainActivity)
                mClusterOverlay!!.setOnClusterClickListener(this@MainActivity)

            }

        }

                .start()


    }


    override fun onClick(marker: Marker, clusterItems: List<ClusterItem>) {

        val builder = LatLngBounds.Builder()
        for (clusterItem in clusterItems) {
            builder.include(clusterItem.position)
        }
        val latLngBounds = builder.build()
        mAMap!!.animateCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds, 0)
        )
    }

    override fun getDrawAble(clusterNum: Int): Drawable {
        val radius = dp2px(applicationContext, 80f)
        if (clusterNum == 1) {
            var bitmapDrawable: Drawable? = mBackDrawAbles[1]
            if (bitmapDrawable == null) {
                bitmapDrawable = application.resources.getDrawable(
                        R.drawable.icon_openmap_mark)
                mBackDrawAbles.put(1, bitmapDrawable)
            }

            return bitmapDrawable!!
        } else if (clusterNum < 5) {

            var bitmapDrawable: Drawable? = mBackDrawAbles[2]
            if (bitmapDrawable == null) {
                bitmapDrawable = BitmapDrawable(null, drawCircle(radius,
                        Color.argb(159, 210, 154, 6)))
                mBackDrawAbles.put(2, bitmapDrawable)
            }

            return bitmapDrawable
        } else if (clusterNum < 10) {
            var bitmapDrawable: Drawable? = mBackDrawAbles[3]
            if (bitmapDrawable == null) {
                bitmapDrawable = BitmapDrawable(null, drawCircle(radius,
                        Color.argb(199, 217, 114, 0)))
                mBackDrawAbles.put(3, bitmapDrawable)
            }

            return bitmapDrawable
        } else {
            var bitmapDrawable: Drawable? = mBackDrawAbles[4]
            if (bitmapDrawable == null) {
                bitmapDrawable = BitmapDrawable(null, drawCircle(radius,
                        Color.argb(235, 215, 66, 2)))
                mBackDrawAbles.put(4, bitmapDrawable)
            }

            return bitmapDrawable
        }
    }

    private fun drawCircle(radius: Int, color: Int): Bitmap {

        val bitmap = Bitmap.createBitmap(radius * 2, radius * 2,
                Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        val paint = Paint()
        val rectF = RectF(0f, 0f, (radius * 2).toFloat(), (radius * 2).toFloat())
        paint.color = color
        canvas.drawArc(rectF, 0f, 360f, true, paint)
        return bitmap
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    fun dp2px(context: Context, dpValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }
}
