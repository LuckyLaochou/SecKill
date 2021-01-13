package cn.laochou.seckill.controller;

//import cn.laochou.seckill.rabbitmq.MQSender;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;

//
@Controller
@RequestMapping("/mq")
public class MQController {
}
//
//
//    @Autowired
//    private MQSender mqSender;
//
//    @RequestMapping("/send")
//    @ResponseBody
//    public String sendMessage(@RequestParam(name = "message") String message) {
//        mqSender.send(message);
//        return "success";
//    }
//
//    @RequestMapping("/send/topic")
//    @ResponseBody
//    public String sendMessageTopic(@RequestParam(name = "message") String message) {
//        mqSender.sendTopic(message);
//        return "success";
//    }
//
//
//    @RequestMapping("/send/fanout")
//    @ResponseBody
//    public String sendMessageFanout(@RequestParam(name = "message") String message) {
//        mqSender.sendFanout(message);
//        return "success";
//    }
//
//
//    @RequestMapping("/send/headers")
//    @ResponseBody
//    public String sendMessageHeaders(@RequestParam(name = "message") String message) {
//        mqSender.sendHeaders(message);
//        return "success";
//    }
//
//}
