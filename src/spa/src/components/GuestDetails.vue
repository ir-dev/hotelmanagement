<template>
  <div class="tm-section tm-section-pad tm-bg-gray" id="tm-section-3">
    <div class="form-row tm-search-form-row">
      <div class="form-group tm-form-element tm-form-element-100">
        <i class="fa fa-map-marker fa-2x tm-form-element-icon"></i>
        <input
          name="city"
          type="text"
          class="form-control"
          id="inputCity"
          placeholder="Type your destination..."
        />
      </div>
      <div class="form-group tm-form-element tm-form-element-50">
        <i class="fa fa-calendar fa-2x tm-form-element-icon"></i>
        <input
          name="check-in"
          type="text"
          class="form-control"
          id="inputCheckIn"
          placeholder="Check In"
        />
      </div>
    </div>
    <div class="form-row tm-search-form-row">
      <div class="form-group tm-form-element tm-form-element-2">
        <select name="adult" class="form-control tm-select" id="adult">
          <option value="">Adult</option>
          <option value="1">1</option>
        </select>
        <i class="fa fa-2x fa-user tm-form-element-icon"></i>
      </div>
      <div class="form-group tm-form-element tm-form-element-2">
        <select name="children" class="form-control tm-select" id="children">
          <option value="">Children</option>
          <option value="0">0</option>
        </select>
        <i
          class="fa fa-user tm-form-element-icon tm-form-element-icon-small"
        ></i>
      </div>
      <div class="form-group tm-form-element tm-form-element-2">
        <select name="room" class="form-control tm-select" id="room">
          <option value="">Room</option>
          <option value="1">1</option>
        </select>
        <i class="fa fa-2x fa-bed tm-form-element-icon"></i>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "GuestDetails",
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
