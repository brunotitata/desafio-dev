package br.com.bycoders.port.adapters.controller;

import br.com.bycoders.application.TransactionApplicationService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.InputStream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = CnabUploadController.class)
public class CnabUploadControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @MockBean
    private TransactionApplicationService transactionApplicationService;

    @Test
    public void mustUploadSuccessfully() throws Exception {

        InputStream resourceAsStream = this.getClass().getResourceAsStream("/file/CNAB.txt");
        MockMultipartFile multipartFile = new MockMultipartFile("file", resourceAsStream);

        Mockito.doNothing().when(transactionApplicationService).saveTransaction(any());

        mockMvc.perform(MockMvcRequestBuilders.multipart("/upload-file")
                        .file("file", multipartFile.getBytes()))
                .andExpect(status().isOk())
                .andExpect(view().name("upload"))
                .andExpect(model().attribute("message", "Arquivo processado com sucesso"));

        verify(transactionApplicationService, Mockito.times(1)).saveTransaction(any());
    }

    @Test
    public void whenUploadEmptyFileShouldNotExecute() throws Exception {

        InputStream resourceAsStream = this.getClass().getResourceAsStream("/file/CNAB-Vazio.txt");
        MockMultipartFile multipartFile = new MockMultipartFile("file", resourceAsStream);

        Mockito.doNothing().when(transactionApplicationService).saveTransaction(any());

        mockMvc.perform(MockMvcRequestBuilders.multipart("/upload-file")
                        .file("file", multipartFile.getBytes()))
                .andExpect(status().isOk())
                .andExpect(view().name("upload"))
                .andExpect(model().attribute("message", "Arquivo vazio!"));

    }


}
