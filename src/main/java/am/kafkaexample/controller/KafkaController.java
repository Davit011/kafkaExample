package am.kafkaexample.controller;

import am.kafkaexample.components.MyTopicConsumer;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class KafkaController {

    private KafkaTemplate<String, String> template;
    private MyTopicConsumer myTopicConsumer;

    public KafkaController(KafkaTemplate<String, String> template, MyTopicConsumer myTopicConsumer) {
        this.template = template;
        this.myTopicConsumer = myTopicConsumer;
    }

    @GetMapping("/variable")
    public String home() {
        return "forVariableInputs";
    }

    @PostMapping("/kafka/produce")
    public String produce(@RequestParam String message) {
        System.out.println(message);
        template.send("myTopic", message);
        return "forVariableInputs";
    }

    @GetMapping("/kafka/consumer")
    public String getMessages(ModelMap modelMap) {
        List<String> allMessages = new ArrayList<>();
        allMessages.addAll(myTopicConsumer.getMessages());
        modelMap.addAttribute("messages", allMessages);
        return "forVariableInputs";
    }
}