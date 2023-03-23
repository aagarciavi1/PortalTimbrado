<template>
  <div>
    <h2 id="page-heading" data-cy="ParametrosHeading">
      <span v-text="$t('proyecto03App.parametros.home.title')" id="parametros-heading">Parametros</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('proyecto03App.parametros.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'ParametrosCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-parametros"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('proyecto03App.parametros.home.createLabel')"> Create a new Parametros </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && parametros && parametros.length === 0">
      <span v-text="$t('proyecto03App.parametros.home.notFound')">No parametros found</span>
    </div>
    <div class="table-responsive" v-if="parametros && parametros.length > 0">
      <table class="table table-striped" aria-describedby="parametros">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="$t('global.field.id')">ID</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('fecha')">
              <span v-text="$t('proyecto03App.parametros.fecha')">Fecha</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'fecha'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('idInstit')">
              <span v-text="$t('proyecto03App.parametros.idInstit')">Id Instit</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'idInstit'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('nivel')">
              <span v-text="$t('proyecto03App.parametros.nivel')">Nivel</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'nivel'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('usuario')">
              <span v-text="$t('proyecto03App.parametros.usuario')">Usuario</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'usuario'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('fechaMod')">
              <span v-text="$t('proyecto03App.parametros.fechaMod')">Fecha Mod</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'fechaMod'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('registinstit.id')">
              <span v-text="$t('proyecto03App.parametros.registinstit')">Registinstit</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'registinstit.id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('tipocfdi.id')">
              <span v-text="$t('proyecto03App.parametros.tipocfdi')">Tipocfdi</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'tipocfdi.id'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="parametros in parametros" :key="parametros.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'ParametrosView', params: { parametrosId: parametros.id } }">{{ parametros.id }}</router-link>
            </td>
            <td>{{ parametros.fecha }}</td>
            <td>{{ parametros.idInstit }}</td>
            <td>{{ parametros.nivel }}</td>
            <td>{{ parametros.usuario }}</td>
            <td>{{ parametros.fechaMod }}</td>
            <td>
              <div v-if="parametros.registinstit">
                <router-link :to="{ name: 'RegistInstitView', params: { registInstitId: parametros.registinstit.id } }">{{
                  parametros.registinstit.id
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="parametros.tipocfdi">
                <router-link :to="{ name: 'TipoCFDIView', params: { tipoCFDIId: parametros.tipocfdi.id } }">{{
                  parametros.tipocfdi.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'ParametrosView', params: { parametrosId: parametros.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'ParametrosEdit', params: { parametrosId: parametros.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(parametros)"
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
        ><span id="proyecto03App.parametros.delete.question" data-cy="parametrosDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-parametros-heading" v-text="$t('proyecto03App.parametros.delete.question', { id: removeId })">
          Are you sure you want to delete this Parametros?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-parametros"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeParametros()"
        >
          Delete
        </button>
      </div>
    </b-modal>
    <div v-show="parametros && parametros.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./parametros.component.ts"></script>
