<script setup lang="ts">

import {PartyService} from "@/services/PartyService";
import type {PartyWithVotes} from "@/models/Party";
import {onMounted, ref} from "vue";
import type {TotalAmountOfVotes} from "@/models/votes";
import {VotesService} from "@/services/VotesService";
import BarChartComponent from "@/components/chart/homepage/BarChartComponent.vue";
import {formatNumber} from "@/helper/formatNumberHelpers";

const barChartDisplayInfo = ref<string>('votes');
const partyService = new PartyService();
const votesService = new VotesService();
const electedParty = ref<PartyWithVotes | null>(null);
const totalAmountOfVotes = ref<TotalAmountOfVotes | null>(null);
const partiesWithVotes = ref<PartyWithVotes[]>([]);

const fetchData = async () => {
  try {
    electedParty.value = await partyService.getElectedParty();
    totalAmountOfVotes.value = await votesService.getTotalAmountOfVotes();
    partiesWithVotes.value = await partyService.getPartiesWithVotes();
  } catch (error) {
    console.error("Error fetching data:", error);
  }
};

onMounted(() => {
  fetchData();
});

function updateBarChartDisplayInfo(value: string) {
  barChartDisplayInfo.value = value;
}
</script>

<template>
  <main class="flex justify-center">
    <div class="w-3/4 bg-red-600">
      <section class="w-full h-auto flex flex-col items-center">
        <section class="mb-2.5 flex flex-col items-center">
          <p class="mt-4 text-xl font-black">Grootste partij</p>
          <p class="text-6xl font-black">{{ electedParty?.name }}</p>
          <img style="height:200px" src="../../public/pvv-logo.png" alt="elected party logo"/>
        </section>
        <section class="mb-3.5 flex flex-col items-center">
          <p class="text-xl font-black">Totaal aantal stemmen in Nederland</p>
          <p class="text-5xl font-black">
            {{ totalAmountOfVotes?.totalAmountOfVotes ? formatNumber(totalAmountOfVotes.totalAmountOfVotes) : '' }}</p>
        </section>
        <section class="mb-3.5 flex flex-col items-center">
          <p class="text-xl font-black">Kiesdeler</p>
          <p class="text-5xl font-black">
            {{ totalAmountOfVotes?.electoralQuota ? formatNumber(totalAmountOfVotes.electoralQuota) : '' }}</p>
        </section>
      </section>
      <section class="w-full flex justify-center space-x-4 py-4">
        <button
            :class="['w-32 h-12 font-bold rounded-lg shadow-lg transition-all duration-300 ease-in-out', barChartDisplayInfo === 'votes' ? 'bg-[#5564c8] text-white' : 'bg-NavBlue text-white hover:bg-opacity-80']"
            @click="updateBarChartDisplayInfo('votes')">
          Stemmen
        </button>
        <button
            :class="['w-32 h-12 font-bold rounded-lg shadow-lg transition-all duration-300 ease-in-out', barChartDisplayInfo === 'seats' ? 'bg-[#5564c8] text-white' : 'bg-NavBlue text-white hover:bg-opacity-80']"
            @click="updateBarChartDisplayInfo('seats')">
          Zetels
        </button>
      </section>
      <BarChartComponent :chartType="barChartDisplayInfo"/>
    </div>
  </main>
</template>
