package org.activiti.editor.language.xml;

import org.activiti.bpmn.model.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

public class CompleteConverterTest extends AbstractConverterTest {

    @Test
    public void convertXMLToModel() throws Exception {
        BpmnModel bpmnModel = readXMLFile();
        validateModel(bpmnModel);
    }

    @Test
    public void convertModelToXML() throws Exception {
        BpmnModel bpmnModel = readXMLFile();
        BpmnModel parsedModel = exportAndReadXMLFile(bpmnModel);
        validateModel(parsedModel);
        deployProcess(parsedModel);
    }

    protected String getResource() {
        return "completemodel.bpmn";
    }

    private void validateModel(BpmnModel model) {
        FlowElement flowElement = model.getMainProcess().getFlowElement("userTask1");
        assertNotNull(flowElement);
        assertThat(flowElement).isInstanceOf(UserTask.class);
        assertEquals("userTask1", flowElement.getId());

        flowElement = model.getMainProcess().getFlowElement("catchsignal");
        assertNotNull(flowElement);
        assertThat(flowElement).isInstanceOf(IntermediateCatchEvent.class);
        assertEquals("catchsignal", flowElement.getId());
        IntermediateCatchEvent catchEvent = (IntermediateCatchEvent) flowElement;
        assertEquals(1, catchEvent.getEventDefinitions().size());
        assertThat(catchEvent.getEventDefinitions().get(0)).isInstanceOf(SignalEventDefinition.class);
        SignalEventDefinition signalEvent = (SignalEventDefinition) catchEvent.getEventDefinitions().get(0);
        assertEquals("testSignal", signalEvent.getSignalRef());

        flowElement = model.getMainProcess().getFlowElement("subprocess");
        assertNotNull(flowElement);
        assertThat(flowElement).isInstanceOf(SubProcess.class);
        assertEquals("subprocess", flowElement.getId());
        SubProcess subProcess = (SubProcess) flowElement;

        flowElement = subProcess.getFlowElement("receiveTask");
        assertNotNull(flowElement);
        assertThat(flowElement).isInstanceOf(ReceiveTask.class);
        assertEquals("receiveTask", flowElement.getId());
    }

}
