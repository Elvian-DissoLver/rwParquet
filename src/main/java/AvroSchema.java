
import java.util.List;

public class AvroSchema {

    private String type;
    private String name;
    private String namespaces;
    private List<AvroSchemaField> fields;

    public AvroSchema() { }

    public AvroSchema(List<AvroSchemaField> fields) {
        this.type = "record";
        this.name = "data_schema";
        this.fields = fields;
        this.namespaces = "laniakea";
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNamespaces() {
        return namespaces;
    }

    public void setNamespaces(String namespaces) {
        this.namespaces = namespaces;
    }

    public List<AvroSchemaField> getFields() {
        return fields;
    }

    public void setFields(List<AvroSchemaField> fields) {
        this.fields = fields;
    }
}
