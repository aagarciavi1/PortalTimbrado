<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="motivosCancel">
        <h2 class="jh-entity-heading" data-cy="motivosCancelDetailsHeading">
          <span v-text="$t('proyecto03App.motivosCancel.detail.title')">MotivosCancel</span> {{ motivosCancel.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('proyecto03App.motivosCancel.fecha')">Fecha</span>
          </dt>
          <dd>
            <span>{{ motivosCancel.fecha }}</span>
          </dd>
          <dt>
            <span v-text="$t('proyecto03App.motivosCancel.motivoCancel')">Motivo Cancel</span>
          </dt>
          <dd>
            <span>{{ motivosCancel.motivoCancel }}</span>
          </dd>
          <dt>
            <span v-text="$t('proyecto03App.motivosCancel.estatus')">Estatus</span>
          </dt>
          <dd>
            <span v-text="$t('proyecto03App.Estatus.' + motivosCancel.estatus)">{{ motivosCancel.estatus }}</span>
          </dd>
          <dt>
            <span v-text="$t('proyecto03App.motivosCancel.usuario')">Usuario</span>
          </dt>
          <dd>
            <span>{{ motivosCancel.usuario }}</span>
          </dd>
          <dt>
            <span v-text="$t('proyecto03App.motivosCancel.fechaMod')">Fecha Mod</span>
          </dt>
          <dd>
            <span>{{ motivosCancel.fechaMod }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="motivosCancel.id"
          :to="{ name: 'MotivosCancelEdit', params: { motivosCancelId: motivosCancel.id } }"
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

<script lang="ts" src="./motivos-cancel-details.component.ts"></script>
