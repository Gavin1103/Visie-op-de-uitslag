<script setup lang="ts">

</script>

<template>
  <div class="profile">
    <h1>Profile Page</h1>

    <!-- Profile Picture Section -->
    <div class="profile-picture-section">
      <h1>Profile Picture here</h1>
      <input type="file" @change="uploadPicture" />
    </div>

    <!-- User Info Section -->
    <div class="user-info">
      <p><strong>First Name:</strong> {{}}</p>
      <p><strong>Last Name:</strong> {{}}</p>
      <p><strong>Email:</strong> {{}}</p>
      <p><strong>Birthdate:</strong> {{}}</p>
    </div>

    <!-- Action Buttons -->
    <div class="action-buttons">
      <button @click="changePassword">Change Password</button>
      <button @click="editAccount">Edit Account</button>
      <button @click="deleteAccount">Delete Account</button>
    </div>
  </div>
</template>


<style>

</style>