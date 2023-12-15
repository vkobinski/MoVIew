package com.kodev.moview.image_api;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.kodev.moview.R;
import com.kodev.moview.api.ApiClient;
import com.kodev.moview.api.ApiImplementation;
import com.kodev.moview.api.ApiInterface;
import com.kodev.moview.api.MovieApi;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ImageApiClient {

    private static ImageApiClient instance;

    private ImageApiClient() {
    }

    public static ImageApiClient getInstance() {
        if(instance == null) {
            instance = new ImageApiClient();
        }
        return instance;
    }

    public void getImage(String imageName, ImageCallback imageCallback) {

        imageName= imageName.replace("/", "");

        ImageApiInterface apiInterface = ImageApiClientImplementation.getClient();
        Call<ResponseBody> call = apiInterface.getImage(imageName);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if(response.isSuccessful() && response.body() != null) {
                    Bitmap bmp = BitmapFactory.decodeStream(response.body().byteStream());
                    imageCallback.downloadImage(bmp);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }
}
