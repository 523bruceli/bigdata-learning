package com.zouxxyy.mr.reducejoin;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * 在reduce中实现join
 */

public class RJDriver {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {

        args = new String[] { "data/input/reduceJoin", "data/output" };

        // 1 获取Job对象
        Job job = Job.getInstance(new Configuration());

        // 2 设置类路径
        job.setJarByClass(RJDriver.class);

        // 3 关联Map和Reduce类
        job.setMapperClass(RJMapper.class);
        job.setReducerClass(RJReducer.class);

        // 添加Comparator
        job.setGroupingComparatorClass(RJComparator.class);

        // 4 设置Mapper输出的key和value类型
        job.setMapOutputKeyClass(OrderBean.class);
        job.setMapOutputValueClass(NullWritable.class);

        // 5 设置数据输出的key和value类型
        job.setOutputKeyClass(OrderBean.class);
        job.setOutputValueClass(NullWritable.class);

        // 6. 设置输入输出路径
        FileInputFormat.setInputPaths(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        boolean b = job.waitForCompletion(true);
        System.exit(b  ? 0 : 1);

    }
}
