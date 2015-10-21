/**
 * 
 */
package com.topad.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The author 欧瑞强 on 2015/7/17.
 * todo 下载图片的管理类
 */
public class ImageManager {
	static ImageManager iManager;
	private ImageMemoryCache imageMemoryCache; // 内存缓存
	private ImageFileCache imageFileCache; // 文件缓存
	ExecutorService threadPool;
	Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
		}
	};

	public static ImageManager getInstance(Context context) {
		if (iManager == null) {
			iManager = new ImageManager(context);
		}
		return iManager;
	}

	public ImageManager(Context context) {
		imageMemoryCache = new ImageMemoryCache(context);
		imageFileCache = new ImageFileCache();
		threadPool = Executors.newFixedThreadPool(2);
	}

	// =============================获取图片========================================
	/*** 获得一张图片,从三个地方获取,首先是内存缓存,然后是文件缓存,最后从网络获取 ***/
	public void getBitmap(final String url, final ImageCallBack callback,final ImageView imageview) {
		if (Utils.isEmpty(url)) {
			callback.loadImage(imageview,null);
			return;
		}
		// 从内存缓存中获取图片
		Bitmap result = imageMemoryCache.getBitmapFromCache(url);
		if (result == null) {
			// 文件缓存中获取
			result = imageFileCache.getImage(url);
			if (result == null) {
				threadPool.submit(new Runnable() {
					@Override
					public void run() {
						// 从网络获取
						final Bitmap bitmap = ImageGetFromHttp
								.downloadBitmap(url);
						
						if (bitmap != null) {
							mHandler.post(new Runnable() {
								public void run() {
									callback.loadImage(imageview,bitmap);
								}
							});
							imageFileCache.saveBitmap(url, bitmap);
							imageMemoryCache.addBitmapToCache(url, bitmap);
						}
					}
				});

			} else {
				// 添加到内存缓存
				imageMemoryCache.addBitmapToCache(url, result);
			}
		}
		callback.loadImage(imageview,result);
	}

	public interface ImageCallBack {
		void loadImage(ImageView imageView, Bitmap bitmap);
	}

	/**
	 * 保存图片到文件缓存中
	 * @param name		图片存贮名字,需带后缀名
	 * @param bitmap
	 */
	public void saveBitmap(final String name, final Bitmap bitmap){
		if (Utils.isEmpty(name)) {
			return;
		}
		if (bitmap != null) {
			imageFileCache.saveBitmap(name, bitmap);
		}
	}

	/**
	 * 获取sd卡路径
	 */
	public String getDirectory() {
		return imageFileCache.getDirectory();
	}
}

// class BitmapUtil {
//
// /**
// * 计算SampleSize
// *
// * @Title computeImageSampleSize
// * @Description TODO
// * @param width
// * @param height
// * @param limitWidth
// * @param limitHeight
// * @param viewScaleType
// * @param powerOf2Scale
// * @return int
// */
// public static int computeImageSampleSize(int width, int height,
// int limitWidth, int limitHeight, int viewScaleType,
// boolean powerOf2Scale) {
//
// int scale = 1;
//
// int widthScale = width / limitWidth;
// int heightScale = height / limitHeight;
//
// switch (viewScaleType) {
// case 1:// FIT_INSIDE
// if (powerOf2Scale) {
// while (width / 2 >= limitWidth || height / 2 >= limitHeight) { // ||
// width /= 2;
// height /= 2;
// scale *= 2;
// }
// } else {
// scale = Math.max(widthScale, heightScale); // max
// }
// break;
// case 2:// CROP
// if (powerOf2Scale) {
// while (width / 2 >= limitWidth && height / 2 >= limitHeight) { // &&
// width /= 2;
// height /= 2;
// scale *= 2;
// }
// } else {
// scale = Math.min(widthScale, heightScale); // min
// }
// break;
// }
//
// if (scale < 1) {
// scale = 1;
// }
//
// return scale;
// }
// }
