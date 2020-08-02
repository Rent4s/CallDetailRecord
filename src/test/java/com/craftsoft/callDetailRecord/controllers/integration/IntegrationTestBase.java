package com.craftsoft.callDetailRecord.controllers.integration;


import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcPrint;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class IntegrationTestBase {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    protected <T> T httpPutSuccess(String url, Class<T> resultClass, Class paramClass, Object body) throws Exception {
        var resultStatus = status().isOk();
        return httpRequestExpectedStatus(url, HttpMethod.PUT, resultStatus, resultClass, paramClass, body, null);
    }

    protected <T> void httpPutError(String url, Object body, List<String> exceptionMessages) throws Exception {
        var resultActions = getResultActions(url, HttpMethod.PUT, body, null);
        var exception = resultActions
                .andExpect(status().is5xxServerError())
                .andReturn().getResolvedException();
        exceptionMessages.forEach(expectedMessage-> {
            assertTrue(exception.getMessage().contains(expectedMessage));
        });
    }

    private <T> ResultActions getResultActions(String url, HttpMethod httpMethod, Object body, MultiValueMap<String, String> params, String... pathVariables) throws Exception {
        var content = objectMapper.writeValueAsString(body);
        var request = MockMvcRequestBuilders.request(httpMethod, url, pathVariables)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        if (params != null && !params.isEmpty()){
            request.params(params);
        }
        return mockMvc.perform(request);
    }


    private <T> ResultActions getResultActionsForFile(String url, Object body, MockMultipartFile file, String... pathVariables) throws Exception {
        var content = objectMapper.writeValueAsString(body);
        return mockMvc.perform(MockMvcRequestBuilders.multipart(url, pathVariables).file(file)
                .contentType(MediaType.MULTIPART_FORM_DATA).content(content));
    }

    protected <T>void httpDeleteSuccess(String url, Object body, String... pathVariables) throws Exception {
        var resultStatus = status().isOk();
        getResultActions(url, HttpMethod.DELETE, body, null, pathVariables)
                .andExpect(resultStatus);
    }

    protected  <T> T httpGetSuccess(String url, Class<T> resultClass,  Object body, String... pathVariables) throws Exception {
        var resultStatus = status().isOk();
        var objectType = objectMapper.getTypeFactory().constructType(resultClass);
        return httpRequestExpectedStatus(url, HttpMethod.GET, resultStatus, objectType, body, null, pathVariables);
    }

    protected <T> T httpPostSuccess(String url, Class<T> resultClass, Object body) throws Exception {
        var resultStatus = status().isOk();
        var objectType = objectMapper.getTypeFactory().constructType(resultClass);
        return httpRequestExpectedStatus(url, HttpMethod.POST, resultStatus, objectType, body, null);
    }

    protected <T> T httpPostSuccess(String url, Class<T> resultClass, Class paramClass, Object body, MultiValueMap<String, String> params) throws Exception {
        var resultStatus = status().isOk();
        var objectType = objectMapper.getTypeFactory().constructParametricType(resultClass, paramClass);
        return httpRequestExpectedStatus(url, HttpMethod.POST, resultStatus, objectType, body, params);
    }

    protected <T> T httpPostSuccess(String url, Class<T> resultClass, Class paramClass, Object body) throws Exception {
        var resultStatus = status().isOk();
        return httpRequestExpectedStatus(url, HttpMethod.POST, resultStatus, resultClass, paramClass, body, null);
    }
    protected <T> T httpPostFileSuccess(String url, Class<T> resultClass, Class paramClass, Object body, MockMultipartFile file) throws Exception {
        var objectType = objectMapper.getTypeFactory().constructParametricType(resultClass, paramClass);
        var resultActions = getResultActionsForFile(url, body, file);
        var resultStatus = status().isOk();
        return getResponse(resultStatus, objectType, resultActions);
    }


    private <T> T httpRequestExpectedStatus(String url, HttpMethod httpMethod, ResultMatcher resultStatus, Class<T> resultClass, Class paramClass, Object body, MultiValueMap<String, String> params, String... pathVariables) throws Exception {
        var objectType = objectMapper.getTypeFactory().constructParametricType(resultClass, paramClass);
        return httpRequestExpectedStatus(url, httpMethod,resultStatus,objectType, body, params, pathVariables);
    }


    private <T> T httpRequestExpectedStatus(String url, HttpMethod httpMethod, ResultMatcher resultStatus, JavaType javaType, Object body, MultiValueMap<String, String> params, String... pathVariables) throws Exception {
        var resultActions = getResultActions(url, httpMethod, body, params, pathVariables);
        return getResponse(resultStatus, javaType, resultActions);
    }

    private <T> T getResponse(ResultMatcher resultStatus, JavaType javaType, ResultActions resultActions) throws Exception {
        var mvcResult = resultActions.andExpect(resultStatus)
                .andReturn();
        var jsonResponse = mvcResult.getResponse().getContentAsString();

        return objectMapper.readValue(jsonResponse, javaType);
    }
}
