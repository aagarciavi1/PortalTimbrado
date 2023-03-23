<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="tipoRecibo">
        <h2 class="jh-entity-heading" data-cy="tipoReciboDetailsHeading">
          <span v-text="$t('proyecto03App.tipoRecibo.detail.title')">TipoRecibo</span> {{ tipoRecibo.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('proyecto03App.tipoRecibo.fecha')">Fecha</span>
          </dt>
          <dd>
            <span>{{ tipoRecibo.fecha }}</span>
          </dd>
          <dt>
            <span v-text="$t('proyecto03App.tipoRecibo.tipoRecibo')">Tipo Recibo</span>
          </dt>
          <dd>
            <span>{{ tipoRecibo.tipoRecibo }}</span>
          </dd>
          <dt>
            <span v-text="$t('proyecto03App.tipoRecibo.clave')">Clave</span>
          </dt>
          <dd>
            <span>{{ tipoRecibo.clave }}</span>
          </dd>
          <dt>
            <span v-text="$t('proyecto03App.tipoRecibo.estatus')">Estatus</span>
          </dt>
          <dd>
            <span v-text="$t('proyecto03App.Estatus.' + tipoRecibo.estatus)">{{ tipoRecibo.estatus }}</span>
          </dd>
          <dt>
            <span v-text="$t('proyecto03App.tipoRecibo.usuario')">Usuario</span>
          </dt>
          <dd>
            <span>{{ tipoRecibo.usuario }}</span>
          </dd>
          <dt>
            <span v-text="$t('proyecto03App.tipoRecibo.fechaMod')">Fecha Mod</span>
          </dt>
          <dd>
            <span>{{ tipoRecibo.fechaMod }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="tipoRecibo.id"
          :to="{ name: 'TipoReciboEdit', params: { tipoReciboId: tipoRecibo.id } }"
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

<script lang="ts" src="./tipo-recibo-details.component.ts"></script>
