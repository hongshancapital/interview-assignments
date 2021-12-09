package com.icbc.gjljfl.config;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.CharArrayWriter;
import java.io.PrintWriter;

public class CharArrayWrapper extends HttpServletResponseWrapper {

    private CharArrayWriter charWriter;

    public CharArrayWrapper(HttpServletResponse response) {
        super(response);
        charWriter = new CharArrayWriter();
    }

    @Override
    public PrintWriter getWriter() {
        return (new PrintWriter(charWriter));
    }

    @Override
    public String toString() {
        return (charWriter.toString());
    }

    public char[] toCharArray() {
        return (charWriter.toCharArray());
    }
}
