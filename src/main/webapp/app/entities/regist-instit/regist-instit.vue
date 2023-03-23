<template>
  <div>
    <h2 id="page-heading" data-cy="RegistInstitHeading">
      <span v-text="$t('proyecto03App.registInstit.home.title')" id="regist-instit-heading">Regist Instits</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('proyecto03App.registInstit.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'RegistInstitCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-regist-instit"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('proyecto03App.registInstit.home.createLabel')"> Create a new Regist Instit </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && registInstits && registInstits.length === 0">
      <span v-text="$t('proyecto03App.registInstit.home.notFound')">No registInstits found</span>
    </div>
    <div class="table-responsive" v-if="registInstits && registInstits.length > 0">
      <table class="table table-striped" aria-describedby="registInstits">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="$t('global.field.id')">ID</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('fecha')">
              <span v-text="$t('proyecto03App.registInstit.fecha')">Fecha</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'fecha'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('idInstit')">
              <span v-text="$t('proyecto03App.registInstit.idInstit')">Id Instit</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'idInstit'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('estatus')">
              <span v-text="$t('proyecto03App.registInstit.estatus')">Estatus</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'estatus'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('nivel')">
              <span v-text="$t('proyecto03App.registInstit.nivel')">Nivel</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'nivel'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('razonSocial')">
              <span v-text="$t('proyecto03App.registInstit.razonSocial')">Razon Social</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'razonSocial'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('rFC')">
              <span v-text="$t('proyecto03App.registInstit.rFC')">R FC</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'rFC'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('pais')">
              <span v-text="$t('proyecto03App.registInstit.pais')">Pais</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'pais'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('municipio')">
              <span v-text="$t('proyecto03App.registInstit.municipio')">Municipio</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'municipio'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('localidad')">
              <span v-text="$t('proyecto03App.registInstit.localidad')">Localidad</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'localidad'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('codigoPostal')">
              <span v-text="$t('proyecto03App.registInstit.codigoPostal')">Codigo Postal</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'codigoPostal'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('colonia')">
              <span v-text="$t('proyecto03App.registInstit.colonia')">Colonia</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'colonia'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('calle')">
              <span v-text="$t('proyecto03App.registInstit.calle')">Calle</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'calle'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('numExt')">
              <span v-text="$t('proyecto03App.registInstit.numExt')">Num Ext</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'numExt'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('numInt')">
              <span v-text="$t('proyecto03App.registInstit.numInt')">Num Int</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'numInt'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('logo')">
              <span v-text="$t('proyecto03App.registInstit.logo')">Logo</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'logo'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('certificado')">
              <span v-text="$t('proyecto03App.registInstit.certificado')">Certificado</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'certificado'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('llavePriv')">
              <span v-text="$t('proyecto03App.registInstit.llavePriv')">Llave Priv</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'llavePriv'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('contrasena')">
              <span v-text="$t('proyecto03App.registInstit.contrasena')">Contrasena</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'contrasena'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('fechaExp')">
              <span v-text="$t('proyecto03App.registInstit.fechaExp')">Fecha Exp</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'fechaExp'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('dias')">
              <span v-text="$t('proyecto03App.registInstit.dias')">Dias</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'dias'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('correo')">
              <span v-text="$t('proyecto03App.registInstit.correo')">Correo</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'correo'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('usuario')">
              <span v-text="$t('proyecto03App.registInstit.usuario')">Usuario</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'usuario'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('fechaMod')">
              <span v-text="$t('proyecto03App.registInstit.fechaMod')">Fecha Mod</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'fechaMod'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('regimenfis.id')">
              <span v-text="$t('proyecto03App.registInstit.regimenfis')">Regimenfis</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'regimenfis.id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('estado.id')">
              <span v-text="$t('proyecto03App.registInstit.estado')">Estado</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'estado.id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('registinstit.id')">
              <span v-text="$t('proyecto03App.registInstit.registinstit')">Registinstit</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'registinstit.id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('registinstiti.id')">
              <span v-text="$t('proyecto03App.registInstit.registinstiti')">Registinstiti</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'registinstiti.id'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="registInstit in registInstits" :key="registInstit.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'RegistInstitView', params: { registInstitId: registInstit.id } }">{{
                registInstit.id
              }}</router-link>
            </td>
            <td>{{ registInstit.fecha }}</td>
            <td>{{ registInstit.idInstit }}</td>
            <td v-text="$t('proyecto03App.Estatus.' + registInstit.estatus)">{{ registInstit.estatus }}</td>
            <td>{{ registInstit.nivel }}</td>
            <td>{{ registInstit.razonSocial }}</td>
            <td>{{ registInstit.rFC }}</td>
            <td>{{ registInstit.pais }}</td>
            <td>{{ registInstit.municipio }}</td>
            <td>{{ registInstit.localidad }}</td>
            <td>{{ registInstit.codigoPostal }}</td>
            <td>{{ registInstit.colonia }}</td>
            <td>{{ registInstit.calle }}</td>
            <td>{{ registInstit.numExt }}</td>
            <td>{{ registInstit.numInt }}</td>
            <td>
              <a
                v-if="registInstit.logo"
                v-on:click="openFile(registInstit.logoContentType, registInstit.logo)"
                v-text="$t('entity.action.open')"
                >open</a
              >
              <span v-if="registInstit.logo">{{ registInstit.logoContentType }}, {{ byteSize(registInstit.logo) }}</span>
            </td>
            <td>
              <a
                v-if="registInstit.certificado"
                v-on:click="openFile(registInstit.certificadoContentType, registInstit.certificado)"
                v-text="$t('entity.action.open')"
                >open</a
              >
              <span v-if="registInstit.certificado"
                >{{ registInstit.certificadoContentType }}, {{ byteSize(registInstit.certificado) }}</span
              >
            </td>
            <td>
              <a
                v-if="registInstit.llavePriv"
                v-on:click="openFile(registInstit.llavePrivContentType, registInstit.llavePriv)"
                v-text="$t('entity.action.open')"
                >open</a
              >
              <span v-if="registInstit.llavePriv">{{ registInstit.llavePrivContentType }}, {{ byteSize(registInstit.llavePriv) }}</span>
            </td>
            <td>{{ registInstit.contrasena }}</td>
            <td>{{ registInstit.fechaExp }}</td>
            <td>{{ registInstit.dias }}</td>
            <td>{{ registInstit.correo }}</td>
            <td>{{ registInstit.usuario }}</td>
            <td>{{ registInstit.fechaMod }}</td>
            <td>
              <div v-if="registInstit.regimenfis">
                <router-link :to="{ name: 'RegimenFisView', params: { regimenFisId: registInstit.regimenfis.id } }">{{
                  registInstit.regimenfis.id
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="registInstit.estado">
                <router-link :to="{ name: 'EstadoView', params: { estadoId: registInstit.estado.id } }">{{
                  registInstit.estado.id
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="registInstit.registinstit">
                <router-link :to="{ name: 'RegistInstitView', params: { registInstitId: registInstit.registinstit.id } }">{{
                  registInstit.registinstit.id
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="registInstit.registinstiti">
                <router-link :to="{ name: 'RegistInstitView', params: { registInstitId: registInstit.registinstiti.id } }">{{
                  registInstit.registinstiti.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'RegistInstitView', params: { registInstitId: registInstit.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'RegistInstitEdit', params: { registInstitId: registInstit.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(registInstit)"
                  variant="danger"
                  class="btn btn-sm"
                  data-cy="entityDeleteButton"
                  v-b-modal.removeEntity
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="$t('entity.action.delete')">Delete</span>
                </b-button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <span slot="modal-title"
        ><span id="proyecto03App.registInstit.delete.question" data-cy="registInstitDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-registInstit-heading" v-text="$t('proyecto03App.registInstit.delete.question', { id: removeId })">
          Are you sure you want to delete this Regist Instit?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-registInstit"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeRegistInstit()"
        >
          Delete
        </button>
      </div>
    </b-modal>
    <div v-show="registInstits && registInstits.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./regist-instit.component.ts"></script>
