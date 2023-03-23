<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="perfiles">
        <h2 class="jh-entity-heading" data-cy="perfilesDetailsHeading">
          <span v-text="$t('proyecto03App.perfiles.detail.title')">Perfiles</span> {{ perfiles.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('proyecto03App.perfiles.fecha')">Fecha</span>
          </dt>
          <dd>
            <span>{{ perfiles.fecha }}</span>
          </dd>
          <dt>
            <span v-text="$t('proyecto03App.perfiles.perfil')">Perfil</span>
          </dt>
          <dd>
            <span>{{ perfiles.perfil }}</span>
          </dd>
          <dt>
            <span v-text="$t('proyecto03App.perfiles.usuario')">Usuario</span>
          </dt>
          <dd>
            <span>{{ perfiles.usuario }}</span>
          </dd>
          <dt>
            <span v-text="$t('proyecto03App.perfiles.fechaMod')">Fecha Mod</span>
          </dt>
          <dd>
            <span>{{ perfiles.fechaMod }}</span>
          </dd>
          <dt>
            <span v-text="$t('proyecto03App.perfiles.funcionalidades')">Funcionalidades</span>
          </dt>
          <dd>
            <div v-if="perfiles.funcionalidades">
              <router-link :to="{ name: 'FuncionalidadesView', params: { funcionalidadesId: perfiles.funcionalidades.id } }">{{
                perfiles.funcionalidades.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="perfiles.id" :to="{ name: 'PerfilesEdit', params: { perfilesId: perfiles.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./perfiles-details.component.ts"></script>
