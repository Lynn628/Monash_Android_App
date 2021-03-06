package com.example.s17.moniteryourlife.HomeDrawer;

import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.CircleOptions;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.poi.PoiSortType;
import com.example.s17.moniteryourlife.R;

import java.util.ArrayList;
import java.util.List;


public class MapFragment extends Fragment implements
        OnGetGeoCoderResultListener{

    private TextureMapView mMapView;
    //Map object
    private BaiduMap mBaiduMap;
    //Search mode
    GeoCoder mSearch = null;
    private PoiSearch mPoiSearch = null;
    LatLng ptCenter;
    List<PoiInfo> nearList;

    View vMap;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        SDKInitializer.initialize(getActivity().getApplicationContext());
        vMap = inflater.inflate(R.layout.map, container, false);

        mMapView = (TextureMapView)vMap.findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();
        //Set it normal map
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.zoomTo(16));

        // Initial the search module ,register event listening
        mSearch = GeoCoder.newInstance();
        mPoiSearch = PoiSearch.newInstance();

      //Locate site

        ptCenter = new LatLng(31.27410,120.7540);

        mSearch.setOnGetGeoCodeResultListener(this);
        // 反Geo搜索
        mSearch.reverseGeoCode(new ReverseGeoCodeOption()
                .location(ptCenter));

        mPoiSearch.setOnGetPoiSearchResultListener(poiListener);
        //searchNeayBy();

        initMarkerClick();
        // searchNeayBy();

        return vMap;
    }
    private void initMarkerClick()
    {// 对Marker的点击
        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener()
        {
            @Override
            public boolean onMarkerClick(Marker marker) {
                String name = "";
                for (int i = 0; i<nearList.size();i++)
                {
                    if(nearList.get(i).location.latitude == marker.getPosition().latitude
                            && nearList.get(i).location.longitude ==marker.getPosition().longitude)
                    {
                        name = nearList.get(i).name;
                    }
                }

                InfoWindow infoWindow;
                //动态生成一个TextView对象，用户在地图中显示InfoWindow
                final TextView textInfo = new TextView(getActivity());
                // textInfo.setBackgroundResource(R.drawable.map_icon1);
                textInfo.setPadding(10, 10, 10, 10);
                textInfo.setTextColor(Color.BLACK);
                textInfo.setTextSize(12);
                textInfo.setText(name);

                //得到点击的覆盖物的经纬度
                LatLng ll = marker.getPosition();
                //将marker所在的经纬度的信息转化成屏幕上的坐标
                Point p = mBaiduMap.getProjection().toScreenLocation(ll);
                p.y -= 90;
                LatLng llInfo = mBaiduMap.getProjection().fromScreenLocation(p);
                //初始化infoWindow，最后那个参数表示显示的位置相对于覆盖物的竖直偏移量，这里也可以传入一个监听器
                infoWindow = new InfoWindow(textInfo, llInfo, 0);
                mBaiduMap.showInfoWindow(infoWindow);//显示此infoWindow
                //让地图以备点击的覆盖物为中心
                MapStatusUpdate status = MapStatusUpdateFactory.newLatLng(ll);
                mBaiduMap.setMapStatus(status);
                return true;
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
        mPoiSearch.destroy();
    }
    @Override
    public void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
        //initMarkerClick();
        //mPoiSearch.destroy();
    }

    @Override
    public void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
        //initMarkerClick();
        //mPoiSearch.destroy();
    }

    /**
     * Search surrounding location
     */
    private void searchNeayBy() {
        PoiNearbySearchOption option = new PoiNearbySearchOption();
        option.keyword("公园");
        option.sortType(PoiSortType.distance_from_near_to_far);
        option.location(ptCenter);
        option.radius(5000);
        option.pageCapacity(10);
        mPoiSearch.searchNearby(option);
    }

    OnGetPoiSearchResultListener poiListener = new OnGetPoiSearchResultListener(){
        public void onGetPoiResult(PoiResult poiResult){
            //Get POI search result
            if (poiResult == null
                    || poiResult.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {
                Toast.makeText(getActivity(), "Sorry, not find ! ",
                        Toast.LENGTH_LONG).show();
                return;
            }
            //store data
            nearList = new ArrayList<PoiInfo>();

            if (poiResult.getAllPoi()!=null && poiResult.getAllPoi().size()>0) {
                nearList.addAll(poiResult.getAllPoi());
                //System.out.println("111111" + nearList.get(1).location.toString());
                if (nearList != null && nearList.size() > 0) {

                    List<Integer> iconarray = new ArrayList<Integer>();
                    iconarray.add(R.drawable.maps);
                    iconarray.add(R.drawable.maps);
                    iconarray.add(R.drawable.maps);
                    iconarray.add(R.drawable.maps);
                    iconarray.add(R.drawable.maps);
                    iconarray.add(R.drawable.maps);
                    iconarray.add(R.drawable.maps);
                    iconarray.add(R.drawable.maps);
                    iconarray.add(R.drawable.maps);
                    iconarray.add(R.drawable.maps);

                    for (int i = 0; i < nearList.size(); i++) {
                        //isSelected.put(i, false);
                        double lati = nearList.get(i).location.latitude;
                        double lon = nearList.get(i).location.longitude;

                        if (i == 0)
                        {
                            CircleOptions circleOptions = new CircleOptions();
                            circleOptions.center(new LatLng(lati, lon));//设置圆心坐标
                            circleOptions.fillColor(0XFF091DF4);//圆的填充颜色
                            circleOptions.radius(30);//设置半径
                            //circleOptions.stroke(new Stroke(5, 0xAA00FF00));//设置边框
                            mBaiduMap.addOverlay(circleOptions);
                        }

                        //构建Marker图标
                        //定义Maker坐标点
                        LatLng point = new LatLng(lati, lon);
                        BitmapDescriptor bitmap = BitmapDescriptorFactory
                                .fromResource(iconarray.get(i));

                        //构建MarkerOption，用于在地图上添加Marker
                        OverlayOptions option = new MarkerOptions()
                                .position(point)
                                .icon(bitmap);
                        mBaiduMap.addOverlay(option);
                    }
                    return;
                }
            }
        }
        public void onGetPoiDetailResult(PoiDetailResult poiDetailResult){
            //Get search result detail of places
            if (poiDetailResult.error != SearchResult.ERRORNO.NO_ERROR) {
                Toast.makeText(getActivity(), "Sorry, not find ! ",
                        Toast.LENGTH_SHORT).show();
            } else {// 正常返回结果的时候，此处可以获得很多相关信息
                System.out.println("11111"+poiDetailResult.getName());
                Toast.makeText(
                        getActivity(),
                        poiDetailResult.getName() + ": "
                                + poiDetailResult.getAddress(),
                        Toast.LENGTH_LONG).show();
            }
        }
    };



    @Override
    public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {}

    @Override
    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText(getActivity(), "Sorry, not find ! ", Toast.LENGTH_LONG)
                    .show();
            return;
        }
        mBaiduMap.clear();
        mBaiduMap.addOverlay(new MarkerOptions().position(result.getLocation())
                .icon(BitmapDescriptorFactory
                        .fromResource(R.drawable.icon4)));

        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(result
                .getLocation()));

        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.zoomTo(16));

        Toast.makeText(getActivity(), result.getAddress(),
                Toast.LENGTH_LONG).show();

        searchNeayBy();
    }

}
