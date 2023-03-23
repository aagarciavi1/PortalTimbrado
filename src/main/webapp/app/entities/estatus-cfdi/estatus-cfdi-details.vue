<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="estatusCFDI">
        <h2 class="jh-entity-heading" data-cy="estatusCFDIDetailsHeading">
          <span v-text="$t('proyecto03App.estatusCFDI.detail.title')">EstatusCFDI</span> {{ estatusCFDI.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('proyecto03App.estatusCFDI.fecha')">Fecha</span>
          </dt>
          <dd>
            <span>{{ estatusCFDI.fecha }}</span>
          </dd>
          <dt>
            <span v-text="$t('proyecto03App.estatusCFDI.descEstCFDI')">Desc Est CFDI</span>
          </dt>
          <dd>
            <span>{{ estatusCFDI.descEstCFDI }}</span>
          </dd>
          <dt>
            <span v-text="$t('proyecto03App.estatusCFDI.estatus')">Estatus</span>
          </dt>
          <dd>
            <span v-text="$t('proyecto03App.Estatus.' + estatusCFDI.estatus)">{{ estatusCFDI.estatus }}</span>
          </dd>
          <dt>
            <span v-text="$t('proyecto03App.estatusCFDI.usuario')">Usuario</span>
          </dt>
          <dd>
            <span>{{ estatusCFDI.usuario }}</span>
          </dd>
          <dt>
            <span v-text="$t('proyecto03App.estatusCFDI.fechaMod')">Fecha Mod</span>
          </dt>
          <dd>
            <span>{{ estatusCFDI.fechaMod }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="estatusCFDI.id"
          :to="{ name: 'EstatusCFDIEdit', params: { estatusCFDIId: estatusCFDI.id } }"
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

<script lang="ts" src="./estatus-cfdi-details.component.ts"></script>
