package br.com.bycoders.port.adapters.controller;

import br.com.bycoders.application.TransactionApplicationService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Controller
public class CnabUploadController {

    private final TransactionApplicationService transactionApplicationService;

    public CnabUploadController(TransactionApplicationService transactionApplicationService) {
        this.transactionApplicationService = transactionApplicationService;
    }

    @GetMapping("/upload")
    public String pageUpload() {
        return "upload";
    }

    @PostMapping("/upload-file")
    public ModelAndView upload(@RequestParam("file") MultipartFile file) {

        ModelAndView modelAndView = new ModelAndView("upload");

        if (file.isEmpty()) {
            return modelAndView.addObject("message", "Arquivo vazio!");
        }

        try {
            transactionApplicationService.saveTransaction(file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        modelAndView.addObject("message", "Arquivo processado com sucesso");
        return modelAndView;
    }


}