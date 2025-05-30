<script setup lang="ts">
import {ref, onMounted, watch} from 'vue';
import {Chart as ChartJS, Title, Tooltip, Legend, BarElement, CategoryScale, LinearScale} from 'chart.js';
import {Bar} from 'vue-chartjs';
import type { ChartEvent, ActiveElement } from 'chart.js';
import type { CandidateWithVotes } from '@/models/Candidate'

ChartJS.register(Title, Tooltip, Legend, BarElement, CategoryScale, LinearScale);

const props = defineProps({
  candidates: {
    type: Array as () => CandidateWithVotes[],
    required: true,
  },
  label: {
    type: String,
    required: true,
  },
});

const newLabel = ref(props.label)
const candidatesWithVotes = ref<CandidateWithVotes[]>(props.candidates);
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
        text: 'Kandidaten',
      },
    },
    y: {
      title: {
        display: true,
        text: 'Aantal Stemmen',
      },
      beginAtZero: true,
      suggestedMax: 0,
    },
  },
  animation: {
    duration: 3000,
    loop: false,
  },
  onHover(event: ChartEvent, elements: ActiveElement[], chart: any) {
    const target = event.native?.target as HTMLCanvasElement | null;
    if (target) {
      target.style.cursor = elements.length ? 'pointer' : 'default';
    }
  },

});

const updateChartData = () => {
  let label = newLabel.value;
  let showData = candidatesWithVotes.value.map(candidate => candidate.votes);
  let suggestedMax = Math.max(...showData) * 1.6;

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
    labels: candidatesWithVotes.value.map(candidate => candidate.fullName),
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
  updateChartData();
};

watch(() => props.candidates, (newCandidates) => {
  candidatesWithVotes.value = [...newCandidates];
  newLabel.value = props.label
  updateChartData()
});

onMounted(() => {
  fetchData();
});
</script>

<template>
  <section class="w-full flex justify-center">
    <div class="w-full overflow-x-auto">
      <div :style="{ width: candidatesWithVotes.length * 100 + 'px', height: '700px' }">
        <Bar v-if="chartData" :data="chartData" :options="chartOptions"/>
        <p v-else></p>
      </div>
    </div>
  </section>
</template>
