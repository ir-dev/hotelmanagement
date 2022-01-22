# OpenApiDefinition.BookingRestControllerApi

All URIs are relative to *http://localhost:8080*

Method | HTTP request | Description
------------- | ------------- | -------------
[**createBooking**](BookingRestControllerApi.md#createBooking) | **POST** /rest/bookings/create | 



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

