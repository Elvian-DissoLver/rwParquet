

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

//import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.htrace.fasterxml.jackson.databind.ObjectMapper;
import org.apache.parquet.avro.AvroParquetWriter;
import org.apache.parquet.hadoop.ParquetWriter;
import org.apache.parquet.hadoop.metadata.CompressionCodecName;


public class WParquet {
    public Schema parseSchema() throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        AvroSchema avroSchema = mapper.readValue(new File("EmpSchema.avsc"), AvroSchema.class);
        String jsonSchema = mapper.writeValueAsString(avroSchema);
        Schema schema = new Schema.Parser().parse(jsonSchema);

//        Schema.Parser parser = new Schema.Parser();
//        Schema schema = null;
//        try {
//            // pass path to schema
//            schema = parser.parse(ClassLoader.getSystemResourceAsStream("EmpSchema.avsc"));
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        return schema;

    }

    public List<GenericData.Record> getRecords(Schema schema){
        List<GenericData.Record> recordList = new ArrayList<GenericData.Record>();
        GenericData.Record record = new GenericData.Record(schema);
        // Adding 2 records
        record.put("id", 1);
        record.put("useragent", "emp1");
        record.put("ip", "192.0.0.2");
        record.put("path", "/D1");
        recordList.add(record);

        record = new GenericData.Record(schema);
        record.put("id", 2);
        record.put("useragent", "emp2");
        record.put("ip", "192.0.0.2");
        record.put("path", "/D2");
        recordList.add(record);

        return recordList;
    }


    public void writeToParquet(List<GenericData.Record> recordList, Schema schema) throws IOException {
        // Path to Parquet file in HDFS
        Configuration conf = new Configuration(false);
        conf.set("fs.defaultFS", "hdfs://localhost:9000");

        FileSystem fs = FileSystem.get(conf);

        String uriStr = fs.getWorkingDirectory() + "/test2/EmpRecord.parquet";
        URI uri = URI.create(uriStr);
        Path path = new Path(uri);

//        Path path = new Path("/test/EmpRecord.parquet");
        ParquetWriter<GenericData.Record> writer = null;
        // Creating ParquetWriter using builder
        try {
            writer = AvroParquetWriter.<GenericData.Record>builder(path)
                    .withRowGroupSize(ParquetWriter.DEFAULT_BLOCK_SIZE)
                    .withPageSize(ParquetWriter.DEFAULT_PAGE_SIZE)
                    .withSchema(schema)
                    .withConf(new Configuration())
                    .withCompressionCodec(CompressionCodecName.SNAPPY)
                    .withValidation(false)
                    .withDictionaryEncoding(false)
                    .build();

            for (GenericData.Record record : recordList) {
                writer.write(record);
            }

        }catch(IOException e) {
            e.printStackTrace();
        }finally {
            if(writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
