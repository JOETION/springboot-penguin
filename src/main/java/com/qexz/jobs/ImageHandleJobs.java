package com.qexz.jobs;

/* 				    
 **********************************************
 *      DATE           PERSON       REASON
 *    2019/4/2          FXY        Created
 **********************************************
 */

import net.coobird.thumbnailator.Thumbnails;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 尽可能成为一个公用的组件
 */
@Component
public class ImageHandleJobs {

    private static ConcurrentLinkedQueue<Param> jobs = new ConcurrentLinkedQueue<>();

    public final static long ONE_MINUTE = 60 * 1000;

    @Scheduled(fixedDelay = ONE_MINUTE)
    public void fixedDelayJob() throws IOException {

        while (!jobs.isEmpty()) {
            Param param = removeImageHandleJob();
            //将内存中的数据裁剪并写入磁盘
            Thumbnails.of(param.inputStream)
                    .size(param.width, param.height)
                    .outputQuality(param.outQuality)
                    .toFile(param.toFile);
        }
    }

    /**
     * 通过参数添加任务
     *
     * @param param
     */
    public static void addImageHandleJob(Param param) {
        jobs.add(param);
    }

    /**
     * 通过流添加任务
     *
     * @param inputStream
     */
    public static void addImageHandleJob(InputStream inputStream) {
        Param param = new Param().setInputStream(inputStream).setOutQuality(1f).setSize(150, 150);
        addImageHandleJob(param);
    }

    /**
     * 移除任务
     */
    private static Param removeImageHandleJob() {
        return jobs.poll();
    }

    /**
     * 任务参数
     */
    public static class Param {
        private int width;
        private int height;
        private double outQuality;
        private InputStream inputStream;
        private File toFile;

        public Param setSize(int width, int height) {
            this.width = width;
            this.height = height;
            return this;
        }

        public Param setOutQuality(double quality) {
            this.outQuality = quality;
            return this;
        }

        public Param setInputStream(InputStream inputStream) {
            this.inputStream = inputStream;
            return this;
        }

        public Param setToFile(File toFile) {
            this.toFile = toFile;
            return this;
        }


    }
}

