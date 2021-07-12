package com.license.test.app;

import com.elon.license.app.LicenseCheckResult;
import com.elon.license.app.LicenseMachine;
import com.elon.license.app.util.FileUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class TestController {

    @GetMapping
    public void test() throws IOException {
        try {
            LicenseCheckResult result = LicenseMachine.verify("./test_license", "./license-key.der");
            System.out.println(result.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
