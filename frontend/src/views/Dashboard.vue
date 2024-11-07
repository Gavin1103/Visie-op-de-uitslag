<template>
  <div class="dashboard-container">
    <h1 class="text-2xl font-bold mb-4">CMS Dashboard</h1>

    <div class="chart-container mb-8" v-if="userData">
      <Chart type="bar" :data="chartData" :options="chartOptions" class="h-[30rem]" />
    </div>

    <div v-else>Loading data...</div>
  </div>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import Chart from 'primevue/chart'
import { UserService } from '@/services/UserService'
import { GetUser } from '@/models/user/GetUser'

onMounted(async () => {
  userData.value = await loadUserData()
  chartData.value = setChartData()
  chartOptions.value = setChartOptions()
})

const chartData = ref()
const chartOptions = ref()
const userData = ref()

const setChartData = () => {
  const documentStyle = getComputedStyle(document.documentElement)

  console.log(userData.value);

  return {
    labels: [...userData.value.labels],
    datasets: [
      {
        label: 'Amount of users per month',
        backgroundColor: documentStyle.getPropertyValue('--p-cyan-500'),
        borderColor: documentStyle.getPropertyValue('--p-cyan-500'),
        data: [...userData.value.counts]
      }
    ]
  };
}


function getUsersPerMonth(users) {
  const monthCounts = {}

  users.forEach(user => {
    const month = user.createdAt.toLocaleString('default', { month: 'long' })
    monthCounts[month] = (monthCounts[month] || 0) + 1
  })

  return {
    labels: Object.keys(monthCounts),
    counts: Object.values(monthCounts)
  }
}

const loadUserData = async () => {
  try {
    const userService = new UserService()
    const response = await userService.getUsers()
    console.log(response);
    const users = response.map(user => new GetUser(
      user.id,
      user.username,
      user.email,
      user.enabled,
      user.roles,
      new Date(user.createdAt)
    ))

    return getUsersPerMonth(users);
  } catch (error) {
    console.error("Error fetching user data:", error)
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


