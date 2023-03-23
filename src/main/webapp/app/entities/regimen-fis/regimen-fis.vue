<template>
  <div>
    <h2 id="page-heading" data-cy="RegimenFisHeading">
      <span v-text="$t('proyecto03App.regimenFis.home.title')" id="regimen-fis-heading">Regimen Fis</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('proyecto03App.regimenFis.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'RegimenFisCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-regimen-fis"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('proyecto03App.regimenFis.home.createLabel')"> Create a new Regimen Fis </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && regimenFis && regimenFis.length === 0">
      <span v-text="$t('proyecto03App.regimenFis.home.notFound')">No regimenFis found</span>
    </div>
    <div class="table-responsive" v-if="regimenFis && regimenFis.length > 0">
      <table class="table table-striped" aria-describedby="regimenFis">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="$t('global.field.id')">ID</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('fecha')">
              <span v-text="$t('proyecto03App.regimenFis.fecha')">Fecha</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'fecha'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('regimenF')">
              <span v-text="$t('proyecto03App.regimenFis.regimenF')">Regimen F</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'regimenF'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('estatus')">
              <span v-text="$t('proyecto03App.regimenFis.estatus')">Estatus</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'estatus'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('usuario')">
              <span v-text="$t('proyecto03App.regimenFis.usuario')">Usuario</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'usuario'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('fechaMod')">
              <span v-text="$t('proyecto03App.regimenFis.fechaMod')">Fecha Mod</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'fechaMod'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="regimenFis in regimenFis" :key="regimenFis.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'RegimenFisView', params: { regimenFisId: regimenFis.id } }">{{ regimenFis.id }}</router-link>
            </td>
            <td>{{ regimenFis.fecha }}</td>
            <td>{{ regimenFis.regimenF }}</td>
            <td v-text="$t('proyecto03App.Estatus.' + regimenFis.estatus)">{{ regimenFis.estatus }}</td>
            <td>{{ regimenFis.usuario }}</td>
            <td>{{ regimenFis.fechaMod }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'RegimenFisView', params: { regimenFisId: regimenFis.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'RegimenFisEdit', params: { regimenFisId: regimenFis.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(regimenFis)"
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
        ><span id="proyecto03App.regimenFis.delete.question" data-cy="regimenFisDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-regimenFis-heading" v-text="$t('proyecto03App.regimenFis.delete.question', { id: removeId })">
          Are you sure you want to delete this Regimen Fis?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-regimenFis"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeRegimenFis()"
        >
          Delete
        </button>
      </div>
    </b-modal>
    <div v-show="regimenFis && regimenFis.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./regimen-fis.component.ts"></script>
