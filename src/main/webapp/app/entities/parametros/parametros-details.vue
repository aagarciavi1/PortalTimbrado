<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="parametros">
        <h2 class="jh-entity-heading" data-cy="parametrosDetailsHeading">
          <span v-text="$t('proyecto03App.parametros.detail.title')">Parametros</span> {{ parametros.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('proyecto03App.parametros.fecha')">Fecha</span>
          </dt>
          <dd>
            <span>{{ parametros.fecha }}</span>
          </dd>
          <dt>
            <span v-text="$t('proyecto03App.parametros.idInstit')">Id Instit</span>
          </dt>
          <dd>
            <span>{{ parametros.idInstit }}</span>
          </dd>
          <dt>
            <span v-text="$t('proyecto03App.parametros.nivel')">Nivel</span>
          </dt>
          <dd>
            <span>{{ parametros.nivel }}</span>
          </dd>
          <dt>
            <span v-text="$t('proyecto03App.parametros.usuario')">Usuario</span>
          </dt>
          <dd>
            <span>{{ parametros.usuario }}</span>
          </dd>
          <dt>
            <span v-text="$t('proyecto03App.parametros.fechaMod')">Fecha Mod</span>
          </dt>
          <dd>
            <span>{{ parametros.fechaMod }}</span>
          </dd>
          <dt>
            <span v-text="$t('proyecto03App.parametros.registinstit')">Registinstit</span>
          </dt>
          <dd>
            <div v-if="parametros.registinstit">
              <router-link :to="{ name: 'RegistInstitView', params: { registInstitId: parametros.registinstit.id } }">{{
                parametros.registinstit.id
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('proyecto03App.parametros.tipocfdi')">Tipocfdi</span>
          </dt>
          <dd>
            <div v-if="parametros.tipocfdi">
              <router-link :to="{ name: 'TipoCFDIView', params: { tipoCFDIId: parametros.tipocfdi.id } }">{{
                parametros.tipocfdi.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="parametros.id"
          :to="{ name: 'ParametrosEdit', params: { parametrosId: parametros.id } }"
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

<script lang="ts" src="./parametros-details.component.ts"></script>
