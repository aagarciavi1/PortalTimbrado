<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="estado">
        <h2 class="jh-entity-heading" data-cy="estadoDetailsHeading">
          <span v-text="$t('proyecto03App.estado.detail.title')">Estado</span> {{ estado.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('proyecto03App.estado.fecha')">Fecha</span>
          </dt>
          <dd>
            <span>{{ estado.fecha }}</span>
          </dd>
          <dt>
            <span v-text="$t('proyecto03App.estado.pais')">Pais</span>
          </dt>
          <dd>
            <span>{{ estado.pais }}</span>
          </dd>
          <dt>
            <span v-text="$t('proyecto03App.estado.estatdo')">Estatdo</span>
          </dt>
          <dd>
            <span>{{ estado.estatdo }}</span>
          </dd>
          <dt>
            <span v-text="$t('proyecto03App.estado.descEstado')">Desc Estado</span>
          </dt>
          <dd>
            <span>{{ estado.descEstado }}</span>
          </dd>
          <dt>
            <span v-text="$t('proyecto03App.estado.estatus')">Estatus</span>
          </dt>
          <dd>
            <span v-text="$t('proyecto03App.Estatus.' + estado.estatus)">{{ estado.estatus }}</span>
          </dd>
          <dt>
            <span v-text="$t('proyecto03App.estado.usuario')">Usuario</span>
          </dt>
          <dd>
            <span>{{ estado.usuario }}</span>
          </dd>
          <dt>
            <span v-text="$t('proyecto03App.estado.fechaMod')">Fecha Mod</span>
          </dt>
          <dd>
            <span>{{ estado.fechaMod }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="estado.id" :to="{ name: 'EstadoEdit', params: { estadoId: estado.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./estado-details.component.ts"></script>
