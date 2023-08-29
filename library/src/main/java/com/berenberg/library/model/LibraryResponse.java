package com.berenberg.library.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/*This is a third party library for reducing boilerplates, 
*@Data--- handle the getter and setters
@AllArgsConstructor-- creates a constructor with all parameters
@NoArgsConstructor-- creates a constructor with no argument/parameter in the class
@ToString-- create a toString() method that prints the object as String 
 * **/

 @Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LibraryResponse {
    
private String responseCode;
private String responseMessage;
private Object data;

}
