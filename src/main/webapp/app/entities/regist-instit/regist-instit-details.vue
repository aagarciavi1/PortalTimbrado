<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="registInstit">
        <h2 class="jh-entity-heading" data-cy="registInstitDetailsHeading">
          <span v-text="$t('proyecto03App.registInstit.detail.title')">RegistInstit</span> {{ registInstit.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('proyecto03App.registInstit.fecha')">Fecha</span>
          </dt>
          <dd>
            <span>{{ registInstit.fecha }}</span>
          </dd>
          <dt>
            <span v-text="$t('proyecto03App.registInstit.idInstit')">Id Instit</span>
          </dt>
          <dd>
            <span>{{ registInstit.idInstit }}</span>
          </dd>
          <dt>
            <span v-text="$t('proyecto03App.registInstit.estatus')">Estatus</span>
          </dt>
          <dd>
            <span v-text="$t('proyecto03App.Estatus.' + registInstit.estatus)">{{ registInstit.estatus }}</span>
          </dd>
          <dt>
            <span v-text="$t('proyecto03App.registInstit.nivel')">Nivel</span>
          </dt>
          <dd>
            <span>{{ registInstit.nivel }}</span>
          </dd>
          <dt>
            <span v-text="$t('proyecto03App.registInstit.razonSocial')">Razon Social</span>
          </dt>
          <dd>
            <span>{{ registInstit.razonSocial }}</span>
          </dd>
          <dt>
            <span v-text="$t('proyecto03App.registInstit.rFC')">R FC</span>
          </dt>
          <dd>
            <span>{{ registInstit.rFC }}</span>
          </dd>
          <dt>
            <span v-text="$t('proyecto03App.registInstit.pais')">Pais</span>
          </dt>
          <dd>
            <span>{{ registInstit.pais }}</span>
          </dd>
          <dt>
            <span v-text="$t('proyecto03App.registInstit.municipio')">Municipio</span>
          </dt>
          <dd>
            <span>{{ registInstit.municipio }}</span>
          </dd>
          <dt>
            <span v-text="$t('proyecto03App.registInstit.localidad')">Localidad</span>
          </dt>
          <dd>
            <span>{{ registInstit.localidad }}</span>
          </dd>
          <dt>
            <span v-text="$t('proyecto03App.registInstit.codigoPostal')">Codigo Postal</span>
          </dt>
          <dd>
            <span>{{ registInstit.codigoPostal }}</span>
          </dd>
          <dt>
            <span v-text="$t('proyecto03App.registInstit.colonia')">Colonia</span>
          </dt>
          <dd>
            <span>{{ registInstit.colonia }}</span>
          </dd>
          <dt>
            <span v-text="$t('proyecto03App.registInstit.calle')">Calle</span>
          </dt>
          <dd>
            <span>{{ registInstit.calle }}</span>
          </dd>
          <dt>
            <span v-text="$t('proyecto03App.registInstit.numExt')">Num Ext</span>
          </dt>
          <dd>
            <span>{{ registInstit.numExt }}</span>
          </dd>
          <dt>
            <span v-text="$t('proyecto03App.registInstit.numInt')">Num Int</span>
          </dt>
          <dd>
            <span>{{ registInstit.numInt }}</span>
          </dd>
          <dt>
            <span v-text="$t('proyecto03App.registInstit.logo')">Logo</span>
          </dt>
          <dd>
            <div v-if="registInstit.logo">
              <a v-on:click="openFile(registInstit.logoContentType, registInstit.logo)" v-text="$t('entity.action.open')">open</a>
              {{ registInstit.logoContentType }}, {{ byteSize(registInstit.logo) }}
            </div>
          </dd>
          <dt>
            <span v-text="$t('proyecto03App.registInstit.certificado')">Certificado</span>
          </dt>
          <dd>
            <div v-if="registInstit.certificado">
              <a v-on:click="openFile(registInstit.certificadoContentType, registInstit.certificado)" v-text="$t('entity.action.open')"
                >open</a
              >
              {{ registInstit.certificadoContentType }}, {{ byteSize(registInstit.certificado) }}
            </div>
          </dd>
          <dt>
            <span v-text="$t('proyecto03App.registInstit.llavePriv')">Llave Priv</span>
          </dt>
          <dd>
            <div v-if="registInstit.llavePriv">
              <a v-on:click="openFile(registInstit.llavePrivContentType, registInstit.llavePriv)" v-text="$t('entity.action.open')">open</a>
              {{ registInstit.llavePrivContentType }}, {{ byteSize(registInstit.llavePriv) }}
            </div>
          </dd>
          <dt>
            <span v-text="$t('proyecto03App.registInstit.contrasena')">Contrasena</span>
          </dt>
          <dd>
            <span>{{ registInstit.contrasena }}</span>
          </dd>
          <dt>
            <span v-text="$t('proyecto03App.registInstit.fechaExp')">Fecha Exp</span>
          </dt>
          <dd>
            <span>{{ registInstit.fechaExp }}</span>
          </dd>
          <dt>
            <span v-text="$t('proyecto03App.registInstit.dias')">Dias</span>
          </dt>
          <dd>
            <span>{{ registInstit.dias }}</span>
          </dd>
          <dt>
            <span v-text="$t('proyecto03App.registInstit.correo')">Correo</span>
          </dt>
          <dd>
            <span>{{ registInstit.correo }}</span>
          </dd>
          <dt>
            <span v-text="$t('proyecto03App.registInstit.usuario')">Usuario</span>
          </dt>
          <dd>
            <span>{{ registInstit.usuario }}</span>
          </dd>
          <dt>
            <span v-text="$t('proyecto03App.registInstit.fechaMod')">Fecha Mod</span>
          </dt>
          <dd>
            <span>{{ registInstit.fechaMod }}</span>
          </dd>
          <dt>
            <span v-text="$t('proyecto03App.registInstit.regimenfis')">Regimenfis</span>
          </dt>
          <dd>
            <div v-if="registInstit.regimenfis">
              <router-link :to="{ name: 'RegimenFisView', params: { regimenFisId: registInstit.regimenfis.id } }">{{
                registInstit.regimenfis.id
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('proyecto03App.registInstit.estado')">Estado</span>
          </dt>
          <dd>
            <div v-if="registInstit.estado">
              <router-link :to="{ name: 'EstadoView', params: { estadoId: registInstit.estado.id } }">{{
                registInstit.estado.id
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('proyecto03App.registInstit.registinstit')">Registinstit</span>
          </dt>
          <dd>
            <div v-if="registInstit.registinstit">
              <router-link :to="{ name: 'RegistInstitView', params: { registInstitId: registInstit.registinstit.id } }">{{
                registInstit.registinstit.id
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('proyecto03App.registInstit.registinstiti')">Registinstiti</span>
          </dt>
          <dd>
            <div v-if="registInstit.registinstiti">
              <router-link :to="{ name: 'RegistInstitView', params: { registInstitId: registInstit.registinstiti.id } }">{{
                registInstit.registinstiti.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="registInstit.id"
          :to="{ name: 'RegistInstitEdit', params: { registInstitId: registInstit.id } }"
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

<script lang="ts" src="./regist-instit-details.component.ts"></script>
