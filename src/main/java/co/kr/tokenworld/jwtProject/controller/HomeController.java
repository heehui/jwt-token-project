package co.kr.tokenworld.jwtProject.controller;

import co.kr.tokenworld.jwtProject.service.SecurityServiceImpl;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;


import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/")
public class HomeController {

    @Autowired
    private SecurityServiceImpl securityService;

    @GetMapping("security/generate/token")
    public Map<String, Object> generateToken(@RequestParam String subject) {
        String token = securityService.createToken(subject, (2 * 1000 * 60));
        Map<String, Object> map = new HashMap<>();
        map.put("userid", subject);
        map.put("token", token);
        return map;
    }

    @ResponseBody
    @GetMapping("security/get/subject")
    public String getSubject(@RequestParam String token) {
        String subject = securityService.getSubject(token);
        return subject;
    }

}
