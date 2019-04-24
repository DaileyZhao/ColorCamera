package com.zcm.camera.net;

import com.zcm.library.net.BaseResponse;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NetApi {
    @GET("/health")
    Observable<BaseResponse<List<FeedModel.FeedItem>>> getHealth(@Query("key") String key, @Query("num") int num,@Query("rand") int rand);
    @GET("/travel/")
    Observable<BaseResponse<List<FeedModel.FeedItem>>> getTravel(@Query("key") String key, @Query("num") int num,@Query("rand") int rand);
}
