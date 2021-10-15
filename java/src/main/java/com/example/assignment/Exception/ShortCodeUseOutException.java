package com.example.assignment.Exception;

public class ShortCodeUseOutException extends Exception {
    public ShortCodeUseOutException() {
        super("短码已耗尽");
    }
}
