package com.luxoft.web;

import com.luxoft.web.page.HomePage;

import com.luxoft.web.page.SetUp;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;


import java.util.Arrays;

public class TaskTest {
    private static HomePage homePage;


    @BeforeAll
    static void init(){
            SetUp setUp = new SetUp();
            homePage = new HomePage(SetUp.driver);  
    }

    @AfterAll
    static void tearDown(){
            SetUp.driver.quit();
        }


    @Test
    void TakeManufacturerList(){
        homePage.ManufacturerList();
    }

    @ParameterizedTest
 //   @CsvSource(value ={"Baby Mix","Evo-Kids","Joy"})
    @CsvFileSource(resources = "/test2.csv", numLinesToSkip = 0, delimiter = ',')
    void verifyListGoods(String nameManufacturer) throws InterruptedException {
         homePage.compareLinkTexts_Count(nameManufacturer)  ;

    }


}
