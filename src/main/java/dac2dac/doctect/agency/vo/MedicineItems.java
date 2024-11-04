package dac2dac.doctect.agency.vo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Arrays;
import java.util.List;

@Data
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MedicineItems {
    @JsonProperty("item")
    private List<MedicineItem> medicineItems;

    @JsonCreator
    public MedicineItems(@JsonProperty("body") JsonNode node) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode itemNode = node.findValue("items");
        this.medicineItems = Arrays.stream(objectMapper.treeToValue(itemNode, MedicineItem[].class)).toList();
    }
}
