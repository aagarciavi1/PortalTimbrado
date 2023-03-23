<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="funcionalidades">
        <h2 class="jh-entity-heading" data-cy="funcionalidadesDetailsHeading">
          <span v-text="$t('proyecto03App.funcionalidades.detail.title')">Funcionalidades</span> {{ funcionalidades.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('proyecto03App.funcionalidades.fecha')">Fecha</span>
          </dt>
          <dd>
            <span>{{ funcionalidades.fecha }}</span>
          </dd>
          <dt>
            <span v-text="$t('proyecto03App.funcionalidades.perfil')">Perfil</span>
          </dt>
          <dd>
            <span>{{ funcionalidades.perfil }}</span>
          </dd>
          <dt>
            <span v-text="$t('proyecto03App.funcionalidades.funcionalidad')">Funcionalidad</span>
          </dt>
          <dd>
            <span v-text="$t('proyecto03App.Funcionalidad.' + funcionalidades.funcionalidad)">{{ funcionalidades.funcionalidad }}</span>
          </dd>
          <dt>
            <span v-text="$t('proyecto03App.funcionalidades.alta')">Alta</span>
          </dt>
          <dd>
            <span>{{ funcionalidades.alta }}</span>
          </dd>
          <dt>
            <span v-text="$t('proyecto03App.funcionalidades.editar')">Editar</span>
          </dt>
          <dd>
            <span>{{ funcionalidades.editar }}</span>
          </dd>
          <dt>
            <span v-text="$t('proyecto03App.funcionalidades.activarInact')">Activar Inact</span>
          </dt>
          <dd>
            <span>{{ funcionalidades.activarInact }}</span>
          </dd>
          <dt>
            <span v-text="$t('proyecto03App.funcionalidades.consulta')">Consulta</span>
          </dt>
          <dd>
            <span>{{ funcionalidades.consulta }}</span>
          </dd>
          <dt>
            <span v-text="$t('proyecto03App.funcionalidades.usuario')">Usuario</span>
          </dt>
          <dd>
            <span>{{ funcionalidades.usuario }}</span>
          </dd>
          <dt>
            <span v-text="$t('proyecto03App.funcionalidades.fechaMod')">Fecha Mod</span>
          </dt>
          <dd>
            <span>{{ funcionalidades.fechaMod }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="funcionalidades.id"
          :to="{ name: 'FuncionalidadesEdit', params: { funcionalidadesId: funcionalidades.id } }"
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

<script lang="ts" src="./funcionalidades-details.component.ts"></script>
