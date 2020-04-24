package com.xiaochen.common.utils;

import android.os.Environment;
import android.os.StatFs;

import java.io.File;
import java.io.FileOutputStream;

/**
 * @author zlc
 * email : zlc921022@163.com
 * desc :
 */
public class SdCardUtil {

    private static FileOutputStream mOutputStream;
    private static FileOutputStream mOutputStream2;

    private SdCardUtil() {
    }

    /**
     * 判断SDCard是否可用
     *
     * @return
     */
    public static boolean isSDCardEnable() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }

    /**
     * 获取sd卡路径
     *
     * @return
     */
    public static String getSDCardPath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath()
                + File.separator;
    }

    /**
     * 获得SDCard的全部空间大小(单位:kb)
     */
    public static long getSDCardSize() {
        if (isSDCardEnable()) {
            String baseDir = getSDCardPath();
            //获取StatFs对象
            StatFs statFs = new StatFs(baseDir);
            //通过StatFs对象获取块的数量
            long blockCount = statFs.getBlockCount();
            //通过StatFs对象获取每块的大小（字节）
            long blockSize = statFs.getBlockSize();
            return (blockCount * blockSize / 1024);
        }
        return 0;
    }

    /**
     * 获取SD卡的剩余容量 单位kb
     *
     * @return
     */
    public static long getSDCardAvailSize() {
        if (isSDCardEnable()) {
            StatFs stat = new StatFs(getSDCardPath());
            // 获取空闲的数据块的数量
            long availableBlocks = (long) stat.getAvailableBlocks();
            // 获取单个数据块的大小（byte）
            long freeBlocks = stat.getBlockSize();
            return (freeBlocks * availableBlocks) / 1024;
        }
        return 0;
    }

    /**
     * 获取指定路径所在空间的剩余可用容量字节数，单位byte
     *
     * @param filePath
     * @return 容量字节 SDCard可用空间，内部存储可用空间
     */
    public static long getFreeBytes(String filePath) {
        // 如果是sd卡的下的路径，则获取sd卡可用容量
        if (filePath.startsWith(getSDCardPath())) {
            filePath = getSDCardPath();
        } else {
            // 如果是内部存储的路径，则获取内存存储的可用容量
            filePath = Environment.getDataDirectory().getAbsolutePath();
        }
        StatFs stat = new StatFs(filePath);
        long availableBlocks = (long) stat.getAvailableBlocks() - 4;
        return stat.getBlockSize() * availableBlocks;
    }


    /**
     * 判断一个文件是否存在
     *
     * @param filePath
     * @return
     */
    public static boolean isFileExists(String filePath) {
        if (isSDCardEnable()) {
            String dir = getSDCardPath() + filePath;
            return new File(dir).exists();
        }
        return false;
    }

    /**
     * 删除一个文件
     *
     * @param filepath
     * @return
     */
    public static boolean removFileFormSDCard(String filepath) {
        if (isSDCardEnable()) {
            String dir = getSDCardPath() + filepath;
            File file = new File(dir);
            if (file.exists()) {
                return file.delete();
            }
        }
        return false;
    }

    /**
     * 获取系统存储路径
     *
     * @return
     */
    public static String getRootDirectoryPath() {
        return Environment.getRootDirectory().getAbsolutePath();
    }

    /**
     * 往文件中写入字节数组
     *
     * @param bytes
     */
    public static void writeBytesToFile(byte[] bytes) {
        String path = getSDCardPath();
        File file = new File(path, "心电1.txt");
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            if (mOutputStream == null) {
                mOutputStream = new FileOutputStream(file);
            }
            mOutputStream.write(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 往文件中写入字符串
     *
     * @param
     */
    public static void writeIntValueToFile(int value) {
        String path = getSDCardPath();
        File file = new File(path, "心电2.txt");
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            if (mOutputStream2 == null) {
                mOutputStream2 = new FileOutputStream(file);
            }
            mOutputStream2.write((value + ",").getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
