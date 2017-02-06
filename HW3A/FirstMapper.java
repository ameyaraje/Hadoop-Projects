import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class MapR extends Mapper<LongWritable, Text, LongWritable, Text> {

	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
		List<String> items = Arrays.asList(value.toString().split("\\t"));
		String input_key = items.get(0);
		String input_value = items.get(1);
		long l = Long.parseLong(input_key);
		key.set(l);		 
		context.write(key, new Text(input_value));
	}

}