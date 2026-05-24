package org.example.onlinesalessystem.services;

import org.example.onlinesalessystem.exceptions.InvalidPriceException;
import org.example.onlinesalessystem.exceptions.InvalidQuantityException;
import org.example.onlinesalessystem.exceptions.ProductNotFoundException;
import org.example.onlinesalessystem.models.Product;
import org.example.onlinesalessystem.models.dto.ProductDto;
import org.example.onlinesalessystem.repository.ProductRepository;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(){
        this.productRepository = new ProductRepository();
    }

    public Product addProduct(ProductDto productDto) throws Exception {
        validateProduct(productDto);

        Product product = new Product(
                productDto.getName(),
                productDto.getDescription(),
                productDto.getPrice(),
                productDto.getQuantity(),
                productDto.getCategory(),
                productDto.getSellerId()
        );
        return productRepository.create(product);
    }

    public Product updateProduct(Product product) throws Exception {
        if (productRepository.getById(product.getId()) == null) {
            throw new ProductNotFoundException("Produkti nuk u gjet.");
        }

        if (product.getPrice() <= 0) {
            throw new InvalidPriceException("Cmimi duhet te jete me i madh se 0.");
        }

        if (product.getQuantity() < 0) {
            throw new InvalidQuantityException("Sasia nuk mund te jete negative.");
        }
         return productRepository.update(product);
    }

    public boolean deleteProduct(int productId) throws Exception {
        Product product = productRepository.getById(productId);

        if (product == null) {
            throw new ProductNotFoundException("Produkti nuk u gjet.");
        }

        return productRepository.delete(productId);
    }

    public Product getProductById(int productId) throws Exception {
        Product product = productRepository.getById(productId);

        if (product == null) {
            throw new ProductNotFoundException("Produkti nuk u gjet.");
        }

        return product;
    }

    public List<Product> getAllProducts() throws Exception {
        return productRepository.getAll();
    }

    public List<Product> searchProducts(String keyword) throws Exception {
        if (keyword == null || keyword.trim().isEmpty()) {
            return productRepository.getAll();
        }
        return productRepository.searchByName(keyword);
    }

    public List<Product> getProductsByCategory(String category) throws Exception {
        if (category == null || category.trim().isEmpty()) {
            return productRepository.getAll();
        }
        return productRepository.getByCategory(category);
    }

    public void  validateProduct(ProductDto productDto) throws Exception {
        if (productDto.getName() == null || productDto.getName().trim().isEmpty()) {
            throw new Exception("Emri i produktit nuk duhet te jete bosh.");
        }

        if (productDto.getPrice() <= 0) {
            throw new InvalidPriceException("Cmimi duhet te jete me i madh se 0.");
        }

        if (productDto.getQuantity() < 0) {
            throw new InvalidQuantityException("Sasia nuk duhet te jete negative.");
        }

        if (productDto.getCategory() == null || productDto.getCategory().trim().isEmpty()) {
            throw new Exception("Kategoria nuk duhet te jete bosh.");
        }
        if (productDto.getSellerId() <= 0) {
            throw new Exception("Seller ID nuk eshte valid.");
        }
    }

    public List<Product> getProductsBySellerId(int sellerId) throws Exception {
        if (sellerId <= 0) {
            throw new Exception("Seller ID nuk eshte valid.");
        }

        return productRepository.getBySellerId(sellerId);
    }

    public boolean deleteMyProduct(int productId, int sellerId) throws Exception {
        Product product = productRepository.getById(productId);

        if (product == null) {
            throw new ProductNotFoundException("Produkti nuk u gjet.");
        }

        if (product.getSellerId() != sellerId) {
            throw new Exception("Nuk keni leje ta fshini kete produkt.");
        }

        return productRepository.softDelete(productId);
    }
}
