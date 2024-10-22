<script setup lang="ts">

import PartyBarChartComponent from '@/components/chart/partyPage/PartyBarChartComponent.vue'
import type { CandidateWithVotes } from '@/models/Candidate'
import { ref } from 'vue'
const props = defineProps({
  candidates: {
    type: Array as () => CandidateWithVotes[],
    required: true,
  }
})

const searchInput = ref<String | undefined>(undefined)
const candidatesWithVotes = ref<CandidateWithVotes[]>(props.candidates);
const searchAvailable = ref(false)
const area = ref("national")

function updateArea(changedArea: String) {
  area.value = changedArea
  if(changedArea === "constituency" || changedArea === "municipality"){
    searchAvailable.value = true
  }
  else{
    searchAvailable.value = false
  }
}
</script>

<template>

  <section class="w-full flex justify-center space-x-4 py-4">
    <button
      :class="['w-32 h-12 font-bold rounded-lg shadow-lg transition-all duration-300 ease-in-out', area === 'national' ? 'bg-purple-800 text-white' : 'bg-NavBlue text-white hover:bg-opacity-80']"
      @click="updateArea('national')">
      Landelijk
    </button>
    <button
      :class="['w-32 h-12 font-bold rounded-lg shadow-lg transition-all duration-300 ease-in-out', area === 'constituency' ? 'bg-purple-800 text-white' : 'bg-NavBlue text-white hover:bg-opacity-80']"
      @click="updateArea('constituency')">
      Per kieskring
    </button>
    <button
      :class="['w-32 h-12 font-bold rounded-lg shadow-lg transition-all duration-300 ease-in-out', area === 'municipality' ? 'bg-purple-800 text-white' : 'bg-NavBlue text-white hover:bg-opacity-80']"
      @click="updateArea('municipality')">
      Per gemeente
    </button>
  </section>
  <input v-if="searchAvailable"
    type="text"
    placeholder="Zoeken..."
    v-model="searchInput"
    class=" h-12 m-3 p-4 font-bold text-white bg-NavBlue rounded-lg shadow-lg transition-all duration-300 ease-in-out placeholder-white focus:outline-none focus:ring-2 focus:ring-purple-800 focus:bg-opacity-90"
  >

  <PartyBarChartComponent :area="area" :candidates="candidatesWithVotes"></PartyBarChartComponent>

</template>

<style scoped>

</style>