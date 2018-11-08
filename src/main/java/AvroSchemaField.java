

import java.util.List;

public class AvroSchemaField {
    private String name;
    private List<String> type;

    public AvroSchemaField() { }

    public AvroSchemaField(String name, List<String> type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getType() {
        return type;
    }

    public void setType(List<String> type) {
        this.type = type;
    }
}
