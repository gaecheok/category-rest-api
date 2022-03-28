package com.test.categoryrestapi;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.categoryrestapi.controller.CategoryController;
import com.test.categoryrestapi.model.Category;
import com.test.categoryrestapi.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = CategoryController.class)
public class CategoryControllerTest {
    @MockBean
    private CategoryService categoryService;

    private MockMvc mockMvc;
    private final ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(new CategoryController(categoryService)).build();
    }

    @Test
    @DisplayName("전체 조회 테스트")
    void test_findAllCategory() throws Exception {
        //given
        given(categoryService.getAllCategoryWithReference())
                .willReturn(categoryList());

        //when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get("/category")
        );
        final MvcResult mvcResult = resultActions.andExpect(status().isOk()).andReturn();
        final List<Category> resultList = mapper.readValue(mvcResult.getResponse().getContentAsString(),  new TypeReference<List<Category>>(){});

        //then
        assertThat(resultList.size()).isEqualTo(3);
    }

    private List<Category> categoryList() {
        final List<Category> categoryList = new ArrayList<>();
        categoryList.add(new Category(100,"test1",0));
        categoryList.add(new Category(200,"test2",0));
        categoryList.add(new Category(300,"test2",0));
        return categoryList;
    }

    @Test
    @DisplayName("단건 조회 테스트")
    void test_findByIdCategory() throws Exception {
        //given
        given(categoryService.getCategoryWithReference(any()))
                .willReturn(new Category(100, "test1", 0));

        //when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get("/category/100")
        );
        final MvcResult mvcResult = resultActions.andExpect(status().isOk()).andReturn();
        final Category category = mapper.readValue(mvcResult.getResponse().getContentAsString(), Category.class);

        //then
        assertThat(category.getCategoryId()).isEqualTo(100);
        assertThat(category.getDescription()).isEqualTo("test1");
        assertThat(category.getReferenceId()).isEqualTo(0);
    }

    @Test
    @DisplayName("등록 테스트")
    void test_insertCategory() throws Exception {
        //given
        given(categoryService.save(any()))
                .willReturn(
                        new Category(100, "test1", 0)
                );
        //when
        final ResultActions actions =
                mockMvc.perform(
                        post("/category")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .characterEncoding("UTF-8")
                                .content(
                                        "{"
                                                + "\"categoryId\":100,"
                                                + "\"description\":\"test1\","
                                                + "\"referenceId\":0"
                                                + "}")
                );

        //then
        actions.andExpect(status().isCreated());
    }

    @Test
    @DisplayName("수정 테스트")
    void test_updateCategory() throws Exception {
        //given
        given(categoryService.save(any()))
                .willReturn(
                        new Category(100, "test1", 0)
                );
        //when
        final ResultActions actions =
                mockMvc.perform(
                        put("/category/100")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .characterEncoding("UTF-8")
                                .content(
                                        "{"
                                                + "\"categoryId\":100,"
                                                + "\"description\":\"test1\","
                                                + "\"referenceId\":0"
                                                + "}")
                );

        //then
        actions.andExpect(status().isOk());
    }

    @Test
    @DisplayName("삭제 테스트")
    void test_deleteCategory() throws Exception {
        //given

        //when
        final ResultActions actions =
                mockMvc.perform(
                        delete("/category/100")
                                .accept(MediaType.APPLICATION_JSON)
                );

        //then
        actions.andExpect(status().isOk());
    }



}
