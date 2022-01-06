package at.fhv.hotelmanagement.bdd.runner;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:features/",
        glue = {"at.fhv.hotelmanagement.bdd"},
        plugin = {"pretty", "html:build/cucumber/hotelmanagement.html"})
public class CucumberTestRunner {
}
