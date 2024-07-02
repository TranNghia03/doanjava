package com.hutech.demo.Controler;


import com.hutech.demo.models.Category;
import com.hutech.demo.models.Product;
import com.hutech.demo.repository.ProductRepository;
import com.hutech.demo.service.CategoryService;
import com.hutech.demo.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;


@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;  // Đảm bảo bạn đã inject CategoryService
    private Path path;

    // Display a list of all products
    @GetMapping
    public String showProductList(Model model ,Authentication authentication) {
        List<Category> categories = categoryService.getAllCategories();
        List<Product> products;
        boolean isAdminOrEmployee = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(role -> role.equals("ADMIN") || role.equals("EMPLOYEE"));

        if (isAdminOrEmployee) {
            products = productService.getAllNotDeletedProducts();

        }
        model.addAttribute("products", productService.getAllProducts());
        model.addAttribute("categories", categories);
        return "/products/product-list";
    }

    // For adding a new product
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryService.getAllCategories()); //Load categories
        return "/products/add-product";
    }
    // Process the form for adding a new product

    @PostMapping("/add")
    public String addProducts(@Valid Product product, BindingResult result, @RequestParam("imageFile") MultipartFile imageFile) {
        if (result.hasErrors()) {
            return "products/add-product";
        }
        if (!imageFile.isEmpty()) {
            try {
                String imageName = saveImageStatic(imageFile);
                product.setImage("/images/" + imageName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        productService.addProduct(product);
        return "redirect:/products";
    }

    private String saveImageStatic(MultipartFile image) throws IOException {
        File saveFile = new ClassPathResource("static/images").getFile();
        String fileName = UUID.randomUUID() + "." + StringUtils.getFilenameExtension(image.getOriginalFilename());
        Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + fileName);
        Files.copy(image.getInputStream(), path);
        return fileName;
    }


    // For editing a product
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Product product = productService.getProductById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid product Id:" + id));
        model.addAttribute("product", product);
        model.addAttribute("categories", categoryService.getAllCategories());  // Load categories
        return "/products/update-product";
    }
    // Process the form for updating a product
    @PostMapping("/update/{id}")
    public String updateProduct(@PathVariable Long id, @Valid Product product, BindingResult result, @RequestParam("imageFile") MultipartFile imageFile) {
        if (result.hasErrors()) {
            product.setId(id);
            return "/products/update-product";
        }
        if (!imageFile.isEmpty()) {
            try {

                String imageName = saveImageStatic(imageFile);
                product.setImage("/images/" + imageName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Product existingProduct = productService.getProductById(id).orElseThrow(() -> new IllegalArgumentException("Invalid product Id:" + id));
            product.setImage(existingProduct.getImage());
        }

        productService.updateProduct(product);
        return "redirect:/products";
    }


    // Handle request to delete a product
    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProductById(id);
        return "redirect:/products";
    }
    @GetMapping("/search")
    public String searchProductsByName(@RequestParam String keyword, Model model) {
        List<Product> products = productService.searchByName(keyword);
        List<Category> categories = categoryService.getAllCategories(); // Lấy tất cả categories
        model.addAttribute("products", products);
        model.addAttribute("categories", categories); // Thêm tất cả categories vào model
        return "/products/product-list"; // Template hiển thị danh sách sản phẩm
    }

    @GetMapping("/category")
    public String showProductsByCategory(@RequestParam Long id, Model model) {
        Category category = categoryService.getCategoryById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid category Id:" + id));
        List<Product> products = productService.getProductsByCategory(category);
        List<Category> categories = categoryService.getAllCategories(); // Lấy tất cả categories
        model.addAttribute("products", products);
        model.addAttribute("categories", categories); // Thêm tất cả categories vào model
        return "/products/product-list";
    }

    @GetMapping("/sortByPriceAsc")
    public String showProductsSortedByPriceAsc(Model model) {
        List<Product> products = productService.getAllProductsSortedByPriceAsc();
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("products", products);
        model.addAttribute("categories", categories);
        return "/products/product-list";
    }

    @GetMapping("/sortByPriceDesc")
    public String showProductsSortedByPriceDesc(Model model) {
        List<Product> products = productService.getAllProductsSortedByPriceDesc();
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("products", products);
        model.addAttribute("categories", categories);
        return "/products/product-list";
    }
    @GetMapping("/toggleActive/{id}")
    public String toggleProductActive(@PathVariable("id") Long id) {
        productService.toggleProductActive(id);
        return "redirect:/products";
    }
}