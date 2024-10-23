<script setup lang="ts">

import PartyBarChartComponent from '@/components/chart/partyPage/PartyBarChartComponent.vue'
import type { CandidateWithVotes } from '@/models/Candidate'
import { ref } from 'vue'
import { CandidateService } from '@/services/CandidateService'
const props = defineProps({
  candidates: {
    type: Array as () => CandidateWithVotes[],
    required: true,
  },
  partyId: {
    type: Number,
    required: false,
  }
})

const candidateService = new CandidateService();
const placeholder = ref("nationaal")
const searchInput = ref<String | undefined>(undefined)
const candidatesWithVotes = ref<CandidateWithVotes[]>(props.candidates);
const searchAvailable = ref(false)
const area = ref("national")

function updateArea(changedArea: String, changedPlaceholder: String) {
  area.value = changedArea
  if(changedArea === "constituency" || changedArea === "municipality"){
    searchAvailable.value = true
    placeholder.value = changedPlaceholder
  }
  else{
    searchAvailable.value = false
    candidatesWithVotes.value = props.candidates
  }
}

const updateCandidates = async () => {
  switch(area.value) {
    case "constituency":
      candidatesWithVotes.value = await candidateService.getCandidatesByArea("constituency", props.partyId, searchInput.value)
      break
    case "municipality":
      candidatesWithVotes.value = await candidateService.getCandidatesByArea("municipality", props.partyId, searchInput.value)
      break
  }
}
</script>

<template>

  <section class="w-full flex justify-center space-x-4 py-4">
    <button
      :class="['w-32 h-12 font-bold rounded-lg shadow-lg transition-all duration-300 ease-in-out', area === 'national' ? 'bg-purple-800 text-white' : 'bg-NavBlue text-white hover:bg-opacity-80']"
      @click="updateArea('national', 'nationaal')">
      Landelijk
    </button>
    <button
      :class="['w-32 h-12 font-bold rounded-lg shadow-lg transition-all duration-300 ease-in-out', area === 'constituency' ? 'bg-purple-800 text-white' : 'bg-NavBlue text-white hover:bg-opacity-80']"
      @click="updateArea('constituency', 'kieskring')">
      Per kieskring
    </button>
    <button
      :class="['w-32 h-12 font-bold rounded-lg shadow-lg transition-all duration-300 ease-in-out', area === 'municipality' ? 'bg-purple-800 text-white' : 'bg-NavBlue text-white hover:bg-opacity-80']"
      @click="updateArea('municipality', 'gemeente')">
      Per gemeente
    </button>
  </section>
  <section class="flex items-center" v-if="searchAvailable">
  <input
    type="text"
    :placeholder="'Zoek ' + placeholder"
    v-model="searchInput"
    class=" h-12 m-3 p-4 font-bold text-white bg-NavBlue border border-amber-50 rounded-lg shadow-lg transition-all duration-300 bg-opacity-90 ease-in-out placeholder-white focus:outline-none focus:ring-2 focus:ring-purple-800 focus:bg-opacity-90"
  >
    <button @click="updateCandidates()" class=" h-11 m-3 px-5 font-bold text-white bg-NavBlue rounded-lg shadow-lg transition-all hover:bg-opacity-80">zoeken</button>
  </section>

  <PartyBarChartComponent :label="placeholder" :candidates="candidatesWithVotes"></PartyBarChartComponent>

</template>

<style scoped>

</style>