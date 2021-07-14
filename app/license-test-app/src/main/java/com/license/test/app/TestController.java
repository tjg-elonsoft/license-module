package com.license.test.app;

import com.elon.license.app.LicenseCheckResult;
import com.elon.license.app.LicenseMachine;
import com.elon.license.app.util.FileUtil;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class TestController {

    @GetMapping
    public void test() throws IOException {
        try {
            System.out.println(new FileSystemResource("").getFile().getAbsolutePath());
            LicenseCheckResult result = LicenseMachine.verify("./p2p_license", "./license-key.der");
            System.out.println(result.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @CrossOrigin
    @GetMapping("/test")
    public Map<String , String> testApi() throws IOException, InterruptedException {
        Thread.sleep(2000);
        Map<String , String> result = new HashMap<>();
        result.put("status", "true");
        result.put("value", "1500");
        return result;
    }
}
