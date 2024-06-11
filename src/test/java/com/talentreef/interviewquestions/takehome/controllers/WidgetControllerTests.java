package com.talentreef.interviewquestions.takehome.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.talentreef.interviewquestions.takehome.models.Widget;
import com.talentreef.interviewquestions.takehome.respositories.WidgetRepository;
import com.talentreef.interviewquestions.takehome.services.WidgetService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WidgetControllerTests {

  final private ObjectMapper objectMapper = new ObjectMapper();

  private MockMvc mockMvc;

  @Mock
  private WidgetService widgetService;

  @InjectMocks
  private WidgetController widgetController;

  @Before
  public void init() {
    mockMvc = MockMvcBuilders.standaloneSetup(widgetController).build();
  }

  @Test
  public void when_getAllWidgets_expect_allWidgets() throws Exception {
    Widget widget = Widget.builder().name("Widget von Hammersmark").build();
    List<Widget> allWidgets = List.of(widget);
    when(widgetService.getAllWidgets()).thenReturn(allWidgets);

    MvcResult result = mockMvc.perform(get("/v1/widgets"))
        .andExpect(status().isOk())
        .andDo(print())
        .andReturn();

    List<Widget> parsedResult = objectMapper.readValue(result.getResponse().getContentAsString(),
        new TypeReference<List<Widget>>() {
        });
    assertThat(parsedResult).isEqualTo(allWidgets);
  }

  @Test
  public void when_getWidgetByName_expect_single_widget() throws Exception {
    Widget widget = Widget.builder().name("Im Get Widget by name").build();

    when(widgetService.getWidgetByName("Im Get Widget by name")).thenReturn(java.util.Optional.of(widget));

    mockMvc.perform(get("/v1/widgets/{name}", "Im Get Widget by name"))
        .andExpect(status().isOk());
  }

  @Test
  public void when_updateWidget_expect_widget_updated() throws Exception {
    Widget widgetToUpdate = Widget.builder().name("Im a widget waiting to be updated").build();

    when(widgetService.updateWidget(widgetToUpdate)).thenReturn(widgetToUpdate);

    mockMvc.perform(put("/v1/widgets")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(widgetToUpdate)))
        .andExpect(status().isOk());

  }

  @Test
  public void when_deleteWidgetByName_expect_toBe_deleted() throws Exception {
    mockMvc.perform(delete("/v1/widgets/{name}", "Delete this widget")).andExpect(status().isOk());
  }

}
