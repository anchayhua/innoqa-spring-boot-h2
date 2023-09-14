package com.bezkoder.spring.jpa.h2;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class SpringBootJpaH2ApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetAllPrices() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/prices"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(4)); // Ajusta esto según tus datos de prueba
    }

    @Test
    public void testGetPriceById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/prices/{id}", 1))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.brandId").value(1));
    }

    // Para cumplir con los requerimientos

    @Test
    public void testRequest1() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/getPrice")
                        .param("brandId", "1")
                        .param("productId", "35455")
                        .param("date", "2020-06-14T10:00:00"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.brandId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.productId").value(35455))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(35.50));
    }

    @Test
    public void testRequest2() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/getPrice")
                        .param("brandId", "1")
                        .param("productId", "35455")
                        .param("date", "2020-06-14T16:00:00"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.brandId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.productId").value(35455))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(25.45));
    }

    @Test
    public void testRequest3() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/getPrice")
                        .param("brandId", "1")
                        .param("productId", "35455")
                        .param("date", "2020-06-14T21:00:00"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.brandId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.productId").value(35455))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(35.50)); // Ajusta esto según tus datos de prueba
    }

    @Test
    public void testRequest4() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/getPrice")
                        .param("brandId", "1")
                        .param("productId", "35455")
                        .param("date", "2020-06-15T10:00:00"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.brandId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.productId").value(35455))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(30.50)); // Ajusta esto según tus datos de prueba
    }

    @Test
    public void testRequest5() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/getPrice")
                        .param("brandId", "1")
                        .param("productId", "35455")
                        .param("date", "2020-06-16T21:00:00"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.brandId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.productId").value(35455))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(38.95)); // Ajusta esto según tus datos de prueba
    }

}
