<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="repGrafica">
        <h2 class="jh-entity-heading" data-cy="repGraficaDetailsHeading">
          <span v-text="$t('proyecto03App.repGrafica.detail.title')">RepGrafica</span> {{ repGrafica.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('proyecto03App.repGrafica.fecha')">Fecha</span>
          </dt>
          <dd>
            <span>{{ repGrafica.fecha }}</span>
          </dd>
          <dt>
            <span v-text="$t('proyecto03App.repGrafica.repGrafica')">Rep Grafica</span>
          </dt>
          <dd>
            <span>{{ repGrafica.repGrafica }}</span>
          </dd>
          <dt>
            <span v-text="$t('proyecto03App.repGrafica.estatus')">Estatus</span>
          </dt>
          <dd>
            <span v-text="$t('proyecto03App.Estatus.' + repGrafica.estatus)">{{ repGrafica.estatus }}</span>
          </dd>
          <dt>
            <span v-text="$t('proyecto03App.repGrafica.usuario')">Usuario</span>
          </dt>
          <dd>
            <span>{{ repGrafica.usuario }}</span>
          </dd>
          <dt>
            <span v-text="$t('proyecto03App.repGrafica.fechaMod')">Fecha Mod</span>
          </dt>
          <dd>
            <span>{{ repGrafica.fechaMod }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="repGrafica.id"
          :to="{ name: 'RepGraficaEdit', params: { repGraficaId: repGrafica.id } }"
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

<script lang="ts" src="./rep-grafica-details.component.ts"></script>
