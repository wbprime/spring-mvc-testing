package me.wbprime.springmvctesting.unittest.rest.xml;


import com.google.common.base.Strings;
import com.google.gson.Gson;
import me.wbprime.springmvctesting.common.dto.TodoDTO;
import me.wbprime.springmvctesting.common.exceptions.TodoNotFoundException;
import me.wbprime.springmvctesting.common.models.Todo;
import me.wbprime.springmvctesting.common.restcontrollers.RestExceptionHandler;
import me.wbprime.springmvctesting.common.restcontrollers.TodoController;
import me.wbprime.springmvctesting.common.services.TodoService;
import me.wbprime.springmvctesting.common.test.TodoBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.context.support.StaticApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.Arrays;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
public class TodoControllerTest_StandAlone {
    private MockMvc mockMvc;

    @Mock
    private TodoService mockedTodoService;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(
            new TodoController(mockedTodoService)
        ).setHandlerExceptionResolvers(exceptionResolver())
            .setViewResolvers(viewResolver())
            .build();
    }

    @Test
    public void getAll_success() throws Exception {
        final Todo todo1 = new TodoBuilder()
            .id(1L)
            .title("First title")
            .description("This is the first description")
            .build();
        final Todo todo2 = new TodoBuilder()
            .id(2L)
            .title("Second title")
            .description("This is the second description")
            .build();

        when(mockedTodoService.findAll()).thenReturn(Arrays.asList(todo1, todo2));

        mockMvc.perform(get("/todo"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$[0].id", is(1)))
            .andExpect(jsonPath("$[0].title", is("First title")))
            .andExpect(jsonPath("$[0].description", is("This is the first description")))
            .andExpect(jsonPath("$[1].id", is(2)))
            .andExpect(jsonPath("$[1].title", is("Second title")))
            .andExpect(jsonPath("$[1].description", is("This is the second description")))
            ;

        verify(mockedTodoService, times(1)).findAll();
        verifyNoMoreInteractions(mockedTodoService);
    }

    @Test
    public void getById_success() throws Exception {
        final Todo todo1 = new TodoBuilder()
            .id(1L)
            .title("First title")
            .description("This is the first description")
            .build();

        when(mockedTodoService.findById(1L)).thenReturn(todo1);

        mockMvc.perform(get("/todo/{id}", 1L))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$.id", is(1)))
            .andExpect(jsonPath("$.title", is("First title")))
            .andExpect(jsonPath("$.description", is("This is the first description")))
           ;

        verify(mockedTodoService, times(1)).findById(1L);
        verifyNoMoreInteractions(mockedTodoService);
    }

    @Test
    public void getById_notFound() throws Exception {
        when(mockedTodoService.findById(1L)).thenThrow(TodoNotFoundException.class);

        mockMvc.perform(get("/todo/{id}", 1L))
            .andExpect(status().isNotFound())
           ;

        verify(mockedTodoService, times(1)).findById(1L);
        verifyNoMoreInteractions(mockedTodoService);
    }

    @Test
    public void add_titleTooLong() throws Exception {
        final String title = Strings.repeat("a", Todo.MAX_LENGTH_TITLE + 1);
        final String desc = "description here";

        final TodoDTO dto = new TodoDTO();
        dto.setTitle(title);
        dto.setDescription(desc);

        mockMvc.perform(
            post("/todo").contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new Gson().toJson(dto))
        ).andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.fieldErrors").isArray())
            ;

        verifyZeroInteractions(mockedTodoService);
    }

    @Test
    public void add_desciptionTooLong() throws Exception {
        final String desc = Strings.repeat("a", Todo.MAX_LENGTH_DESCRIPTION + 1);
        final String title = "new title";

        final TodoDTO dto = new TodoDTO();
        dto.setTitle(title);
        dto.setDescription(desc);

        mockMvc.perform(
            post("/todo").contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new Gson().toJson(dto))
        ).andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.fieldErrors").isArray())
            ;

        verifyZeroInteractions(mockedTodoService);
    }

    @Test
    public void add_success() throws Exception {
        final String desc = "new description";
        final String title = "new title";

        final Todo todo1 = new TodoBuilder()
            .id(1L)
            .title(title)
            .description(desc)
            .build();

        when(mockedTodoService.add(Mockito.isA(TodoDTO.class))).thenReturn(todo1);

        final TodoDTO dto = new TodoDTO();
        dto.setTitle(title);
        dto.setDescription(desc);

        mockMvc.perform(
            post("/todo").contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new Gson().toJson(dto))
        ).andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$.id", is(1)))
            .andExpect(jsonPath("$.title", is(title)))
            .andExpect(jsonPath("$.description", is(desc)))
            ;

        final ArgumentCaptor<TodoDTO> captor = ArgumentCaptor.forClass(TodoDTO.class);
        verify(mockedTodoService, times(1)).add(captor.capture());
        verifyNoMoreInteractions(mockedTodoService);

        final TodoDTO capturedDto = captor.getValue();
        assertThat(capturedDto.getId(), nullValue());
        assertThat(capturedDto.getTitle(), is(title));
        assertThat(capturedDto.getDescription(), is(desc));
    }

    private HandlerExceptionResolver exceptionResolver() {
        final StaticApplicationContext applicationContext = new StaticApplicationContext();

        final ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("i18n/messages");
        messageSource.setUseCodeAsDefaultMessage(true);

        final MutablePropertyValues props = new MutablePropertyValues();
        props.add("messageSource", messageSource);
        applicationContext.registerSingleton(
            "exceptionHandler",
            RestExceptionHandler.class,
            props
        );

        final WebMvcConfigurationSupport webMvcConfigurationSupport = new WebMvcConfigurationSupport();
        webMvcConfigurationSupport.setApplicationContext(applicationContext);

        return webMvcConfigurationSupport.handlerExceptionResolver();
    }

    private ViewResolver viewResolver() {
        final InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();

        viewResolver.setPrefix("/WEB-INF/jsp/");
        viewResolver.setSuffix(".jsp");

        return viewResolver;
    }
}
