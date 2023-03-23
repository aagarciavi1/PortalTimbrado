<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="proyecto03App.fTP.home.createOrEditLabel"
          data-cy="FTPCreateUpdateHeading"
          v-text="$t('proyecto03App.fTP.home.createOrEditLabel')"
        >
          Create or edit a FTP
        </h2>
        <div>
          <div class="form-group" v-if="fTP.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="fTP.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proyecto03App.fTP.fecha')" for="ftp-fecha">Fecha</label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="ftp-fecha"
                  v-model="$v.fTP.fecha.$model"
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
                id="ftp-fecha"
                data-cy="fecha"
                type="text"
                class="form-control"
                name="fecha"
                :class="{ valid: !$v.fTP.fecha.$invalid, invalid: $v.fTP.fecha.$invalid }"
                v-model="$v.fTP.fecha.$model"
                required
              />
            </b-input-group>
            <div v-if="$v.fTP.fecha.$anyDirty && $v.fTP.fecha.$invalid">
              <small class="form-text text-danger" v-if="!$v.fTP.fecha.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proyecto03App.fTP.idInstit')" for="ftp-idInstit">Id Instit</label>
            <input
              type="number"
              class="form-control"
              name="idInstit"
              id="ftp-idInstit"
              data-cy="idInstit"
              :class="{ valid: !$v.fTP.idInstit.$invalid, invalid: $v.fTP.idInstit.$invalid }"
              v-model.number="$v.fTP.idInstit.$model"
              required
            />
            <div v-if="$v.fTP.idInstit.$anyDirty && $v.fTP.idInstit.$invalid">
              <small class="form-text text-danger" v-if="!$v.fTP.idInstit.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.fTP.idInstit.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proyecto03App.fTP.nivel')" for="ftp-nivel">Nivel</label>
            <input
              type="number"
              class="form-control"
              name="nivel"
              id="ftp-nivel"
              data-cy="nivel"
              :class="{ valid: !$v.fTP.nivel.$invalid, invalid: $v.fTP.nivel.$invalid }"
              v-model.number="$v.fTP.nivel.$model"
              required
            />
            <div v-if="$v.fTP.nivel.$anyDirty && $v.fTP.nivel.$invalid">
              <small class="form-text text-danger" v-if="!$v.fTP.nivel.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.fTP.nivel.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proyecto03App.fTP.clave')" for="ftp-clave">Clave</label>
            <input
              type="text"
              class="form-control"
              name="clave"
              id="ftp-clave"
              data-cy="clave"
              :class="{ valid: !$v.fTP.clave.$invalid, invalid: $v.fTP.clave.$invalid }"
              v-model="$v.fTP.clave.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proyecto03App.fTP.carpetaFTP')" for="ftp-carpetaFTP">Carpeta FTP</label>
            <input
              type="text"
              class="form-control"
              name="carpetaFTP"
              id="ftp-carpetaFTP"
              data-cy="carpetaFTP"
              :class="{ valid: !$v.fTP.carpetaFTP.$invalid, invalid: $v.fTP.carpetaFTP.$invalid }"
              v-model="$v.fTP.carpetaFTP.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proyecto03App.fTP.subcarpetaFTP')" for="ftp-subcarpetaFTP">Subcarpeta FTP</label>
            <input
              type="text"
              class="form-control"
              name="subcarpetaFTP"
              id="ftp-subcarpetaFTP"
              data-cy="subcarpetaFTP"
              :class="{ valid: !$v.fTP.subcarpetaFTP.$invalid, invalid: $v.fTP.subcarpetaFTP.$invalid }"
              v-model="$v.fTP.subcarpetaFTP.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proyecto03App.fTP.descripcionFTP')" for="ftp-descripcionFTP"
              >Descripcion FTP</label
            >
            <input
              type="text"
              class="form-control"
              name="descripcionFTP"
              id="ftp-descripcionFTP"
              data-cy="descripcionFTP"
              :class="{ valid: !$v.fTP.descripcionFTP.$invalid, invalid: $v.fTP.descripcionFTP.$invalid }"
              v-model="$v.fTP.descripcionFTP.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proyecto03App.fTP.ipFTP')" for="ftp-ipFTP">Ip FTP</label>
            <input
              type="text"
              class="form-control"
              name="ipFTP"
              id="ftp-ipFTP"
              data-cy="ipFTP"
              :class="{ valid: !$v.fTP.ipFTP.$invalid, invalid: $v.fTP.ipFTP.$invalid }"
              v-model="$v.fTP.ipFTP.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proyecto03App.fTP.puerto')" for="ftp-puerto">Puerto</label>
            <input
              type="number"
              class="form-control"
              name="puerto"
              id="ftp-puerto"
              data-cy="puerto"
              :class="{ valid: !$v.fTP.puerto.$invalid, invalid: $v.fTP.puerto.$invalid }"
              v-model.number="$v.fTP.puerto.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proyecto03App.fTP.usuarioFTP')" for="ftp-usuarioFTP">Usuario FTP</label>
            <input
              type="text"
              class="form-control"
              name="usuarioFTP"
              id="ftp-usuarioFTP"
              data-cy="usuarioFTP"
              :class="{ valid: !$v.fTP.usuarioFTP.$invalid, invalid: $v.fTP.usuarioFTP.$invalid }"
              v-model="$v.fTP.usuarioFTP.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proyecto03App.fTP.contrasena')" for="ftp-contrasena">Contrasena</label>
            <input
              type="text"
              class="form-control"
              name="contrasena"
              id="ftp-contrasena"
              data-cy="contrasena"
              :class="{ valid: !$v.fTP.contrasena.$invalid, invalid: $v.fTP.contrasena.$invalid }"
              v-model="$v.fTP.contrasena.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proyecto03App.fTP.usuario')" for="ftp-usuario">Usuario</label>
            <input
              type="text"
              class="form-control"
              name="usuario"
              id="ftp-usuario"
              data-cy="usuario"
              :class="{ valid: !$v.fTP.usuario.$invalid, invalid: $v.fTP.usuario.$invalid }"
              v-model="$v.fTP.usuario.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proyecto03App.fTP.fechaMod')" for="ftp-fechaMod">Fecha Mod</label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="ftp-fechaMod"
                  v-model="$v.fTP.fechaMod.$model"
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
                id="ftp-fechaMod"
                data-cy="fechaMod"
                type="text"
                class="form-control"
                name="fechaMod"
                :class="{ valid: !$v.fTP.fechaMod.$invalid, invalid: $v.fTP.fechaMod.$invalid }"
                v-model="$v.fTP.fechaMod.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proyecto03App.fTP.registinstit')" for="ftp-registinstit">Registinstit</label>
            <select class="form-control" id="ftp-registinstit" data-cy="registinstit" name="registinstit" v-model="fTP.registinstit">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="fTP.registinstit && registInstitOption.id === fTP.registinstit.id ? fTP.registinstit : registInstitOption"
                v-for="registInstitOption in registInstits"
                :key="registInstitOption.id"
              >
                {{ registInstitOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proyecto03App.fTP.tipocfdi')" for="ftp-tipocfdi">Tipocfdi</label>
            <select class="form-control" id="ftp-tipocfdi" data-cy="tipocfdi" name="tipocfdi" v-model="fTP.tipocfdi">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="fTP.tipocfdi && tipoCFDIOption.id === fTP.tipocfdi.id ? fTP.tipocfdi : tipoCFDIOption"
                v-for="tipoCFDIOption in tipoCFDIS"
                :key="tipoCFDIOption.id"
              >
                {{ tipoCFDIOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proyecto03App.fTP.tiporecibo')" for="ftp-tiporecibo">Tiporecibo</label>
            <select class="form-control" id="ftp-tiporecibo" data-cy="tiporecibo" name="tiporecibo" v-model="fTP.tiporecibo">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="fTP.tiporecibo && tipoReciboOption.id === fTP.tiporecibo.id ? fTP.tiporecibo : tipoReciboOption"
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
            :disabled="$v.fTP.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./ftp-update.component.ts"></script>
