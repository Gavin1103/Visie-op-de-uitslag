<script  lang="ts">

import { useRoute } from 'vue-router'
import { onMounted, ref } from 'vue'
import type { PartyWithCandidates } from '@/models/Party'
import { ElectionService } from '@/services/ElectionService'
import CandidatesListItem from '@/components/partyPage/CandidatesListItem.vue'
import PartyBarChartComponent from '@/components/chart/partyPage/PartyBarChartComponent.vue'
import PartyChartComponent from '@/components/partyPage/PartyChartComponent.vue'

export default {
  components: { PartyChartComponent, PartyBarChartComponent, CandidatesListItem },
  setup() {
    const route = useRoute();
    const id = route.params.id;
    const party = ref<PartyWithCandidates | undefined>(undefined);

    const electionService = new ElectionService();

    const fetchParty = async () => {
      try{
        party.value = await electionService.getPartyById(id);
      }
      catch (error){
        console.error("no party found:", error);
      }
    }

    onMounted(() => {
      fetchParty();
    })


    return {
      party
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
    <section class="w-80 h-72">
      <img :src="`../../public/partyLogos/${party.logo}.png`" alt="Party Logo" class="party-logo" />
    </section>
    <p class="votes text-xl text-gray-700">Aantal stemmen:</p>
    <span class="text-5xl font-semibold">{{ party?.amountOfVotes }}</span>
    <p class="mt-5 votes text-xl text-gray-700">zetels:</p>
    <span class="text-5xl font-semibold">{{ party?.amountOfSeats }}</span>

    <PartyChartComponent :candidates="party?.candidates" ></PartyChartComponent>

    <h2 class="candidates-title text-2xl mt-6 mb-2 font-semibold">Kandidaten:</h2>

    <ul class="w-full space-y-2">
      <li v-for="candidate in party.candidates" :key="candidate.candidateId" class="candidate-item p-4 bg-gray-100 rounded-md shadow-sm">
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