package org.activiti.editor.language;

import org.activiti.bpmn.model.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class SimpleConverterTest extends AbstractConverterTest {

  @Test
  public void convertJsonToModel() throws Exception {
    BpmnModel bpmnModel = readJsonFile();
    validateModel(bpmnModel);
  }

  @Test
  public void doubleConversionValidation() throws Exception {
    BpmnModel bpmnModel = readJsonFile();
    bpmnModel = convertToJsonAndBack(bpmnModel);
    validateModel(bpmnModel);
  }

  protected String getResource() {
    return "test.simplemodel.json";
  }

  private void validateModel(BpmnModel model) {
    assertEquals("simpleProcess", model.getMainProcess().getId());
    assertEquals("Simple process", model.getMainProcess().getName());
    assertEquals(true, model.getMainProcess().isExecutable());

    FlowElement flowElement = model.getMainProcess().getFlowElement("flow1", true);
    assertNotNull(flowElement);
    assertTrue(flowElement instanceof SequenceFlow);
    assertEquals("flow1", flowElement.getId());

    flowElement = model.getMainProcess().getFlowElement("catchEvent", true);
    assertNotNull(flowElement);
    assertTrue(flowElement instanceof IntermediateCatchEvent);
    assertEquals("catchEvent", flowElement.getId());
    IntermediateCatchEvent catchEvent = (IntermediateCatchEvent) flowElement;
    assertTrue(catchEvent.getEventDefinitions().size() == 1);
    EventDefinition eventDefinition = catchEvent.getEventDefinitions().get(0);
    assertTrue(eventDefinition instanceof TimerEventDefinition);
    TimerEventDefinition timerDefinition = (TimerEventDefinition) eventDefinition;
    assertEquals("PT5M", timerDefinition.getTimeDuration());

    flowElement = model.getMainProcess().getFlowElement("flow1Condition", true);
    assertNotNull(flowElement);
    assertTrue(flowElement instanceof SequenceFlow);
    assertEquals("flow1Condition", flowElement.getId());
    SequenceFlow flow = (SequenceFlow) flowElement;
    assertEquals("${number <= 1}", flow.getConditionExpression());
  }
}
