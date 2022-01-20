<template>
  <div class="tm-main-content" id="top">
    <Navigationbar></Navigationbar>
    <div class="alert alert-danger" role="alert" v-if="errormessage">
      <h1>{{ this.errormessage }}</h1>
    </div>

    <form v-on:submit.prevent="submitForm()" method="post" id="bookingForm"
          class="tm-search-form tm-section-pad-2">
      <StayDetails :formProp="form" @update-form="updateFormStayDetails"
                   @get-categories="getCategories"></StayDetails>

      <div class="card-header" style="background: #0062cc" id="tm-section-2">

        <h5 style="color: #9fcdff">Categories</h5>
      </div>
      <RoomAssignment
          v-for="category in categories"
          :key="category.name"
          :category="category"
          @selected-categories="updateSelectedCategories"
      >
      </RoomAssignment>
      <div class="card-footer">
        <small class="text-muted">Last updated 3 mins ago</small>
      </div>
      <GuestDetails :formProp="form" @update-form="updateFormGuestDetails"></GuestDetails>
      <PaymentDetails :formProp="form" @update-form="updateFormPaymentDetails"></PaymentDetails>
      <input type="reset" class="btn"/>
      <input type="submit" class="btn"/>
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
        arrivalDate: "",
        departureDate: "",
        arrivalTime: "",
        numberOfPersons: "",

        selectedCategoriesRoomCount: {},

        isOrganization: false,
        organizationName: "",
        discountRate: "",
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
      errormessage: "",
    };
  },
  methods: {
    getCategories() {
      if (this.form.arrivalDate && this.form.departureDate) {
        axios.get("http://127.0.0.1:8080/rest/categories?arrivalDate=" + this.form.arrivalDate + "&departureDate=" + this.form.departureDate).then(
            (response) => {
              console.log(response.data);
              this.categories = response.data;
              this.errormessage = response.data.message;
            },
            (error) => {
              console.log(error);
              this.errormessage = error.message;
            }
        );
      }
    },
    submitForm() {
      axios.post("http://127.0.0.1:8080/rest/bookings/create", this.form).then(
          (response) => {
            console.log(response.status);
            document.getElementById("bookingForm").reset();
            this.errormessage = response.data.message;
          },
          (error) => {
            console.log(error);
            this.errormessage = error.message;
          }
      );
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
      this.form.discountRate = form.discountRate;
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
  },
};
</script>

<style>
@import "assets/css/bootstrap.min.css";
@import "assets/css/tooplate-style.css";
@import "assets/font-awesome-4.7.0/css/font-awesome.css";
</style>
