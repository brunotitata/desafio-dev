package br.com.bycoders.application;

import br.com.bycoders.domain.TransactionDTO;
import br.com.bycoders.domain.exception.CnabInvalidException;
import org.beanio.BeanReader;
import org.beanio.StreamFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class CnabApplicationService {

    public List<TransactionDTO> extractTransactionsToFile(byte[] cnabFile) {

        try (FileOutputStream fos = new FileOutputStream("CNAB.txt")) {
            fos.write(cnabFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        List<TransactionDTO> transactions = new ArrayList<>();

        try {
            StreamFactory factory = StreamFactory.newInstance();
            factory.load("transaction-mapper.xml");
            BeanReader in = factory.createReader("transactions", new File("CNAB.txt"));
            TransactionDTO transaction;

            while ((transaction = (TransactionDTO) in.read()) != null) {
                transactions.add(transaction);
            }
            in.close();
        } catch (Exception e) {
            throw new CnabInvalidException("Arquivo invalido, não é do tipo CNAB !");
        }

        return transactions;
    }
}
