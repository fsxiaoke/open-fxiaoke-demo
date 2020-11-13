package com.fxiaoke.demo;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import com.fxiaoke.open.demo.controller.arg.CrmCallBackArg;
import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({"classpath*:spring/applicationContext.xml"})
public class EventCallbackControllerTest {
    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void crmCallback() throws Exception {
        Gson gson = new Gson();
        CrmCallBackArg arg = new CrmCallBackArg();
        arg.setEncryptedContent(
            "tRPD30ygQKxFurdWxHQKpRypuXNbRcW/knsKZBCPJv9bsKnQJlUr26USpOgJqhVUm5Q2NiVG1IBrCr0dbrsreRZY6Z5/fbw75IlGQ2d7PkMWtVTAE0RG62j7HJQWAwm+XdmQC4y1AbjHzatjjrXiCKcId112DHZSqB0qznivDStywTjscnNWhaR7xbnSbmKQWsF5YlPUEudZ+P07Ol/cd6F8IXOo+nEu7UH21hRSgKI=");
        arg.setEnterpriseAccount("gxcxkj");
        arg.setTimestamp(1604644770244L);
        arg.setMessageId("AC11291700002A9F00000608C038697A0");
        arg.setNonce("mi3wrpKNnq4YdIZC");
        arg.setRetryTimes(1);
        arg.setSignature("4d7c6742d8efed705054fe895ee487df738c8c50");
        mockMvc.perform((MockMvcRequestBuilders.post("/event/crmCallback").characterEncoding("UTF-8").content(gson.toJson(arg)).contentType(MediaType.APPLICATION_JSON)))
            .andExpect(MockMvcResultMatchers.status().isOk()).andDo(print());
    }
}
