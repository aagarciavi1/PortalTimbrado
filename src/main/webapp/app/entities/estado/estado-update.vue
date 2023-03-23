<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="proyecto03App.estado.home.createOrEditLabel"
          data-cy="EstadoCreateUpdateHeading"
          v-text="$t('proyecto03App.estado.home.createOrEditLabel')"
        >
          Create or edit a Estado
        </h2>
        <div>
          <div class="form-group" v-if="estado.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="estado.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proyecto03App.estado.fecha')" for="estado-fecha">Fecha</label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="estado-fecha"
                  v-model="$v.estado.fecha.$model"
                  name="fecha"
                  class="form-control"
                  :locale="currentLanguage"
                  button-only
                  today-button
                  reset-button
                  close-button
                >
                </b-form-datepicker>
              </b-input-group-prepend>
              <b-form-input
                id="estado-fecha"
                data-cy="fecha"
                type="text"
                class="form-control"
                name="fecha"
                :class="{ valid: !$v.estado.fecha.$invalid, invalid: $v.estado.fecha.$invalid }"
                v-model="$v.estado.fecha.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proyecto03App.estado.pais')" for="estado-pais">Pais</label>
            <input
              type="text"
              class="form-control"
              name="pais"
              id="estado-pais"
              data-cy="pais"
              :class="{ valid: !$v.estado.pais.$invalid, invalid: $v.estado.pais.$invalid }"
              v-model="$v.estado.pais.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proyecto03App.estado.estatdo')" for="estado-estatdo">Estatdo</label>
            <input
              type="text"
              class="form-control"
              name="estatdo"
              id="estado-estatdo"
              data-cy="estatdo"
              :class="{ valid: !$v.estado.estatdo.$invalid, invalid: $v.estado.estatdo.$invalid }"
              v-model="$v.estado.estatdo.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proyecto03App.estado.descEstado')" for="estado-descEstado">Desc Estado</label>
            <input
              type="text"
              class="form-control"
              name="descEstado"
              id="estado-descEstado"
              data-cy="descEstado"
              :class="{ valid: !$v.estado.descEstado.$invalid, invalid: $v.estado.descEstado.$invalid }"
              v-model="$v.estado.descEstado.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proyecto03App.estado.estatus')" for="estado-estatus">Estatus</label>
            <select
              class="form-control"
              name="estatus"
              :class="{ valid: !$v.estado.estatus.$invalid, invalid: $v.estado.estatus.$invalid }"
              v-model="$v.estado.estatus.$model"
              id="estado-estatus"
              data-cy="estatus"
            >
              <option
                v-for="estatus in estatusValues"
                :key="estatus"
                v-bind:value="estatus"
                v-bind:label="$t('proyecto03App.Estatus.' + estatus)"
              >
                {{ estatus }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proyecto03App.estado.usuario')" for="estado-usuario">Usuario</label>
            <input
              type="text"
              class="form-control"
              name="usuario"
              id="estado-usuario"
              data-cy="usuario"
              :class="{ valid: !$v.estado.usuario.$invalid, invalid: $v.estado.usuario.$invalid }"
              v-model="$v.estado.usuario.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proyecto03App.estado.fechaMod')" for="estado-fechaMod">Fecha Mod</label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="estado-fechaMod"
                  v-model="$v.estado.fechaMod.$model"
                  name="fechaMod"
                  class="form-control"
                  :locale="currentLanguage"
                  button-only
                  today-button
                  reset-button
                  close-button
                >
                </b-form-datepicker>
              </b-input-group-prepend>
              <b-form-input
                id="estado-fechaMod"
                data-cy="fechaMod"
                type="text"
                class="form-control"
                name="fechaMod"
                :class="{ valid: !$v.estado.fechaMod.$invalid, invalid: $v.estado.fechaMod.$invalid }"
                v-model="$v.estado.fechaMod.$model"
              />
            </b-input-group>
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="$v.estado.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./estado-update.component.ts"></script>
