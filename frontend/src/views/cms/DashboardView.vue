<script setup lang="ts">
import { onMounted, ref } from 'vue'
import Chart from 'primevue/chart'
import { UserService } from '@/services/UserService'
import { TopicService } from '@/services/TopicService'
import { GetUser } from '@/models/user/GetUser'
import { GetTopic } from '@/models/topic/GetTopic'

onMounted(async () => {
  userData.value = await loadUserData()
  topicData.value = await loadTopicData()
  alignChartData()
  chartData.value = setChartData()
  chartOptions.value = setChartOptions()
})

const chartData = ref()
const chartOptions = ref()
const userData = ref()
const topicData = ref()

const alignChartData = () => {
  const allLabels = Array.from(new Set([...userData.value.labels, ...topicData.value.labels]))
    .sort((a, b) => {
      const dateA = new Date(a);
      const dateB = new Date(b);
      return dateA.getTime() - dateB.getTime();
    });

  userData.value = fillMissingData(userData.value, allLabels);
  topicData.value = fillMissingData(topicData.value, allLabels);
};

const fillMissingData = (data: { labels: string[], counts: number[] }, allLabels: string[]) => {
  const labelToCount = Object.fromEntries(
    data.labels.map((label: string, index: number) => [label, data.counts[index]])
  );
  const counts = allLabels.map(label => labelToCount[label] || 0);
  return { labels: allLabels, counts };
}

const setChartData = () => {
  const documentStyle = getComputedStyle(document.documentElement)

  return {
    labels: [...userData.value.labels],
    datasets: [
      {
        label: 'Amount of users per month',
        backgroundColor: documentStyle.getPropertyValue('--p-cyan-500'),
        borderColor: documentStyle.getPropertyValue('--p-cyan-500'),
        data: [...userData.value.counts]
      },
      {
        label: 'Amount of topics per month',
        backgroundColor: documentStyle.getPropertyValue('--p-orange-500'),
        borderColor: documentStyle.getPropertyValue('--p-orange-500'),
        data: [...topicData.value.counts]
      }
    ]
  }
}

function aggregateByMonthYear(items: any[], getDate: any) {
  const monthYearCounts: Record<string, number> = {};

  items.forEach(item => {
    const date = getDate(item);
    const monthYear = `${date.toLocaleString('default', { month: 'long' })} ${date.getFullYear()}`;
    monthYearCounts[monthYear] = (monthYearCounts[monthYear] || 0) + 1;
  });

  const sortedEntries = Object.entries(monthYearCounts).sort(([a], [b]) => {
    const dateA = new Date(a as string);
    const dateB = new Date(b as string);
    return dateA.getTime() - dateB.getTime();
  });

  return {
    labels: sortedEntries.map(([label]) => label),
    counts: sortedEntries.map(([, count]) => count)
  };
}

const loadUserData = async () => {
  try {
    const userService = new UserService()
    const response = await userService.getUsers()
    const users = response.map(user => new GetUser(
      user.id,
      user.username,
      user.email,
      user.enabled,
      user.roles,
      new Date(user.createdAt)
    ))

    return aggregateByMonthYear(users, (user: GetUser) => user.createdAt)
  } catch (error) {
    console.error("Error fetching user data:", error)
  }
}

const loadTopicData = async () => {
  try {
    const topicService = new TopicService()
    const response = await topicService.getTopics()

    const topics = response.content.map(topic => new GetTopic(
      topic.id,
      topic.statement,
      topic.createdAt ? new Date(topic.createdAt) : new Date()
    ))

    return aggregateByMonthYear(topics, (topic: GetTopic) => topic.createdAt)
  } catch (error) {
    console.error("Error fetching topic data:", error)
  }
}

const setChartOptions = () => {
  const documentStyle = getComputedStyle(document.documentElement)
  const textColor = documentStyle.getPropertyValue('--p-text-color')
  const textColorSecondary = documentStyle.getPropertyValue('--p-text-muted-color')
  const surfaceBorder = documentStyle.getPropertyValue('--p-content-border-color')

  return {
    maintainAspectRatio: false,
    aspectRatio: 0.8,
    plugins: {
      legend: {
        labels: {
          color: textColor
        }
      }
    },
    scales: {
      x: {
        ticks: {
          color: textColorSecondary,
          font: {
            weight: 500
          }
        },
        grid: {
          display: false,
          drawBorder: false
        }
      },
      y: {
        ticks: {
          stepSize: 1,
          color: textColorSecondary
        },
        grid: {
          color: surfaceBorder,
          drawBorder: false
        }
      }
    }
  }
}

</script>

<template>
  <div class="dashboard-container">
    <h1 class="text-2xl font-bold mb-4">CMS Dashboard</h1>

    <div class="chart-container mb-8" v-if="userData && topicData">
      <Chart type="bar" :data="chartData" :options="chartOptions" class="h-[30rem]" />
    </div>

    <div v-else>Loading data...</div>
  </div>
</template>
