<template>
  <div class="tm-section tm-section-pad tm-bg-gray" id="tm-section-3">
    <div class="form-row tm-search-form-row">
      <div class="form-check">
        <input class="form-check-input" v-model="form.isOrganization" @click="toggle()" @change="updateForm" type="checkbox" value="true" id="flexCheckDefault">
        <label class="form-check-label" for="flexCheckDefault">
          Organization
        </label>
      </div>
      <div class="form-group tm-form-element tm-form-element-50">
        <i class="fa fa-map-marker fa-2x tm-form-element-icon"></i>
        <input name="organizationName" v-model="form.organizationName" @change="updateForm" type="text" class="form-control" id="organizationName" placeholder="" disabled/>
      </div>
      <div class="form-group tm-form-element tm-form-element-50">
        <i class="fa fa-map-marker fa-2x tm-form-element-icon"></i>
        <input name="discountRate" v-model="form.discountRate" @change="updateForm" type="text" class="form-control" id="discountRate" placeholder="" disabled/>
      </div>
    </div>

    <div class="form-row tm-search-form-row">
      <div class="form-group tm-form-element tm-form-element-50">
        <select name="gender" class="form-control tm-select" id="gender">
          <option value="">Mr</option>
          <option value="1">Ms</option>
        </select>
        <i class="fa fa-2x fa-user tm-form-element-icon"></i>
      </div>
      <div class="form-group tm-form-element tm-form-element-50">
        <i class="fa fa-map-marker fa-2x tm-form-element-icon"></i>
        <input name="firstname" v-model="form.firstname" @change="updateForm" type="text" class="form-control" id="firstname" placeholder="Firstname"/>
      </div>
      <div class="form-group tm-form-element tm-form-element-50">
        <i class="fa fa-calendar fa-2x tm-form-element-icon"></i>
        <input name="lastname" v-model="form.lastname" @change="updateForm" type="text" class="form-control" id="lastname" placeholder="Lastname"/>
      </div>
    </div>
    <div class="form-row tm-search-form-row">
      <div class="form-group tm-form-element tm-form-element-50">
        <i class="fa fa-map-marker fa-2x tm-form-element-icon"></i>
        <input name="dateOfBirth" v-model="form.dateOfBirth" @change="updateForm" type="text" class="form-control" id="dateOfBirth" placeholder="Date Of Birth"/>
      </div>
      <div class="form-group tm-form-element tm-form-element-50">
        <i class="fa fa-map-marker fa-2x tm-form-element-icon"></i>
        <input name="street" v-model="form.street" @change="updateForm" type="text" class="form-control" id="street" placeholder="Street"/>
      </div>
      <div class="form-group tm-form-element tm-form-element-50">
        <i class="fa fa-map-marker fa-2x tm-form-element-icon"></i>
        <input name="zipcode" v-model="form.zipcode" @change="updateForm" type="text" class="form-control" id="zipcode" placeholder="Zipcode"/>
      </div>
    </div>
    <div class="form-row tm-search-form-row">
      <div class="form-group tm-form-element tm-form-element-50">
        <i class="fa fa-map-marker fa-2x tm-form-element-icon"></i>
        <input name="city" v-model="form.city" @change="updateForm" type="text" class="form-control" id="city" placeholder="City"/>
      </div>
      <div class="form-group tm-form-element tm-form-element-50">
        <i class="fa fa-map-marker fa-2x tm-form-element-icon"></i>
        <input name="country" v-model="form.country" @change="updateForm" type="text" class="form-control" id="country" placeholder="country"/>
      </div>
      <div class="form-group tm-form-element tm-form-element-50">
        <i class="fa fa-map-marker fa-2x tm-form-element-icon"></i>
        <input name="zipcode" v-model="form.specialNotes" @change="updateForm" type="text" class="form-control" id="specialNotes" placeholder="Special Notes"/>
      </div>
    </div>


  </div>

</template>

<script>

export default {
  name: "GuestDetails",
  props: {
    formProp: {
      type: Object,
      required: true
    }
  },
  data() {
    return {
      form: this.copyFormProp()
    }
  },
  methods: {
    updateForm() {
      this.$emit("update-form", this.form);
    },
    toggle() {
      let organizationName = document.getElementById("organizationName");
      let discountRate = document.getElementById("discountRate");
      let checkbox = document.getElementById("flexCheckDefault");
      if(checkbox.checked){
        organizationName.disabled = false;
        organizationName.placeholder = "Organization Name";
        discountRate.disabled = false;
        discountRate.placeholder = "Discount Rate";
      } else {
        organizationName.disabled = true;
        organizationName.placeholder = "";
        organizationName.value = "";
        discountRate.disabled = true;
        discountRate.placeholder = "";
        discountRate.value = "";
      }
    },
    copyFormProp() {
      let localObj = {};
      Object.assign(localObj, this.formProp);
      return localObj;
    }
  }
};
</script>

<style>

</style>
