<template>
  <div class="tm-bg-img">
    <div class="tm-bg-img ie-container-width-fix-2">
      <div class="container ie-h-align-center-fix">
        <div class="col ml-auto mr-auto ie-container-width-fix">
          <div class="form-row tm-search-form">
            <div class="form-check">
              <input
                  class="form-check-input"
                  v-model="form.isOrganization"
                  @click="toggle()"
                  @change="updateForm"
                  type="checkbox"
                  value="true"
                  id="flexCheckChecked"
              />
              <label class="form-check-label" for="flexCheckChecked">ORGANIZATION</label>
            </div>
          </div>

          <div class="form-row tm-search-form ">
            <div class="form-group tm-form-element" id="rowFlex">
              <i class="fa fa-2x fa-building-o tm-form-element-icon"></i>
              <input
                  name="organizationName"
                  v-model="form.organizationName"
                  @change="updateForm"
                  type="text"
                  class="form-control"
                  id="organizationName"
                  placeholder=""
                  disabled
              />
            </div>
          </div>

          <div class="form-row tm-search-form-row">
            <div class="form-group tm-form-element tm-form-element-2">
              <select
                  v-model="form.salutation"
                  @change="updateForm"
                  class="form-control tm-select"
                  id="salutation"
                  required
              >
                <option value="" selected disabled hidden>Salutation</option>
                <option value="MR">Mr</option>
                <option value="MS">Ms</option>
              </select>
              <i class="fa fa-2x fa-user tm-form-element-icon"></i>
            </div>
            <div class="form-group tm-form-element tm-form-element-2">
              <i class="fa fa-2x fa-user tm-form-element-icon"></i>
              <input
                  name="firstName"
                  v-model="form.firstName"
                  @change="updateForm"
                  type="text"
                  class="form-control"
                  id="firstName"
                  placeholder="Firstname"
                  required
              />
            </div>
            <div class="form-group tm-form-element tm-form-element-2">
              <i class="fa fa-2x fa-user tm-form-element-icon"></i>
              <input
                  name="lastName"
                  v-model="form.lastName"
                  @change="updateForm"
                  type="text"
                  class="form-control"
                  id="lastName"
                  placeholder="Lastname"
                  required
              />
            </div>
            <div class="form-group tm-form-element tm-form-element-2">
              <i class="fa fa-calendar fa-2x tm-form-element-icon"></i>
              <input
                  name="dateOfBirth"
                  v-model="form.dateOfBirth"
                  @change="updateForm"
                  type="date"
                  class="form-control"
                  id="dateOfBirth"
                  required
              />
            </div>


            <div class="form-group tm-form-element tm-form-element-2">
              <i class="fa fa-map-marker fa-2x tm-form-element-icon"></i>
              <input
                  name="street"
                  v-model="form.street"
                  @change="updateForm"
                  type="text"
                  class="form-control"
                  id="street"
                  placeholder="Street"
                  required
              />
            </div>
            <div class="form-group tm-form-element tm-form-element-2">
              <i class="fa fa-map-marker fa-2x tm-form-element-icon"></i>
              <input
                  name="zipcode"
                  v-model="form.zipcode"
                  @change="updateForm"
                  type="text"
                  class="form-control"
                  id="zipcode"
                  placeholder="Zipcode"
                  required
              />
            </div>
            <div class="form-group tm-form-element tm-form-element-2">
              <i class="fa fa-map-marker fa-2x tm-form-element-icon"></i>
              <input
                  name="city"
                  v-model="form.city"
                  @change="updateForm"
                  type="text"
                  class="form-control"
                  id="city"
                  placeholder="City"
                  required
              />
            </div>
            <div class="form-group tm-form-element tm-form-element-2">
              <select
                  v-model="form.country"
                  @change="updateForm"
                  class="form-control tm-select"
                  id="country"
                  required
              >
                <option value="" disabled selected hidden>Country</option>
                <option value="AT">Austria</option>
                <option value="DE">Germany</option>
                <option value="CH">Switzerland</option>
              </select>
              <i class="fa fa-map-marker fa-2x tm-form-element-icon"></i>
            </div>

            <div class="form-group tm-form-element tm-form-element-2">
              <i class="fa fa-2x fa-sticky-note tm-form-element-icon"></i>
              <input
                  name="specialNotes"
                  v-model="form.specialNotes"
                  @change="updateForm"
                  type="text"
                  class="form-control"
                  id="specialNotes"
                  placeholder="Special Notes"
              />
            </div>
          </div>
        </div>
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
      required: true,
    },
  },
  data() {
    return {
      form: this.copyFormProp(),
    };
  },
  methods: {
    updateForm() {
      this.$emit("update-form", this.form);
    },
    toggle() {
      let organizationName = document.getElementById("organizationName");
      let discountRate = document.getElementById("discountRate");
      let checkbox = document.getElementById("flexCheckChecked");
      if (checkbox.checked) {
        organizationName.disabled = false;
        organizationName.placeholder = "Organization Name";
        discountRate.disabled = false;
        discountRate.placeholder = "Discount Rate";
      } else {
        organizationName.disabled = true;
        organizationName.placeholder = "";
        this.form.organizationName = "";
        discountRate.disabled = true;
        discountRate.placeholder = "";
        this.form.discountRate = "";
      }
    },
    copyFormProp() {
      let localObj = {};
      Object.assign(localObj, this.formProp);
      return localObj;
    },
  },
};
</script>

<style>
.form-check-label {
  cursor: pointer;
  color: gray;
  font-size: 15px;
  font-family: Dubai;
  font-weight: bold;
}

.form-check {
  margin: 12px 12px 12px 45px;
}

#flexCheckChecked.form-check-input {
  box-sizing: border-box;
}

#rowFlex {
  width: 100%
}

#space {
  margin: 10px;
}
</style>
