package br.com.bycoders.port.adapters.controller;

import br.com.bycoders.application.TransactionApplicationService;
import br.com.bycoders.port.adapters.controller.DTO.MerchantResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping
public class MerchantController {

    private final TransactionApplicationService transactionApplicationService;

    public MerchantController(TransactionApplicationService transactionApplicationService) {
        this.transactionApplicationService = transactionApplicationService;
    }

    @GetMapping("/merchant")
    @Operation(summary = "Busca todas transações e a somatória de um determinado estabelecimento pelo CPF")
    public ResponseEntity<MerchantResponseDTO> findByTaxId(@RequestParam String taxId) {
        return ResponseEntity.ok(transactionApplicationService.operationByTaxId(taxId));
    }

    @GetMapping("/merchants")
    @Operation(summary = "Busca todas transações processadas pelo arquivo CNAB")
    public ResponseEntity<List<MerchantResponseDTO>> findAllMerchants() {
        return ResponseEntity.ok(transactionApplicationService.allMerchants());
    }
}
