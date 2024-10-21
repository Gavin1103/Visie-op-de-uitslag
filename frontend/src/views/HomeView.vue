<script setup lang="ts">

import { PartyService } from "@/services/PartyService";
import type { PartyWithVotes } from "@/models/Party";
import {onMounted, ref} from "vue";
import type {TotalAmountOfVotes} from "@/models/votes";
import {VotesService} from "@/services/VotesService";
import BarChartComponent from "@/components/chart/BarChartComponent.vue";

const partyService = new PartyService();
const votesService = new VotesService();

const electedParty = ref<PartyWithVotes | null>(null);
const totalAmountOfVotes = ref<TotalAmountOfVotes | null>(null);

const fetchData = async () => {
  try {
    electedParty.value = await partyService.getElectedParty();
    totalAmountOfVotes.value = await votesService.getTotalAmountOfVotes();

  } catch (error) {
    console.error("Error fetching data:", error);
  }
};

onMounted(() => {
  fetchData();
});

const formatNumber = (number: number): string => {
  return new Intl.NumberFormat('nl-NL').format(number);
};

</script>

<template>
  <main>
    <section class="w-full h-auto flex flex-col items-center">
      <section class="mb-2.5 flex flex-col items-center">
        <p class="text-xl font-black">Winnaar</p>
        <p class="text-6xl font-black">{{ electedParty?.name }}</p>
        <img style="height:200px" src="../../../public/pvv-logo.png" alt="elected party logo"/>
      </section>
      <section class="mb-3.5 flex flex-col items-center">
        <p class="text-xl font-black">Totaal aantal stemmen</p>
        <p class="text-5xl font-black">{{ totalAmountOfVotes?.totalAmountOfVotes ? formatNumber(totalAmountOfVotes.totalAmountOfVotes) : '' }}</p>
      </section>
      <section class="mb-3.5 flex flex-col items-center">
        <p class="text-xl font-black">Kiesdeler</p>
        <p class="text-5xl font-black">{{ totalAmountOfVotes?.electoralQuota ? formatNumber(totalAmountOfVotes.electoralQuota) : '' }}</p>
      </section>
    </section>
    <BarChartComponent></BarChartComponent>
  </main>
</template>
