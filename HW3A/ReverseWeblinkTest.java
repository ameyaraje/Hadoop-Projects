import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.MapWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import com.google.common.primitives.UnsignedInteger;

import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;


public class generateRWL {
	  public static void main(String[] args) throws Exception {
		    Configuration conf = new Configuration();
		    Job job = Job.getInstance(conf, "Reverse Weblink Count");
		    job.setJarByClass(RWL.class);
		    job.setMapperClass(WeblinkMapper.class);
		    job.setReducerClass(WeblinkReducer.class);
		    job.setOutputKeyClass(Text.class);
		    job.setOutputValueClass(MapWritable.class);
		    job.setMapOutputKeyClass(Text.class);
		    job.setMapOutputValueClass(Text.class);
		    FileInputFormat.addInputPath(job, new Path("src/main/resources/url_map.txt"));
		    FileOutputFormat.setOutputPath(job, new Path("src/main/resources/output11"));
		    if(job.waitForCompletion(true)) {
		    	System.out.println("Job inverted complete");
		    }
		    else {
		    	System.out.println("Job inverted Failed!!!");
		    	System.exit(0);
		    }

			Configuration conf1 = new Configuration();
			Job job1 = new Job(conf1, "Sorting Mapreduce App");
			job1.setJarByClass(generateRWL.class);
			job1.setInputFormatClass(TextInputFormat.class);
			job1.setOutputFormatClass(TextOutputFormat.class);
			job1.setMapperClass(MapR.class);
			job1.setReducerClass(ReduceR.class);
			job1.setSortComparatorClass(SortFiles.class);
			job1.setNumReduceTasks(1);
			FileInputFormat.addInputPath(job1, new Path("src/main/graph"));
			FileOutputFormat.setOutputPath(job1, new Path("src/main/sortedGraph"));
			if(job1.waitForCompletion(true)) {
		    	System.out.println("Sorting Job complete");
		    }
		    else  {
		    	System.out.println("Couldnt Sort");
		    	System.exit(0);
		    }
		    
		     
			System.out.println("Completed Sorting");
			System.exit(0);
		    
		  }

}



