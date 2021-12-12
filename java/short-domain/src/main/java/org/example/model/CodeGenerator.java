package org.example.model;

import org.example.exception.CodeGeneratorException;

public interface CodeGenerator {

    String createNewCode() throws CodeGeneratorException;
}
