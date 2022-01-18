<template>
  <div class="tm-main-content" id="top">
    <Navigationbar></Navigationbar>
    <div class="tm-bg-white ie-container-width-fix-2">
      <div class="container ie-h-align-center-fix">
        <div class="row">
          <div class="col-xs-12 ml-auto mr-auto ie-container-width-fix">
            <form @submit.prevent="submitForm()" class="tm-search-form tm-section-pad-2">
              <StayDetails></StayDetails>
              <RoomAssignment></RoomAssignment>
              <GuestDetails></GuestDetails>
              <PaymentDetails></PaymentDetails>
              <input type="reset" class="btn" />
              <input type="submit" class="btn" />
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

export default {
  name: "App",
  components: {
    Navigationbar,
    StayDetails,
    RoomAssignment,
    GuestDetails,
    PaymentDetails,
  },
  // data() {
    // arrivalDate: String,
    // departureDate,
    // arrivalTime,
    // numberOfPersons,
    // selectedCategoriesRoomCount,
    // isOrganization,
    // organizationName,
    // discountRate,
    // salutation,
    // firstName,
    // lastName,
    // dateOfBirth,
    // street,
    // zipcode,
    // city,
    // country,
    // specialNotes,
    // cardHolderName,
    // cardNumber,
    // cardValidThru,
    // cardCvc,
    // paymentType
  // },
  methods: {
    async submitForm() {
      const res = await fetch("http://localhost:8080/rest/bookings/create", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
          arrivalDate: this.arrivalDate,
          departureDate: this.departureDate,
          arrivalTime: this.arrivalTime,
          numberOfPersons: this.numberOfPersons,
          selectedCategoriesRoomCount: this.selectedCategoriesRoomCount,
          isOrganization: this.isOrganization,
          organizationName: this.organizationName,
          discountRate: this.discountRate,
          salutation: this.salutation,
          firstName: this.firstName,
          lastName: this.lastName,
          dateOfBirth: this.dateOfBirth,
          street: this.street,
          zipcode: this.zipcode,
          city: this.city,
          country: this.country,
          specialNotes: this.specialNotes,
          cardHolderName: this.cardHolderName,
          cardNumber: this.cardNumber,
          cardValidThru: this.cardValidThru,
          cardCvc: this.cardCvc,
          paymentType: this.paymentType,
        }),
      });
      console.log(res)
    },
  }
};
</script>

<style>
@import "assets/slick/slick.less";
@import "assets/slick/slick.scss";
@import "assets/slick/slick.css";
@import "assets/slick/slick-theme.css";
@import "assets/css/bootstrap.min.css";
@import "assets/css/datepicker.css";
@import "assets/css/tooplate-style.css";
@import "assets/font-awesome-4.7.0/css/font-awesome.css";
@import "assets/font-awesome-4.7.0/css/font-awesome.min.css";
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
