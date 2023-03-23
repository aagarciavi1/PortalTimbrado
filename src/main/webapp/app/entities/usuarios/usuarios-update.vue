<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="proyecto03App.usuarios.home.createOrEditLabel"
          data-cy="UsuariosCreateUpdateHeading"
          v-text="$t('proyecto03App.usuarios.home.createOrEditLabel')"
        >
          Create or edit a Usuarios
        </h2>
        <div>
          <div class="form-group" v-if="usuarios.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="usuarios.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proyecto03App.usuarios.fecha')" for="usuarios-fecha">Fecha</label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="usuarios-fecha"
                  v-model="$v.usuarios.fecha.$model"
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
                id="usuarios-fecha"
                data-cy="fecha"
                type="text"
                class="form-control"
                name="fecha"
                :class="{ valid: !$v.usuarios.fecha.$invalid, invalid: $v.usuarios.fecha.$invalid }"
                v-model="$v.usuarios.fecha.$model"
                required
              />
            </b-input-group>
            <div v-if="$v.usuarios.fecha.$anyDirty && $v.usuarios.fecha.$invalid">
              <small class="form-text text-danger" v-if="!$v.usuarios.fecha.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proyecto03App.usuarios.usuario')" for="usuarios-usuario">Usuario</label>
            <input
              type="text"
              class="form-control"
              name="usuario"
              id="usuarios-usuario"
              data-cy="usuario"
              :class="{ valid: !$v.usuarios.usuario.$invalid, invalid: $v.usuarios.usuario.$invalid }"
              v-model="$v.usuarios.usuario.$model"
              required
            />
            <div v-if="$v.usuarios.usuario.$anyDirty && $v.usuarios.usuario.$invalid">
              <small class="form-text text-danger" v-if="!$v.usuarios.usuario.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proyecto03App.usuarios.descr')" for="usuarios-descr">Descr</label>
            <input
              type="text"
              class="form-control"
              name="descr"
              id="usuarios-descr"
              data-cy="descr"
              :class="{ valid: !$v.usuarios.descr.$invalid, invalid: $v.usuarios.descr.$invalid }"
              v-model="$v.usuarios.descr.$model"
              required
            />
            <div v-if="$v.usuarios.descr.$anyDirty && $v.usuarios.descr.$invalid">
              <small class="form-text text-danger" v-if="!$v.usuarios.descr.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proyecto03App.usuarios.idInstit')" for="usuarios-idInstit">Id Instit</label>
            <input
              type="number"
              class="form-control"
              name="idInstit"
              id="usuarios-idInstit"
              data-cy="idInstit"
              :class="{ valid: !$v.usuarios.idInstit.$invalid, invalid: $v.usuarios.idInstit.$invalid }"
              v-model.number="$v.usuarios.idInstit.$model"
              required
            />
            <div v-if="$v.usuarios.idInstit.$anyDirty && $v.usuarios.idInstit.$invalid">
              <small class="form-text text-danger" v-if="!$v.usuarios.idInstit.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.usuarios.idInstit.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proyecto03App.usuarios.correo')" for="usuarios-correo">Correo</label>
            <input
              type="text"
              class="form-control"
              name="correo"
              id="usuarios-correo"
              data-cy="correo"
              :class="{ valid: !$v.usuarios.correo.$invalid, invalid: $v.usuarios.correo.$invalid }"
              v-model="$v.usuarios.correo.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proyecto03App.usuarios.contrasena')" for="usuarios-contrasena">Contrasena</label>
            <input
              type="text"
              class="form-control"
              name="contrasena"
              id="usuarios-contrasena"
              data-cy="contrasena"
              :class="{ valid: !$v.usuarios.contrasena.$invalid, invalid: $v.usuarios.contrasena.$invalid }"
              v-model="$v.usuarios.contrasena.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proyecto03App.usuarios.confirmarContras')" for="usuarios-confirmarContras"
              >Confirmar Contras</label
            >
            <input
              type="text"
              class="form-control"
              name="confirmarContras"
              id="usuarios-confirmarContras"
              data-cy="confirmarContras"
              :class="{ valid: !$v.usuarios.confirmarContras.$invalid, invalid: $v.usuarios.confirmarContras.$invalid }"
              v-model="$v.usuarios.confirmarContras.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proyecto03App.usuarios.estatus')" for="usuarios-estatus">Estatus</label>
            <select
              class="form-control"
              name="estatus"
              :class="{ valid: !$v.usuarios.estatus.$invalid, invalid: $v.usuarios.estatus.$invalid }"
              v-model="$v.usuarios.estatus.$model"
              id="usuarios-estatus"
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
            <label class="form-control-label" v-text="$t('proyecto03App.usuarios.usuarioCrea')" for="usuarios-usuarioCrea"
              >Usuario Crea</label
            >
            <input
              type="text"
              class="form-control"
              name="usuarioCrea"
              id="usuarios-usuarioCrea"
              data-cy="usuarioCrea"
              :class="{ valid: !$v.usuarios.usuarioCrea.$invalid, invalid: $v.usuarios.usuarioCrea.$invalid }"
              v-model="$v.usuarios.usuarioCrea.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proyecto03App.usuarios.registinstit')" for="usuarios-registinstit"
              >Registinstit</label
            >
            <select
              class="form-control"
              id="usuarios-registinstit"
              data-cy="registinstit"
              name="registinstit"
              v-model="usuarios.registinstit"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  usuarios.registinstit && registInstitOption.id === usuarios.registinstit.id ? usuarios.registinstit : registInstitOption
                "
                v-for="registInstitOption in registInstits"
                :key="registInstitOption.id"
              >
                {{ registInstitOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proyecto03App.usuarios.perfil')" for="usuarios-perfil">Perfil</label>
            <select class="form-control" id="usuarios-perfil" data-cy="perfil" name="perfil" v-model="usuarios.perfil">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="usuarios.perfil && perfilesOption.id === usuarios.perfil.id ? usuarios.perfil : perfilesOption"
                v-for="perfilesOption in perfiles"
                :key="perfilesOption.id"
              >
                {{ perfilesOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proyecto03App.usuarios.tipocfdi')" for="usuarios-tipocfdi">Tipocfdi</label>
            <select class="form-control" id="usuarios-tipocfdi" data-cy="tipocfdi" name="tipocfdi" v-model="usuarios.tipocfdi">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="usuarios.tipocfdi && tipoCFDIOption.id === usuarios.tipocfdi.id ? usuarios.tipocfdi : tipoCFDIOption"
                v-for="tipoCFDIOption in tipoCFDIS"
                :key="tipoCFDIOption.id"
              >
                {{ tipoCFDIOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proyecto03App.usuarios.tiporecibo')" for="usuarios-tiporecibo">Tiporecibo</label>
            <select class="form-control" id="usuarios-tiporecibo" data-cy="tiporecibo" name="tiporecibo" v-model="usuarios.tiporecibo">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  usuarios.tiporecibo && tipoReciboOption.id === usuarios.tiporecibo.id ? usuarios.tiporecibo : tipoReciboOption
                "
                v-for="tipoReciboOption in tipoRecibos"
                :key="tipoReciboOption.id"
              >
                {{ tipoReciboOption.id }}
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
            :disabled="$v.usuarios.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./usuarios-update.component.ts"></script>
