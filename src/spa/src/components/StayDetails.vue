<template>
  <div class="tm-section tm-section-pad tm-bg-gray" id="tm-section-1">
    <div class="form-row tm-search-form-row">
      <div class="form-group tm-form-element">
        <i class="fa fa-calendar fa-2x tm-form-element-icon"></i>
        <input name="arrivalDate" type="date" class="form-control" id="arrivalDate" required>
      </div>
      <div class="form-group tm-form-element">
        <i class="fa fa-calendar fa-2x tm-form-element-icon"></i>
        <input name="departureDate" type="date" class="form-control" id="departureDate" required>
      </div>
      <div class="form-group tm-form-element">
        <i class="fa fa-calendar fa-2x tm-form-element-icon"></i>
        <input name="arrivalTime" type="time" class="form-control" id="arrivalTime" required>
      </div>
      <div class="form-group tm-form-element">
        <i class="fa fa-2x fa-user tm-form-element-icon"></i>
        <input type="number" id="numberOfPersons" class="form-control" min="1" placeholder="2" required>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "StayDetails",
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
