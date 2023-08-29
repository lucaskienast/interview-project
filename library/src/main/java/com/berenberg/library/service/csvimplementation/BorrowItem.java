package com.berenberg.library.service.csvimplementation;

import java.io.IOException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.berenberg.library.Utils.CSVUpdateLibrary;
import com.berenberg.library.Utils.CalculateDate;
import com.berenberg.library.Utils.CheckItemAvailability;
import com.berenberg.library.dto.BorrowedItemRequest;
import com.berenberg.library.dto.BorrowedItemResponse;
import com.berenberg.library.model.Item;
import com.berenberg.library.model.LibraryResponse;

import lombok.Synchronized;

@Service
public class BorrowItem {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(BorrowItem.class);
    @Autowired
    private CheckItemAvailability checkItemAvailability;

    @Autowired
    private CalculateDate calculateDate;

    @Autowired
    CSVUpdateLibrary csvUpdateLibrary;

    public BorrowItem(CheckItemAvailability checkItemAvailability, CalculateDate calculateDate,
            CSVUpdateLibrary csvUpdateLibrary) {
        this.checkItemAvailability = checkItemAvailability;
        this.calculateDate = calculateDate;
        this.csvUpdateLibrary = csvUpdateLibrary;

    }


    @Synchronized
    public BorrowedItemResponse borrowItem(BorrowedItemRequest request) {

        BorrowedItemResponse response = new BorrowedItemResponse();
        LibraryResponse libraryResponse = new LibraryResponse();
         LibraryResponse updateResponse = new LibraryResponse();
        Item itemObj = new Item();

        Item item = request.getItem();

        try {
            logger.info("item.getUniqueId()******" + item.getUniqueId());
            boolean confirmation = checkItemAvailability.isItemAvailable(item.getUniqueId());
            logger.info("confirmation******" + confirmation);
            if (!confirmation) {
                libraryResponse.setResponseCode("99");
                libraryResponse.setResponseMessage(
                        "The Item you have requested for is unavailable at the moment. Kindly check back");
                response.setLibraryResponse(libraryResponse);
                return response;
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        if (request.getUserId() != null && item.getItemId() != null && item.getUniqueId() != null) {
            Date now = new Date();
            Date returnDate = calculateDate.calculateReturnDate(now);
            String finalreturnDate = returnDate.toString();
            Date borrowDate = now;

            try {
                updateResponse =csvUpdateLibrary.UpdateCSV(item.getItemId(), item.getUniqueId(), "N", finalreturnDate,
                        request.getUserId());

              libraryResponse.setResponseCode(updateResponse.getResponseCode());
              libraryResponse.setResponseMessage(updateResponse.getResponseMessage());
                itemObj.setItemId(item.getItemId());
                itemObj.setItemtitle(item.getItemtitle());
                itemObj.setItemType(item.getItemType());
            } catch (IOException e) {
                e.printStackTrace();
            }

            response.setUserId(request.getUserId());
            response.setUserName(request.getUserName());
            response.setItem(itemObj);
            response.setLibraryResponse(libraryResponse);
            response.setBorrowedItemDate(borrowDate);
            response.setItemDueDate(returnDate);

        }

        return response;

    }

}
