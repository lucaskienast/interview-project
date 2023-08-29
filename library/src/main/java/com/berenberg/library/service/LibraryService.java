package com.berenberg.library.service;



import org.springframework.http.ResponseEntity;

import com.berenberg.library.dto.BorrowedItemRequest;
import com.berenberg.library.dto.BorrowedItemResponse;
import com.berenberg.library.model.LibraryResponse;


public interface LibraryService {

        public ResponseEntity<LibraryResponse> getBorrowedItem(int itemId);
        public ResponseEntity<BorrowedItemResponse> borrowItem(BorrowedItemRequest request);
        public ResponseEntity<LibraryResponse> returnItem(String  uniqueId);
        public ResponseEntity<LibraryResponse> getCurrentInventory();
        public ResponseEntity<LibraryResponse>  checkItemAvailability(String uniqueItemId);
        public ResponseEntity<LibraryResponse> getAllItemByUserId(String userId);
        public ResponseEntity<LibraryResponse> getOverdueItems();

        



    
}
