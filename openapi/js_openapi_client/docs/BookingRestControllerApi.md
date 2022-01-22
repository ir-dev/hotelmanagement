# OpenApiDefinition.BookingRestControllerApi

All URIs are relative to *http://localhost:8080*

Method | HTTP request | Description
------------- | ------------- | -------------
[**availableCategoriesForBooking**](BookingRestControllerApi.md#availableCategoriesForBooking) | **GET** /rest/categories | 
[**createBooking**](BookingRestControllerApi.md#createBooking) | **POST** /rest/bookings/create | 



## availableCategoriesForBooking

> [AvailableCategoryDTO] availableCategoriesForBooking(arrivalDate, departureDate)



### Example

```javascript
import OpenApiDefinition from 'open_api_definition';

let apiInstance = new OpenApiDefinition.BookingRestControllerApi();
let arrivalDate = new Date("2013-10-20"); // Date | 
let departureDate = new Date("2013-10-20"); // Date | 
apiInstance.availableCategoriesForBooking(arrivalDate, departureDate, (error, data, response) => {
  if (error) {
    console.error(error);
  } else {
    console.log('API called successfully. Returned data: ' + data);
  }
});
```

### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **arrivalDate** | **Date**|  | 
 **departureDate** | **Date**|  | 

### Return type

[**[AvailableCategoryDTO]**](AvailableCategoryDTO.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: */*


## createBooking

> CreateBookingResponse createBooking(bookingForm)



### Example

```javascript
import OpenApiDefinition from 'open_api_definition';

let apiInstance = new OpenApiDefinition.BookingRestControllerApi();
let bookingForm = new OpenApiDefinition.BookingForm(); // BookingForm | 
apiInstance.createBooking(bookingForm, (error, data, response) => {
  if (error) {
    console.error(error);
  } else {
    console.log('API called successfully. Returned data: ' + data);
  }
});
```

### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **bookingForm** | [**BookingForm**](BookingForm.md)|  | 

### Return type

[**CreateBookingResponse**](CreateBookingResponse.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: */*

