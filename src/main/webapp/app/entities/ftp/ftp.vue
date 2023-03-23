<template>
  <div>
    <h2 id="page-heading" data-cy="FTPHeading">
      <span v-text="$t('proyecto03App.fTP.home.title')" id="ftp-heading">FTPS</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('proyecto03App.fTP.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'FTPCreate' }" custom v-slot="{ navigate }">
          <button @click="navigate" id="jh-create-entity" data-cy="entityCreateButton" class="btn btn-primary jh-create-entity create-ftp">
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('proyecto03App.fTP.home.createLabel')"> Create a new FTP </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && fTPS && fTPS.length === 0">
      <span v-text="$t('proyecto03App.fTP.home.notFound')">No fTPS found</span>
    </div>
    <div class="table-responsive" v-if="fTPS && fTPS.length > 0">
      <table class="table table-striped" aria-describedby="fTPS">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="$t('global.field.id')">ID</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('fecha')">
              <span v-text="$t('proyecto03App.fTP.fecha')">Fecha</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'fecha'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('idInstit')">
              <span v-text="$t('proyecto03App.fTP.idInstit')">Id Instit</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'idInstit'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('nivel')">
              <span v-text="$t('proyecto03App.fTP.nivel')">Nivel</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'nivel'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('clave')">
              <span v-text="$t('proyecto03App.fTP.clave')">Clave</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'clave'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('carpetaFTP')">
              <span v-text="$t('proyecto03App.fTP.carpetaFTP')">Carpeta FTP</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'carpetaFTP'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('subcarpetaFTP')">
              <span v-text="$t('proyecto03App.fTP.subcarpetaFTP')">Subcarpeta FTP</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'subcarpetaFTP'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('descripcionFTP')">
              <span v-text="$t('proyecto03App.fTP.descripcionFTP')">Descripcion FTP</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'descripcionFTP'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('ipFTP')">
              <span v-text="$t('proyecto03App.fTP.ipFTP')">Ip FTP</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'ipFTP'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('puerto')">
              <span v-text="$t('proyecto03App.fTP.puerto')">Puerto</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'puerto'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('usuarioFTP')">
              <span v-text="$t('proyecto03App.fTP.usuarioFTP')">Usuario FTP</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'usuarioFTP'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('contrasena')">
              <span v-text="$t('proyecto03App.fTP.contrasena')">Contrasena</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'contrasena'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('usuario')">
              <span v-text="$t('proyecto03App.fTP.usuario')">Usuario</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'usuario'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('fechaMod')">
              <span v-text="$t('proyecto03App.fTP.fechaMod')">Fecha Mod</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'fechaMod'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('registinstit.id')">
              <span v-text="$t('proyecto03App.fTP.registinstit')">Registinstit</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'registinstit.id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('tipocfdi.id')">
              <span v-text="$t('proyecto03App.fTP.tipocfdi')">Tipocfdi</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'tipocfdi.id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('tiporecibo.id')">
              <span v-text="$t('proyecto03App.fTP.tiporecibo')">Tiporecibo</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'tiporecibo.id'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="fTP in fTPS" :key="fTP.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'FTPView', params: { fTPId: fTP.id } }">{{ fTP.id }}</router-link>
            </td>
            <td>{{ fTP.fecha }}</td>
            <td>{{ fTP.idInstit }}</td>
            <td>{{ fTP.nivel }}</td>
            <td>{{ fTP.clave }}</td>
            <td>{{ fTP.carpetaFTP }}</td>
            <td>{{ fTP.subcarpetaFTP }}</td>
            <td>{{ fTP.descripcionFTP }}</td>
            <td>{{ fTP.ipFTP }}</td>
            <td>{{ fTP.puerto }}</td>
            <td>{{ fTP.usuarioFTP }}</td>
            <td>{{ fTP.contrasena }}</td>
            <td>{{ fTP.usuario }}</td>
            <td>{{ fTP.fechaMod }}</td>
            <td>
              <div v-if="fTP.registinstit">
                <router-link :to="{ name: 'RegistInstitView', params: { registInstitId: fTP.registinstit.id } }">{{
                  fTP.registinstit.id
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="fTP.tipocfdi">
                <router-link :to="{ name: 'TipoCFDIView', params: { tipoCFDIId: fTP.tipocfdi.id } }">{{ fTP.tipocfdi.id }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="fTP.tiporecibo">
                <router-link :to="{ name: 'TipoReciboView', params: { tipoReciboId: fTP.tiporecibo.id } }">{{
                  fTP.tiporecibo.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'FTPView', params: { fTPId: fTP.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'FTPEdit', params: { fTPId: fTP.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(fTP)"
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
        ><span id="proyecto03App.fTP.delete.question" data-cy="fTPDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-fTP-heading" v-text="$t('proyecto03App.fTP.delete.question', { id: removeId })">
          Are you sure you want to delete this FTP?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-fTP"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeFTP()"
        >
          Delete
        </button>
      </div>
    </b-modal>
    <div v-show="fTPS && fTPS.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./ftp.component.ts"></script>
