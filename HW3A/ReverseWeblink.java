import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.MapWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;

public class RWL {
	public static class WeblinkMapper extends Mapper<LongWritable, Text, Text, Text> {
		private Text word = new Text();

		public void map(LongWritable key, Text value, Context context)  throws IOException, InterruptedException {
			List<String> items = Arrays.asList(value.toString().replaceAll("\\[", "").replaceAll("\\]","").split("\\s*,\\s*"));
			String input_key = items.get(0);
			int index = 1;
			while (index < items.size()) {
				word.set(items.get(index));
				context.write(word, new Text(input_key));
				index++;
			}
		}
	}

	public static class WeblinkReducer extends Reducer<Text, Text,LongWritable,Text> {
		private LongWritable result = new LongWritable();

		@Override
		public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {

			String inverted = key.toString();
			inverted += "=>  { ";

			HashMap<Text, IntWritable> map = new HashMap<Text, IntWritable>();
			int count = 0;
			for(Text value: values){
				count++;
				inverted +=value.toString();
				inverted +="  ;  ";

			}
			inverted += " } ";

			result.set(count);

			MapWritable outputMap = new MapWritable();
			outputMap.putAll(map);
			context.write(result,new Text(inverted));
		}
	}

}
