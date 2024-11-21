<template>
  <div class="reports-container">
    <h1>Beheer Meldingen</h1>

    <div v-if="isLoading" class="loading">Laden...</div>
    <div v-else>
      <div v-if="reports.length === 0" class="no-reports">Geen meldingen gevonden.</div>

      <div v-else class="reports-list">
        <div v-for="report in reports" :key="report.id" class="report-card">
          <h2>Reden: {{ report.reason }}</h2>
          <p><strong>Gebruiker:</strong> {{ report.user.name }}</p>
          <p><strong>Bericht:</strong> {{ report.message.content }}</p>
          <p><strong>Status:</strong> {{ report.isHandled ? "Afgehandeld" : "Open" }}</p>

          <button
            v-if="!report.isHandled"
            @click="handleReport(report.id)"
            class="mark-handled">
            Markeer als afgehandeld
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { ref, onMounted } from "vue";
import { ReportService } from '@/services/ReportService.js'
import type {Report} from '@/models/report/Report'
export default {
  name: "ReportsView",
  setup() {
    const reports = ref<Report[] | undefined>([undefined]);
    const isLoading = ref(true);
    const reportService = new ReportService();
    const fetchReports = async () => {
      isLoading.value = true;
      try {
        reports.value = await reportService.getUnhandledReports();

      } catch (error) {
        console.error("Fout bij het ophalen van meldingen:", error);
      } finally {
        isLoading.value = false;
      }
    };

    const handleReport = async (reportId) => {
      try {
        await apiService.patch(`/api/reports/${reportId}`, { isHandled: true });
        // Werk de lokale lijst bij
        const report = reports.value.find((r) => r.id === reportId);
        if (report) report.isHandled = true;
      } catch (error) {
        console.error("Fout bij het verwerken van de melding:", error);
      }
    };

    onMounted(fetchReports);

    return {
      reports,
      isLoading,
      handleReport,
    };
  },
};
</script>

<style scoped>
.reports-container {
  max-width: 800px;
  margin: 0 auto;
  padding: 20px;
}

.report-card {
  border: 1px solid #ccc;
  padding: 15px;
  margin-bottom: 10px;
  border-radius: 5px;
  background: #f9f9f9;
}

.mark-handled {
  background-color: #28a745;
  color: #fff;
  padding: 10px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

.mark-handled:hover {
  background-color: #218838;
}

.loading {
  text-align: center;
  font-size: 1.2em;
}

.no-reports {
  text-align: center;
  font-size: 1.2em;
  color: #888;
}
</style>