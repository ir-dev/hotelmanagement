<template>
  <div class="tm-main-content" id="top">
    <Navigationbar></Navigationbar>
    <div class="tm-bg-white ie-container-width-fix-2">
      <div class="container ie-h-align-center-fix">
        <div class="row">
          <div class="col-xs-12 ml-auto mr-auto ie-container-width-fix">
            <form v-on:submit.prevent="submitForm()" method="post" class="tm-search-form tm-section-pad-2">
              <StayDetails :formProp="form" @update-form="updateFormStayDetails"></StayDetails>
              <RoomAssignment></RoomAssignment>
              <GuestDetails :formProp="form" @update-form="updateFormGuestDetails"></GuestDetails>
              <PaymentDetails :formProp="form" @update-form="updateFormPaymentDetails"></PaymentDetails>
              <input type="reset" class="btn"/>
              <input type="submit" class="btn"/>
            </form>
          </div>
        </div>
      </div>
    </div>
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
createApp().use(VueAxios, axios)

export default {
  name: "App",
  components: {
    Navigationbar,
    StayDetails,
    RoomAssignment,
    GuestDetails,
    PaymentDetails,
  },
  data(){
    return {
        form: {
          arrivalDate: '',
          departureDate: '',
          arrivalTime: '',
          numberOfPersons: '',

          isOrganization: false,
          organizationName: '',
          discountRate: '',
          firstname: '',
          lastname: '',
          dateOfBirth: '',
          street: '',
          zipcode: '',
          city: '',
          country: '',
          specialNotes: '',

          cardHolderName: '',
          cardNumber: '',
          cardValidThru: '',
          cardCvc: ''
        }
    }
  },
  methods: {
    submitForm(){
      axios.post("127.0.0.1:8080/rest/bookings/create", this.form)
          .then( function( response ){
            if( response.status != 201 ){
              this.fetchError = response.status;
            }else{
              response.json().then( function( data ){
                this.fetchResponse = data;
              }.bind(this));
            }
          }.bind(this));

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
      this.form.firstname = form.firstname;
      this.form.lastname = form.lastname;
      this.form.dateOfBirth = form.dateOfBirth;
      this.form.street = form.street;
      this.form.zipcode = form.zipcode;
      this.form.city = form.city;
      this.form.country = form.country
      this.form.specialNotes = form.specialNotes;
    },
    updateFormPaymentDetails(form) {
      this.form.cardHolderName = form.cardHolderName
      this.form.cardNumber = form.cardNumber;
      this.form.cardValidThru = form.cardValidThru;
      this.form.cardCvc = form.cardCvc;
    }
  }
};
</script>

<style>
@import "assets/css/bootstrap.min.css";
@import "assets/css/tooplate-style.css";
@import "assets/font-awesome-4.7.0/css/font-awesome.css";
</style>
