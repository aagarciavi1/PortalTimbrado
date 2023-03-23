<template>
  <div>
    <h2 id="page-heading" data-cy="FuncionalidadesHeading">
      <span v-text="$t('proyecto03App.funcionalidades.home.title')" id="funcionalidades-heading">Funcionalidades</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('proyecto03App.funcionalidades.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'FuncionalidadesCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-funcionalidades"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('proyecto03App.funcionalidades.home.createLabel')"> Create a new Funcionalidades </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && funcionalidades && funcionalidades.length === 0">
      <span v-text="$t('proyecto03App.funcionalidades.home.notFound')">No funcionalidades found</span>
    </div>
    <div class="table-responsive" v-if="funcionalidades && funcionalidades.length > 0">
      <table class="table table-striped" aria-describedby="funcionalidades">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="$t('global.field.id')">ID</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('fecha')">
              <span v-text="$t('proyecto03App.funcionalidades.fecha')">Fecha</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'fecha'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('perfil')">
              <span v-text="$t('proyecto03App.funcionalidades.perfil')">Perfil</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'perfil'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('funcionalidad')">
              <span v-text="$t('proyecto03App.funcionalidades.funcionalidad')">Funcionalidad</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'funcionalidad'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('alta')">
              <span v-text="$t('proyecto03App.funcionalidades.alta')">Alta</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'alta'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('editar')">
              <span v-text="$t('proyecto03App.funcionalidades.editar')">Editar</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'editar'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('activarInact')">
              <span v-text="$t('proyecto03App.funcionalidades.activarInact')">Activar Inact</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'activarInact'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('consulta')">
              <span v-text="$t('proyecto03App.funcionalidades.consulta')">Consulta</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'consulta'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('usuario')">
              <span v-text="$t('proyecto03App.funcionalidades.usuario')">Usuario</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'usuario'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('fechaMod')">
              <span v-text="$t('proyecto03App.funcionalidades.fechaMod')">Fecha Mod</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'fechaMod'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="funcionalidades in funcionalidades" :key="funcionalidades.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'FuncionalidadesView', params: { funcionalidadesId: funcionalidades.id } }">{{
                funcionalidades.id
              }}</router-link>
            </td>
            <td>{{ funcionalidades.fecha }}</td>
            <td>{{ funcionalidades.perfil }}</td>
            <td v-text="$t('proyecto03App.Funcionalidad.' + funcionalidades.funcionalidad)">{{ funcionalidades.funcionalidad }}</td>
            <td>{{ funcionalidades.alta }}</td>
            <td>{{ funcionalidades.editar }}</td>
            <td>{{ funcionalidades.activarInact }}</td>
            <td>{{ funcionalidades.consulta }}</td>
            <td>{{ funcionalidades.usuario }}</td>
            <td>{{ funcionalidades.fechaMod }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'FuncionalidadesView', params: { funcionalidadesId: funcionalidades.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link
                  :to="{ name: 'FuncionalidadesEdit', params: { funcionalidadesId: funcionalidades.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(funcionalidades)"
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
        ><span
          id="proyecto03App.funcionalidades.delete.question"
          data-cy="funcionalidadesDeleteDialogHeading"
          v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-funcionalidades-heading" v-text="$t('proyecto03App.funcionalidades.delete.question', { id: removeId })">
          Are you sure you want to delete this Funcionalidades?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-funcionalidades"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeFuncionalidades()"
        >
          Delete
        </button>
      </div>
    </b-modal>
    <div v-show="funcionalidades && funcionalidades.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./funcionalidades.component.ts"></script>
