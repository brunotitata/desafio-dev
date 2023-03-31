package br.com.bycoders.port.adapters.controller;

import br.com.bycoders.application.TransactionApplicationService;
import br.com.bycoders.port.adapters.controller.DTO.MerchantResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class MerchantController {

    private final TransactionApplicationService transactionApplicationService;

    public MerchantController(TransactionApplicationService transactionApplicationService) {
        this.transactionApplicationService = transactionApplicationService;
    }

    @GetMapping("/merchant")
    public ResponseEntity<MerchantResponseDTO> findByTaxId(@RequestParam String taxId) {
        return ResponseEntity.ok(transactionApplicationService.operationByTaxId(taxId));
    }
}
