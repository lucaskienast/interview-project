package com.berenberg.library.Utils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;


import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;

import com.berenberg.library.model.LibraryResponse;

import lombok.Synchronized;



@Service
public class CSVUpdateLibrary {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(CSVUpdateLibrary.class);

    @Synchronized
    public LibraryResponse UpdateCSV(String itemId, String loanUniqueId, String action, String dueDate, String userId)
            throws java.io.IOException {
                 LibraryResponse libraryResponse= new LibraryResponse();
        String csvFilePath = "C:\\Users\\bradesin\\OneDrive - University of Bradford\\Documents\\Berenberglibrary.csv";

        String targetId = loanUniqueId; // New age for Bob
        logger.info("targetId******" + targetId);
        List<String[]> updatedRows = new ArrayList<>();

        // Read data from the CSV file and update records
        try (Reader reader = new FileReader(csvFilePath);
                CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {

            for (CSVRecord record : csvParser) {
                String uniqueId = record.get("UniqueID");
                String type = record.get("Type");
                String itemId2 = record.get("ItemID");
                String title = record.get("Title");
                String availabilityFlag= record.get("Availability");

                if (uniqueId.equals(targetId)) {
                    // logger.info("Updating record for UniqueID: " + uniqueId);

                    String[] updatedRow = new String[record.size()];
                    for (int i = 0; i < record.size(); i++) {
                        String header = csvParser.getHeaderNames().get(i);
                        if ("Availability".equals(header)) {
                            updatedRow[i] = action;
                        } else if ("DueDate".equals(header)) {
                            updatedRow[i] = dueDate;
                        } else if ("UserId".equals(header)) {
                            updatedRow[i] = userId;
                        } else if ("UniqueID".equals(header)) {
                            updatedRow[i] = uniqueId;
                        } else if ("ItemID".equals(header)) {
                            updatedRow[i] = itemId2;
                        } else if ("Type".equals(header)) {
                            updatedRow[i] = type;
                        } else if ("Title".equals(header)) {
                            updatedRow[i] = title;
                        } else {
                            updatedRow[i] = record.get(i);
                        }
                    }
                    updatedRows.add(updatedRow);
                } else {

                    // updatedRows.add(record.values());
                    String[] updatedRow = new String[record.size()];
                    for (int i = 0; i < record.size(); i++) {
                        String header = csvParser.getHeaderNames().get(i);
                        if ("Availability".equals(header)) {
                            updatedRow[i] = availabilityFlag;
                        } else if ("DueDate".equals(header)) {
                            updatedRow[i] = "";
                        } else if ("UserId".equals(header)) {
                            updatedRow[i] = "";
                        } else if ("UniqueID".equals(header)) {
                            updatedRow[i] = uniqueId;
                        } else if ("ItemID".equals(header)) {
                            updatedRow[i] = itemId2;
                        } else if ("Type".equals(header)) {
                            updatedRow[i] = type;
                        } else if ("Title".equals(header)) {
                            updatedRow[i] = title;
                        } else {
                            updatedRow[i] = record.get(i);
                        }
                    }
                    updatedRows.add(updatedRow);
                }
            }
        }

        // Write the updated data to a new CSV file
        try (Writer writer = new FileWriter(
                "C:\\Users\\bradesin\\OneDrive - University of Bradford\\Documents\\Berenberglibrary.csv");
                CSVPrinter csvPrinter = new CSVPrinter(writer,
                        CSVFormat.DEFAULT.withHeader("UniqueID", "ItemID", "Type", "Title", "Availability", "DueDate",
                                "UserId"))) {

            for (String[] row : updatedRows) {
                csvPrinter.printRecord(row);
            }

            csvPrinter.flush();
            libraryResponse.setResponseCode("00");
            libraryResponse.setResponseMessage("Success");
        } catch (FileNotFoundException ex) {
            
            libraryResponse.setResponseCode("99");
            libraryResponse.setResponseMessage("Server Busy");
        }
        return libraryResponse;
    }
}
