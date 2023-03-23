<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="notificaciones">
        <h2 class="jh-entity-heading" data-cy="notificacionesDetailsHeading">
          <span v-text="$t('proyecto03App.notificaciones.detail.title')">Notificaciones</span> {{ notificaciones.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('proyecto03App.notificaciones.fecha')">Fecha</span>
          </dt>
          <dd>
            <span>{{ notificaciones.fecha }}</span>
          </dd>
          <dt>
            <span v-text="$t('proyecto03App.notificaciones.idInstit')">Id Instit</span>
          </dt>
          <dd>
            <span>{{ notificaciones.idInstit }}</span>
          </dd>
          <dt>
            <span v-text="$t('proyecto03App.notificaciones.nivel')">Nivel</span>
          </dt>
          <dd>
            <span>{{ notificaciones.nivel }}</span>
          </dd>
          <dt>
            <span v-text="$t('proyecto03App.notificaciones.tipoNot')">Tipo Not</span>
          </dt>
          <dd>
            <span v-text="$t('proyecto03App.TipoNot.' + notificaciones.tipoNot)">{{ notificaciones.tipoNot }}</span>
          </dd>
          <dt>
            <span v-text="$t('proyecto03App.notificaciones.clave')">Clave</span>
          </dt>
          <dd>
            <span>{{ notificaciones.clave }}</span>
          </dd>
          <dt>
            <span v-text="$t('proyecto03App.notificaciones.asunto')">Asunto</span>
          </dt>
          <dd>
            <span>{{ notificaciones.asunto }}</span>
          </dd>
          <dt>
            <span v-text="$t('proyecto03App.notificaciones.texto')">Texto</span>
          </dt>
          <dd>
            <span>{{ notificaciones.texto }}</span>
          </dd>
          <dt>
            <span v-text="$t('proyecto03App.notificaciones.piePagina')">Pie Pagina</span>
          </dt>
          <dd>
            <span>{{ notificaciones.piePagina }}</span>
          </dd>
          <dt>
            <span v-text="$t('proyecto03App.notificaciones.usuario')">Usuario</span>
          </dt>
          <dd>
            <span>{{ notificaciones.usuario }}</span>
          </dd>
          <dt>
            <span v-text="$t('proyecto03App.notificaciones.fechaMod')">Fecha Mod</span>
          </dt>
          <dd>
            <span>{{ notificaciones.fechaMod }}</span>
          </dd>
          <dt>
            <span v-text="$t('proyecto03App.notificaciones.registinstit')">Registinstit</span>
          </dt>
          <dd>
            <div v-if="notificaciones.registinstit">
              <router-link :to="{ name: 'RegistInstitView', params: { registInstitId: notificaciones.registinstit.id } }">{{
                notificaciones.registinstit.id
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('proyecto03App.notificaciones.tipocfdi')">Tipocfdi</span>
          </dt>
          <dd>
            <div v-if="notificaciones.tipocfdi">
              <router-link :to="{ name: 'TipoCFDIView', params: { tipoCFDIId: notificaciones.tipocfdi.id } }">{{
                notificaciones.tipocfdi.id
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('proyecto03App.notificaciones.tiporecibo')">Tiporecibo</span>
          </dt>
          <dd>
            <div v-if="notificaciones.tiporecibo">
              <router-link :to="{ name: 'TipoReciboView', params: { tipoReciboId: notificaciones.tiporecibo.id } }">{{
                notificaciones.tiporecibo.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="notificaciones.id"
          :to="{ name: 'NotificacionesEdit', params: { notificacionesId: notificaciones.id } }"
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

<script lang="ts" src="./notificaciones-details.component.ts"></script>
