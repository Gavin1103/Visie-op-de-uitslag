<script  lang="ts">

import { useRoute } from 'vue-router'
import { onMounted, ref } from 'vue'
import type { PartyWithCandidates } from '@/models/Party'
import { ElectionService } from '@/services/ElectionService'
import CandidatesListItem from '@/components/partyPage/CandidatesListItem.vue'
import PartyChartComponent from '@/components/partyPage/PartyChartComponent.vue'
import {formatNumber} from "@/helper/formatNumberHelpers";

export default {
  methods: {formatNumber},
  components: { PartyChartComponent, CandidatesListItem },
  setup() {
    const route = useRoute();
    const id: number = parseInt(route.params.id as string, 10);
    const party = ref<PartyWithCandidates | undefined>(undefined);
    const seats = ref<number>(0)

    const electionService = new ElectionService();

    const fetchParty = async () => {
      try{
        party.value = await electionService.getPartyById(id);
        seats.value = party.value.amountOfSeats
      }
      catch (error){
        console.error("no party found:", error);
      }
    }

    onMounted(() => {
      fetchParty();
    })

    return {
      party,
      id,
      seats
    }
  }
}
</script>

<template>
  <section class="flex justify-center">
    <div class="w-3/4">
  <div class="p-6 flex flex-col items-center bg-white shadow-md rounded-md" v-if="party">
    <p class="votes text-xl text-gray-700">Partij</p>
    <h1 class="party-name text-5xl font-bold mb-4">{{ party?.name }}</h1>
    <section class="w-80 h-72 flex items-center justify-center">
      <img :src="`../../public/partyLogos/${party.logo}.png`" alt="Party Logo" class="party-logo" />
    </section>
    <p class="votes text-xl text-gray-700">Aantal stemmen:</p>

      <span class="text-5xl font-semibold">{{ formatNumber(party?.amountOfVotes) }}</span>

    <p class="mt-5 votes text-xl text-gray-700">zetels:</p>
    <span class="text-5xl font-semibold">{{ party?.amountOfSeats }}</span>

    <PartyChartComponent :candidates="party?.candidates" :partyId="party?.partyId"></PartyChartComponent>

    <h2 class="candidates-title text-2xl mt-6 mb-2 font-semibold">Kandidaten:</h2>
    <div class="flex items-center my-3">
      <div class="w-4 h-4 bg-amber-300 rounded-full mr-2"></div>
      <span class="text-sm text-black">krijgen een zetel</span>
    </div>
    <ul class="w-full space-y-2">
      <li v-for="(candidate, index) in party.candidates" :key="index" :class="['candidate-item p-4  rounded-md shadow-sm',
        candidate.candidateId.candidateId <= seats ? 'bg-amber-300' : 'bg-gray-100']">
        <CandidatesListItem :candidate="candidate"></CandidatesListItem>
      </li>
    </ul>
  </div>

  <div v-else class="loading-state text-center">
    <p>Loading party details...</p>
  </div>
    </div>
  </section>
</template>

<style scoped>

</style>