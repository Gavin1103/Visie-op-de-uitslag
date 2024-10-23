<script setup lang="ts">
import { ref, watch } from 'vue';
import { ElectionService } from '@/services/ElectionService'


  const props = defineProps({
    area: {
      type: String,
      required: true,
    },
    chartLabel: {
      type: String,
      required: true,
    },

  })
  const emits = defineEmits( ['select-option'],)

  const searchQuery = ref('');
  const selectedOption = ref("");
  const optionsMunicipality = ref([]);
  const optionsConstituency = ref([]);
  const filteredOptions = ref([]);
  const electionService = new ElectionService();

  const fetchOptions = async () => {
      try {
        const areas = await electionService.getAllAreas()
        optionsMunicipality.value = areas.municipalities.map(item => ({ value: item.municipalityId, label: item.name }));
        optionsConstituency.value = areas.constituencies.map(item => ({ value: item.constituencyId, label: item.name }));
        let options = []
        switch(props.area){
          case "municipality":
            options = optionsMunicipality
            break;
          case "constituency":
            options = optionsConstituency
            break;
        }
        filteredOptions.value = options.value;
      } catch (error) {
          console.error('Error fetching options:', error);
      }
    };

  const fetchFilteredOptions = () => {
      const query = searchQuery.value.toLowerCase();
      let options = [];
      switch(props.area){
        case "municipality":
          options = optionsMunicipality
          break;
        case "constituency":
          options = optionsConstituency
          break;
      }
      filteredOptions.value = options.value.filter(option =>
        option.label.toLowerCase().includes(query)
      );
    };

    const updateParent = () => {
      emits('select-option', selectedOption.value);
    };

    fetchOptions();

    watch(searchQuery, fetchFilteredOptions);
</script>

<template>
  <div class="w-full flex flex-col items-center">
    <input
      type="text"
      v-model="searchQuery"
      @input="fetchFilteredOptions"
      placeholder="zoeken..."
      class="h-12 m-2 p-4 font-bold rounded-lg shadow-lg transition-all duration-300 ease-in-out text-white bg-NavBlue border border-amber-50 bg-opacity-90 placeholder-white focus:outline-none focus:ring-2 focus:ring-purple-800 focus:bg-opacity-90 w-72"
    />

    <div class="relative w-72 m-3">
      <select v-model="selectedOption" @change="updateParent" class="h-14 p-3 font-bold rounded-lg shadow-lg text-white bg-NavBlue border border-amber-50 bg-opacity-90 placeholder-white w-full">
        <option value="" disabled selected>klik om te zoeken</option>
        <option v-for="option in filteredOptions" :key="option.value" :value="option.label">
          {{ option.label }}
        </option>
      </select>
    </div>
    <p class="text-2xl">{{chartLabel}}</p>
  </div>
</template>

<style scoped>

</style>