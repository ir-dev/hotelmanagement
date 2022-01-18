<template>
  <div class="tm-section tm-section-pad tm-bg-gray" id="tm-section-4">
    <div class="form-row tm-search-form-row">
      <div class="form-group tm-form-element">
        <label for="cardHolderName" class="form-label">Card Holder Name</label>
        <input type="text" id="cardHolderName" v-model="cardHolderName" class="form-control" placeholder="Max Mustermann" />
      </div>
      <div class="form-group tm-form-element">
        <label for="cardNumber" class="form-label">Card Number</label>
        <input type="text" id="cardNumber" v-model="cardNumber" class="form-control" placeholder="#### #### #### #### ####" />
      </div>
      <div class="form-group tm-form-element">
        <label for="cardValidThru" class="form-label">Valid thru</label>
        <input type="text" id="cardValidThru" v-model="cardValidThru" class="form-control" placeholder="12/24" />
      </div>
      <div class="form-group tm-form-element">
        <label for="cardCvc" class="form-label">CVC</label>
        <input type="text" id="cardCvc" v-model="cardCvc" class="form-control" placeholder="###" />
      </div>
      <div class="form-group tm-form-element">
        <label for="paymentType" class="form-label">Payment Type</label>
        <select id="paymentType" name="paymentType" v-model="paymentType">
          <option value="CREDITCARD">CREDITCARD</option>
          <option value="CASH">CASH</option>
          <option value="INVOICE">INVOICE</option>
        </select>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "PaymentDetails",
  data() {
    return {
      cardHolderName: "",
      cardNumber: "",
      cardValidThru: "",
      cardCvc: "",
      paymentType: "",
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
