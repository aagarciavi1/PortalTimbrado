<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="fTP">
        <h2 class="jh-entity-heading" data-cy="fTPDetailsHeading">
          <span v-text="$t('proyecto03App.fTP.detail.title')">FTP</span> {{ fTP.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('proyecto03App.fTP.fecha')">Fecha</span>
          </dt>
          <dd>
            <span>{{ fTP.fecha }}</span>
          </dd>
          <dt>
            <span v-text="$t('proyecto03App.fTP.idInstit')">Id Instit</span>
          </dt>
          <dd>
            <span>{{ fTP.idInstit }}</span>
          </dd>
          <dt>
            <span v-text="$t('proyecto03App.fTP.nivel')">Nivel</span>
          </dt>
          <dd>
            <span>{{ fTP.nivel }}</span>
          </dd>
          <dt>
            <span v-text="$t('proyecto03App.fTP.clave')">Clave</span>
          </dt>
          <dd>
            <span>{{ fTP.clave }}</span>
          </dd>
          <dt>
            <span v-text="$t('proyecto03App.fTP.carpetaFTP')">Carpeta FTP</span>
          </dt>
          <dd>
            <span>{{ fTP.carpetaFTP }}</span>
          </dd>
          <dt>
            <span v-text="$t('proyecto03App.fTP.subcarpetaFTP')">Subcarpeta FTP</span>
          </dt>
          <dd>
            <span>{{ fTP.subcarpetaFTP }}</span>
          </dd>
          <dt>
            <span v-text="$t('proyecto03App.fTP.descripcionFTP')">Descripcion FTP</span>
          </dt>
          <dd>
            <span>{{ fTP.descripcionFTP }}</span>
          </dd>
          <dt>
            <span v-text="$t('proyecto03App.fTP.ipFTP')">Ip FTP</span>
          </dt>
          <dd>
            <span>{{ fTP.ipFTP }}</span>
          </dd>
          <dt>
            <span v-text="$t('proyecto03App.fTP.puerto')">Puerto</span>
          </dt>
          <dd>
            <span>{{ fTP.puerto }}</span>
          </dd>
          <dt>
            <span v-text="$t('proyecto03App.fTP.usuarioFTP')">Usuario FTP</span>
          </dt>
          <dd>
            <span>{{ fTP.usuarioFTP }}</span>
          </dd>
          <dt>
            <span v-text="$t('proyecto03App.fTP.contrasena')">Contrasena</span>
          </dt>
          <dd>
            <span>{{ fTP.contrasena }}</span>
          </dd>
          <dt>
            <span v-text="$t('proyecto03App.fTP.usuario')">Usuario</span>
          </dt>
          <dd>
            <span>{{ fTP.usuario }}</span>
          </dd>
          <dt>
            <span v-text="$t('proyecto03App.fTP.fechaMod')">Fecha Mod</span>
          </dt>
          <dd>
            <span>{{ fTP.fechaMod }}</span>
          </dd>
          <dt>
            <span v-text="$t('proyecto03App.fTP.registinstit')">Registinstit</span>
          </dt>
          <dd>
            <div v-if="fTP.registinstit">
              <router-link :to="{ name: 'RegistInstitView', params: { registInstitId: fTP.registinstit.id } }">{{
                fTP.registinstit.id
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('proyecto03App.fTP.tipocfdi')">Tipocfdi</span>
          </dt>
          <dd>
            <div v-if="fTP.tipocfdi">
              <router-link :to="{ name: 'TipoCFDIView', params: { tipoCFDIId: fTP.tipocfdi.id } }">{{ fTP.tipocfdi.id }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('proyecto03App.fTP.tiporecibo')">Tiporecibo</span>
          </dt>
          <dd>
            <div v-if="fTP.tiporecibo">
              <router-link :to="{ name: 'TipoReciboView', params: { tipoReciboId: fTP.tiporecibo.id } }">{{
                fTP.tiporecibo.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="fTP.id" :to="{ name: 'FTPEdit', params: { fTPId: fTP.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./ftp-details.component.ts"></script>
