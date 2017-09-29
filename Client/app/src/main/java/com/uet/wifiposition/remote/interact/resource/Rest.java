package com.uet.wifiposition.remote.interact.resource;

import com.uet.wifiposition.remote.model.getbuilding.GetBuildingsResponse;
import com.uet.wifiposition.remote.model.getbuilding.GetRoomsResponse;
import com.uet.wifiposition.remote.model.getbuilding.PostReferencePoint;

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
    @GET("info/getBuildings")
    Observable<GetBuildingsResponse> getBuildings();

    @GET("info/getRooms")
    Observable<GetRoomsResponse> getRooms(@Query("buildingId") int buildingId);

    @POST("info/postReferencePoint")
    Observable<PostReferencePoint> postReferencePoint(@Body RequestBody requestBody);
}
