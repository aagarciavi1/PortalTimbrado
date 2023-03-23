<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="proyecto03App.funcionalidades.home.createOrEditLabel"
          data-cy="FuncionalidadesCreateUpdateHeading"
          v-text="$t('proyecto03App.funcionalidades.home.createOrEditLabel')"
        >
          Create or edit a Funcionalidades
        </h2>
        <div>
          <div class="form-group" v-if="funcionalidades.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="funcionalidades.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proyecto03App.funcionalidades.fecha')" for="funcionalidades-fecha">Fecha</label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="funcionalidades-fecha"
                  v-model="$v.funcionalidades.fecha.$model"
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
                id="funcionalidades-fecha"
                data-cy="fecha"
                type="text"
                class="form-control"
                name="fecha"
                :class="{ valid: !$v.funcionalidades.fecha.$invalid, invalid: $v.funcionalidades.fecha.$invalid }"
                v-model="$v.funcionalidades.fecha.$model"
                required
              />
            </b-input-group>
            <div v-if="$v.funcionalidades.fecha.$anyDirty && $v.funcionalidades.fecha.$invalid">
              <small class="form-text text-danger" v-if="!$v.funcionalidades.fecha.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proyecto03App.funcionalidades.perfil')" for="funcionalidades-perfil"
              >Perfil</label
            >
            <input
              type="text"
              class="form-control"
              name="perfil"
              id="funcionalidades-perfil"
              data-cy="perfil"
              :class="{ valid: !$v.funcionalidades.perfil.$invalid, invalid: $v.funcionalidades.perfil.$invalid }"
              v-model="$v.funcionalidades.perfil.$model"
              required
            />
            <div v-if="$v.funcionalidades.perfil.$anyDirty && $v.funcionalidades.perfil.$invalid">
              <small class="form-text text-danger" v-if="!$v.funcionalidades.perfil.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proyecto03App.funcionalidades.funcionalidad')" for="funcionalidades-funcionalidad"
              >Funcionalidad</label
            >
            <select
              class="form-control"
              name="funcionalidad"
              :class="{ valid: !$v.funcionalidades.funcionalidad.$invalid, invalid: $v.funcionalidades.funcionalidad.$invalid }"
              v-model="$v.funcionalidades.funcionalidad.$model"
              id="funcionalidades-funcionalidad"
              data-cy="funcionalidad"
            >
              <option
                v-for="funcionalidad in funcionalidadValues"
                :key="funcionalidad"
                v-bind:value="funcionalidad"
                v-bind:label="$t('proyecto03App.Funcionalidad.' + funcionalidad)"
              >
                {{ funcionalidad }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proyecto03App.funcionalidades.alta')" for="funcionalidades-alta">Alta</label>
            <input
              type="text"
              class="form-control"
              name="alta"
              id="funcionalidades-alta"
              data-cy="alta"
              :class="{ valid: !$v.funcionalidades.alta.$invalid, invalid: $v.funcionalidades.alta.$invalid }"
              v-model="$v.funcionalidades.alta.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proyecto03App.funcionalidades.editar')" for="funcionalidades-editar"
              >Editar</label
            >
            <input
              type="text"
              class="form-control"
              name="editar"
              id="funcionalidades-editar"
              data-cy="editar"
              :class="{ valid: !$v.funcionalidades.editar.$invalid, invalid: $v.funcionalidades.editar.$invalid }"
              v-model="$v.funcionalidades.editar.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proyecto03App.funcionalidades.activarInact')" for="funcionalidades-activarInact"
              >Activar Inact</label
            >
            <input
              type="text"
              class="form-control"
              name="activarInact"
              id="funcionalidades-activarInact"
              data-cy="activarInact"
              :class="{ valid: !$v.funcionalidades.activarInact.$invalid, invalid: $v.funcionalidades.activarInact.$invalid }"
              v-model="$v.funcionalidades.activarInact.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proyecto03App.funcionalidades.consulta')" for="funcionalidades-consulta"
              >Consulta</label
            >
            <input
              type="text"
              class="form-control"
              name="consulta"
              id="funcionalidades-consulta"
              data-cy="consulta"
              :class="{ valid: !$v.funcionalidades.consulta.$invalid, invalid: $v.funcionalidades.consulta.$invalid }"
              v-model="$v.funcionalidades.consulta.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proyecto03App.funcionalidades.usuario')" for="funcionalidades-usuario"
              >Usuario</label
            >
            <input
              type="text"
              class="form-control"
              name="usuario"
              id="funcionalidades-usuario"
              data-cy="usuario"
              :class="{ valid: !$v.funcionalidades.usuario.$invalid, invalid: $v.funcionalidades.usuario.$invalid }"
              v-model="$v.funcionalidades.usuario.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proyecto03App.funcionalidades.fechaMod')" for="funcionalidades-fechaMod"
              >Fecha Mod</label
            >
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="funcionalidades-fechaMod"
                  v-model="$v.funcionalidades.fechaMod.$model"
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
                id="funcionalidades-fechaMod"
                data-cy="fechaMod"
                type="text"
                class="form-control"
                name="fechaMod"
                :class="{ valid: !$v.funcionalidades.fechaMod.$invalid, invalid: $v.funcionalidades.fechaMod.$invalid }"
                v-model="$v.funcionalidades.fechaMod.$model"
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
            :disabled="$v.funcionalidades.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./funcionalidades-update.component.ts"></script>
