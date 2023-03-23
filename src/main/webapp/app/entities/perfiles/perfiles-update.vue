<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="proyecto03App.perfiles.home.createOrEditLabel"
          data-cy="PerfilesCreateUpdateHeading"
          v-text="$t('proyecto03App.perfiles.home.createOrEditLabel')"
        >
          Create or edit a Perfiles
        </h2>
        <div>
          <div class="form-group" v-if="perfiles.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="perfiles.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proyecto03App.perfiles.fecha')" for="perfiles-fecha">Fecha</label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="perfiles-fecha"
                  v-model="$v.perfiles.fecha.$model"
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
                id="perfiles-fecha"
                data-cy="fecha"
                type="text"
                class="form-control"
                name="fecha"
                :class="{ valid: !$v.perfiles.fecha.$invalid, invalid: $v.perfiles.fecha.$invalid }"
                v-model="$v.perfiles.fecha.$model"
                required
              />
            </b-input-group>
            <div v-if="$v.perfiles.fecha.$anyDirty && $v.perfiles.fecha.$invalid">
              <small class="form-text text-danger" v-if="!$v.perfiles.fecha.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proyecto03App.perfiles.perfil')" for="perfiles-perfil">Perfil</label>
            <input
              type="text"
              class="form-control"
              name="perfil"
              id="perfiles-perfil"
              data-cy="perfil"
              :class="{ valid: !$v.perfiles.perfil.$invalid, invalid: $v.perfiles.perfil.$invalid }"
              v-model="$v.perfiles.perfil.$model"
              required
            />
            <div v-if="$v.perfiles.perfil.$anyDirty && $v.perfiles.perfil.$invalid">
              <small class="form-text text-danger" v-if="!$v.perfiles.perfil.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proyecto03App.perfiles.usuario')" for="perfiles-usuario">Usuario</label>
            <input
              type="text"
              class="form-control"
              name="usuario"
              id="perfiles-usuario"
              data-cy="usuario"
              :class="{ valid: !$v.perfiles.usuario.$invalid, invalid: $v.perfiles.usuario.$invalid }"
              v-model="$v.perfiles.usuario.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proyecto03App.perfiles.fechaMod')" for="perfiles-fechaMod">Fecha Mod</label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="perfiles-fechaMod"
                  v-model="$v.perfiles.fechaMod.$model"
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
                id="perfiles-fechaMod"
                data-cy="fechaMod"
                type="text"
                class="form-control"
                name="fechaMod"
                :class="{ valid: !$v.perfiles.fechaMod.$invalid, invalid: $v.perfiles.fechaMod.$invalid }"
                v-model="$v.perfiles.fechaMod.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proyecto03App.perfiles.funcionalidades')" for="perfiles-funcionalidades"
              >Funcionalidades</label
            >
            <select
              class="form-control"
              id="perfiles-funcionalidades"
              data-cy="funcionalidades"
              name="funcionalidades"
              v-model="perfiles.funcionalidades"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  perfiles.funcionalidades && funcionalidadesOption.id === perfiles.funcionalidades.id
                    ? perfiles.funcionalidades
                    : funcionalidadesOption
                "
                v-for="funcionalidadesOption in funcionalidades"
                :key="funcionalidadesOption.id"
              >
                {{ funcionalidadesOption.id }}
              </option>
            </select>
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
            :disabled="$v.perfiles.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./perfiles-update.component.ts"></script>
