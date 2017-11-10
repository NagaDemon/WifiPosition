package com.uet.wifiposition.remote.interact.resource;

import com.uet.wifiposition.remote.model.getbuilding.GetBuildingsResponse;
import com.uet.wifiposition.remote.model.getbuilding.GetRoomsResponse;
import com.uet.wifiposition.remote.model.getbuilding.PostReferencePoint;
import com.uet.wifiposition.remote.model.getposition.GetLocationResponse;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by ducnd on 9/29/17.
 */

public interface Rest {
    @GET("api/mobile/info/getBuildings")
    Observable<GetBuildingsResponse> getBuildings();

    @GET("api/mobile/info/getRooms")
    Observable<GetRoomsResponse> getRooms(@Query("buildingId") int buildingId);

    @POST("api/mobile/info/postReferencePoint")
    Observable<PostReferencePoint> postReferencePoint(@Body RequestBody requestBody);

    @POST("api/mobile/info/postReferencePointGauss")
    Observable<PostReferencePoint> postReferencePointGauss(@Body RequestBody requestBodyD);

    @POST("api/mobile/location/getLocation")
    Observable<GetLocationResponse> getLocation(@Body RequestBody requestBody);


}
