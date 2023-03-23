<template>
  <div>
    <h2 id="page-heading" data-cy="MotivosCancelHeading">
      <span v-text="$t('proyecto03App.motivosCancel.home.title')" id="motivos-cancel-heading">Motivos Cancels</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('proyecto03App.motivosCancel.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'MotivosCancelCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-motivos-cancel"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('proyecto03App.motivosCancel.home.createLabel')"> Create a new Motivos Cancel </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && motivosCancels && motivosCancels.length === 0">
      <span v-text="$t('proyecto03App.motivosCancel.home.notFound')">No motivosCancels found</span>
    </div>
    <div class="table-responsive" v-if="motivosCancels && motivosCancels.length > 0">
      <table class="table table-striped" aria-describedby="motivosCancels">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="$t('global.field.id')">ID</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('fecha')">
              <span v-text="$t('proyecto03App.motivosCancel.fecha')">Fecha</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'fecha'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('motivoCancel')">
              <span v-text="$t('proyecto03App.motivosCancel.motivoCancel')">Motivo Cancel</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'motivoCancel'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('estatus')">
              <span v-text="$t('proyecto03App.motivosCancel.estatus')">Estatus</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'estatus'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('usuario')">
              <span v-text="$t('proyecto03App.motivosCancel.usuario')">Usuario</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'usuario'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('fechaMod')">
              <span v-text="$t('proyecto03App.motivosCancel.fechaMod')">Fecha Mod</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'fechaMod'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="motivosCancel in motivosCancels" :key="motivosCancel.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'MotivosCancelView', params: { motivosCancelId: motivosCancel.id } }">{{
                motivosCancel.id
              }}</router-link>
            </td>
            <td>{{ motivosCancel.fecha }}</td>
            <td>{{ motivosCancel.motivoCancel }}</td>
            <td v-text="$t('proyecto03App.Estatus.' + motivosCancel.estatus)">{{ motivosCancel.estatus }}</td>
            <td>{{ motivosCancel.usuario }}</td>
            <td>{{ motivosCancel.fechaMod }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'MotivosCancelView', params: { motivosCancelId: motivosCancel.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link
                  :to="{ name: 'MotivosCancelEdit', params: { motivosCancelId: motivosCancel.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(motivosCancel)"
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
          id="proyecto03App.motivosCancel.delete.question"
          data-cy="motivosCancelDeleteDialogHeading"
          v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-motivosCancel-heading" v-text="$t('proyecto03App.motivosCancel.delete.question', { id: removeId })">
          Are you sure you want to delete this Motivos Cancel?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-motivosCancel"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeMotivosCancel()"
        >
          Delete
        </button>
      </div>
    </b-modal>
    <div v-show="motivosCancels && motivosCancels.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./motivos-cancel.component.ts"></script>
