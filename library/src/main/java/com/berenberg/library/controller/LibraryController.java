package com.berenberg.library.controller;




import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.berenberg.library.dto.BorrowedItemRequest;
import com.berenberg.library.dto.BorrowedItemResponse;
import com.berenberg.library.dto.TokenRequest;
import com.berenberg.library.dto.TokenResponse;
import com.berenberg.library.model.LibraryResponse;
import com.berenberg.library.security.jwt.JwtUtils;
import com.berenberg.library.service.LibraryServiceImpl;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/api/v1/library")
@SecurityRequirement(name = "BearerAuth")

public class LibraryController {
     private static Logger logger = LoggerFactory.getLogger(LibraryController.class);
   
    private final LibraryServiceImpl libraryService;
    private final JwtUtils jwtUtils;

    public LibraryController(LibraryServiceImpl libraryService, JwtUtils jwtUtils) {
        this.libraryService = libraryService;
        this.jwtUtils= jwtUtils;
    }

    @RequestMapping(value = { "/getToken" }, method = { RequestMethod.POST }, produces = {
            "application/json" })
    public TokenResponse getToken(@RequestBody TokenRequest token, HttpServletRequest servletRequest) {
        logger.info("call is getting here*************************************************");
        return jwtUtils.createToken(token);
    }


    @RequestMapping(value = { "/getBorrowedItem/{itemId}" }, method = { RequestMethod.GET }, produces = {
            "application/json" })
    public ResponseEntity<LibraryResponse> getBorrowedItem(@RequestParam int itemId,HttpServletRequest servletRequest) {
        return libraryService.getBorrowedItem(itemId);
    }

    @RequestMapping(value = { "/borrowItem" }, method = { RequestMethod.POST }, produces = {
            "application/json" })
    public ResponseEntity<BorrowedItemResponse> borrowItem(@RequestBody BorrowedItemRequest request,HttpServletRequest servletRequest) {

        return libraryService.borrowItem(request);
    };

    @RequestMapping(value = { "/returnItem/{uniqueId}" }, method = { RequestMethod.PATCH }, produces = {
            "application/json" })
    public ResponseEntity<LibraryResponse> returnItem(String uniqueId,HttpServletRequest servletRequest) {

        return libraryService.returnItem(uniqueId);

    };

    @RequestMapping(value = { "/getCurrentInventory" }, method = { RequestMethod.GET }, produces = {
            "application/json" })
    public ResponseEntity<LibraryResponse> getCurrentInventory(HttpServletRequest servletRequest) {
        logger.info("call is getting here*************************************************ll");
        return libraryService.getCurrentInventory();
    };

    @RequestMapping(value = { "/checkItemAvailability/{itemId}" }, method = { RequestMethod.GET }, produces = {
            "application/json" })
    public ResponseEntity<LibraryResponse> checkItemAvailability(@RequestParam String itemId,HttpServletRequest servletRequest) {

        return libraryService.checkItemAvailability(itemId);
    };

    @RequestMapping(value = { "/getAllItemByUserId/{userId}" }, method = { RequestMethod.GET }, produces = {
            "application/json" })
    public ResponseEntity<LibraryResponse> getAllItemByUserId(@RequestParam String userId,HttpServletRequest servletRequest) {

        return libraryService.getAllItemByUserId(userId);
    };

    @RequestMapping(value = { "/getOverdueItems}" }, method = { RequestMethod.GET }, produces = {
            "application/json" })
    public ResponseEntity<LibraryResponse> getOverdueItems(HttpServletRequest servletRequest) {

        return libraryService.getOverdueItems();
    };

}
