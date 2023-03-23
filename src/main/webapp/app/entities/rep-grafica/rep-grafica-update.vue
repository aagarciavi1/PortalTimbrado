<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="proyecto03App.repGrafica.home.createOrEditLabel"
          data-cy="RepGraficaCreateUpdateHeading"
          v-text="$t('proyecto03App.repGrafica.home.createOrEditLabel')"
        >
          Create or edit a RepGrafica
        </h2>
        <div>
          <div class="form-group" v-if="repGrafica.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="repGrafica.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proyecto03App.repGrafica.fecha')" for="rep-grafica-fecha">Fecha</label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="rep-grafica-fecha"
                  v-model="$v.repGrafica.fecha.$model"
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
                id="rep-grafica-fecha"
                data-cy="fecha"
                type="text"
                class="form-control"
                name="fecha"
                :class="{ valid: !$v.repGrafica.fecha.$invalid, invalid: $v.repGrafica.fecha.$invalid }"
                v-model="$v.repGrafica.fecha.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proyecto03App.repGrafica.repGrafica')" for="rep-grafica-repGrafica"
              >Rep Grafica</label
            >
            <input
              type="text"
              class="form-control"
              name="repGrafica"
              id="rep-grafica-repGrafica"
              data-cy="repGrafica"
              :class="{ valid: !$v.repGrafica.repGrafica.$invalid, invalid: $v.repGrafica.repGrafica.$invalid }"
              v-model="$v.repGrafica.repGrafica.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proyecto03App.repGrafica.estatus')" for="rep-grafica-estatus">Estatus</label>
            <select
              class="form-control"
              name="estatus"
              :class="{ valid: !$v.repGrafica.estatus.$invalid, invalid: $v.repGrafica.estatus.$invalid }"
              v-model="$v.repGrafica.estatus.$model"
              id="rep-grafica-estatus"
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
            <label class="form-control-label" v-text="$t('proyecto03App.repGrafica.usuario')" for="rep-grafica-usuario">Usuario</label>
            <input
              type="text"
              class="form-control"
              name="usuario"
              id="rep-grafica-usuario"
              data-cy="usuario"
              :class="{ valid: !$v.repGrafica.usuario.$invalid, invalid: $v.repGrafica.usuario.$invalid }"
              v-model="$v.repGrafica.usuario.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proyecto03App.repGrafica.fechaMod')" for="rep-grafica-fechaMod">Fecha Mod</label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="rep-grafica-fechaMod"
                  v-model="$v.repGrafica.fechaMod.$model"
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
                id="rep-grafica-fechaMod"
                data-cy="fechaMod"
                type="text"
                class="form-control"
                name="fechaMod"
                :class="{ valid: !$v.repGrafica.fechaMod.$invalid, invalid: $v.repGrafica.fechaMod.$invalid }"
                v-model="$v.repGrafica.fechaMod.$model"
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
            :disabled="$v.repGrafica.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./rep-grafica-update.component.ts"></script>
