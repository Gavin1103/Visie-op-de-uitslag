<script setup lang="ts">

import IconLogo from "@/components/icons/IconLogo.vue";
import SocailsIcons from "@/components/footer/SocialsIcons.vue";
</script>

<template>
  <footer class="bg-NavBlue px-8 py-6 h-56 text-white w-full flex justify-center">
    <div class="flex flex-col w-11/12 items-center">
      <div class="w-full ">
        <section class="flex gap-14">
          <IconLogo width="w-40" height="h-20" />
          <section>
            <ul class="list-none text-lg font-extrabold">
              <li><router-link class="text-white" to="/about">Over ons</router-link></li>
              <li><router-link class="text-white" to="/about">FAQ</router-link></li>
              <li><router-link class="text-white" to="/contact">Contact</router-link></li>
            </ul>
          </section>
        </section>
      </div>
      <div class="w-full flex justify-center m-5">
        <div class="h-2 w-full bg-white"></div>
      </div>
      <SocailsIcons />


    </div>
  </footer>

</template>
