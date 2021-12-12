package org.example.model;

import org.example.exception.CodeGeneratorException;
import org.hashids.Hashids;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("hashIds")
public class CodeGeneratorWithHashIds implements CodeGenerator {
    private static final Object obj = new Object();

    @Value("${salt}")
    private String salt;

    private static long current = 1L;

    @Override
    public String createNewCode() throws CodeGeneratorException {
        synchronized (obj) {
            Hashids hashids = new Hashids(salt, 1);
            String code = hashids.encode(current);
            if(code.length() > 8) {
                throw new CodeGeneratorException("the code length exceeds the limit");
            }

            current++;
            return code;
        }
    }
}
