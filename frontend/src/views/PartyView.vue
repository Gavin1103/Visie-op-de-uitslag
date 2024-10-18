<script  lang="ts">

import { useRoute } from 'vue-router'
import { onMounted, ref } from 'vue'
import type { fullParty } from '@/models/Party'
import { ElectionService } from '@/services/ElectionService'

export default {
  setup() {
    const route = useRoute();
    const id = route.params.id;
    const party = ref<fullParty | undefined>(undefined);

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
  <div class="party-details p-6 bg-white shadow-md rounded-md" v-if="party">
    <h1 class="party-name text-3xl font-bold mb-4">{{ party?.name }}</h1>
    <p class="votes text-lg text-gray-700">Total Votes: <span class="font-semibold">{{ party?.totalVotes }}</span></p>

    <h2 class="candidates-title text-2xl mt-6 mb-2 font-semibold">Candidates</h2>

    <ul class="candidate-list space-y-2">
      <li v-for="candidate in party.candidates" :key="candidate.candidateId" class="candidate-item p-4 bg-gray-100 rounded-md shadow-sm">
        <div class="flex justify-between items-center">
          <span class="candidate-name font-medium text-lg">{{ candidate.fullName }}</span>
          <span class="candidate-votes text-gray-600">{{ candidate.votes }} votes</span>
        </div>
      </li>
    </ul>
  </div>

  <div v-else class="loading-state text-center">
    <p>Loading party details...</p>
  </div>
</template>

<style scoped>

</style>