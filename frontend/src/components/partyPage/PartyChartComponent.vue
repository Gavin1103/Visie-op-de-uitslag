<script setup lang="ts">

import PartyBarChartComponent from '@/components/chart/partyPage/PartyBarChartComponent.vue'
import type { CandidateWithVotes } from '@/models/Candidate'
import { ref } from 'vue'
import { CandidateService } from '@/services/CandidateService'
import AreaSearchbar from '@/components/partyPage/AreaSearchbar.vue'
import { emptyCandidateList } from '@/stores/EmptyCandidateList'
import { AreaEnum } from '@/models/enum/AreaEnum'
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
const candidatesWithVotes = ref<CandidateWithVotes[]>(props.candidates);
const searchAvailable = ref(false)
const area = ref<AreaEnum>(AreaEnum.NATIONAL)
const chartLabel = ref("landelijk")

function updateArea(changedArea: AreaEnum, changedPlaceholder: string) {
  area.value = changedArea
  if(changedArea === AreaEnum.CONSTITUENCY || changedArea === AreaEnum.MUNICIPALITY){
    searchAvailable.value = true
    chartLabel.value = "Amsterdam"
    updateCandidates("Amsterdam")
    placeholder.value = changedPlaceholder
  }
  else{
    searchAvailable.value = false
    candidatesWithVotes.value = props.candidates
    placeholder.value = changedPlaceholder
  }
}

const updateCandidates = async (Area: string) => {
  switch(area.value) {
    case AreaEnum.CONSTITUENCY:
      try{
        candidatesWithVotes.value = await candidateService.getCandidatesByArea(AreaEnum.CONSTITUENCY, props.partyId, Area)
        chartLabel.value = Area
      }
      catch (error) {
        chartLabel.value = "geen stemmen in dit gebied gevonden"
        candidatesWithVotes.value = emptyCandidateList
        console.error("no votes in aera", error)
      }
      break
    case AreaEnum.MUNICIPALITY:
      try{
        candidatesWithVotes.value = await candidateService.getCandidatesByArea(AreaEnum.MUNICIPALITY, props.partyId, Area)
        chartLabel.value = Area
      }
      catch (error) {
        chartLabel.value = "geen stemmen in dit gebied gevonden"
        candidatesWithVotes.value = emptyCandidateList
        console.error("no votes in aera", error)
      }
      break
  }
}
</script>

<template>

  <section class="w-full flex justify-center space-x-4 py-4">
    <button
      :class="['w-32 h-12 font-bold rounded-lg shadow-lg transition-all duration-300 ease-in-out', area === AreaEnum.NATIONAL ? 'bg-[#5564c8]' : 'bg-NavBlue text-white hover:bg-opacity-80']"
      @click="updateArea(AreaEnum.NATIONAL, 'nationaal')">
      Landelijk
    </button>
    <button
      :class="['w-32 h-12 font-bold rounded-lg shadow-lg transition-all duration-300 ease-in-out', area === AreaEnum.CONSTITUENCY ? 'bg-[#5564c8]' : 'bg-NavBlue text-white hover:bg-opacity-80']"
      @click="updateArea(AreaEnum.CONSTITUENCY, 'kieskring')">
      Per kieskring
    </button>
    <button
      :class="['w-32 h-12 font-bold rounded-lg shadow-lg transition-all duration-300 ease-in-out', area === AreaEnum.MUNICIPALITY ? 'bg-[#5564c8]' : 'bg-NavBlue text-white hover:bg-opacity-80']"
      @click="updateArea(AreaEnum.MUNICIPALITY, 'gemeente')">
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