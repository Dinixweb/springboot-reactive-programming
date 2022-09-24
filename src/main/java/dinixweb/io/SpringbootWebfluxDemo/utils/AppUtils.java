package dinixweb.io.SpringbootWebfluxDemo.utils;

import com.fasterxml.jackson.databind.util.BeanUtil;
import dinixweb.io.SpringbootWebfluxDemo.entity.ProductEntity;
import dinixweb.io.SpringbootWebfluxDemo.model.Products;
import org.springframework.beans.BeanUtils;

public class AppUtils {

        public static Products entityToModel(ProductEntity productEntity){
                Products products = new Products();
                BeanUtils.copyProperties(productEntity, products);
                return products;
        }
        public static ProductEntity modelToEntity(Products products){
                ProductEntity productEntity = new ProductEntity();
                BeanUtils.copyProperties(products, productEntity);
                return productEntity;
        }

}
