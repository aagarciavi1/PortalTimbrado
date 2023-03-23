<template>
  <div>
    <h2 id="page-heading" data-cy="RepGraficaHeading">
      <span v-text="$t('proyecto03App.repGrafica.home.title')" id="rep-grafica-heading">Rep Graficas</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('proyecto03App.repGrafica.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'RepGraficaCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-rep-grafica"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('proyecto03App.repGrafica.home.createLabel')"> Create a new Rep Grafica </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && repGraficas && repGraficas.length === 0">
      <span v-text="$t('proyecto03App.repGrafica.home.notFound')">No repGraficas found</span>
    </div>
    <div class="table-responsive" v-if="repGraficas && repGraficas.length > 0">
      <table class="table table-striped" aria-describedby="repGraficas">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="$t('global.field.id')">ID</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('fecha')">
              <span v-text="$t('proyecto03App.repGrafica.fecha')">Fecha</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'fecha'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('repGrafica')">
              <span v-text="$t('proyecto03App.repGrafica.repGrafica')">Rep Grafica</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'repGrafica'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('estatus')">
              <span v-text="$t('proyecto03App.repGrafica.estatus')">Estatus</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'estatus'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('usuario')">
              <span v-text="$t('proyecto03App.repGrafica.usuario')">Usuario</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'usuario'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('fechaMod')">
              <span v-text="$t('proyecto03App.repGrafica.fechaMod')">Fecha Mod</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'fechaMod'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="repGrafica in repGraficas" :key="repGrafica.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'RepGraficaView', params: { repGraficaId: repGrafica.id } }">{{ repGrafica.id }}</router-link>
            </td>
            <td>{{ repGrafica.fecha }}</td>
            <td>{{ repGrafica.repGrafica }}</td>
            <td v-text="$t('proyecto03App.Estatus.' + repGrafica.estatus)">{{ repGrafica.estatus }}</td>
            <td>{{ repGrafica.usuario }}</td>
            <td>{{ repGrafica.fechaMod }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'RepGraficaView', params: { repGraficaId: repGrafica.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'RepGraficaEdit', params: { repGraficaId: repGrafica.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(repGrafica)"
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
        ><span id="proyecto03App.repGrafica.delete.question" data-cy="repGraficaDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-repGrafica-heading" v-text="$t('proyecto03App.repGrafica.delete.question', { id: removeId })">
          Are you sure you want to delete this Rep Grafica?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-repGrafica"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeRepGrafica()"
        >
          Delete
        </button>
      </div>
    </b-modal>
    <div v-show="repGraficas && repGraficas.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./rep-grafica.component.ts"></script>
