<template>
  <div class="tm-section tm-section-pad tm-bg-gray" id="tm-section-4">
    <div class="form-row tm-search-form-row">
      <div class="form-group tm-form-element tm-form-element-50">
        <label for="cardHolderName" class="form-label">Card Holder Name</label>
        <input type="text" id="cardholdername" class="form-control" placeholder="Max Mustermann" required />
      </div>
      <div class="form-group tm-form-element tm-form-element-50">
        <label for="cardNumber" class="form-label">Card Number</label>
        <input type="text" id="cvc" class="form-control" placeholder="#### #### #### #### ####" required />
      </div>
      <div class="form-group tm-form-element tm-form-element-50">
        <label for="cardValidThru" class="form-label">Valid thru</label>
        <input type="text" id="validthru" class="form-control" placeholder="12/24" required />
      </div>
      <div class="form-group tm-form-element tm-form-element-50">
        <label for="cardCvc" class="form-label">CVC</label>
        <input type="text" id="cvc" class="form-control" placeholder="###" required />
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "PaymentDetails",
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
