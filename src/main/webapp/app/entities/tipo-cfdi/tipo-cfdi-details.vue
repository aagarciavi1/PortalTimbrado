<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="tipoCFDI">
        <h2 class="jh-entity-heading" data-cy="tipoCFDIDetailsHeading">
          <span v-text="$t('proyecto03App.tipoCFDI.detail.title')">TipoCFDI</span> {{ tipoCFDI.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('proyecto03App.tipoCFDI.fecha')">Fecha</span>
          </dt>
          <dd>
            <span>{{ tipoCFDI.fecha }}</span>
          </dd>
          <dt>
            <span v-text="$t('proyecto03App.tipoCFDI.tipoCFDI')">Tipo CFDI</span>
          </dt>
          <dd>
            <span>{{ tipoCFDI.tipoCFDI }}</span>
          </dd>
          <dt>
            <span v-text="$t('proyecto03App.tipoCFDI.estatus')">Estatus</span>
          </dt>
          <dd>
            <span v-text="$t('proyecto03App.Estatus.' + tipoCFDI.estatus)">{{ tipoCFDI.estatus }}</span>
          </dd>
          <dt>
            <span v-text="$t('proyecto03App.tipoCFDI.usuario')">Usuario</span>
          </dt>
          <dd>
            <span>{{ tipoCFDI.usuario }}</span>
          </dd>
          <dt>
            <span v-text="$t('proyecto03App.tipoCFDI.fechaMod')">Fecha Mod</span>
          </dt>
          <dd>
            <span>{{ tipoCFDI.fechaMod }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="tipoCFDI.id" :to="{ name: 'TipoCFDIEdit', params: { tipoCFDIId: tipoCFDI.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./tipo-cfdi-details.component.ts"></script>
