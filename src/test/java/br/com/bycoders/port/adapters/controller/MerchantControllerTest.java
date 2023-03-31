package br.com.bycoders.port.adapters.controller;

import br.com.bycoders.application.TransactionApplicationService;
import br.com.bycoders.port.adapters.controller.DTO.MerchantResponseDTO;
import br.com.bycoders.port.adapters.controller.DTO.MerchantResponseDTO.TransactionMerchantDTO;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = MerchantController.class)
public class MerchantControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @MockBean
    private TransactionApplicationService transactionApplicationService;

    @Test
    public void findByTaxId() throws Exception {

        MerchantResponseDTO merchantResponseDTO = new MerchantResponseDTO(
                BigDecimal.valueOf(150.35),
                Arrays.asList(
                        new TransactionMerchantDTO(
                                1,
                                LocalDateTime.of(2020, 1, 1, 10, 1, 1),
                                BigDecimal.valueOf(100.00),
                                "72998832016",
                                "8473****1231",
                                "JOSÉ COSTA",
                                "MERCEARIA 3 IRMÃOS"
                        ),
                        new TransactionMerchantDTO(
                                4,
                                LocalDateTime.of(2020, 1, 1, 10, 1, 1),
                                BigDecimal.valueOf(50.35),
                                "72998832016",
                                "8473****1231",
                                "JOSÉ COSTA",
                                "MERCEARIA 3 IRMÃOS"
                        )
                )
        );

        given(transactionApplicationService.operationByTaxId("72998832016")).willReturn(merchantResponseDTO);

        mockMvc.perform(get("/merchant?taxId=72998832016"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.totalAmount").value(150.35))
                .andExpect(jsonPath("$.transactions[0].transactionType").value(1))
                .andExpect(jsonPath("$.transactions[0].date").value(LocalDateTime.of(2020, 1, 1, 10, 1, 1).toString()))
                .andExpect(jsonPath("$.transactions[0].amount").value(100.00))
                .andExpect(jsonPath("$.transactions[0].taxId").value("72998832016"))
                .andExpect(jsonPath("$.transactions[0].card").value("8473****1231"))
                .andExpect(jsonPath("$.transactions[0].ownerName").value("JOSÉ COSTA"))
                .andExpect(jsonPath("$.transactions[0].merchantName").value("MERCEARIA 3 IRMÃOS"))
                .andExpect(jsonPath("$.transactions[1].transactionType").value(4))
                .andExpect(jsonPath("$.transactions[1].date").value(LocalDateTime.of(2020, 1, 1, 10, 1, 1).toString()))
                .andExpect(jsonPath("$.transactions[1].amount").value(50.35))
                .andExpect(jsonPath("$.transactions[1].taxId").value("72998832016"))
                .andExpect(jsonPath("$.transactions[1].card").value("8473****1231"))
                .andExpect(jsonPath("$.transactions[1].ownerName").value("JOSÉ COSTA"))
                .andExpect(jsonPath("$.transactions[1].merchantName").value("MERCEARIA 3 IRMÃOS"));

    }

}
