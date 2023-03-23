<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="recibo">
        <h2 class="jh-entity-heading" data-cy="reciboDetailsHeading">
          <span v-text="$t('proyecto03App.recibo.detail.title')">Recibo</span> {{ recibo.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('proyecto03App.recibo.fecha')">Fecha</span>
          </dt>
          <dd>
            <span>{{ recibo.fecha }}</span>
          </dd>
          <dt>
            <span v-text="$t('proyecto03App.recibo.idInstit')">Id Instit</span>
          </dt>
          <dd>
            <span>{{ recibo.idInstit }}</span>
          </dd>
          <dt>
            <span v-text="$t('proyecto03App.recibo.nivel')">Nivel</span>
          </dt>
          <dd>
            <span>{{ recibo.nivel }}</span>
          </dd>
          <dt>
            <span v-text="$t('proyecto03App.recibo.tipoCFDI')">Tipo CFDI</span>
          </dt>
          <dd>
            <span>{{ recibo.tipoCFDI }}</span>
          </dd>
          <dt>
            <span v-text="$t('proyecto03App.recibo.clave')">Clave</span>
          </dt>
          <dd>
            <span>{{ recibo.clave }}</span>
          </dd>
          <dt>
            <span v-text="$t('proyecto03App.recibo.envioConCFDI')">Envio Con CFDI</span>
          </dt>
          <dd>
            <span>{{ recibo.envioConCFDI }}</span>
          </dd>
          <dt>
            <span v-text="$t('proyecto03App.recibo.envioSinCFDI')">Envio Sin CFDI</span>
          </dt>
          <dd>
            <span>{{ recibo.envioSinCFDI }}</span>
          </dd>
          <dt>
            <span v-text="$t('proyecto03App.recibo.aplicaLeyenda')">Aplica Leyenda</span>
          </dt>
          <dd>
            <span>{{ recibo.aplicaLeyenda }}</span>
          </dd>
          <dt>
            <span v-text="$t('proyecto03App.recibo.leyenda')">Leyenda</span>
          </dt>
          <dd>
            <span>{{ recibo.leyenda }}</span>
          </dd>
          <dt>
            <span v-text="$t('proyecto03App.recibo.usuario')">Usuario</span>
          </dt>
          <dd>
            <span>{{ recibo.usuario }}</span>
          </dd>
          <dt>
            <span v-text="$t('proyecto03App.recibo.fechaMod')">Fecha Mod</span>
          </dt>
          <dd>
            <span>{{ recibo.fechaMod }}</span>
          </dd>
          <dt>
            <span v-text="$t('proyecto03App.recibo.tiporecibo')">Tiporecibo</span>
          </dt>
          <dd>
            <div v-if="recibo.tiporecibo">
              <router-link :to="{ name: 'TipoReciboView', params: { tipoReciboId: recibo.tiporecibo.id } }">{{
                recibo.tiporecibo.id
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('proyecto03App.recibo.repgrafica')">Repgrafica</span>
          </dt>
          <dd>
            <div v-if="recibo.repgrafica">
              <router-link :to="{ name: 'RepGraficaView', params: { repGraficaId: recibo.repgrafica.id } }">{{
                recibo.repgrafica.id
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('proyecto03App.recibo.parametros')">Parametros</span>
          </dt>
          <dd>
            <div v-if="recibo.parametros">
              <router-link :to="{ name: 'ParametrosView', params: { parametrosId: recibo.parametros.id } }">{{
                recibo.parametros.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="recibo.id" :to="{ name: 'ReciboEdit', params: { reciboId: recibo.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./recibo-details.component.ts"></script>
