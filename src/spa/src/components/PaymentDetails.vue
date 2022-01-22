<template>
  <div class="tm-bg-img">
    <div class="tm-bg-img ie-container-width-fix-2">
      <div class="container ie-h-align-center-fix">
        <div class="col ml-auto mr-auto ie-container-width-fix">
          <div class="form-row tm-search-form-row">
            <div class="form-group tm-form-element tm-form-element-2">
              <i class="fa fa-2x fa-user tm-form-element-icon"></i>
              <input
                  type="text"
                  v-model="form.cardHolderName"
                  @change="updateForm"
                  id="cardHolderName"
                  class="form-control"
                  placeholder="Card Holder Name"
                  required
              />
            </div>
            <div class="form-group tm-form-element tm-form-element-2">
              <i class="fa fa-2x fa-credit-card tm-form-element-icon"></i>
              <input
                  type="text"
                  v-model="form.cardNumber"
                  @change="updateForm"
                  id="cardNumber"
                  class="form-control"
                  placeholder="Card Number"
                  required
              />
            </div>
            <div class="form-group tm-form-element tm-form-element-2">
              <i class="fa fa-2x fa-credit-card tm-form-element-icon"></i>
              <input
                  type="text"
                  v-model="form.cardValidThru"
                  @change="updateForm"
                  id="cardValidThru"
                  class="form-control"
                  placeholder="Valid thru"
                  required
              />
            </div>
            <div class="form-group tm-form-element tm-form-element-2">
              <i class="fa fa-2x fa-credit-card tm-form-element-icon"></i>
              <input
                  type="text"
                  v-model="form.cardCvc"
                  @change="updateForm"
                  id="cardCvc"
                  class="form-control"
                  placeholder="CVC"
                  required
              />
            </div>
            <div class="form-group tm-form-element tm-form-element-2">
              <select
                  v-model="form.paymentType"
                  @change="updateForm"
                  class="form-control tm-select"
                  id="PaymentType"
                  required
              >
                <option value="" selected disabled hidden>Payment Type</option>
                <option value="CREDITCARD">Credit Card</option>
                <option value="CASH">Cash</option>
                <option value="INVOICE">Invoice</option>
              </select>
              <i class="fa fa-credit-card fa-2x tm-form-element-icon"></i>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "PaymentDetails",
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
    copyFormProp() {
      let localObj = {};
      Object.assign(localObj, this.formProp);
      return localObj;
    },
  },
};
</script>

<style></style>
