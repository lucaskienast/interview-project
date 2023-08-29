package com.berenberg.library.Utils;

import java.util.Date;

import org.springframework.stereotype.Service;

import lombok.Synchronized;

@Service
public class CalculateDate {

    @Synchronized
    public Date calculateReturnDate(Date borrowDate){

        // Implement your logic to calculate the return date (e.g., +7 days)
        return new Date(borrowDate.getTime() + 7 * 24 * 60 * 60 * 1000);
        
    }

    @Synchronized
    public static Date calculateNullDate(Date borrowDate){

        // Implement your logic to calculate the return date (e.g., +7 days)
        return new Date(borrowDate.getTime() + 1 * 24 * 60 * 60 * 1000);
        
    }
    
}
