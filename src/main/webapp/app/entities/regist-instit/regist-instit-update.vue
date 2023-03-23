<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="proyecto03App.registInstit.home.createOrEditLabel"
          data-cy="RegistInstitCreateUpdateHeading"
          v-text="$t('proyecto03App.registInstit.home.createOrEditLabel')"
        >
          Create or edit a RegistInstit
        </h2>
        <div>
          <div class="form-group" v-if="registInstit.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="registInstit.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proyecto03App.registInstit.fecha')" for="regist-instit-fecha">Fecha</label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="regist-instit-fecha"
                  v-model="$v.registInstit.fecha.$model"
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
                id="regist-instit-fecha"
                data-cy="fecha"
                type="text"
                class="form-control"
                name="fecha"
                :class="{ valid: !$v.registInstit.fecha.$invalid, invalid: $v.registInstit.fecha.$invalid }"
                v-model="$v.registInstit.fecha.$model"
                required
              />
            </b-input-group>
            <div v-if="$v.registInstit.fecha.$anyDirty && $v.registInstit.fecha.$invalid">
              <small class="form-text text-danger" v-if="!$v.registInstit.fecha.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>



                <!--ID Institución y Estatus-->
                <div class="form-group">                      
                  <div class="container-ms">                          
                    <div class="row row-cols-1 row-cols-sm-2 row-cols-md-4">                                  
                      <div class="col"></div>
                      <div class="col"></div>
                      <div class="col">
                        <label class="form-control-label" v-text="$t('proyecto03App.registInstit.idInstit')" for="regist-instit-idInstit"
                          >Id Instit</label
                        >
                        <input
                          type="number"
                          class="form-control"
                          name="idInstit"
                          id="regist-instit-idInstit"
                          data-cy="idInstit"
                          :class="{ valid: !$v.registInstit.idInstit.$invalid, invalid: $v.registInstit.idInstit.$invalid }"
                          v-model.number="$v.registInstit.idInstit.$model"
                          required
                        />
                      </div> 
                        <div v-if="$v.registInstit.idInstit.$anyDirty && $v.registInstit.idInstit.$invalid">
                          <small class="form-text text-danger" v-if="!$v.registInstit.idInstit.required" v-text="$t('entity.validation.required')">
                            This field is required.
                          </small>
                          <small class="form-text text-danger" v-if="!$v.registInstit.idInstit.numeric" v-text="$t('entity.validation.number')">
                            This field should be a number.
                          </small>
                        </div>
                        <div class="col">
                          <label class="form-control-label" v-text="$t('proyecto03App.registInstit.estatus')" for="regist-instit-estatus">Estatus</label>
                          <select
                            class="form-control"
                            name="estatus"
                            :class="{ valid: !$v.registInstit.estatus.$invalid, invalid: $v.registInstit.estatus.$invalid }"
                            v-model="$v.registInstit.estatus.$model"
                            id="regist-instit-estatus"
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
                     </div>
                    </div>
                   </div>   
                   
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proyecto03App.registInstit.nivel')" for="regist-instit-nivel">Nivel</label>
            <input
              type="number"
              class="form-control"
              name="nivel"
              id="regist-instit-nivel"
              data-cy="nivel"
              :class="{ valid: !$v.registInstit.nivel.$invalid, invalid: $v.registInstit.nivel.$invalid }"
              v-model.number="$v.registInstit.nivel.$model"
              required
            />
            <div v-if="$v.registInstit.nivel.$anyDirty && $v.registInstit.nivel.$invalid">
              <small class="form-text text-danger" v-if="!$v.registInstit.nivel.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.registInstit.nivel.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proyecto03App.registInstit.razonSocial')" for="regist-instit-razonSocial"
              >Razon Social</label
            >
            <input
              type="text"
              class="form-control"
              name="razonSocial"
              id="regist-instit-razonSocial"
              data-cy="razonSocial"
              :class="{ valid: !$v.registInstit.razonSocial.$invalid, invalid: $v.registInstit.razonSocial.$invalid }"
              v-model="$v.registInstit.razonSocial.$model"
              required
            />
            <div v-if="$v.registInstit.razonSocial.$anyDirty && $v.registInstit.razonSocial.$invalid">
              <small class="form-text text-danger" v-if="!$v.registInstit.razonSocial.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proyecto03App.registInstit.rFC')" for="regist-instit-rFC">R FC</label>
            <input
              type="text"
              class="form-control"
              name="rFC"
              id="regist-instit-rFC"
              data-cy="rFC"
              :class="{ valid: !$v.registInstit.rFC.$invalid, invalid: $v.registInstit.rFC.$invalid }"
              v-model="$v.registInstit.rFC.$model"
              required
            />
            <div v-if="$v.registInstit.rFC.$anyDirty && $v.registInstit.rFC.$invalid">
              <small class="form-text text-danger" v-if="!$v.registInstit.rFC.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proyecto03App.registInstit.pais')" for="regist-instit-pais">Pais</label>
            <input
              type="text"
              class="form-control"
              name="pais"
              id="regist-instit-pais"
              data-cy="pais"
              :class="{ valid: !$v.registInstit.pais.$invalid, invalid: $v.registInstit.pais.$invalid }"
              v-model="$v.registInstit.pais.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proyecto03App.registInstit.municipio')" for="regist-instit-municipio"
              >Municipio</label
            >
            <input
              type="text"
              class="form-control"
              name="municipio"
              id="regist-instit-municipio"
              data-cy="municipio"
              :class="{ valid: !$v.registInstit.municipio.$invalid, invalid: $v.registInstit.municipio.$invalid }"
              v-model="$v.registInstit.municipio.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proyecto03App.registInstit.localidad')" for="regist-instit-localidad"
              >Localidad</label
            >
            <input
              type="text"
              class="form-control"
              name="localidad"
              id="regist-instit-localidad"
              data-cy="localidad"
              :class="{ valid: !$v.registInstit.localidad.$invalid, invalid: $v.registInstit.localidad.$invalid }"
              v-model="$v.registInstit.localidad.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proyecto03App.registInstit.codigoPostal')" for="regist-instit-codigoPostal"
              >Codigo Postal</label
            >
            <input
              type="number"
              class="form-control"
              name="codigoPostal"
              id="regist-instit-codigoPostal"
              data-cy="codigoPostal"
              :class="{ valid: !$v.registInstit.codigoPostal.$invalid, invalid: $v.registInstit.codigoPostal.$invalid }"
              v-model.number="$v.registInstit.codigoPostal.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proyecto03App.registInstit.colonia')" for="regist-instit-colonia">Colonia</label>
            <input
              type="text"
              class="form-control"
              name="colonia"
              id="regist-instit-colonia"
              data-cy="colonia"
              :class="{ valid: !$v.registInstit.colonia.$invalid, invalid: $v.registInstit.colonia.$invalid }"
              v-model="$v.registInstit.colonia.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proyecto03App.registInstit.calle')" for="regist-instit-calle">Calle</label>
            <input
              type="text"
              class="form-control"
              name="calle"
              id="regist-instit-calle"
              data-cy="calle"
              :class="{ valid: !$v.registInstit.calle.$invalid, invalid: $v.registInstit.calle.$invalid }"
              v-model="$v.registInstit.calle.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proyecto03App.registInstit.numExt')" for="regist-instit-numExt">Num Ext</label>
            <input
              type="number"
              class="form-control"
              name="numExt"
              id="regist-instit-numExt"
              data-cy="numExt"
              :class="{ valid: !$v.registInstit.numExt.$invalid, invalid: $v.registInstit.numExt.$invalid }"
              v-model.number="$v.registInstit.numExt.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proyecto03App.registInstit.numInt')" for="regist-instit-numInt">Num Int</label>
            <input
              type="number"
              class="form-control"
              name="numInt"
              id="regist-instit-numInt"
              data-cy="numInt"
              :class="{ valid: !$v.registInstit.numInt.$invalid, invalid: $v.registInstit.numInt.$invalid }"
              v-model.number="$v.registInstit.numInt.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proyecto03App.registInstit.logo')" for="regist-instit-logo">Logo</label>
            <div>
              <div v-if="registInstit.logo" class="form-text text-danger clearfix">
                <a
                  class="pull-left"
                  v-on:click="openFile(registInstit.logoContentType, registInstit.logo)"
                  v-text="$t('entity.action.open')"
                  >open</a
                ><br />
                <span class="pull-left">{{ registInstit.logoContentType }}, {{ byteSize(registInstit.logo) }}</span>
                <button
                  type="button"
                  v-on:click="
                    registInstit.logo = null;
                    registInstit.logoContentType = null;
                  "
                  class="btn btn-secondary btn-xs pull-right"
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                </button>
              </div>
              <input
                type="file"
                ref="file_logo"
                id="file_logo"
                data-cy="logo"
                v-on:change="setFileData($event, registInstit, 'logo', false)"
                v-text="$t('entity.action.addblob')"
              />
            </div>
            <input
              type="hidden"
              class="form-control"
              name="logo"
              id="regist-instit-logo"
              data-cy="logo"
              :class="{ valid: !$v.registInstit.logo.$invalid, invalid: $v.registInstit.logo.$invalid }"
              v-model="$v.registInstit.logo.$model"
            />
            <input
              type="hidden"
              class="form-control"
              name="logoContentType"
              id="regist-instit-logoContentType"
              v-model="registInstit.logoContentType"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proyecto03App.registInstit.certificado')" for="regist-instit-certificado"
              >Certificado</label
            >
            <div>
              <div v-if="registInstit.certificado" class="form-text text-danger clearfix">
                <a
                  class="pull-left"
                  v-on:click="openFile(registInstit.certificadoContentType, registInstit.certificado)"
                  v-text="$t('entity.action.open')"
                  >open</a
                ><br />
                <span class="pull-left">{{ registInstit.certificadoContentType }}, {{ byteSize(registInstit.certificado) }}</span>
                <button
                  type="button"
                  v-on:click="
                    registInstit.certificado = null;
                    registInstit.certificadoContentType = null;
                  "
                  class="btn btn-secondary btn-xs pull-right"
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                </button>
              </div>
              <input
                type="file"
                ref="file_certificado"
                id="file_certificado"
                data-cy="certificado"
                v-on:change="setFileData($event, registInstit, 'certificado', false)"
                v-text="$t('entity.action.addblob')"
              />
            </div>
            <input
              type="hidden"
              class="form-control"
              name="certificado"
              id="regist-instit-certificado"
              data-cy="certificado"
              :class="{ valid: !$v.registInstit.certificado.$invalid, invalid: $v.registInstit.certificado.$invalid }"
              v-model="$v.registInstit.certificado.$model"
            />
            <input
              type="hidden"
              class="form-control"
              name="certificadoContentType"
              id="regist-instit-certificadoContentType"
              v-model="registInstit.certificadoContentType"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proyecto03App.registInstit.llavePriv')" for="regist-instit-llavePriv"
              >Llave Priv</label
            >
            <div>
              <div v-if="registInstit.llavePriv" class="form-text text-danger clearfix">
                <a
                  class="pull-left"
                  v-on:click="openFile(registInstit.llavePrivContentType, registInstit.llavePriv)"
                  v-text="$t('entity.action.open')"
                  >open</a
                ><br />
                <span class="pull-left">{{ registInstit.llavePrivContentType }}, {{ byteSize(registInstit.llavePriv) }}</span>
                <button
                  type="button"
                  v-on:click="
                    registInstit.llavePriv = null;
                    registInstit.llavePrivContentType = null;
                  "
                  class="btn btn-secondary btn-xs pull-right"
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                </button>
              </div>
              <input
                type="file"
                ref="file_llavePriv"
                id="file_llavePriv"
                data-cy="llavePriv"
                v-on:change="setFileData($event, registInstit, 'llavePriv', false)"
                v-text="$t('entity.action.addblob')"
              />
            </div>
            <input
              type="hidden"
              class="form-control"
              name="llavePriv"
              id="regist-instit-llavePriv"
              data-cy="llavePriv"
              :class="{ valid: !$v.registInstit.llavePriv.$invalid, invalid: $v.registInstit.llavePriv.$invalid }"
              v-model="$v.registInstit.llavePriv.$model"
            />
            <input
              type="hidden"
              class="form-control"
              name="llavePrivContentType"
              id="regist-instit-llavePrivContentType"
              v-model="registInstit.llavePrivContentType"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proyecto03App.registInstit.contrasena')" for="regist-instit-contrasena"
              >Contrasena</label
            >
            <input
              type="text"
              class="form-control"
              name="contrasena"
              id="regist-instit-contrasena"
              data-cy="contrasena"
              :class="{ valid: !$v.registInstit.contrasena.$invalid, invalid: $v.registInstit.contrasena.$invalid }"
              v-model="$v.registInstit.contrasena.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proyecto03App.registInstit.fechaExp')" for="regist-instit-fechaExp"
              >Fecha Exp</label
            >
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="regist-instit-fechaExp"
                  v-model="$v.registInstit.fechaExp.$model"
                  name="fechaExp"
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
                id="regist-instit-fechaExp"
                data-cy="fechaExp"
                type="text"
                class="form-control"
                name="fechaExp"
                :class="{ valid: !$v.registInstit.fechaExp.$invalid, invalid: $v.registInstit.fechaExp.$invalid }"
                v-model="$v.registInstit.fechaExp.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proyecto03App.registInstit.dias')" for="regist-instit-dias">Dias</label>
            <input
              type="number"
              class="form-control"
              name="dias"
              id="regist-instit-dias"
              data-cy="dias"
              :class="{ valid: !$v.registInstit.dias.$invalid, invalid: $v.registInstit.dias.$invalid }"
              v-model.number="$v.registInstit.dias.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proyecto03App.registInstit.correo')" for="regist-instit-correo">Correo</label>
            <input
              type="text"
              class="form-control"
              name="correo"
              id="regist-instit-correo"
              data-cy="correo"
              :class="{ valid: !$v.registInstit.correo.$invalid, invalid: $v.registInstit.correo.$invalid }"
              v-model="$v.registInstit.correo.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proyecto03App.registInstit.usuario')" for="regist-instit-usuario">Usuario</label>
            <input
              type="text"
              class="form-control"
              name="usuario"
              id="regist-instit-usuario"
              data-cy="usuario"
              :class="{ valid: !$v.registInstit.usuario.$invalid, invalid: $v.registInstit.usuario.$invalid }"
              v-model="$v.registInstit.usuario.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proyecto03App.registInstit.fechaMod')" for="regist-instit-fechaMod"
              >Fecha Mod</label
            >
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="regist-instit-fechaMod"
                  v-model="$v.registInstit.fechaMod.$model"
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
                id="regist-instit-fechaMod"
                data-cy="fechaMod"
                type="text"
                class="form-control"
                name="fechaMod"
                :class="{ valid: !$v.registInstit.fechaMod.$invalid, invalid: $v.registInstit.fechaMod.$invalid }"
                v-model="$v.registInstit.fechaMod.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proyecto03App.registInstit.regimenfis')" for="regist-instit-regimenfis"
              >Regimenfis</label
            >
            <select
              class="form-control"
              id="regist-instit-regimenfis"
              data-cy="regimenfis"
              name="regimenfis"
              v-model="registInstit.regimenfis"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  registInstit.regimenfis && regimenFisOption.id === registInstit.regimenfis.id ? registInstit.regimenfis : regimenFisOption
                "
                v-for="regimenFisOption in regimenFis"
                :key="regimenFisOption.id"
              >
                {{ regimenFisOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proyecto03App.registInstit.estado')" for="regist-instit-estado">Estado</label>
            <select class="form-control" id="regist-instit-estado" data-cy="estado" name="estado" v-model="registInstit.estado">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="registInstit.estado && estadoOption.id === registInstit.estado.id ? registInstit.estado : estadoOption"
                v-for="estadoOption in estados"
                :key="estadoOption.id"
              >
                {{ estadoOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proyecto03App.registInstit.registinstit')" for="regist-instit-registinstit"
              >Registinstit</label
            >
            <select
              class="form-control"
              id="regist-instit-registinstit"
              data-cy="registinstit"
              name="registinstit"
              v-model="registInstit.registinstit"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  registInstit.registinstit && registInstitOption.id === registInstit.registinstit.id
                    ? registInstit.registinstit
                    : registInstitOption
                "
                v-for="registInstitOption in registInstits"
                :key="registInstitOption.id"
              >
                {{ registInstitOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proyecto03App.registInstit.registinstiti')" for="regist-instit-registinstiti"
              >Registinstiti</label
            >
            <select
              class="form-control"
              id="regist-instit-registinstiti"
              data-cy="registinstiti"
              name="registinstiti"
              v-model="registInstit.registinstiti"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  registInstit.registinstiti && registInstitOption.id === registInstit.registinstiti.id
                    ? registInstit.registinstiti
                    : registInstitOption
                "
                v-for="registInstitOption in registInstits"
                :key="registInstitOption.id"
              >
                {{ registInstitOption.id }}
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
            :disabled="$v.registInstit.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./regist-instit-update.component.ts"></script>
