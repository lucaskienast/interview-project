package com.berenberg.library.service.csvimplementation;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.berenberg.library.Utils.CalculateDate;
import com.berenberg.library.Utils.ConvertDate;
import com.berenberg.library.model.Item;
import com.berenberg.library.model.LibraryResponse;

import lombok.Synchronized;

@Service
public class FetchOverdueItems {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(FetchOverdueItems.class);

    @Synchronized
    public LibraryResponse fetch() {
        LibraryResponse libraryResponse = new LibraryResponse();
        String csvFilePath = "C:\\Users\\bradesin\\OneDrive - University of Bradford\\Documents\\Berenberglibrary.csv";
        List<Item> fetchedList = new ArrayList<Item>();
        Date now = new Date();

        try (Reader reader = new FileReader(csvFilePath);
                CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT)) {

            Item fetchedItem;

            for (CSVRecord csvRecord : csvParser) {
                fetchedItem = new Item();
                String overDueTime = csvRecord.get(5);
                String currentDate = now.toString();
                logger.info("csvRecord===========" + csvRecord);
                logger.info("overDueTime===========" + overDueTime);
                     String dt= null;
                if(overDueTime==null && !(overDueTime.equals("DueDate"))){
                     dt= (CalculateDate.calculateNullDate(now)).toString();
                }
                
                
         logger.info("condition===========" + now.before(ConvertDate.convertDates(overDueTime == null ? "" : dt))+ overDueTime);

                    // Access columns by index
                    if (now.before(ConvertDate.convertDates(overDueTime == null ? "" : dt))) {
                        fetchedItem.setUniqueId(csvRecord.get(0));
                        fetchedItem.setItemId(csvRecord.get(1));
                        fetchedItem.setItemType(csvRecord.get(2));
                        fetchedItem.setItemtitle(csvRecord.get(3));

                        fetchedList.add(fetchedItem);
                    }

                

            }
            logger.info("fetchedList =====" + fetchedList);

            libraryResponse.setData(fetchedList);
            libraryResponse.setResponseCode("00");
            libraryResponse.setResponseMessage("Successful");

            return libraryResponse;
        } catch (FileNotFoundException ex) {
            libraryResponse.setResponseCode("99");
            libraryResponse.setResponseMessage("Server Busy");
        } catch (IOException ev) {
            libraryResponse.setResponseCode("99");
            libraryResponse.setResponseMessage("Server Busy");
        } catch (Exception e) {
            libraryResponse.setResponseCode("99");
            libraryResponse.setResponseMessage("Server Busy");
        }
        return libraryResponse;

    }

}
