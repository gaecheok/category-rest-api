package com.test.categoryrestapi;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.categoryrestapi.controller.CategoryController;
import com.test.categoryrestapi.model.Category;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CategoryRestApiApplicationTests {

    @Autowired
    CategoryController controller;

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    @DisplayName("저장 후 조회")
    void saveAndFind() throws Exception {
        //저장
        mockMvc.perform(
                post("/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(
                                "{"
                                        + "\"categoryId\":1000,"
                                        + "\"description\":\"test10\","
                                        + "\"referenceId\":0"
                                        + "}")
        ).andExpect(status().isCreated());

        MvcResult mvcResult = mockMvc.perform(
                get("/category")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        String jsonResult = mvcResult.getResponse().getContentAsString();
        boolean isId1000 = false;
        JsonNode rootNode = mapper.readTree(jsonResult);
        for (JsonNode jsonNode : rootNode) {
            if (jsonNode.findPath("categoryId").asInt(0) == 1000) {
                isId1000 = true;
                break;
            }
        }
        assertThat(isId1000).isTrue();
    }

    @Test
    @DisplayName("삭제 후 갯수 변화 체크")
    void deleteAndSizeCheck() throws Exception {

        mockMvc.perform(
                post("/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(
                                "{"
                                        + "\"categoryId\":1000,"
                                        + "\"description\":\"test10\","
                                        + "\"referenceId\":0"
                                        + "}")
        ).andExpect(status().isCreated());

        //초기 갯수 체크
        MvcResult oldSizeCheckResult = mockMvc.perform(
                get("/category")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        final List<Category> oldList = mapper.readValue(oldSizeCheckResult.getResponse().getContentAsString(),  new TypeReference<List<Category>>(){});

        //삭제
        mockMvc.perform(
                        delete("/category/1000")
                                .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andReturn();

        //삭제 후 갯수 체크
        MvcResult newSizeCheckResult = mockMvc.perform(
                        get("/category")
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        final List<Category> newList = mapper.readValue(newSizeCheckResult.getResponse().getContentAsString(),  new TypeReference<List<Category>>(){});

        //갯수 비교
        assertThat(oldList.size() - 1).isEqualTo(newList.size());
    }

    @Test
    @DisplayName("업데이트, 단건조회 테스트")
    void updateAndFindOne() throws Exception {

        mockMvc.perform(
                post("/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(
                                "{"
                                        + "\"categoryId\":1000,"
                                        + "\"description\":\"test10\","
                                        + "\"referenceId\":0"
                                        + "}")
        ).andExpect(status().isCreated());

        MvcResult oldFindOneResult = mockMvc.perform(
                        get("/category/1000")
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        Category oldCategory = mapper.readValue(oldFindOneResult.getResponse().getContentAsString(), Category.class);

        String updatedText = "updateText";
        mockMvc.perform(
                put("/category/1000")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(
                                "{"
                                        + "\"categoryId\":1000,"
                                        + "\"description\":\""+ updatedText + "\","
                                        + "\"referenceId\":0"
                                        + "}")
        ).andExpect(status().isOk());

        MvcResult newFindOneResult = mockMvc.perform(
                        get("/category/1000")
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        Category newCategory = mapper.readValue(newFindOneResult.getResponse().getContentAsString(), Category.class);

        assertThat(oldCategory.getDescription()).isNotEqualTo(updatedText);
        assertThat(newCategory.getDescription()).isEqualTo(updatedText);
    }
}
