package dinixweb.io.SpringbootWebfluxDemo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class ProductEntity {

    private String id;
    private String name;
    private int qty;
    private double price;
}
