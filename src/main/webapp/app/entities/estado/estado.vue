<template>
  <div>
    <h2 id="page-heading" data-cy="EstadoHeading">
      <span v-text="$t('proyecto03App.estado.home.title')" id="estado-heading">Estados</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('proyecto03App.estado.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'EstadoCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-estado"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('proyecto03App.estado.home.createLabel')"> Create a new Estado </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && estados && estados.length === 0">
      <span v-text="$t('proyecto03App.estado.home.notFound')">No estados found</span>
    </div>
    <div class="table-responsive" v-if="estados && estados.length > 0">
      <table class="table table-striped" aria-describedby="estados">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="$t('global.field.id')">ID</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('fecha')">
              <span v-text="$t('proyecto03App.estado.fecha')">Fecha</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'fecha'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('pais')">
              <span v-text="$t('proyecto03App.estado.pais')">Pais</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'pais'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('estatdo')">
              <span v-text="$t('proyecto03App.estado.estatdo')">Estatdo</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'estatdo'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('descEstado')">
              <span v-text="$t('proyecto03App.estado.descEstado')">Desc Estado</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'descEstado'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('estatus')">
              <span v-text="$t('proyecto03App.estado.estatus')">Estatus</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'estatus'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('usuario')">
              <span v-text="$t('proyecto03App.estado.usuario')">Usuario</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'usuario'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('fechaMod')">
              <span v-text="$t('proyecto03App.estado.fechaMod')">Fecha Mod</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'fechaMod'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="estado in estados" :key="estado.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'EstadoView', params: { estadoId: estado.id } }">{{ estado.id }}</router-link>
            </td>
            <td>{{ estado.fecha }}</td>
            <td>{{ estado.pais }}</td>
            <td>{{ estado.estatdo }}</td>
            <td>{{ estado.descEstado }}</td>
            <td v-text="$t('proyecto03App.Estatus.' + estado.estatus)">{{ estado.estatus }}</td>
            <td>{{ estado.usuario }}</td>
            <td>{{ estado.fechaMod }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'EstadoView', params: { estadoId: estado.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'EstadoEdit', params: { estadoId: estado.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(estado)"
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
        ><span id="proyecto03App.estado.delete.question" data-cy="estadoDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-estado-heading" v-text="$t('proyecto03App.estado.delete.question', { id: removeId })">
          Are you sure you want to delete this Estado?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-estado"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeEstado()"
        >
          Delete
        </button>
      </div>
    </b-modal>
    <div v-show="estados && estados.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./estado.component.ts"></script>
