<script setup lang="ts">
import {ref, onMounted} from 'vue';
import {Chart as ChartJS, Title, Tooltip, Legend, BarElement, CategoryScale, LinearScale} from 'chart.js';
import {Bar} from 'vue-chartjs';
import {PartyWithVotes} from "@/models/Party";
import {PartyService} from "@/services/PartyService";

ChartJS.register(Title, Tooltip, Legend, BarElement, CategoryScale, LinearScale);

const partyService = new PartyService();
const partiesWithVotes = ref<PartyWithVotes[]>([]);
const chartData = ref(null);

const chartOptions = ref({
  responsive: true,
  maintainAspectRatio: false,
  plugins: {
    legend: {
      display: false,
    },
  },
  scales: {
    x: {
      title: {
        display: true,
        text: 'Partijen',
      },
    },
    y: {
      title: {
        display: true,
        text: 'Aantal Stemmen',
      },
      beginAtZero: true,
    },
  },
});

const fetchData = async () => {
  partiesWithVotes.value = await partyService.getPartiesWithVotes();

  chartData.value = {
    labels: partiesWithVotes.value.map(party => party.name),
    datasets: [
      {
        label: 'Aantal Stemmen',
        data: partiesWithVotes.value.map(party => party.amountOfVotes),
        backgroundColor: 'rgba(22, 31, 64, 0.7)',
        borderColor: 'rgba(22, 31, 64, 1)',
        hoverBackgroundColor: 'rgba(22, 31, 64, 1)',
        hoverBorderColor: 'rgba(22, 31, 64, 1)',
        borderWidth: 1,
      },
    ],
  };
};

onMounted(() => {
  fetchData();
});
</script>

<template>
  <section class="w-full flex justify-center">
    <div class="w-9/12 overflow-x-auto">
      <div :style="{ width: partiesWithVotes.length * 100 + 'px', height: '700px' }">
        <Bar v-if="chartData" :data="chartData" :options="chartOptions"/>
        <p v-else>Loading...</p>
      </div>
    </div>
  </section>
</template>

