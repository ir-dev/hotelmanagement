<template>
  <div class="tm-section tm-section-pad tm-bg-gray" id="tm-section-3">
    <div class="form-row tm-search-form-row">
      <div class="form-group tm-form-element">
        <label for="isOrganization" class="form-label">Organization</label>
        <input type="checkbox" id="isOrganization" v-model="isOrganization" class="form-control"/>
      </div>
      <div class="form-group tm-form-element">
        <label for="organizationName" class="form-label">Organization Name</label>
        <input type="text" id="organizationName" v-model="organizationName" class="form-control" placeholder="FHV" />
      </div>
      <div class="form-group tm-form-element">
        <label for="discountRate" class="form-label">Discount Rate</label>
        <input type="number" id="discountRate" v-model="discountRate" class="form-control" placeholder="25" />
      </div>
    </div>
    <div class="form-row tm-search-form-row">
      <div class="form-group tm-form-element">
        <label for="salutation" class="form-label">Salutation</label>
        <select id="salutation" name="salutation" v-model="salutation">
          <option value="MR">MR</option>
          <option value="MS">MS</option>
          <option value="DIVERSER">DIVERSE</option>
        </select>
      </div>
      <div class="form-group tm-form-element">
        <i class="fa fa-calendar fa-2x tm-form-element-icon"></i>
        <label for="dateOfBirth" class="form-label">Date of Birth</label>
        <input name="dateOfBirth" v-model="dateOfBirth" type="date" class="form-control" id="dateOfBirth">
      </div>
    </div>
    <div class="form-row tm-search-form-row">
      <div class="form-group tm-form-element">
        <label for="firstName" class="form-label">First Name</label>
        <input type="text" id="firstName" v-model="firstName" class="form-control" placeholder="###" />
      </div>
      <div class="form-group tm-form-element">
        <label for="lastName" class="form-label">Last Name</label>
        <input type="text" id="lastName" v-model="lastName" class="form-control" placeholder="###" />
      </div>
    </div>
    <div class="form-row tm-search-form-row">
      <div class="form-group tm-form-element">
        <label for="street" class="form-label">Street</label>
        <input type="text" id="street" v-model="street" class="form-control" placeholder="Street 1" />
      </div>
      <div class="form-group tm-form-element">
        <label for="zipcode" class="form-label">Zipcode</label>
        <input type="text" id="zipcode" v-model="zipcode" class="form-control" placeholder="6900" />
      </div>
      <div class="form-group tm-form-element">
        <label for="city" class="form-label">City</label>
        <input type="text" id="city" v-model="city" class="form-control" placeholder="Bregenz" />
      </div>
    </div>
    <div class="form-row tm-search-form-row">
      <div class="form-group tm-form-element">
        <label for="country" class="form-label">Country</label>
        <select id="country" name="country" v-model="country">
          <option value="AT">AT</option>
          <option value="DE">DE</option>
          <option value="CH">CH</option>
        </select>
      </div>
      <div class="form-group tm-form-element">
        <label for="specialNotes" class="form-label">Special Notes</label>
        <input type="text" id="specialNotes" v-model="specialNotes" class="form-control"/>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "GuestDetails",
  data() {
    return {
      isOrganization: true,
      organizationName: "",
      discountRate: "",
      salutation: "",
      dateOfBirth: "",
      firstName: "",
      lastName: "",
      street: "",
      zipcode: "",
      city: "",
      country: "",
      specialNotes: ""
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
