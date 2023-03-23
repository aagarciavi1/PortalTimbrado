<template>
  <div>
    <h2 id="page-heading" data-cy="TipoCFDIHeading">
      <span v-text="$t('proyecto03App.tipoCFDI.home.title')" id="tipo-cfdi-heading">Tipo CFDIS</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('proyecto03App.tipoCFDI.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'TipoCFDICreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-tipo-cfdi"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('proyecto03App.tipoCFDI.home.createLabel')"> Create a new Tipo CFDI </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && tipoCFDIS && tipoCFDIS.length === 0">
      <span v-text="$t('proyecto03App.tipoCFDI.home.notFound')">No tipoCFDIS found</span>
    </div>
    <div class="table-responsive" v-if="tipoCFDIS && tipoCFDIS.length > 0">
      <table class="table table-striped" aria-describedby="tipoCFDIS">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="$t('global.field.id')">ID</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('fecha')">
              <span v-text="$t('proyecto03App.tipoCFDI.fecha')">Fecha</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'fecha'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('tipoCFDI')">
              <span v-text="$t('proyecto03App.tipoCFDI.tipoCFDI')">Tipo CFDI</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'tipoCFDI'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('estatus')">
              <span v-text="$t('proyecto03App.tipoCFDI.estatus')">Estatus</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'estatus'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('usuario')">
              <span v-text="$t('proyecto03App.tipoCFDI.usuario')">Usuario</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'usuario'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('fechaMod')">
              <span v-text="$t('proyecto03App.tipoCFDI.fechaMod')">Fecha Mod</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'fechaMod'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="tipoCFDI in tipoCFDIS" :key="tipoCFDI.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'TipoCFDIView', params: { tipoCFDIId: tipoCFDI.id } }">{{ tipoCFDI.id }}</router-link>
            </td>
            <td>{{ tipoCFDI.fecha }}</td>
            <td>{{ tipoCFDI.tipoCFDI }}</td>
            <td v-text="$t('proyecto03App.Estatus.' + tipoCFDI.estatus)">{{ tipoCFDI.estatus }}</td>
            <td>{{ tipoCFDI.usuario }}</td>
            <td>{{ tipoCFDI.fechaMod }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'TipoCFDIView', params: { tipoCFDIId: tipoCFDI.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'TipoCFDIEdit', params: { tipoCFDIId: tipoCFDI.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(tipoCFDI)"
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
        ><span id="proyecto03App.tipoCFDI.delete.question" data-cy="tipoCFDIDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-tipoCFDI-heading" v-text="$t('proyecto03App.tipoCFDI.delete.question', { id: removeId })">
          Are you sure you want to delete this Tipo CFDI?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-tipoCFDI"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeTipoCFDI()"
        >
          Delete
        </button>
      </div>
    </b-modal>
    <div v-show="tipoCFDIS && tipoCFDIS.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./tipo-cfdi.component.ts"></script>
