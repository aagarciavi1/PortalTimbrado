<template>
  <div>
    <b-dropdown-item to="/regist-instit">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.registInstit')">Regist Instit</span>
    </b-dropdown-item>
    <b-dropdown-item to="/parametros">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.parametros')">Parametros</span>
    </b-dropdown-item>
    <b-dropdown-item to="/recibo">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.recibo')">Recibo</span>
    </b-dropdown-item>
    <b-dropdown-item to="/ftp">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.ftp')">FTP</span>
    </b-dropdown-item>
    <b-dropdown-item to="/notificaciones">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.notificaciones')">Notificaciones</span>
    </b-dropdown-item>
    <b-dropdown-item to="/perfiles">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.perfiles')">Perfiles</span>
    </b-dropdown-item>
    <b-dropdown-item to="/funcionalidades">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.funcionalidades')">Funcionalidades</span>
    </b-dropdown-item>
    <b-dropdown-item to="/usuarios">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.usuarios')">Usuarios</span>
    </b-dropdown-item>
    <b-dropdown-item to="/regimen-fis">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.regimenFis')">Regimen Fis</span>
    </b-dropdown-item>
    <b-dropdown-item to="/estado">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.estado')">Estado</span>
    </b-dropdown-item>
    <b-dropdown-item to="/estatus-cfdi">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.estatusCfdi')">Estatus CFDI</span>
    </b-dropdown-item>
    <b-dropdown-item to="/motivos-cancel">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.motivosCancel')">Motivos Cancel</span>
    </b-dropdown-item>
    <b-dropdown-item to="/tipo-cfdi">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.tipoCfdi')">Tipo CFDI</span>
    </b-dropdown-item>
    <b-dropdown-item to="/tipo-recibo">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.tipoRecibo')">Tipo Recibo</span>
    </b-dropdown-item>
    <b-dropdown-item to="/rep-grafica">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.repGrafica')">Rep Grafica</span>
    </b-dropdown-item>
    <!-- jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here -->
  </div>
</template>

<script lang="ts" src="./entities-menu.component.ts"></script>
