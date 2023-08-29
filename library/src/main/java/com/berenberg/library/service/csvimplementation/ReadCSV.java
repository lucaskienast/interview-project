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

import org.springframework.stereotype.Service;

import com.berenberg.library.model.Item;

import lombok.Synchronized;

@Service
public class ReadCSV {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ReadCSV.class);

    @Synchronized
    public List<Item> reading2() {

        String csvFilePath = "C:\\Users\\bradesin\\OneDrive - University of Bradford\\Documents\\Berenberglibrary.csv";
        List<Item> fetchedList = new ArrayList<Item>();
        try (Reader reader = new FileReader(csvFilePath);
                CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT)) {
            Item fetchedItem;

            for (CSVRecord csvRecord : csvParser) {
                fetchedItem = new Item();

                // Access columns by index

                fetchedItem.setUniqueId(csvRecord.get(0));
                fetchedItem.setItemId(csvRecord.get(1));
                fetchedItem.setItemType(csvRecord.get(2));
                fetchedItem.setItemtitle(csvRecord.get(3));

                fetchedList.add(fetchedItem);

            }
            return fetchedList;
        } catch (FileNotFoundException ex) {

        } catch (IOException ev) {

        } catch (Exception e) {
            // TODO: handle exception
        }
        return fetchedList;

    }

}
