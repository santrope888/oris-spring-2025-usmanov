package com.electronics.store;

import com.electronics.store.model.AttributeTypeEntity;
import com.electronics.store.model.AttributeValueEntity;
import com.electronics.store.model.CategoryEntity;
import com.electronics.store.model.ProductEntity;
import com.electronics.store.repository.AttributeTypeRepository;
import com.electronics.store.repository.AttributeValueRepository;
import com.electronics.store.repository.CategoryRepository;
import com.electronics.store.repository.ProductRepository;
import com.electronics.store.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class StoreIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private AttributeTypeRepository attributeTypeRepository;

    @Autowired
    private AttributeValueRepository attributeValueRepository;

    @Autowired
    private UserRepository userRepository;

    @AfterEach
    void cleanDatabase() {
        attributeValueRepository.deleteAll();
        productRepository.deleteAll();
        attributeTypeRepository.deleteAll();
        categoryRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @WithMockUser
    void catalogCartFlowWorksFromCategoryToQuantityUpdate() throws Exception {
        ProductEntity product = createProductWithAttribute("Smartphones", "smartphones", "Phone 15", "Black");

        mockMvc.perform(get("/catalog"))
                .andExpect(status().isOk())
                .andExpect(view().name("categories"))
                .andExpect(model().attributeExists("categories"));

        mockMvc.perform(get("/catalog/smartphones"))
                .andExpect(status().isOk())
                .andExpect(view().name("products"))
                .andExpect(model().attributeExists("category", "products", "filterOptions"));

        MvcResult addResult = mockMvc.perform(post("/cart/add/{productId}", product.getId())
                        .param("quantity", "2"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/cart"))
                .andReturn();

        MockHttpSession session = (MockHttpSession) addResult.getRequest().getSession(false);

        mockMvc.perform(post("/cart/update")
                        .session(session)
                        .param("productId", product.getId().toString())
                        .param("quantity", "5"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/cart"));

        mockMvc.perform(get("/cart").session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("cart"))
                .andExpect(model().attributeExists("cart"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void adminCreatesCategoryAttributeTypeProductWithAttributeAndDeletesProduct() throws Exception {
        mockMvc.perform(post("/admin/categories/save")
                        .param("name", "Laptops")
                        .param("slug", "laptops")
                        .param("description", "Portable computers"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/categories"));

        CategoryEntity category = categoryRepository.findBySlug("laptops").orElseThrow();

        mockMvc.perform(post("/admin/attribute-types/save")
                        .param("name", "RAM")
                        .param("category.id", category.getId().toString()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/attribute-types"));

        AttributeTypeEntity attributeType = attributeTypeRepository.findByCategoryId(category.getId()).get(0);

        mockMvc.perform(post("/admin/products/save")
                        .param("name", "ThinkPad")
                        .param("slug", "thinkpad")
                        .param("description", "Business laptop")
                        .param("price", "120000.00")
                        .param("category.id", category.getId().toString())
                        .param("attrTypeIds", attributeType.getId().toString())
                        .param("attrValues", "32GB"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/products"));

        ProductEntity product = productRepository.findAll().get(0);
        List<AttributeValueEntity> values = attributeValueRepository.findAll();

        assertThat(product.getName()).isEqualTo("ThinkPad");
        assertThat(values).hasSize(1);
        assertThat(values.get(0).getValue()).isEqualTo("32GB");

        mockMvc.perform(get("/admin/products"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin-products-list"))
                .andExpect(model().attributeExists("products"));

        mockMvc.perform(get("/admin/products/delete/{id}", product.getId()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/products"));

        assertThat(productRepository.findById(product.getId())).isEmpty();
        assertThat(attributeValueRepository.findAll()).isEmpty();
    }

    @Test
    @WithMockUser
    void missingRouteReturns404() throws Exception {
        mockMvc.perform(get("/missing-page"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    void missingCategoryReturns404() throws Exception {
        mockMvc.perform(get("/catalog/unknown-category"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    void cartUpdateWithoutProductIdReturns400() throws Exception {
        mockMvc.perform(post("/cart/update")
                        .param("quantity", "2"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    void addingUnknownProductReturns404() throws Exception {
        mockMvc.perform(post("/cart/add/{productId}", 999999L)
                        .param("quantity", "1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void protectedCatalogRedirectsAnonymousUserToLogin() throws Exception {
        mockMvc.perform(get("/catalog"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }

    private ProductEntity createProductWithAttribute(String categoryName,
                                                     String categorySlug,
                                                     String productName,
                                                     String attrValue) {
        CategoryEntity category = CategoryEntity.builder()
                .name(categoryName)
                .slug(categorySlug)
                .description(categoryName + " description")
                .build();
        category = categoryRepository.save(category);

        AttributeTypeEntity attributeType = AttributeTypeEntity.builder()
                .name("Color")
                .category(category)
                .build();
        attributeType = attributeTypeRepository.save(attributeType);

        ProductEntity product = ProductEntity.builder()
                .name(productName)
                .slug(productName.toLowerCase().replace(" ", "-"))
                .description(productName + " description")
                .price(new BigDecimal("1000.00"))
                .category(category)
                .build();
        product = productRepository.save(product);

        AttributeValueEntity value = AttributeValueEntity.builder()
                .product(product)
                .attributeType(attributeType)
                .value(attrValue)
                .build();
        attributeValueRepository.save(value);

        return product;
    }
}
