/**
 * 
 */
package com.topad.util;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.StatFs;
import android.support.v4.util.LruCache;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.SoftReference;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashMap;

/**
 * The author 欧瑞强 on 2015/7/17.
 * todo 内存缓存
 */
public class ImageMemoryCache {
	/**
	 * 从内存读取数据速度是最快的，为了更大限度使用内存，这里使用了两层缓存。 硬引用缓存不会轻易被回收，用来保存常用数据，不常用的转入软引用缓存。
	 */
	private static final int SOFT_CACHE_SIZE = 15; // 软引用缓存容量
	private static LruCache<String, Bitmap> mLruCache; // 硬引用缓存
	private static LinkedHashMap<String, SoftReference<Bitmap>> mSoftCache; // 软引用缓存

	public ImageMemoryCache(Context context) {
		int memClass = ((ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE)).getMemoryClass();
		int cacheSize = 1024 * 1024 * memClass / 8; // 硬引用缓存容量，为系统可用内存的1/8
		mLruCache = new LruCache<String, Bitmap>(cacheSize) {
			@Override
			protected int sizeOf(String key, Bitmap value) {
				if (value != null)
					return value.getRowBytes() * value.getHeight();
				else
					return 0;
			}

			@Override
			protected void entryRemoved(boolean evicted, String key,
					Bitmap oldValue, Bitmap newValue) {
				if (oldValue != null)
					// 硬引用缓存容量满的时候，会根据LRU算法把最近没有被使用的图片转入此软引用缓存
					mSoftCache.put(key, new SoftReference<Bitmap>(oldValue));
			}
		};
		mSoftCache = new LinkedHashMap<String, SoftReference<Bitmap>>(
				SOFT_CACHE_SIZE, 0.75f, true) {
			private static final long serialVersionUID = 6040103833179403725L;

			@Override
			protected boolean removeEldestEntry(
					Entry<String, SoftReference<Bitmap>> eldest) {
				if (size() > SOFT_CACHE_SIZE) {
					return true;
				}
				return false;
			}
		};
	}

	/**
	 * 从缓存中获取图片
	 */
	public Bitmap getBitmapFromCache(String url) {
		Bitmap bitmap;
		// 先从硬引用缓存中获取
		synchronized (mLruCache) {
			bitmap = mLruCache.get(url);
			if (bitmap != null) {
				// 如果找到的话，把元素移到LinkedHashMap的最前面，从而保证在LRU算法中是最后被删除
				mLruCache.remove(url);
				mLruCache.put(url, bitmap);
				return bitmap;
			}
		}
		// 如果硬引用缓存中找不到，到软引用缓存中找
		synchronized (mSoftCache) {
			SoftReference<Bitmap> bitmapReference = mSoftCache.get(url);
			if (bitmapReference != null) {
				bitmap = bitmapReference.get();
				if (bitmap != null) {
					// 将图片移回硬缓存
					mLruCache.put(url, bitmap);
					mSoftCache.remove(url);
					return bitmap;
				} else {
					mSoftCache.remove(url);
				}
			}
		}
		return null;
	}

	/**
	 * 添加图片到缓存
	 */
	public void addBitmapToCache(String url, Bitmap bitmap) {
		if (bitmap != null) {
			synchronized (mLruCache) {
				mLruCache.put(url, bitmap);
			}
		}
	}

	public void clearCache() {
		mSoftCache.clear();
	}
}

// 文件缓存，缓存到SD卡上
class ImageFileCache {
	private static final String CACHDIR = "walletapp";
	private static final String WHOLESALE_CONV = ".png";

	private static final int MB = 1024 * 1024;
	private static final int CACHE_SIZE = 10;
	private static final int FREE_SD_SPACE_NEEDED_TO_CACHE = 10;

	public ImageFileCache() {
		// //清理文件缓存
		removeCache(getDirectory());
	}

	/** 从缓存中获取图片 **/
	public Bitmap getImage(final String url) {
		final String path = getDirectory() + "/" + convertUrlToFileName(url);
		File file = new File(path);
		if (file.exists()) {
			Bitmap bmp = BitmapFactory.decodeFile(path);
			if (bmp == null) {
				file.delete();
			} else {
				updateFileTime(path);
				return bmp;
			}
		}
		return null;
	}

	/** 将图片存入文件缓存 **/
	public void saveBitmap(String url, Bitmap bm) {
		if (bm == null) {
			return;
		}
		// 判断sdcard上的空间
		if (FREE_SD_SPACE_NEEDED_TO_CACHE > freeSpaceOnSd()) {
			// SD空间不足
			return;
		}
		String filename = convertUrlToFileName(url);
		String dir = getDirectory();
		File dirFile = new File(dir);
		if (!dirFile.exists())
			dirFile.mkdirs();
		File file = new File(dir + "/" + filename);
		try {
			file.createNewFile();
			OutputStream outStream = new FileOutputStream(file);

			bm.compress(Bitmap.CompressFormat.PNG, 100, outStream);
//			if (!url.endsWith("PNG")&&!url.endsWith("png")) {
//				bm.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
//			} else {
//				bm.compress(Bitmap.CompressFormat.PNG, 100, outStream);
//			}

			outStream.flush();
			outStream.close();
		} catch (FileNotFoundException e) {

			LogUtil.d("ImageFileCache", "FileNotFoundException");
		} catch (IOException e) {
			LogUtil.d("ImageFileCache", "IOException");
		}
	}

	/**
	 * 计算存储目录下的文件大小，
	 * 当文件总大小大于规定的CACHE_SIZE或者sdcard剩余空间小于FREE_SD_SPACE_NEEDED_TO_CACHE的规定
	 * 那么删除40%最近没有被使用的文件
	 */
	private boolean removeCache(String dirPath) {
		File dir = new File(dirPath);
		File[] files = dir.listFiles();
		if (files == null) {
			return true;
		}

		if (!Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			return false;
		}

		int dirSize = 0;
		for (int i = 0; i < files.length; i++) {
			if (files[i].getName().contains(WHOLESALE_CONV)) {
				dirSize += files[i].length();
			}
		}

		if (dirSize > CACHE_SIZE * MB
				|| FREE_SD_SPACE_NEEDED_TO_CACHE > freeSpaceOnSd()) {
			int removeFactor = (int) ((0.4 * files.length) + 1);
			Arrays.sort(files, new FileLastModifSort());
			for (int i = 0; i < removeFactor; i++) {
				if (files[i].getName().contains(WHOLESALE_CONV)) {
					files[i].delete();
				}
			}
		}

		if (freeSpaceOnSd() <= CACHE_SIZE) {
			return false;
		}

		return true;
	}

	/** 修改文件的最后修改时间 **/
	public void updateFileTime(String path) {
		File file = new File(path);
		long newModifiedTime = System.currentTimeMillis();
		file.setLastModified(newModifiedTime);
	}

	/** 计算sdcard上的剩余空间 **/
	private int freeSpaceOnSd() {
		StatFs stat = new StatFs(Environment.getExternalStorageDirectory()
				.getPath());
		double sdFreeMB = ((double) stat.getAvailableBlocks() * (double) stat
				.getBlockSize()) / MB;
		return (int) sdFreeMB;
	}

	// /** 将url转成文件名 **/
	// private String convertUrlToFileName(String url) {
	// String[] strs = url.split("/");
	// return strs[strs.length - 1] + WHOLESALE_CONV;
	// }
	/** 将url转成文件名 **/
	private String convertUrlToFileName(String url) {
		return Md5.md5s(url) + WHOLESALE_CONV;
	}

	/** 获得缓存目录 **/
	public String getDirectory() {
		String dir = getSDPath() + "/" + CACHDIR;
		return dir;
	}

	/** 取SD卡路径 **/
	private String getSDPath() {
		File sdDir = null;
		boolean sdCardExist = Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
		if (sdCardExist) {
			sdDir = Environment.getExternalStorageDirectory(); // 获取根目录
		}
		if (sdDir != null) {
			return sdDir.toString();
		} else {
			return "";
		}
	}

	/**
	 * 根据文件的最后修改时间进行排序
	 */
	private class FileLastModifSort implements Comparator<File> {
		public int compare(File arg0, File arg1) {
			if (arg0.lastModified() > arg1.lastModified()) {
				return 1;
			} else if (arg0.lastModified() == arg1.lastModified()) {
				return 0;
			} else {
				return -1;
			}
		}
	}

}

// 从网络加载图片
class ImageGetFromHttp {
	private static final String LOG_TAG = "ImageGetFromHttp";

	public static Bitmap downloadBitmap(String url) {
		final HttpClient client = new DefaultHttpClient();
		final HttpGet getRequest = new HttpGet(url);

		try {
			HttpResponse response = client.execute(getRequest);
			final int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				LogUtil.d("Error " + statusCode
						+ " while retrieving bitmap from " + url);
				return null;
			}

			final HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream inputStream = null;
				try {
					inputStream = entity.getContent();
					FilterInputStream fit = new FlushedInputStream(inputStream);
					return BitmapFactory.decodeStream(fit);
				} finally {
					if (inputStream != null) {
						inputStream.close();
						inputStream = null;
					}
					entity.consumeContent();
				}
			}
		} catch (IOException e) {
			getRequest.abort();
			LogUtil.d("I/O error while retrieving bitmap from " + url);
		} catch (IllegalStateException e) {
			getRequest.abort();
			LogUtil.d("Incorrect URL: " + url);
		} catch (Exception e) {
			getRequest.abort();
			LogUtil.d("Error while retrieving bitmap from " + url);
		} finally {
			client.getConnectionManager().shutdown();
		}
		return null;
	}

	/*
	 * An InputStream that skips the exact number of bytes provided, unless it
	 * reaches EOF.
	 */
	static class FlushedInputStream extends FilterInputStream {
		public FlushedInputStream(InputStream inputStream) {
			super(inputStream);
		}

		@Override
		public long skip(long n) throws IOException {
			long totalBytesSkipped = 0L;
			while (totalBytesSkipped < n) {
				long bytesSkipped = in.skip(n - totalBytesSkipped);
				if (bytesSkipped == 0L) {
					int b = read();
					if (b < 0) {
						break; // we reached EOF
					} else {
						bytesSkipped = 1; // we read one byte
					}
				}
				totalBytesSkipped += bytesSkipped;
			}
			return totalBytesSkipped;
		}
	}
}
