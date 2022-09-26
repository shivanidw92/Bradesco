package relatorio;

import cucumber.api.Argument;
import cucumber.api.PickleStepTestStep;
import cucumber.api.event.*;
import org.apache.commons.lang3.StringUtils;

public class AcceptanceStepNameLogger implements ConcurrentEventListener {

    @Override
    public void setEventPublisher(EventPublisher publisher) {

        publisher.registerHandlerFor(TestStepStarted.class, new EventHandler<TestStepStarted>() {
            @Override
            public void receive(TestStepStarted event) {
                if (event.testStep instanceof PickleStepTestStep) {
                    final PickleStepTestStep ev = (PickleStepTestStep) event.testStep;
                    final String args = StringUtils.join(ev.getDefinitionArgument().stream().map(Argument::getValue).toArray(), ",");
                    String testDescription = ev.getStepText() + " : " + ev.getStepLocation();
                    if (StringUtils.isNotBlank(args)) {
                        testDescription += (" : args = (" + args + ")");
                    }
                    System.out.println("STARTING STEP: " + testDescription);
                }
            }
        });

        publisher.registerHandlerFor(TestStepFinished.class, new EventHandler<TestStepFinished>() {
            @Override
            public void receive(TestStepFinished event) {
                if (event.testStep instanceof PickleStepTestStep) {
                    PickleStepTestStep ev = (PickleStepTestStep) event.testStep;
                    final String testDescription = ev.getStepText() + " : " + ev.getStepLocation();
                    System.out.println("FINISHED STEP: " + testDescription);
                }
            }
        });

    }

}
