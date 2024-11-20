<script setup lang="ts">
import {ref, onMounted, watch} from 'vue';
import {Chart as ChartJS, Title, Tooltip, Legend, BarElement, CategoryScale, LinearScale} from 'chart.js';
import {Bar} from 'vue-chartjs';
import type { ChartEvent, ActiveElement } from 'chart.js';
import type {PartyWithVotes} from "@/models/Party";
import {PartyService} from "@/services/PartyService";

ChartJS.register(Title, Tooltip, Legend, BarElement, CategoryScale, LinearScale);

const props = defineProps({
  chartType: {
    type: String,
    required: true,
  },
});

const partyService = new PartyService();
const partiesWithVotes = ref<PartyWithVotes[]>([]);
const chartData = ref<any>(null);

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
        text: 'Aantal Stemmen / Zetels',
      },
      beginAtZero: true,
      suggestedMax: 0,
    },
  },
  animation: {
    duration: 3000,
    loop: false,
  },
  onHover(event: ChartEvent, elements: ActiveElement[]) {
    const chartCanvas = (event.native?.target as HTMLCanvasElement)?.style;
    if (elements.length) {
      chartCanvas.cursor = 'pointer';
    } else {
      chartCanvas.cursor = 'default';
    }
  },
  onClick(event: ChartEvent, elements: ActiveElement[]) {
    if (elements.length > 0) {
      const chartElement = elements[0];
      const index = chartElement.index;
      const party = partiesWithVotes.value[index].partyId;

      window.location.href = `/party/${party}`;
    }
  },
});

const updateChartData = () => {
  let label = '';
  let showData: any[] = [];
  let suggestedMax = 0;

  // Show data for seats
  if (props.chartType === 'seats') {
    label = 'Aantal zetels';
    showData = partiesWithVotes.value.map(party => party.amountOfSeats);
    suggestedMax = Math.max(...showData) * 1.6;
  }

  // Show data for votes
  if (props.chartType === 'votes') {
    label = 'Aantal stemmen';
    showData = partiesWithVotes.value.map(party => party.amountOfVotes);
    suggestedMax = Math.max(...showData) * 1.2;
  }

  chartOptions.value = {
    ...chartOptions.value,
    scales: {
      ...chartOptions.value.scales,
      y: {
        ...chartOptions.value.scales.y,
        suggestedMax: suggestedMax,
      },
    },
  }

  chartData.value = {
    labels: partiesWithVotes.value.map(party => party.name),
    datasets: [
      {
        label: label,
        data: showData,
        backgroundColor: 'rgba(22, 31, 64, 0.7)',
        borderColor: 'rgba(22, 31, 64, 1)',
        hoverBackgroundColor: '#5564c8',
        hoverBorderColor: '#5564c8',
        borderWidth: 1,
      },
    ],
  };
};

const fetchData = async () => {
  partiesWithVotes.value = await partyService.getPartiesWithVotes();
  updateChartData();
};

watch(() => props.chartType, () => {
  updateChartData();
});

onMounted(() => {
  fetchData();
});
</script>

<template>
  <section class="w-full flex justify-center">
    <div class="w-full overflow-x-auto">
      <div :style="{ width: partiesWithVotes.length * 100 + 'px', height: '700px' }">
        <Bar v-if="chartData" :data="chartData" :options="chartOptions"/>
        <p v-else></p>
      </div>
    </div>
  </section>
</template>
