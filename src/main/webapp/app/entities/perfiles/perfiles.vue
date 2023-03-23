<template>
  <div>
    <h2 id="page-heading" data-cy="PerfilesHeading">
      <span v-text="$t('proyecto03App.perfiles.home.title')" id="perfiles-heading">Perfiles</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('proyecto03App.perfiles.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'PerfilesCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-perfiles"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('proyecto03App.perfiles.home.createLabel')"> Create a new Perfiles </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && perfiles && perfiles.length === 0">
      <span v-text="$t('proyecto03App.perfiles.home.notFound')">No perfiles found</span>
    </div>
    <div class="table-responsive" v-if="perfiles && perfiles.length > 0">
      <table class="table table-striped" aria-describedby="perfiles">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="$t('global.field.id')">ID</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('fecha')">
              <span v-text="$t('proyecto03App.perfiles.fecha')">Fecha</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'fecha'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('perfil')">
              <span v-text="$t('proyecto03App.perfiles.perfil')">Perfil</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'perfil'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('usuario')">
              <span v-text="$t('proyecto03App.perfiles.usuario')">Usuario</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'usuario'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('fechaMod')">
              <span v-text="$t('proyecto03App.perfiles.fechaMod')">Fecha Mod</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'fechaMod'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('funcionalidades.id')">
              <span v-text="$t('proyecto03App.perfiles.funcionalidades')">Funcionalidades</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'funcionalidades.id'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="perfiles in perfiles" :key="perfiles.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'PerfilesView', params: { perfilesId: perfiles.id } }">{{ perfiles.id }}</router-link>
            </td>
            <td>{{ perfiles.fecha }}</td>
            <td>{{ perfiles.perfil }}</td>
            <td>{{ perfiles.usuario }}</td>
            <td>{{ perfiles.fechaMod }}</td>
            <td>
              <div v-if="perfiles.funcionalidades">
                <router-link :to="{ name: 'FuncionalidadesView', params: { funcionalidadesId: perfiles.funcionalidades.id } }">{{
                  perfiles.funcionalidades.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'PerfilesView', params: { perfilesId: perfiles.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'PerfilesEdit', params: { perfilesId: perfiles.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(perfiles)"
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
        ><span id="proyecto03App.perfiles.delete.question" data-cy="perfilesDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-perfiles-heading" v-text="$t('proyecto03App.perfiles.delete.question', { id: removeId })">
          Are you sure you want to delete this Perfiles?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-perfiles"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removePerfiles()"
        >
          Delete
        </button>
      </div>
    </b-modal>
    <div v-show="perfiles && perfiles.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./perfiles.component.ts"></script>
