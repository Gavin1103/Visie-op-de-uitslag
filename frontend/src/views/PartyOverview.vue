<script lang="ts">
import { ref } from 'vue'
import {Party} from '@/models/Party'
import PartyCard from '@/components/PartyCard.vue'

export default {
  components: { PartyCard },
  setup(){
    const parties: Party[] = ref<Party[]>([{id: "1",name: "vvd",image: "",candidates: []}])

    const fetchParties = () => {
      
    }

    return {
      parties
    }
  }
}



</script>

<template>
  <div class="">
    <div class="party-cards">
      <PartyCard v-for="party in parties" :key="party.id" :party="party" />
    </div>
  </div>
</template>

<style scoped>

</style>