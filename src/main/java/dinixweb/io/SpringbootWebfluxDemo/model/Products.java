package dinixweb.io.SpringbootWebfluxDemo.model;


import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Products {

    private String id;
    private String name;
    private int qty;
    private double price;
}
