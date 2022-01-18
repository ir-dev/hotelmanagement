<template>
  <div class="tm-section tm-section-pad tm-bg-gray" id="tm-section-1">
    <div class="form-row tm-search-form-row">
      <div class="form-group tm-form-element">
        <i class="fa fa-calendar fa-2x tm-form-element-icon"></i>
        <input name="arrivalDate" v-model="arrivalDate" type="date" class="form-control" id="arrivalDate">
      </div>
      <div class="form-group tm-form-element">
        <i class="fa fa-calendar fa-2x tm-form-element-icon"></i>
        <input name="departureDate" v-model="departureDate" type="date" class="form-control" id="departureDate">
      </div>
      <div class="form-group tm-form-element">
        <i class="fa fa-calendar fa-2x tm-form-element-icon"></i>
        <input name="arrivalTime" v-model="arrivalTime" type="time" class="form-control" id="arrivalTime">
      </div>
      <div class="form-group tm-form-element">
        <i class="fa fa-2x fa-user tm-form-element-icon"></i>
        <input name="numberOfPersons" type="number" id="numberOfPersons" v-model="numberOfPersons" class="form-control" min="1" placeholder="2">
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "StayDetails",
  data() {
    return {
      arrivalDate: "",
      departureDate: "",
      arrivalTime: "",
      numberOfPersons: ""
    }
  }
};
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
