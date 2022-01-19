<template>
  <div class="card">
    <div class="card-horizontal">
      <div class="img-square-wrapper">
        <img class="img" :src="category.imageUrl" alt="Card image cap"/>
      </div>
      <div style="width: 18rem;float:left">
        <div class="card-body">
          <h5 class="card-title">{{ this.category.name }}</h5>
          <p class="card-text">
            {{this.category.description}}
          </p>
          <div class="d-inline p-2 bg-primary text-white">{{this.category.price.amount}} {{this.category.price.currency.code}} Per Night</div>
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
  props: {
    category: {
      type: Object,
      required: true
    }
  },
  methods: {
    incrementCounter: function() {
      if(this.roomCount < 15) {
        this.roomCount += 1
        this.$emit('selected-categories', this.category.name, this.roomCount)
      }
    },
    decrementCounter: function() {
      if(this.roomCount > 0){
        this.roomCount -= 1
        this.$emit('selected-categories', this.category.name, this.roomCount)
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
