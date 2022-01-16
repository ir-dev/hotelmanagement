/**
 * OpenAPI definition
 * No description provided (generated by Openapi Generator https://github.com/openapitools/openapi-generator)
 *
 * The version of the OpenAPI document: v0
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */

import * as models from './models';

export interface BookingForm {
    arrivalDate?: string;

    departureDate?: string;

    arrivalTime?: models.LocalTime;

    numberOfPersons?: number;

    selectedCategoriesRoomCount?: { [key: string]: number; };

    isOrganization?: boolean;

    organizationName?: string;

    discountRate?: number;

    salutation?: string;

    firstName?: string;

    lastName?: string;

    dateOfBirth?: string;

    street?: string;

    zipcode?: string;

    city?: string;

    country?: string;

    specialNotes?: string;

    cardHolderName?: string;

    cardNumber?: string;

    cardValidThru?: string;

    cardCvc?: string;

    paymentType?: string;

}
