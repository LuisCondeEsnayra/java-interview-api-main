package com.talentreef.interviewquestions.takehome.controllers;

import com.talentreef.interviewquestions.takehome.models.Widget;
import com.talentreef.interviewquestions.takehome.services.WidgetService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping(value = "/v1/widgets", produces = MediaType.APPLICATION_JSON_VALUE)
public class WidgetController {

  private final WidgetService widgetService;

  public WidgetController(WidgetService widgetService) {
    Assert.notNull(widgetService, "widgetService must not be null");
    this.widgetService = widgetService;
  }

  @GetMapping
  public ResponseEntity<List<Widget>> getAllWidgets() {
    return ResponseEntity.ok(widgetService.getAllWidgets());
  }

  @GetMapping("/{name}")
  public ResponseEntity<Optional<Widget>> getWidgetByName(@PathVariable String name){
      return ResponseEntity.ok(widgetService.getWidgetByName(name));
  }

  @PutMapping
  public ResponseEntity<Widget> updateWidget(@RequestBody Widget widget){
      return ResponseEntity.ok( widgetService.updateWidget(widget));
  }
  
  @DeleteMapping("/{name}")
  public void deleteWidgetByName(@PathVariable String name){
       widgetService.deleteWidget(name);
  }
}
