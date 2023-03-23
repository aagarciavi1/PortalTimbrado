package mx.conacyt.crip.proyecto03.cucumber;

import io.cucumber.spring.CucumberContextConfiguration;
import mx.conacyt.crip.proyecto03.IntegrationTest;
import org.springframework.test.context.web.WebAppConfiguration;

@CucumberContextConfiguration
@IntegrationTest
@WebAppConfiguration
public class CucumberTestContextConfiguration {}
