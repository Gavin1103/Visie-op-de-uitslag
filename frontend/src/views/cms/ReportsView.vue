<template>
  <div class="reports-container">
    <h1 class="text-xl font-bold mb-4">Reports</h1>
    <div v-if="isLoading" class="loading">Laden...</div>
    <div v-else>
      <DataTable :value="reports" class="p-datatable-gridlines">
        <Column field="id" header="ID" />
        <Column field="reporterEmail" header="Reporter"></Column>
        <Column field="reportedEmail" header="gerapporteerde"></Column>
        <Column field="message" header="Bericht"></Column>
        <Column field="reason" header="Reden"></Column>
        <Column header="Datum">
          <template #body="slotProps">
            {{ formatDate(slotProps.data.createdAt) }}
          </template>
        </Column>
        <Column header="Actions">
          <template #body="slotProps">
            <Button label="Afhandlen" icon="pi pi-save" class="p-button-info mr-2"
                    @click="() => { showModal = true; selectedReport = slotProps.data; }" />
            <div v-if="showModal" class="modal-overlay">
              <div class="modal flex flex-col gap-3 text-lg">
                <h3>Selecteer opties:</h3>
                <div class="flex items-center gap-2">
                  <Checkbox binary v-model="deleteMessage" name="delete" />
                  <label for="delete"> Verwijder bericht </label>
                </div>
                <div class="flex items-center gap-2">
                  <Checkbox binary v-model="disableUser" name="disable" />
                  <label for="disable"> Gebruiker deactiveren </label>
                </div>
                <Button severity="danger" @click="cancelReport">Terug</Button>
                <Button @click="handleReport()">Handel af</Button>
              </div>
            </div>
          </template>
        </Column>
      </DataTable>
      <div v-if="reports.length === 0" class="no-reports">Geen meldingen gevonden.</div>
    </div>
  </div>
</template>

<script lang="ts">
import { ref, onMounted } from "vue";
import { ReportService } from '@/services/ReportService.js'
import type {Report} from '@/models/report/Report'
import { formatDate } from '@/helper/formatDateHelper'
import DataTable from 'primevue/datatable'
import Column from 'primevue/column'
import Button from 'primevue/button'
import { useToast } from 'primevue/usetoast'
import Checkbox from 'primevue/checkbox'
export default {
  name: "ReportsView",
  methods: { formatDate },
  components: {
    Column,
    DataTable,
    Button,
    Checkbox},
  setup() {
    const reports = ref<Report[]>([]);
    const isLoading = ref(true);
    const reportService = new ReportService();
    const showModal = ref(false)
    const selectedReport = ref<Report | null>(null);
    const deleteMessage = ref(false)
    const disableUser = ref(false)
    const toast = useToast()


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

    const cancelReport = () => {
      showModal.value = false;
      disableUser.value = false;
      selectedReport.value = null;
      deleteMessage.value = false;
    }

    const handleReport = async () => {
      if(!selectedReport.value){
        toast.add({
          severity: 'error',
          summary: 'Server error',
          detail: 'Geen bericht geselecteerd.',
          life: 3000
        })
      }
      try {
        await reportService.handleReport(selectedReport.value, disableUser.value, deleteMessage.value);
        toast.add({
          severity: 'success',
          summary: 'Afgehandeld',
          detail: 'De report is afgehandeld.',
          life: 3000
        })
        if (selectedReport.value) {
          reports.value = reports.value.filter((m) => m.id !== selectedReport.value?.id);
        }
      }
      catch (error){
        console.error(error);
        toast.add({
          severity: 'error',
          summary: 'Server error',
          detail: 'Er is iets mis gegaan.',
          life: 3000
        })
      }
      cancelReport();

    };

    onMounted(fetchReports);

    return {
      reports,
      isLoading,
      handleReport,
      cancelReport,
      selectedReport,
      showModal,
      deleteMessage,
      disableUser
    };
  },
};
</script>

<style scoped>

.modal {
  background: white;
  padding: 40px;
  border-radius: 10px;
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.3);
  text-align: center;
  width: 300px;
  position: absolute;
}

.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 9999;
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