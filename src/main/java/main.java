import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;

import java.util.List;

public class main {
    public static void main(String[] args) throws Exception {
//        String hadoopPath = "your-hadoop-path";
//        hdfs HDFS = new hdfs(hadoopPath );
        // First thing - parse the schema as it will be used
        WParquet wParquet = new WParquet();
        Schema schema = wParquet.parseSchema();
        List<GenericData.Record> recordList = wParquet.getRecords(schema);
        wParquet.writeToParquet(recordList, schema);


    }
}
