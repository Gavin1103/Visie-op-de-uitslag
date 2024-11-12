<template>
  <div class="map-container">
    <svg
        width="600"
        height="5000"
        viewBox="0 0 1000 5000"
        xmlns="http://www.w3.org/2000/svg"
    >
      <!-- Each kieskring region with path data -->
      <path
          id="groningen"
          d="M 100,100 L 300,100 L 300,200 L 100,200 Z" <!-- Replace with actual SVG path data -->
      @click="handleKieskringClick('Groningen')"
      class="kieskring"
      />
      <path
          id="fryslan"
          d="M 100,250 L 300,250 L 300,350 L 100,350 Z"
          @click="handleKieskringClick('Fryslân')"
          class="kieskring"
      />
      <path
          id="drenthe"
          d="M 100,400 L 300,400 L 300,500 L 100,500 Z"
          @click="handleKieskringClick('Drenthe')"
          class="kieskring"
      />
      <path
          id="overijssel"
          d="M 100,550 L 300,550 L 300,650 L 100,650 Z"
          @click="handleKieskringClick('Overijssel')"
          class="kieskring"
      />
      <path
          id="flevoland"
          d="M 100,700 L 300,700 L 300,800 L 100,800 Z"
          @click="handleKieskringClick('Flevoland')"
          class="kieskring"
      />
      <path
          id="nijmegen"
          d="M 100,850 L 300,850 L 300,950 L 100,950 Z"
          @click="handleKieskringClick('Nijmegen')"
          class="kieskring"
      />
      <path
          id="arnhem"
          d="M 100,1000 L 300,1000 L 300,1100 L 100,1100 Z"
          @click="handleKieskringClick('Arnhem')"
          class="kieskring"
      />
      <path
          id="utrecht"
          d="M 100,1150 L 300,1150 L 300,1250 L 100,1250 Z"
          @click="handleKieskringClick('Utrecht')"
          class="kieskring"
      />
      <path
          id="amsterdam"
          d="M 100,1300 L 300,1300 L 300,1400 L 100,1400 Z"
          @click="handleKieskringClick('Amsterdam')"
          class="kieskring"
      />
      <path
          id="haarlem"
          d="M 100,1450 L 300,1450 L 300,1550 L 100,1550 Z"
          @click="handleKieskringClick('Haarlem')"
          class="kieskring"
      />
      <path
          id="den-helder"
          d="M 100,1600 L 300,1600 L 300,1700 L 100,1700 Z"
          @click="handleKieskringClick('Den Helder')"
          class="kieskring"
      />
      <path
          id="s-gravenhage"
          d="M 100,1750 L 300,1750 L 300,1850 L 100,1850 Z"
          @click="handleKieskringClick('\'s-Gravenhage')"
          class="kieskring"
      />
      <path
          id="rotterdam"
          d="M 100,1900 L 300,1900 L 300,2000 L 100,2000 Z"
          @click="handleKieskringClick('Rotterdam')"
          class="kieskring"
      />
      <path
          id="dordrecht"
          d="M 100,2050 L 300,2050 L 300,2150 L 100,2150 Z"
          @click="handleKieskringClick('Dordrecht')"
          class="kieskring"
      />
      <path
          id="leiden"
          d="M 100,2200 L 300,2200 L 300,2300 L 100,2300 Z"
          @click="handleKieskringClick('Leiden')"
          class="kieskring"
      />
      <path
          id="middelburg"
          d="M 100,2350 L 300,2350 L 300,2450 L 100,2450 Z"
          @click="handleKieskringClick('Middelburg')"
          class="kieskring"
      />
      <path
          id="tilburg"
          d="M 100,2500 L 300,2500 L 300,2600 L 100,2600 Z"
          @click="handleKieskringClick('Tilburg')"
          class="kieskring"
      />
      <path
          id="s-hertogenbosch"
          d="M 100,2650 L 300,2650 L 300,2750 L 100,2750 Z"
          @click="handleKieskringClick('\'s-Hertogenbosch')"
          class="kieskring"
      />
      <path
          id="maastricht"
          d="M 100,2800 L 300,2800 L 300,2900 L 100,2900 Z"
          @click="handleKieskringClick('Maastricht')"
          class="kieskring"
      />
      <path
          id="bonaire"
          d="M 100,2950 L 300,2950 L 300,3050 L 100,3050 Z"
          @click="handleKieskringClick('Bonaire')"
          class="kieskring"
      />
    </svg>
  </div>
</template>

<script>
export default {
  name: 'KieskringMap',
  methods: {
    handleKieskringClick(kieskringName) {
      console.log(`Clicked on ${kieskringName}`);
      // Add any specific logic for when a kieskring is clicked
    },
  },
};
</script>

<style scoped>
.map-container {
  max-width: 100%;
}

.kieskring {
  fill: #ccc; /* Base color */
  cursor: pointer;
  transition: fill 0.2s;
}

.kieskring:hover {
  fill: #4CAF50; /* Change color on hover */
}
</style>
