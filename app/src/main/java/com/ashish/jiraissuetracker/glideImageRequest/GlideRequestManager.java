package com.ashish.jiraissuetracker.glideImageRequest;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.widget.ImageView;

import com.ashish.jiraissuetracker.R;
import com.bumptech.glide.GenericRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.StreamEncoder;
import com.bumptech.glide.load.resource.file.FileToStreamDecoder;
import com.caverock.androidsvg.SVG;

import java.io.InputStream;

/**
 * Created by Ashish on 15/06/16.
 */
public class GlideRequestManager {

    private static GenericRequestBuilder<Uri, InputStream, SVG, PictureDrawable> requestBuilder;

    public static GenericRequestBuilder getRequestBuilder(Context context) {
        if (context == null) {
            return null;
        }
        requestBuilder = Glide.with(context)
                .using(Glide.buildStreamModelLoader(Uri.class, context), InputStream.class)
                .from(Uri.class)
                .as(SVG.class)
                .transcode(new SvgDrawableTranscoder(), PictureDrawable.class)
                .sourceEncoder(new StreamEncoder())
                .cacheDecoder(new FileToStreamDecoder<SVG>(new SvgDecoder()))
                .decoder(new SvgDecoder())
                .placeholder(R.drawable.test_user)
                .error(R.drawable.test_user)
                .animate(android.R.anim.fade_in)
                .listener(new SvgSoftwareLayerSetter<Uri>());
        return requestBuilder;
    }

    public static GenericRequestBuilder getRequestBuilder(Context context, int loadingImage) {
        requestBuilder = Glide.with(context)
                .using(Glide.buildStreamModelLoader(Uri.class, context), InputStream.class)
                .from(Uri.class)
                .as(SVG.class)
                .transcode(new SvgDrawableTranscoder(), PictureDrawable.class)
                .sourceEncoder(new StreamEncoder())
                .cacheDecoder(new FileToStreamDecoder<SVG>(new SvgDecoder()))
                .decoder(new SvgDecoder())
                .placeholder(loadingImage)
                .error(loadingImage)
                .animate(android.R.anim.fade_in)
                .listener(new SvgSoftwareLayerSetter<Uri>());
        return requestBuilder;
    }

    public GenericRequestBuilder getRequestBuilderCommon(Context context) {
        if (context == null) {
            return null;
        }

        GenericRequestBuilder builder = Glide.with(context)
                .using(Glide.buildStreamModelLoader(Uri.class, context), InputStream.class)
                .from(Uri.class)
                .as(SVG.class)
                .transcode(new SvgDrawableTranscoder(), PictureDrawable.class)
                .sourceEncoder(new StreamEncoder())
                .cacheDecoder(new FileToStreamDecoder<SVG>(new SvgDecoder()))
                .decoder(new SvgDecoder())
                .placeholder(R.drawable.symphony)
                .animate(android.R.anim.fade_in)
                .listener(new SvgSoftwareLayerSetter<Uri>());

        return builder;
    }

    public static void loadImage(String url, ImageView imageView, Context context) {
        try {
            Uri uri = Uri.parse(url);
            getRequestBuilder(context, R.drawable.issue_priority_image).diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .load(uri)
                    .into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
