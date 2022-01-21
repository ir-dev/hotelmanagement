<template>
  <div id="tm-section-1" class="tm-section tm-bg-img">
    <div class="tm-bg-video ie-container-width-fix-2">
      <div class="container ie-h-align-center-fix">
        <div class="row">
          <div class="col ml-auto mr-auto ie-container-width-fix">
            <div class="form-row tm-search-form">
              <div class="form-group tm-form-element">
                <i class="fa fa-calendar fa-2x tm-form-element-icon"></i>
                <input
                  name="arrivalDate"
                  v-model="form.arrivalDate"
                  @change="updateForm"
                  type="date"
                  class="form-control"
                  id="arrivalDate"
                />
              </div>
              <div class="form-group tm-form-element">
                <i class="fa fa-calendar fa-2x tm-form-element-icon"></i>
                <input
                  name="departureDate"
                  v-model="form.departureDate"
                  @change="updateForm"
                  type="date"
                  class="form-control"
                  id="departureDate"
                />
              </div>
              <div class="form-group tm-form-element">
                <i class="fa fa-clock-o fa-2x tm-form-element-icon"></i>
                <input
                  name="arrivalTime"
                  v-model="form.arrivalTime"
                  @change="updateForm"
                  type="time"
                  class="form-control"
                  id="arrivalTime"
                />
              </div>
              <div class="form-group tm-form-element">
                <i class="fa fa-2x fa-user tm-form-element-icon"></i>
                <input
                  type="number"
                  v-model="form.numberOfPersons"
                  @change="updateForm"
                  id="numberOfPersons"
                  class="form-control"
                  min="1"
                />
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "StayDetails",
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
      this.$emit("get-categories", this.form);
    },
    copyFormProp() {
      let localObj = {};
      Object.assign(localObj, this.formProp);
      return localObj;
    },
  },
};
</script>

<style></style>
