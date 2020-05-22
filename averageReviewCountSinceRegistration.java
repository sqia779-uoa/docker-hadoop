package org.myorg;

import java.io.IOException;
import java.util.*;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.json.*;

/**
 * How many reviews have been created if the user account is created for over 3 years
**/
public class ReviewFrequency {
    public static class Map extends Mapper<LongWritable, Text, Text, Text> {
        String reviewCount;
        String createdSince;
        String lines = value.toString();
        String[] users = lines.split("\\n");

        try {
            for (int i = 0 ; i < users.length; i++) {
                JSONObject obj = new JSONObject(users[i]);
                reviewCount = obj.getString("review_count");
                createdSince = obj.getString("yelping_since");
                context.write(new Text(reviewCount), new Text(createdSince));
            }
        }
        catch(JSONException e) {
            e.printStackTrace();
        }
    }

    // public static class Reduce extends Reducer<Text, Text, NullWritable, Text> {
    //     public void reduce(Text key, Iterable<Text> values, Context context)
    //         throws IOException, InterruptedException {
    //         try{
    //             JSONObject obj = new JSONObject();
    //             JSONArray ja = new JSONArray();
    //             for(Text val : values){
    //                 JSONObject jo = new JSONObject().put("review_count", val.toString());
    //                 ja.put(jo);
    //             }
    //             obj.put("review_coun", ja);
    //             obj.put("author", key.toString());
    //             context.write(NullWritable.get(), new Text(obj.toString()));
    //         }catch(JSONException e){
    //             e.printStackTrace();
    //         }
    //     }
    // }

    public static void main(String[] args) throws Exception
    {
        Configuration conf = new Configuration();

        Job job = new Job(conf, "ReviewFrequency");
        job.setJarByClass(ReviewFrequency.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        job.setMapperClass(Map.class);
        // job.setReducerClass(Reduce.class);

        job.setInputFormatClass(TextInputFormat.class);
        job.setOutputFormatClass(TextOutputFormat.class);

        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        job.waitForCompletion(true);
    }
}