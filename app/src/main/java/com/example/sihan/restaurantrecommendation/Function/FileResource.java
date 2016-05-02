/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.sihan.restaurantrecommendation.Function;

import android.annotation.TargetApi;
import android.os.Build;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;

/**
 *
 * @author Ginxi
 */
class FileResource {

    private String myPath;
    private String mySource;
    private File mySaveFile;

    public FileResource(String filename, InputStream is) {
        initRead(filename, is);
    }

    public CSVParser getCSVParser() {
        return getCSVParser(true);
    }

    public CSVParser getCSVParser(boolean withHeader) {
        return getCSVParser(withHeader, ",");
    }

    public CSVParser getCSVParser(boolean withHeader, String delimiter) {
        if (delimiter == null || delimiter.length() != 1) {
            throw new ResourceException("FileResource: CSV delimiter must be a single character: " + delimiter);
        }
        try {
            char delim = delimiter.charAt(0);
            Reader input = new StringReader(mySource);
            if (withHeader) {
                return new CSVParser(input, CSVFormat.EXCEL.withHeader().withDelimiter(delim));
            } else {
                return new CSVParser(input, CSVFormat.EXCEL.withDelimiter(delim));
            }
        } catch (Exception e) {
            throw new ResourceException("FileResource: cannot read " + myPath + " as a CSV file.");
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private String initFromStream(InputStream stream) {
        try (BufferedReader buff = new BufferedReader(new InputStreamReader(stream, "UTF-8"))) {
            StringBuilder contents = new StringBuilder();
            String line = null;
            while ((line = buff.readLine()) != null) {
                contents.append(line + "\n");
            }
            return contents.toString();
        } catch (Exception e) {
            throw new ResourceException("FileResource: error encountered reading " + myPath, e);
        }
    }

    private void initRead(String fname, InputStream is) {
        try {
            myPath = fname;
          //  InputStream is = getClass().getClassLoader().getResourceAsStream(fname);
            if (is == null) {
                is = new FileInputStream(fname);
            }
            mySource = initFromStream(is);
        } catch (Exception e) {
            throw new ResourceException("FileResource: cannot access " + fname);
        }
    }
}
