<template>
  <div>
    <h2 id="page-heading" data-cy="EstatusCFDIHeading">
      <span v-text="$t('proyecto03App.estatusCFDI.home.title')" id="estatus-cfdi-heading">Estatus CFDIS</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('proyecto03App.estatusCFDI.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'EstatusCFDICreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-estatus-cfdi"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('proyecto03App.estatusCFDI.home.createLabel')"> Create a new Estatus CFDI </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && estatusCFDIS && estatusCFDIS.length === 0">
      <span v-text="$t('proyecto03App.estatusCFDI.home.notFound')">No estatusCFDIS found</span>
    </div>
    <div class="table-responsive" v-if="estatusCFDIS && estatusCFDIS.length > 0">
      <table class="table table-striped" aria-describedby="estatusCFDIS">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="$t('global.field.id')">ID</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('fecha')">
              <span v-text="$t('proyecto03App.estatusCFDI.fecha')">Fecha</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'fecha'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('descEstCFDI')">
              <span v-text="$t('proyecto03App.estatusCFDI.descEstCFDI')">Desc Est CFDI</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'descEstCFDI'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('estatus')">
              <span v-text="$t('proyecto03App.estatusCFDI.estatus')">Estatus</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'estatus'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('usuario')">
              <span v-text="$t('proyecto03App.estatusCFDI.usuario')">Usuario</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'usuario'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('fechaMod')">
              <span v-text="$t('proyecto03App.estatusCFDI.fechaMod')">Fecha Mod</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'fechaMod'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="estatusCFDI in estatusCFDIS" :key="estatusCFDI.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'EstatusCFDIView', params: { estatusCFDIId: estatusCFDI.id } }">{{ estatusCFDI.id }}</router-link>
            </td>
            <td>{{ estatusCFDI.fecha }}</td>
            <td>{{ estatusCFDI.descEstCFDI }}</td>
            <td v-text="$t('proyecto03App.Estatus.' + estatusCFDI.estatus)">{{ estatusCFDI.estatus }}</td>
            <td>{{ estatusCFDI.usuario }}</td>
            <td>{{ estatusCFDI.fechaMod }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'EstatusCFDIView', params: { estatusCFDIId: estatusCFDI.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'EstatusCFDIEdit', params: { estatusCFDIId: estatusCFDI.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(estatusCFDI)"
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
        ><span id="proyecto03App.estatusCFDI.delete.question" data-cy="estatusCFDIDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-estatusCFDI-heading" v-text="$t('proyecto03App.estatusCFDI.delete.question', { id: removeId })">
          Are you sure you want to delete this Estatus CFDI?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-estatusCFDI"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeEstatusCFDI()"
        >
          Delete
        </button>
      </div>
    </b-modal>
    <div v-show="estatusCFDIS && estatusCFDIS.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./estatus-cfdi.component.ts"></script>
