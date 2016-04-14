package me.wbprime.springmvctesting.unittest.normal.java;


import com.google.common.base.Strings;
import me.wbprime.springmvctesting.common.controllers.TodoController;
import me.wbprime.springmvctesting.common.dto.TodoDTO;
import me.wbprime.springmvctesting.common.exceptions.TodoNotFoundException;
import me.wbprime.springmvctesting.common.models.Todo;
import me.wbprime.springmvctesting.common.services.TodoService;
import me.wbprime.springmvctesting.common.test.TodoBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.view.InternalResourceView;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.Arrays;
import java.util.Properties;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
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
            new TodoController(messageSource(), mockedTodoService)
        ).setHandlerExceptionResolvers(exceptionResolver())
            .setValidator(validator())
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
            .andExpect(view().name("todo/list"))
            .andExpect(forwardedUrl("/WEB-INF/jsp/todo/list.jsp"))
            .andExpect(model().attribute("todos", hasSize(2)))
            .andExpect(model().attribute("todos", hasItem(
                allOf(
                    hasProperty("id", is(1L)),
                    hasProperty("title", is("First title")),
                    hasProperty("description", is("This is the first description"))
                )
            )))
            .andExpect(model().attribute("todos", hasItem(
                allOf(
                    hasProperty("id", is(2L)),
                    hasProperty("title", is("Second title")),
                    hasProperty("description", is("This is the second description"))
                )
            ))) ;

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
            .andExpect(view().name("todo/view"))
            .andExpect(forwardedUrl("/WEB-INF/jsp/todo/view.jsp"))
            .andExpect( model().attribute("todo", hasProperty("id", is(1L))) )
            .andExpect( model().attribute("todo", hasProperty("title", is("First title"))) )
            .andExpect(model().attribute("todo", hasProperty("description", is("This is the first description"))))
           ;

        verify(mockedTodoService, times(1)).findById(1L);
        verifyNoMoreInteractions(mockedTodoService);
    }

    @Test
    public void getById_notFound() throws Exception {
        when(mockedTodoService.findById(1L)).thenThrow(TodoNotFoundException.class);

        mockMvc.perform(get("/todo/{id}", 1L))
            .andExpect(status().isNotFound())
            .andExpect(view().name("error/404"))
            .andExpect(forwardedUrl("/WEB-INF/jsp/error/404.jsp"))
           ;

        verify(mockedTodoService, times(1)).findById(1L);
        verifyNoMoreInteractions(mockedTodoService);
    }

    @Test
    public void add_titleTooLong() throws Exception {
        final String title = Strings.repeat("a", Todo.MAX_LENGTH_TITLE + 1);

        mockMvc.perform(
            post("/todo/add").contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("title", title)
                .param("description", "A normal description")
                .sessionAttr("todo", new TodoDTO())
        ).andExpect(status().isOk())
            .andExpect(view().name("todo/add"))
            .andExpect(forwardedUrl("/WEB-INF/jsp/todo/add.jsp"))
            .andExpect(model().attributeHasFieldErrors("todo", "title"))
            ;

        verifyZeroInteractions(mockedTodoService);
    }

    @Test
    public void add_desciptionTooLong() throws Exception {
        final String desc = Strings.repeat("a", Todo.MAX_LENGTH_DESCRIPTION + 1);

        mockMvc.perform(
            post("/todo/add").contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("title", "title")
                .param("description", desc)
                .sessionAttr("todo", new TodoDTO())
        ).andExpect(status().isOk())
            .andExpect(view().name("todo/add"))
            .andExpect(forwardedUrl("/WEB-INF/jsp/todo/add.jsp"))
            .andExpect(model().attributeHasFieldErrors("todo", "description"))
            ;

        verifyZeroInteractions(mockedTodoService);
    }

    @Test
    public void add_success() throws Exception {
        final Todo todo1 = new TodoBuilder()
            .id(1L)
            .title("title")
            .description("desc")
            .build();

        when(mockedTodoService.add(Mockito.isA(TodoDTO.class))).thenReturn(todo1);

        mockMvc.perform(
            post("/todo/add").contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("title", "title")
                .param("description", "desc")
                .sessionAttr("todo", new TodoDTO())
        ).andExpect(status().isFound())
            .andExpect(view().name("redirect:/todo/{id}"))
            .andExpect(redirectedUrl("/todo/1"))
            .andExpect(model().attribute("id", is("1")))
            ;

        final ArgumentCaptor<TodoDTO> captor = ArgumentCaptor.forClass(TodoDTO.class);
        verify(mockedTodoService, times(1)).add(captor.capture());
        verifyNoMoreInteractions(mockedTodoService);

        final TodoDTO capturedDto = captor.getValue();
        assertThat(capturedDto.getId(), nullValue());
        assertThat(capturedDto.getTitle(), is("title"));
        assertThat(capturedDto.getDescription(), is("desc"));
    }

    private HandlerExceptionResolver exceptionResolver() {
        SimpleMappingExceptionResolver exceptionResolver = new SimpleMappingExceptionResolver();

        Properties exceptionMappings = new Properties();

        exceptionMappings.put(
            "me.wbprime.springmvctesting.common.exceptions.TodoNotFoundException",
            "error/404"
        );
        exceptionMappings.put("java.lang.Exception", "error/error");
        exceptionMappings.put("java.lang.RuntimeException", "error/error");

        exceptionResolver.setExceptionMappings(exceptionMappings);

        Properties statusCodes = new Properties();

        statusCodes.put("error/404", "404");
        statusCodes.put("error/error", "500");

        exceptionResolver.setStatusCodes(statusCodes);

        return exceptionResolver;
    }

    private MessageSource messageSource() {
        final ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();

        messageSource.setBasename("i18n/messages");
        messageSource.setUseCodeAsDefaultMessage(true);

        return messageSource;
    }

    private LocalValidatorFactoryBean validator() {
        return new LocalValidatorFactoryBean();
    }

    private ViewResolver viewResolver() {
        final InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();

        viewResolver.setViewClass(InternalResourceView.class);
        viewResolver.setPrefix("/WEB-INF/jsp/");
        viewResolver.setSuffix(".jsp");

        return viewResolver;
    }
}
