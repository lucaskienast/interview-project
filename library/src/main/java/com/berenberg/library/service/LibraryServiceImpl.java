package com.berenberg.library.service;


import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.berenberg.library.Utils.CheckItemAvailability;
import com.berenberg.library.dto.BorrowedItemRequest;
import com.berenberg.library.dto.BorrowedItemResponse;
import com.berenberg.library.model.Item;
import com.berenberg.library.model.LibraryResponse;
import com.berenberg.library.service.csvimplementation.BorrowItem;
import com.berenberg.library.service.csvimplementation.FetchBorrowedItemByUser;
import com.berenberg.library.service.csvimplementation.FetchOverdueItems;
import com.berenberg.library.service.csvimplementation.ReadCSV;
import com.berenberg.library.service.csvimplementation.ReturnItem;

@Service
public class LibraryServiceImpl implements LibraryService {

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(LibraryServiceImpl.class);


    @Autowired
   private  ReadCSV readCSV;

   @Autowired
   private BorrowItem borrowItem;

   @Autowired
   private CheckItemAvailability checkItemAvailability;

   @Autowired
    private ReturnItem returnItem;

    @Autowired
    private FetchBorrowedItemByUser fetchBorrowedItemByUser;

    @Autowired
    private FetchOverdueItems fetchOverdueItems;

   public LibraryServiceImpl(ReadCSV readCSV, 
                            BorrowItem borrowItem,
                            ReturnItem returnItem,
                            CheckItemAvailability checkItemAvailability,
                            FetchBorrowedItemByUser fetchBorrowedItemByUser,
                            FetchOverdueItems fetchOverdueItems){
    this.readCSV= readCSV;
    this.borrowItem=borrowItem;
    this.returnItem= returnItem;
    this.checkItemAvailability=checkItemAvailability;
    this.fetchBorrowedItemByUser=fetchBorrowedItemByUser;
    this.fetchOverdueItems= fetchOverdueItems;
   }
    

    @Override
    public ResponseEntity<LibraryResponse> getBorrowedItem(int itemId) {
        // TODO Auto-generated method stub

        LibraryResponse libraryResponse = new LibraryResponse();

        try {

        } catch (Exception e) {
            // TODO: handle exception
            throw new UnsupportedOperationException("Unimplemented method 'getBorrowedItem'");
        }

        return ResponseEntity.ok(libraryResponse);
    }

    @Override
    public ResponseEntity<BorrowedItemResponse> borrowItem(BorrowedItemRequest request) {
        // TODO Auto-generated method stub

        BorrowedItemResponse response = new BorrowedItemResponse();

        try {
            response=borrowItem.borrowItem(request);
        } catch (Exception e) {
            // TODO: handle exception
            throw new UnsupportedOperationException("Unimplemented method 'borrowItem'");
        }

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<LibraryResponse> returnItem(String uniqueId) {
        
        LibraryResponse libraryResponse = new LibraryResponse();
        String action="Y";
        try {
            libraryResponse= returnItem.UpdateReturnedCSV(uniqueId, action);

        } catch (Exception e) {
            
        }

        return ResponseEntity.ok(libraryResponse);
    }

    @Override
    public ResponseEntity<LibraryResponse> getCurrentInventory() {
        LibraryResponse libraryResponse = new LibraryResponse();
        List<Item> returnedItem= new ArrayList<>();
      

        try {
    
            returnedItem=  readCSV.reading2();
            logger.info("returned Items are:"+ returnedItem);

           libraryResponse.setData(returnedItem);
           libraryResponse.setResponseCode("00"); 
           libraryResponse.setResponseMessage("Success");

        } catch (Exception e) {
          libraryResponse.setResponseCode("99");
            libraryResponse.setResponseMessage("Server Busy");
        }

        return ResponseEntity.ok(libraryResponse);
    }

    @Override
    public ResponseEntity<LibraryResponse> checkItemAvailability(String uniqueItemId) {
       
        LibraryResponse libraryResponse = new LibraryResponse();

        try {
            libraryResponse=checkItemAvailability.confirmAvailability(uniqueItemId);
        } catch (Exception e) {
            
        }

        return ResponseEntity.ok(libraryResponse);
    }

    @Override
    public ResponseEntity<LibraryResponse> getAllItemByUserId(String userId) {
    
        LibraryResponse libraryResponse = new LibraryResponse();
        List<Item>     items= new ArrayList<>();

        try {
           
            items=fetchBorrowedItemByUser.fetch(userId);
            libraryResponse.setData(items);
            libraryResponse.setResponseCode("00");
            libraryResponse.setResponseMessage("Successful");
        } catch (Exception e) {
            libraryResponse.setResponseCode("99");
            libraryResponse.setResponseMessage("Server Busy");
        }

        return ResponseEntity.ok(libraryResponse);
    }

    @Override
    public ResponseEntity<LibraryResponse> getOverdueItems() {
        
        LibraryResponse libraryResponse = new LibraryResponse();
         List<Item>     items= new ArrayList<>();

        try {
            libraryResponse=fetchOverdueItems.fetch();
            
        } catch (Exception e) {
             libraryResponse.setResponseCode("99");
            libraryResponse.setResponseMessage("Server Busy");
        }

        return ResponseEntity.ok(libraryResponse);
    }

}
