<template>
  <div class="card-header" style="background: #ee4c52" id="tm-section-2">
    <h5>Categories</h5>
  </div>
  <div class="card">
    <div class="card-horizontal">
      <div class="img-square-wrapper">
        <img class="img" src="../assets/img/business-casual-ez.jpg" alt="Card image cap">
      </div>
      <div style="width: 18rem;float:left">
        <div class="card-body">
          <h5 class="card-title">Business Casual Singe Bed</h5>
          <p class="card-text">
            fully air-conditioned, very good soundproof windows, non-smoking,
            with bathtub/WC, hairdryer,
            desk, carpeted floor, room safe, SAT-TV + Sky, telephone, WLAN
          </p>
        </div>
      </div>
      <div style="width: 25rem;float:right">
        <div class="card-body">
          <h4>Selected Rooms</h4>
          <table class="center">
            <tr>
               <td><button type="button" class="btn btn-outline-danger" @click="decrementCounter">-</button></td>
               <td><input type="number" style="width:50px; height: 30px" :value="roomCount"></td>
               <td><button type="button" class="btn btn-outline-success" @click="incrementCounter">+</button></td>
            </tr>
          </table>
        </div>
      </div>
    </div>
    <div class="card-footer">
      <small class="text-muted">Last updated 3 mins ago</small>
    </div>
  </div>

</template>

<script>
export default {
  name: "RoomAssignment",
  data() {
    return {
      roomCount: 0
    }
  },
  methods: {
    incrementCounter: function() {
      if(this.roomCount < 15) {
        this.roomCount += 1
        this.$emit('selected-categories', this.roomCount)
      }
    },
    decrementCounter: function() {
      if(this.roomCount > 0){
        this.roomCount -= 1
        this.$emit('selected-categories', this.roomCount)
      }
    }
  }
};
</script>

<style>
.img {
  float: left;
  width: 17rem;
}

.center {
  margin-left: auto;
  margin-right: auto;
}

/* Hide Arrows in number fields*/
/* Chrome, Safari, Edge, Opera */
input::-webkit-outer-spin-button,
input::-webkit-inner-spin-button {
  -webkit-appearance: none;
  margin: 0;
}
/* Firefox */
input[type=number] {
  -moz-appearance: textfield;
}

</style>
