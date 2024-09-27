<script setup lang="ts">

import IconLogo from "@/components/icons/IconLogo.vue";
</script>

<template>
  <footer class="bg-NavBlue px-8 py-6 h-56 text-white w-full">
      <div class="flex flex-col">
        <section class="flex gap-14">
          <IconLogo width="w-40" height="h-20" />
          <section>
            <lu class="list-none text-lg font-extrabold">
              <li><router-link class="text-white" to="/about">Over ons</router-link></li>
              <li><router-link class="text-white" to="/about">FAQ</router-link></li>
              <li><router-link class="text-white" to="/contact">Contact</router-link></li>
            </lu>
          </section>

        </section>
        <div class="w-full flex justify-center m-5">
          <div class="h-2 w-11/12 bg-white"></div>
        </div>



      </div>
  </footer>

</template>
