package app;


import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

public class IntegrationTest {


    protected InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();

    protected MockMvc mockMvc;

    public void setup() {
        viewResolver.setPrefix("/WEB-INF/jsp/view/");
        viewResolver.setSuffix(".jsp");
    }
}
