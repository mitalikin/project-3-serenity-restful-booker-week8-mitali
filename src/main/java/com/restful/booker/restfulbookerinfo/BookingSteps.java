package com.restful.booker.restfulbookerinfo;

import com.restful.booker.constant.EndPoints;
import com.restful.booker.model.AuthPojo;
import com.restful.booker.model.BookingPojo;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import jdk.nashorn.internal.parser.Token;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

import java.util.HashMap;

public class BookingSteps {
    @Step("Auth the user username:{0},password{1}")
    public ValidatableResponse authUser(String username, String password) {
        AuthPojo authPojo = new AuthPojo();
        authPojo.setUsername(username);
        authPojo.setPassword(password);
        return SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .body(authPojo)
                .when()
                .post(EndPoints.AUTH)
                .then();

    }

    @Step("Create Booking firstname:{0},lastname:{1},totalprice:{2},depositpaid:{3},bookingdates:{4},additionalneeds:{5},")
    public ValidatableResponse createBooking(String firstname, String lastname, int totalprice, boolean depositpaid,
                                             HashMap<Object, Object> bookingdates, String additionalneeds) {
        BookingPojo bookingPojo = new BookingPojo();
        bookingPojo.setFirstname(firstname);
        bookingPojo.setLastname(lastname);
        bookingPojo.setTotalprice(totalprice);
        bookingPojo.setDepositpaid(depositpaid);
        bookingPojo.setBookingdates(bookingdates);
        bookingPojo.setAdditionalneeds(additionalneeds);
        return SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .body(bookingPojo)
                .when()
                .post(EndPoints.CREATE_BOOKING)
                .then();
    }

    @Step("Update Booking  BookingID: {0},firstname: {1}, lastname: {2},totalprice: {3},depositepaid: {4}, bookingsDates:{5},addtionalsneeds :{6} ")
    public ValidatableResponse updateBooking(int bookingID,
                                             String firstname,
                                             String lastname,
                                             int totalprice,
                                             boolean depositpaid,
                                             HashMap<Object, Object> bookingdates,
                                             String additionalneeds) {

        BookingPojo bookingPojo = new BookingPojo();
        bookingPojo.setFirstname(firstname);
        bookingPojo.setLastname(lastname);
        bookingPojo.setTotalprice(totalprice);
        bookingPojo.setDepositpaid(depositpaid);
        bookingPojo.setBookingdates(bookingdates);
        bookingPojo.setAdditionalneeds(additionalneeds);
        return SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .body(bookingPojo)
                .pathParam("bookingID", bookingID)
                .auth().preemptive().basic("admin", "password123")
                .when()
                .put(EndPoints.UPDATE_BOOKING)
                .then();

    }

    @Step("Delete Booking  BookingID: {0}")
    public ValidatableResponse deleteBooking(int bookingID) {
        return SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .pathParam("bookingID", bookingID)
                .auth().preemptive().basic("admin", "password123")
                .when()
                .delete(EndPoints.DELETE_BOOKING)
                .then();
    }

    @Step("Get Booking by BookingID: {0}")
    public ValidatableResponse getBookingByID(int bookingID) {
        return SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .pathParam("bookingID", bookingID)
                .when()
                .get(EndPoints.GET_ALL_BOOKING_IDS)
                .then();
    }


}
