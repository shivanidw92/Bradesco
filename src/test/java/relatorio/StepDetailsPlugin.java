package relatorio;

import cucumber.api.PickleStepTestStep;
import cucumber.api.TestCase;
import cucumber.api.event.ConcurrentEventListener;
import cucumber.api.event.EventHandler;
import cucumber.api.event.EventPublisher;
import cucumber.api.event.TestStepStarted;
import report.Report;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class StepDetailsPlugin implements ConcurrentEventListener {

    public EventHandler<TestStepStarted> stepHandler = this::handleTestStepStarted;

    @Override
    public void setEventPublisher(EventPublisher publisher) {
        publisher.registerHandlerFor(TestStepStarted.class, stepHandler);
    }

    private void handleTestStepStarted(TestStepStarted event) {
        if (event.testStep instanceof PickleStepTestStep) {
            PickleStepTestStep testStep = (PickleStepTestStep) event.testStep;
            TestCase testCase = event.getTestCase();
            String stepName = getStepName(testStep, testCase);
            Report.add(stepName, true);
        }
    }

    private String getStepName(PickleStepTestStep testStep, TestCase testCase) {
        String featureFilePath = testCase.getUri().substring(5);
        int stepLine = testStep.getStepLine() - 1;
        return getStepTextFromFeatureFile(featureFilePath, stepLine);
    }

    //Nome do step foi capturado dessa forma pois as apis do cucumber retornam apenas o nome sem a keyword do step
    //Um step "Dado que acesso o Google" ficaria "que acesso o Google", o que não ficaria legal em um relatório
    private String getStepTextFromFeatureFile(String filename, int line) {
        BufferedReader reader;
        String stepName = null;
        try {
            reader = new BufferedReader(new FileReader(filename));
            Object[] lines = reader.lines().toArray();
            stepName = lines[line].toString();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return stepName;
    }
}
