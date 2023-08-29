package com.berenberg.library.Utils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;

import com.berenberg.library.model.LibraryResponse;

import lombok.Synchronized;

@Service
public class CheckItemAvailability {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(CheckItemAvailability.class);
   
   
    @Synchronized
    public boolean isItemAvailable(String uniqueId) throws IOException {
        String csvFilePath = "C:\\Users\\bradesin\\OneDrive - University of Bradford\\Documents\\Berenberglibrary.csv";

        List<CSVRecord> records = new ArrayList<>();

        // Read data from the CSV file
        try (Reader reader = new FileReader(csvFilePath);
                CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {

            for (CSVRecord csvRecord : csvParser) {
                records.add(csvRecord);
            }
        } catch (FileNotFoundException fx) {

        }

        // Update the data
        for (CSVRecord record : records) {
            String availability = record.get("Availability");
            String targetItem= record.get("UniqueID");

            logger.info("availability******1"+availability);
            if (( targetItem.equals(uniqueId) ))  {
                if(availability.equals("N")){
                    return false;
                }
                return true;
            }
        }

        return true;
    }

    @Synchronized
    public LibraryResponse confirmAvailability(String uniqueItemId){
        LibraryResponse libraryResponse= new LibraryResponse();
        boolean confirmation= true;

            try {
                confirmation= isItemAvailable(uniqueItemId);
                logger.info("confirmation******"+confirmation);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        if(!confirmation){
            libraryResponse.setResponseCode("99");
            libraryResponse.setResponseMessage("The Item you have requested for is unavailable at the moment. Kindly check back");

           
        }else{
             libraryResponse.setResponseCode("00");
            libraryResponse.setResponseMessage("The Item you have requested for is available");

            
        }

        return libraryResponse;
    }





}