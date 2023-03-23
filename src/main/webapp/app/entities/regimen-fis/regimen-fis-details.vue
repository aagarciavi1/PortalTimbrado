<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="regimenFis">
        <h2 class="jh-entity-heading" data-cy="regimenFisDetailsHeading">
          <span v-text="$t('proyecto03App.regimenFis.detail.title')">RegimenFis</span> {{ regimenFis.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('proyecto03App.regimenFis.fecha')">Fecha</span>
          </dt>
          <dd>
            <span>{{ regimenFis.fecha }}</span>
          </dd>
          <dt>
            <span v-text="$t('proyecto03App.regimenFis.regimenF')">Regimen F</span>
          </dt>
          <dd>
            <span>{{ regimenFis.regimenF }}</span>
          </dd>
          <dt>
            <span v-text="$t('proyecto03App.regimenFis.estatus')">Estatus</span>
          </dt>
          <dd>
            <span v-text="$t('proyecto03App.Estatus.' + regimenFis.estatus)">{{ regimenFis.estatus }}</span>
          </dd>
          <dt>
            <span v-text="$t('proyecto03App.regimenFis.usuario')">Usuario</span>
          </dt>
          <dd>
            <span>{{ regimenFis.usuario }}</span>
          </dd>
          <dt>
            <span v-text="$t('proyecto03App.regimenFis.fechaMod')">Fecha Mod</span>
          </dt>
          <dd>
            <span>{{ regimenFis.fechaMod }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="regimenFis.id"
          :to="{ name: 'RegimenFisEdit', params: { regimenFisId: regimenFis.id } }"
          custom
          v-slot="{ navigate }"
        >
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./regimen-fis-details.component.ts"></script>
