package com.entertainment.project.common.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;
import android.os.Environment;
import android.view.WindowManager;

import com.entertainment.project.common.Constants;
import com.entertainment.project.common.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Sick on 2016/10/27.
 */
public class ImageUtil {

    public static void compressImage(Bitmap image, File f) throws IOException {
        ByteArrayOutputStream baos = null;
        FileOutputStream fileOutputStream = null;
        try {

            baos = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
            int options = 100;
            while (baos.toByteArray().length / 1024 > 100) {    //循环判断如果压缩后图片是否大于100kb,大于继续压缩
                baos.reset();//重置baos即清空baos
                if (options > 50) {
                    options -= 10;//每次都减少10
                }
                image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中

            }
            fileOutputStream = new FileOutputStream(f);
            fileOutputStream.write(baos.toByteArray(), 0, baos.size());
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Utils", e);
        } finally {
            try {
                if (baos != null) {
                    baos.close();
                }
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("Utils", e);
            }
            baos = null;
            fileOutputStream = null;
        }
    }


    /**
     * 退票改签 图片压缩
     *
     * @param image
     * @param f
     * @throws IOException
     */
    public static void compressImage2(Bitmap image, File f) throws IOException {
        if (image == null) {
            return;
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 90, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        FileOutputStream fileOutputStream = new FileOutputStream(f);
        fileOutputStream.write(baos.toByteArray(), 0, baos.size());

    }

    /**
     * 根据路径获得突破并压缩返回bitmap用于显示
     *
     * @param filePath
     * @return
     */
    public static void getSmallBitmap(String filePath, File file, int reqHeight, int reqWidth) {

        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options.outHeight, options.outWidth, reqHeight, reqWidth);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        Bitmap bitmapy = BitmapFactory.decodeFile(filePath, options);
        try {
            ImageUtil.compressImage2(bitmapy, file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (bitmapy != null)
            bitmapy.recycle();
    }

    /**
     * 计算图片的缩放值
     *
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static int calculateInSampleSize(int outHeight, int outWidth,
                                            int reqHeight, int reqWidth) {
        // Raw height and width of image

        int inSampleSize = 1;

        if (outHeight > reqHeight || outWidth > reqWidth) {

            // Calculate ratios of height and width to requested height and
            // width
            final int heightRatio = Math.round((float) outHeight
                    / (float) reqHeight);
            final int widthRatio = Math.round((float) outWidth / (float) reqWidth);

            // Choose the smallest ratio as inSampleSize value, this will
            // guarantee
            // a final image with both dimensions larger than or equal to the
            // requested height and width.
            inSampleSize = heightRatio > widthRatio ? heightRatio : widthRatio;
        }

        return inSampleSize;
    }

    /**
     * 按照屏幕比例缩放图片 得到并显示图片
     */
    public static Bitmap getBitmap(Context context, String picturePath) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();
        BitmapFactory.Options options = new BitmapFactory.Options();
        //设置inJustDecodeBounds为true后，decodeFile并不分配空间，
        // 但可计算出原始图片的长度和宽度，即opts.width和opts.height。
        // 有了这两个参数，再通过一定的算法，即可得到一个恰当的inSampleSize
        options.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(picturePath, options);
        int realheight = options.outHeight;
        int realwidth = options.outWidth;
        // 计算缩放。
        //手机屏幕的高度
        int scal = (realheight > realwidth ? realwidth : realheight) / (realheight > realwidth ? width : height - 50);
        if (scal <= 0) {
            scal = 1;
        }
        options.inSampleSize = scal;
        //android.util.Log.d(TAG, options.inSampleSize + "缩放比例");
        options.inJustDecodeBounds = false;

        try {
            // 实例化Bitmap
            bitmap = BitmapFactory.decodeFile(picturePath, options);
        } catch (OutOfMemoryError e) {
            //
        }
        /*if (bitmap == null) {
            // 如果实例化失败 返回默认的Bitmap对象
            //return defaultBitmap;
        }*/
        return bitmap;
    }


    //图片压缩
    public static String reSize(String imgPath) {
        File dir = new File(Environment.getExternalStorageDirectory() + "/" + Constants.LocalFileDIR.LOCAL_PICTURE_DIR + "/resize/");

        if (!dir.exists()) {
            dir.mkdirs();
        }
        File originalFile = new File(imgPath);
        if (originalFile.exists()) {
            if (originalFile.length() <= 1024 * 512) {//512k不压缩
                return imgPath;
            }
        }

        String resizeFileName = imgPath.substring(imgPath.lastIndexOf("/"));
        File f = new File(dir.getAbsolutePath() + resizeFileName);
//            String[] strings = imgPath.split(".");
//            imgPathResize = strings[0]+"_resize."+strings[1];
        String imgPathResize = f.getAbsolutePath();
        if (!f.exists()) {

            //改为调用Android标准的图片压缩方法,减少设备匹配问题  20151008 黄朝阳

            //ExifInterface exif1 = null;
            try {
                    /*
                    exif1 = new ExifInterface(imgPath);
                    int orientation1 = exif1.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);

                    int r = ImageUtil.reSize(imgPath, imgPathResize, 600, 600,orientation1);
                */

                //旋转图片
                //Bitmap bitmap=Utils.rotateBitmap(imgPath);
                //压缩图片

                ImageUtil.rotateAndCompressBitmap(imgPath, f, 900, 900);


            } catch (Exception e) {
                e.printStackTrace();
            }


        }
        return imgPathResize;
    }

    public static Bitmap rotateAndCompressBitmap(String filePath, File file, int reqHeight, int reqWidth) {
        Bitmap bitmap = null;

        try {

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.RGB_565;

            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(filePath, options);
            int outHeight = options.outHeight;
            int outWidth = options.outWidth;
            if (options.outHeight < 0 || options.outWidth < 0) {
                Log.e("rotateAndCompressBitmap", "第二次调用方法");
                BitmapFactory.decodeFile(filePath, options);//todo 处理小米note2 第一次获取不到数据的问题
                outHeight = options.outHeight;
                outWidth = options.outWidth;
                if (options.outHeight < 0 || options.outWidth < 0) {
                    Log.e("rotateAndCompressBitmap", "第二次调用失败");
                    return null;
                }
                Log.e("rotateAndCompressBitmap", "第二次调用成功");
            }

            ExifInterface exifInterface = new ExifInterface(filePath);
            int result = exifInterface.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
            int rotate = 0;
            switch (result) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate = 90;
                    outHeight = options.outWidth;
                    outWidth = options.outHeight;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate = 270;
                    outHeight = options.outWidth;
                    outWidth = options.outHeight;
                    break;
                default:
                    break;
            }


            options.inJustDecodeBounds = false;
            // Calculate inSampleSize
            options.inSampleSize = calculateInSampleSize(outHeight, outWidth, reqHeight, reqWidth);

            bitmap = BitmapFactory.decodeFile(filePath, options);
            if (rotate > 0) {
                Matrix matrix = new Matrix();
                matrix.setRotate(rotate);
                Bitmap rotateBitmap = Bitmap.createBitmap(
                        bitmap, 0, 0, options.outWidth, options.outHeight, matrix, true);
                if (rotateBitmap != null) {
                    bitmap.recycle();
                    bitmap = rotateBitmap;

                }
            }

            ImageUtil.compressImage2(bitmap, file);


        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * 调图片的色调，饱和度，亮度
     *
     * @param bm
     * @param hue
     * @param saturation
     * @param lum
     * @return
     */
    public static Bitmap handleImageEffect(Bitmap bm, float hue, float saturation, float lum) {

        ColorMatrix hueMatrix = new ColorMatrix();
        hueMatrix.setRotate(0, hue);
        hueMatrix.setRotate(1, hue);
        hueMatrix.setRotate(2, hue);

        ColorMatrix saturationMatrix = new ColorMatrix();
        saturationMatrix.setSaturation(saturation);

        ColorMatrix lumMatrix = new ColorMatrix();
        lumMatrix.setScale(lum, lum, lum, 1);

        ColorMatrix imageMatrix = new ColorMatrix();
        imageMatrix.postConcat(hueMatrix);
        imageMatrix.postConcat(saturationMatrix);
        imageMatrix.postConcat(lumMatrix);


        /**
         * 设置好处理的颜色矩阵后，通过使用Paint类的setColorFilter()方法，将通过imageMatrix构造的
         * ColorMatrixColorFilter对象传递进去，并使用这个画笔来绘制原来的图像，从而将颜色矩阵作用到原图中
         */
        Paint paint = new Paint();
        paint.setColorFilter(new ColorMatrixColorFilter(imageMatrix));

        /**
         * Android系统也不允许直接修改原图，类似Photoshop中的锁定，必须通过原图创建一个同样大小的Bitmap，并将
         * 原图绘制到该Bitmap中，以一个副本的形式来修改图像。
         */
        Bitmap bmp = Bitmap.createBitmap(bm.getWidth(), bm.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmp);
        canvas.drawBitmap(bm, 0, 0, paint);

        return bmp;
    }


}
