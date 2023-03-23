<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="proyecto03App.tipoCFDI.home.createOrEditLabel"
          data-cy="TipoCFDICreateUpdateHeading"
          v-text="$t('proyecto03App.tipoCFDI.home.createOrEditLabel')"
        >
          Create or edit a TipoCFDI
        </h2>
        <div>
          <div class="form-group" v-if="tipoCFDI.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="tipoCFDI.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proyecto03App.tipoCFDI.fecha')" for="tipo-cfdi-fecha">Fecha</label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="tipo-cfdi-fecha"
                  v-model="$v.tipoCFDI.fecha.$model"
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
                id="tipo-cfdi-fecha"
                data-cy="fecha"
                type="text"
                class="form-control"
                name="fecha"
                :class="{ valid: !$v.tipoCFDI.fecha.$invalid, invalid: $v.tipoCFDI.fecha.$invalid }"
                v-model="$v.tipoCFDI.fecha.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proyecto03App.tipoCFDI.tipoCFDI')" for="tipo-cfdi-tipoCFDI">Tipo CFDI</label>
            <input
              type="text"
              class="form-control"
              name="tipoCFDI"
              id="tipo-cfdi-tipoCFDI"
              data-cy="tipoCFDI"
              :class="{ valid: !$v.tipoCFDI.tipoCFDI.$invalid, invalid: $v.tipoCFDI.tipoCFDI.$invalid }"
              v-model="$v.tipoCFDI.tipoCFDI.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proyecto03App.tipoCFDI.estatus')" for="tipo-cfdi-estatus">Estatus</label>
            <select
              class="form-control"
              name="estatus"
              :class="{ valid: !$v.tipoCFDI.estatus.$invalid, invalid: $v.tipoCFDI.estatus.$invalid }"
              v-model="$v.tipoCFDI.estatus.$model"
              id="tipo-cfdi-estatus"
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
            <label class="form-control-label" v-text="$t('proyecto03App.tipoCFDI.usuario')" for="tipo-cfdi-usuario">Usuario</label>
            <input
              type="text"
              class="form-control"
              name="usuario"
              id="tipo-cfdi-usuario"
              data-cy="usuario"
              :class="{ valid: !$v.tipoCFDI.usuario.$invalid, invalid: $v.tipoCFDI.usuario.$invalid }"
              v-model="$v.tipoCFDI.usuario.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proyecto03App.tipoCFDI.fechaMod')" for="tipo-cfdi-fechaMod">Fecha Mod</label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="tipo-cfdi-fechaMod"
                  v-model="$v.tipoCFDI.fechaMod.$model"
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
                id="tipo-cfdi-fechaMod"
                data-cy="fechaMod"
                type="text"
                class="form-control"
                name="fechaMod"
                :class="{ valid: !$v.tipoCFDI.fechaMod.$invalid, invalid: $v.tipoCFDI.fechaMod.$invalid }"
                v-model="$v.tipoCFDI.fechaMod.$model"
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
            :disabled="$v.tipoCFDI.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./tipo-cfdi-update.component.ts"></script>
