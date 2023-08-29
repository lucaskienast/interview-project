package com.berenberg.library.service.csvimplementation;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import com.berenberg.library.model.Item;

import lombok.Synchronized;

public class FetchBorrowedItem {
     private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(FetchBorrowedItemByUser.class);


     @Synchronized
    public List<Item> fetch(String targetId) {

        String csvFilePath = "C:\\Users\\bradesin\\OneDrive - University of Bradford\\Documents\\Berenberglibrary.csv";
        List<Item> fetchedList = new ArrayList<Item>();
        
        try (Reader reader = new FileReader(csvFilePath);
                CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT)) {
           
                    Item fetchedItem;
            
            for (CSVRecord csvRecord : csvParser) {
                fetchedItem = new Item();
                String availabilityflag = csvRecord.get(4);
               
                // Access columns by index
                if (availabilityflag.equals("N")) {
                    fetchedItem.setUniqueId(csvRecord.get(0));
                    fetchedItem.setItemId(csvRecord.get(1));
                    fetchedItem.setItemType(csvRecord.get(2));
                    fetchedItem.setItemtitle(csvRecord.get(3));

                    fetchedList.add(fetchedItem);
                }

            }
             logger.info("fetchedList ====="+fetchedList);
            return fetchedList;
        } catch (FileNotFoundException ex) {

        } catch (IOException ev) {

        } catch (Exception e) {
            // TODO: handle exception
        }
        return fetchedList;

    }

}
