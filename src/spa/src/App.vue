<template>
  <div class="tm-main-content" id="top">
    <Navigationbar></Navigationbar>
    <form
        v-on:submit.prevent="submitForm()"
        method="post"
        id="bookingForm"
        class="tm-search-form tm-section-pad-2"
    >
      <div class="card-header" id="tm-section-1">
        <h5>STAY DETAILS</h5>
      </div>
      <StayDetails
          :formProp="form"
          @update-form="updateFormStayDetails"
          @get-categories="getCategories"
      ></StayDetails>
      <div class="card-header" id="tm-section-2">
        <h5>CATEGORIES</h5>
      </div>
      <div class="tm-section-2">
        <RoomAssignment
            v-for="category in categories"
            :key="category.name"
            :category="category"
            @selected-categories="updateSelectedCategories"
        >
        </RoomAssignment>
        <div v-if="categories" id="tSum">
          <h3>Total Sum: € {{ calculateSum() }} ,-</h3>
        </div>
      </div>
      <div class="card-header" id="tm-section-3">
        <h5>GUEST DETAILS</h5>
      </div>
      <div class="tm-section-3">
        <GuestDetails :formProp="form" @update-form="updateFormGuestDetails">
        </GuestDetails>
      </div>
      <div class="card-header" id="tm-section-4">
        <h5>PAYMENT DETAILS</h5>
      </div>
      <div class="tm-section-4">
        <PaymentDetails :formProp="form" @update-form="updateFormPaymentDetails">
        </PaymentDetails>
      </div>
      <div class="tm-section-4">
        <input type="reset" class="btn btn-outline-danger btn-lg mr-lg-2" value="Reset" style="width: 100px"/>
        <input type="submit" class="btn btn-outline-success btn-lg" value="Send" style="width: 180px"/>
      </div>
    </form>
  </div>
</template>

<script>
import Navigationbar from "@/components/Navigationbar";
import StayDetails from "@/components/StayDetails";
import RoomAssignment from "@/components/RoomAssignment";
import GuestDetails from "@/components/GuestDetails";
import PaymentDetails from "@/components/PaymentDetails";


import axios from "axios";
import VueAxios from "vue-axios";
import {createApp} from "vue";

createApp().use(VueAxios, axios);

import CategoryRestControllerApi from '../../openapi/js_openapi_client/src/api/CategoryRestControllerApi';
import BookingRestControllerApi from '../../openapi/js_openapi_client/src/api/BookingRestControllerApi';
import BookingForm from '../../openapi/js_openapi_client/src/model/BookingForm';

export default {
  name: "App",
  components: {
    Navigationbar,
    StayDetails,
    RoomAssignment,
    GuestDetails,
    PaymentDetails,
  },
  data() {
    return {
      form: {
        arrivalDate: this.currentDay(),
        departureDate: this.currentDatePlus7Days(),
        arrivalTime: "",
        numberOfPersons: 2,

        selectedCategoriesRoomCount: {},

        isOrganization: false,
        organizationName: "",
        salutation: "",
        firstName: "",
        lastName: "",
        dateOfBirth: "",
        street: "",
        zipcode: "",
        city: "",
        country: "",
        specialNotes: "",

        cardHolderName: "",
        cardNumber: "",
        cardValidThru: "",
        cardCvc: "",
        paymentType: "",
      },
      categories: null,
    };
  },
  methods: {
    calculateSum() {
      if (this.categories !== null) {
        let price = 0;
        console.log(this.categories);
        for (let x = 0; x < this.categories.length; x++) {
          if (this.form.selectedCategoriesRoomCount[this.categories[x].name]) {
            console.log(this.form.selectedCategoriesRoomCount[this.categories[x].name]);
            price +=
                this.form.selectedCategoriesRoomCount[this.categories[x].name] *
                this.categories[x].price.amount;
          }
        }
        return price;
      }
    },
    getCategories() {
      if (this.form.arrivalDate && this.form.departureDate) {
        let arrivalDate = new Date(this.form.arrivalDate); // Date |
        let departureDate = new Date(this.form.departureDate); // Date |
        console.log(arrivalDate);
        let apiInstance = new CategoryRestControllerApi();
        apiInstance.availableCategoriesForBooking(arrivalDate, departureDate, (error, data, response) => {
          if (error) {
            console.error(error);
            alert(error.message);
          } else {
            console.log('API called successfully. Returned data: ' + data);
            this.categories = data
            if (response.data.message) alert(response.data.message);
          }
        });
      }
    },
    submitForm() {
      if (this.form.selectedCategoriesRoomCount !== null) {
        let apiInstance = new BookingRestControllerApi();
        let bookingForm = BookingForm.constructFromObject(this.form, null); // BookingForm
        apiInstance.createBooking(bookingForm, (error, data, response) => {
          if (error) {
            console.error(error);
          } else {
            console.log('API called successfully. Returned data: ' + data);
          }
        });
      } else {
        alert("Please select categories!");
      }
    },
    updateFormStayDetails(form) {
      this.form.arrivalDate = form.arrivalDate;
      this.form.departureDate = form.departureDate;
      this.form.arrivalTime = form.arrivalTime;
      this.form.numberOfPersons = form.numberOfPersons;
    },
    updateFormGuestDetails(form) {
      this.form.isOrganization = form.isOrganization;
      this.form.organizationName = form.organizationName;
      this.form.salutation = form.salutation;
      this.form.firstName = form.firstName;
      this.form.lastName = form.lastName;
      this.form.dateOfBirth = form.dateOfBirth;
      this.form.street = form.street;
      this.form.zipcode = form.zipcode;
      this.form.city = form.city;
      this.form.country = form.country;
      this.form.specialNotes = form.specialNotes;
    },
    updateFormPaymentDetails(form) {
      this.form.cardHolderName = form.cardHolderName;
      this.form.cardNumber = form.cardNumber;
      this.form.cardValidThru = form.cardValidThru;
      this.form.cardCvc = form.cardCvc;
      this.form.paymentType = form.paymentType;
    },
    updateSelectedCategories(categoryName, roomCount) {
      this.form.selectedCategoriesRoomCount[categoryName] = roomCount;
    },
    currentDay() {
      return new Date().toISOString().substring(0,10);
    },
    currentDatePlus7Days() {
      var date = new Date();
      date.setDate(date.getDate() + 7);
      return date.toISOString().substring(0,10);
    }
  }
};
</script>

<style>
@import "assets/css/bootstrap.min.css";
@import "assets/css/tooplate-style.css";
@import "assets/font-awesome-4.7.0/css/font-awesome.css";

.card-header {
  background: #9fcdff;
}

h5 {
  color: #0056b3;
  font-family: Dubai;
  font-weight: bold;
  font-size: 19px;
}

#tSum {
  margin-left: 650px;
  margin-top: 30px;
  margin-bottom: 30px
}
</style>
