<script setup lang="ts">

import PartyBarChartComponent from '@/components/chart/partyPage/PartyBarChartComponent.vue'
import type { CandidateWithVotes } from '@/models/Candidate'
import { ref } from 'vue'
import { CandidateService } from '@/services/CandidateService'
import AreaSearchbar from '@/components/partyPage/AreaSearchbar.vue'
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
const chartLabel = ref("landelijk")

function updateArea(changedArea: String, changedPlaceholder: String) {
  area.value = changedArea
  if(changedArea === "constituency" || changedArea === "municipality"){
    searchAvailable.value = true
    chartLabel.value = "Landelijk"
    placeholder.value = changedPlaceholder
  }
  else{
    searchAvailable.value = false
    candidatesWithVotes.value = props.candidates
  }
}

const updateCandidates = async (Area) => {
  switch(area.value) {
    case "constituency":
      try{
        candidatesWithVotes.value = await candidateService.getCandidatesByArea("constituency", props.partyId, Area)
        chartLabel.value = Area
      }
      catch (error) {
        chartLabel.value = "geen stemmen in dit gebied gevonden"
        console.error("no votes in aera", error)
      }
      break
    case "municipality":
      try{
        candidatesWithVotes.value = await candidateService.getCandidatesByArea("municipality", props.partyId, Area)
        chartLabel.value = Area
      }
      catch (error) {
        chartLabel.value = "geen stemmen in dit gebied gevonden"
        console.error("no votes in aera", error)
      }
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

  <AreaSearchbar v-if="searchAvailable"
    @select-option="updateCandidates"
    :area="area"
                 :chart-label="chartLabel"
  />

  <PartyBarChartComponent :label="placeholder" :candidates="candidatesWithVotes"></PartyBarChartComponent>

</template>

<style scoped>

</style>