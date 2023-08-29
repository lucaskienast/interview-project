package com.berenberg.library.utilsTest;

import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.io.IOException;

import com.berenberg.library.Utils.CheckItemAvailability;

public class isItemAvailableTest {

    private CheckItemAvailability checkItemAvailability;

        @BeforeEach
        void setUp(){
            this.checkItemAvailability= new CheckItemAvailability();

        }


         @ParameterizedTest
         @CsvSource({
         "8,true",
         "10,true"})
        void checkIfParticularItemIsAvailable(String itemId) throws IOException{

            //given



            //when
            boolean isValid= checkItemAvailability.isItemAvailable(itemId);


            //then
            assertThat(isValid).isTrue();

            
        }


         @ParameterizedTest
         @CsvSource({
         "7,false"})
        void checkIfParticularItemIsnotAvailable(String itemId) throws IOException{

            //given



            //when
            boolean isValid= checkItemAvailability.isItemAvailable(itemId);


            //then
            assertThat(isValid).isFalse();

            
        }

    
}
