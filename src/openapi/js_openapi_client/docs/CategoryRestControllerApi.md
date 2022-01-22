# OpenApiDefinition.CategoryRestControllerApi

All URIs are relative to *http://localhost:8080*

Method | HTTP request | Description
------------- | ------------- | -------------
[**availableCategoriesForBooking**](CategoryRestControllerApi.md#availableCategoriesForBooking) | **GET** /rest/categories | 



## availableCategoriesForBooking

> [AvailableCategoryDTO] availableCategoriesForBooking(arrivalDate, departureDate)



### Example

```javascript
import OpenApiDefinition from 'open_api_definition';

let apiInstance = new OpenApiDefinition.CategoryRestControllerApi();
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

