package ru.clevertec.ecl.knyazev.servlet.request.wrapper;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import org.springframework.http.MediaType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

/**
 * Wrap For request when using HTTP PATCH method
 */
public class PatchServletRequestWrapper extends HttpServletRequestWrapper {
    private final String newRequestBody;

    public PatchServletRequestWrapper(HttpServletRequest request, String newRequestBody) {
        super(request);
        this.newRequestBody = newRequestBody;
    }

    @Override
    public int getContentLength() {
        return newRequestBody != null
                ? newRequestBody.getBytes(Charset.defaultCharset()).length
                : -1;
    }

    @Override
    public long getContentLengthLong() {
        return getContentLength();
    }

    @Override
    public String getContentType() {
        return MediaType.APPLICATION_JSON_VALUE;
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        return new ServletInputStream() {
            private int index = 0;

            @Override
            public boolean isFinished() {
                return index == newRequestBody.length();
            }

            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setReadListener(ReadListener listener) {

            }

            @Override
            public int read() throws IOException {
                return isFinished()
                        ? -1
                        : (int) newRequestBody.getBytes(Charset.defaultCharset())[index++];
            }
        };
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }
}
