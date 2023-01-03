package com.restful.booker.restfulbookerinfo;

import com.restful.booker.testbase.TestBase;
import com.restful.booker.utils.TestUtils;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;

import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.core.IsAnything.anything;

@RunWith(SerenityRunner.class)
public class BookingCRUDTestwithStep extends TestBase {
public static String username="admin";
public static String password="password123";
public static String firstname="josh"+ TestUtils.getRandomValue();
public static String lastname="Brown"+ TestUtils.getRandomValue();
public static Integer totalprice=150;
public static Boolean depositpaid=true;


public static String additionalneeds="Breakfast";
public static int bookingID;
public static String token;

@Steps
    BookingSteps bookingSteps;
@Title("This will create auth")
@Test
public void createTokenTest001(){
    ValidatableResponse response=bookingSteps.authUser(username,password);
    response.log().all().statusCode(200);
    HashMap<String,Object>token=response.log().all().extract().path("");
    Assert.assertThat(token,hasKey("token"));
}
@Title("This will create booking user")
@Test
public void test002(){
    HashMap<Object,Object>bookingdates=new HashMap<>();
    bookingdates.put("check in","2023-05-01");
    bookingdates.put("check out","2023-09-01");
    ValidatableResponse response = bookingSteps.createBooking(firstname,lastname,totalprice,depositpaid,
            bookingdates,additionalneeds);
    response.log().all().statusCode(200);
    bookingID= response.log().all().extract().path("bookingid");
    HashMap<String,Object>booking=response.log().all().extract().path("");
    Assert.assertThat(booking,hasKey("firstname"));
}

    @Title("This will update booking user")
    @Test
    public void test003(){
        HashMap<Object,Object>bookingdates=new HashMap<>();
        bookingdates.put("check in","2023-05-01");
        bookingdates.put("check out","2023-09-01");
        firstname = firstname+"_updated";
        lastname = lastname+"_updated";
        additionalneeds = "wheelchairs";
        ValidatableResponse response = bookingSteps.createBooking(firstname,lastname,totalprice,depositpaid,bookingdates,additionalneeds);
        response.log().all().statusCode(200);
        bookingID= response.log().all().extract().path("bookingid");
        HashMap<String,Object>booking=response.log().all().extract().path("");
        Assert.assertThat(booking,hasKey("firstname"));
    }
    @Title("This will Deleted a user")
    @Test
    public void test004() {

        ValidatableResponse response = bookingSteps.deleteBooking(bookingID);
        response.log().all().statusCode(200);
        ValidatableResponse response1 = bookingSteps.getBookingByID(bookingID);
        response1.log().all().statusCode(404);

    }







}
