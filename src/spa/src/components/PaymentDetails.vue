<template>
  <div class="tm-section tm-section-pad tm-bg-gray" id="tm-section-4">
    <div class="form-row tm-search-form-row">
      <div class="form-group tm-form-element">
        <label for="cardHolderName" class="form-label">Card Holder Name</label>
        <input type="text" v-model="form.cardHolderName" @change="updateForm" id="cardHolderName" class="form-control" placeholder="Max Mustermann" required />
      </div>
      <div class="form-group tm-form-element">
        <label for="cardNumber" class="form-label">Card Number</label>
        <input type="text" v-model="form.cardNumber" @change="updateForm" id="cardNumber" class="form-control" placeholder="#### #### #### #### ####" required />
      </div>
      <div class="form-group tm-form-element">
        <label for="cardValidThru" class="form-label">Valid thru</label>
        <input type="text" v-model="form.cardValidThru" @change="updateForm" id="cardValidThru" class="form-control" placeholder="12/24" required />
      </div>
      <div class="form-group tm-form-element">
        <label for="cardCvc" class="form-label">CVC</label>
        <input type="text" v-model="form.cardCvc" @change="updateForm" id="cardCvc" class="form-control" placeholder="###" required />
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
