<script setup lang="ts">
import { ref, watch } from 'vue';
import { ElectionService } from '@/services/ElectionService'
import { AreaEnum } from '@/models/enum/AreaEnum'
import type { SearchbarArea } from '@/models/Areas'


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

  const isDropdownVisible= ref(false)
  const searchQuery = ref('');
  const selectedOption = ref("");
  const optionsMunicipality = ref<SearchbarArea[]>([]);
  const optionsConstituency = ref<SearchbarArea[]>([]);
  const filteredOptions = ref<SearchbarArea[]>([]);
  const electionService = new ElectionService();

  const fetchOptions = async () => {
      try {
        const areas = await electionService.getAllAreas()
        optionsMunicipality.value = areas.municipalities.map(item => ({ value: item.municipalityId, label: item.name }));
        optionsConstituency.value = areas.constituencies.map(item => ({ value: item.constituencyId, label: item.name }));
        let options: SearchbarArea[] = []
        switch(props.area){
          case AreaEnum.MUNICIPALITY:
            options = optionsMunicipality.value
            break;
          case AreaEnum.CONSTITUENCY:
            options = optionsConstituency.value
            break;
        }
        filteredOptions.value = options;
      } catch (error) {
          console.error('Error fetching options:', error);
      }
    };
  const toggleDropdown = (visible: boolean) => {
      isDropdownVisible.value = visible;
  }

  const fetchFilteredOptions = () => {
      const query = searchQuery.value.toLowerCase();
      let options: SearchbarArea[] = [];
      switch(props.area){
        case AreaEnum.MUNICIPALITY:
          options = optionsMunicipality.value
          break;
        case AreaEnum.CONSTITUENCY:
          options = optionsConstituency.value
          break;
      }
      filteredOptions.value = options.filter((option: SearchbarArea) =>
        option.label.toLowerCase().includes(query)
      );
    };

    const updateParent = (option: SearchbarArea) => {
      isDropdownVisible.value = false
      emits('select-option', option.label);
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
    @focus="toggleDropdown(true)"
    placeholder="zoeken..."
    class="h-12 m-2 p-4 font-bold rounded-lg shadow-lg transition-all duration-300 ease-in-out text-white bg-NavBlue border border-amber-50 bg-opacity-90 placeholder-white focus:outline-none focus:ring-2 focus:ring-purple-800 focus:bg-opacity-90 w-72"
    />

    <div class="relative w-72 m-3">
      <ul
        v-show="isDropdownVisible"
        class="absolute w-full bg-white shadow-lg max-h-60 rounded-lg overflow-y-auto z-10"
      >
        <li
          v-for="option in filteredOptions"
          :key="option.value"
          @click="updateParent(option)"
          class="p-3 cursor-pointer hover:bg-gray-200"
        >
          {{ option.label }}
        </li>
      </ul>
    </div>
    <p class="text-2xl">{{chartLabel}}</p>
  </div>
</template>

<style scoped>

</style>