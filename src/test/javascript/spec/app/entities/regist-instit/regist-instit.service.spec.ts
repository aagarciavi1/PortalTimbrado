/* tslint:disable max-line-length */
import axios from 'axios';
import sinon from 'sinon';
import dayjs from 'dayjs';

import { DATE_FORMAT } from '@/shared/date/filters';
import RegistInstitService from '@/entities/regist-instit/regist-instit.service';
import { RegistInstit } from '@/shared/model/regist-instit.model';
import { Estatus } from '@/shared/model/enumerations/estatus.model';

const error = {
  response: {
    status: null,
    data: {
      type: null,
    },
  },
};

const axiosStub = {
  get: sinon.stub(axios, 'get'),
  post: sinon.stub(axios, 'post'),
  put: sinon.stub(axios, 'put'),
  patch: sinon.stub(axios, 'patch'),
  delete: sinon.stub(axios, 'delete'),
};

describe('Service Tests', () => {
  describe('RegistInstit Service', () => {
    let service: RegistInstitService;
    let elemDefault;
    let currentDate: Date;

    beforeEach(() => {
      service = new RegistInstitService();
      currentDate = new Date();
      elemDefault = new RegistInstit(
        123,
        currentDate,
        0,
        Estatus.Activo,
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        0,
        'AAAAAAA',
        'AAAAAAA',
        0,
        0,
        'image/png',
        'AAAAAAA',
        'image/png',
        'AAAAAAA',
        'image/png',
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
        0,
        'AAAAAAA',
        'AAAAAAA',
        currentDate
      );
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            fecha: dayjs(currentDate).format(DATE_FORMAT),
            fechaExp: dayjs(currentDate).format(DATE_FORMAT),
            fechaMod: dayjs(currentDate).format(DATE_FORMAT),
          },
          elemDefault
        );
        axiosStub.get.resolves({ data: returnedFromService });

        return service.find(123).then(res => {
          expect(res).toMatchObject(elemDefault);
        });
      });

      it('should not find an element', async () => {
        axiosStub.get.rejects(error);
        return service
          .find(123)
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should create a RegistInstit', async () => {
        const returnedFromService = Object.assign(
          {
            id: 123,
            fecha: dayjs(currentDate).format(DATE_FORMAT),
            fechaExp: dayjs(currentDate).format(DATE_FORMAT),
            fechaMod: dayjs(currentDate).format(DATE_FORMAT),
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            fecha: currentDate,
            fechaExp: currentDate,
            fechaMod: currentDate,
          },
          returnedFromService
        );

        axiosStub.post.resolves({ data: returnedFromService });
        return service.create({}).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not create a RegistInstit', async () => {
        axiosStub.post.rejects(error);

        return service
          .create({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should update a RegistInstit', async () => {
        const returnedFromService = Object.assign(
          {
            fecha: dayjs(currentDate).format(DATE_FORMAT),
            idInstit: 1,
            estatus: 'BBBBBB',
            nivel: 1,
            razonSocial: 'BBBBBB',
            rFC: 'BBBBBB',
            pais: 'BBBBBB',
            municipio: 'BBBBBB',
            localidad: 'BBBBBB',
            codigoPostal: 1,
            colonia: 'BBBBBB',
            calle: 'BBBBBB',
            numExt: 1,
            numInt: 1,
            logo: 'BBBBBB',
            certificado: 'BBBBBB',
            llavePriv: 'BBBBBB',
            contrasena: 'BBBBBB',
            fechaExp: dayjs(currentDate).format(DATE_FORMAT),
            dias: 1,
            correo: 'BBBBBB',
            usuario: 'BBBBBB',
            fechaMod: dayjs(currentDate).format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fecha: currentDate,
            fechaExp: currentDate,
            fechaMod: currentDate,
          },
          returnedFromService
        );
        axiosStub.put.resolves({ data: returnedFromService });

        return service.update(expected).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not update a RegistInstit', async () => {
        axiosStub.put.rejects(error);

        return service
          .update({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should partial update a RegistInstit', async () => {
        const patchObject = Object.assign(
          {
            fecha: dayjs(currentDate).format(DATE_FORMAT),
            idInstit: 1,
            razonSocial: 'BBBBBB',
            pais: 'BBBBBB',
            colonia: 'BBBBBB',
            numInt: 1,
            logo: 'BBBBBB',
            certificado: 'BBBBBB',
            llavePriv: 'BBBBBB',
            correo: 'BBBBBB',
            usuario: 'BBBBBB',
            fechaMod: dayjs(currentDate).format(DATE_FORMAT),
          },
          new RegistInstit()
        );
        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            fecha: currentDate,
            fechaExp: currentDate,
            fechaMod: currentDate,
          },
          returnedFromService
        );
        axiosStub.patch.resolves({ data: returnedFromService });

        return service.partialUpdate(patchObject).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not partial update a RegistInstit', async () => {
        axiosStub.patch.rejects(error);

        return service
          .partialUpdate({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should return a list of RegistInstit', async () => {
        const returnedFromService = Object.assign(
          {
            fecha: dayjs(currentDate).format(DATE_FORMAT),
            idInstit: 1,
            estatus: 'BBBBBB',
            nivel: 1,
            razonSocial: 'BBBBBB',
            rFC: 'BBBBBB',
            pais: 'BBBBBB',
            municipio: 'BBBBBB',
            localidad: 'BBBBBB',
            codigoPostal: 1,
            colonia: 'BBBBBB',
            calle: 'BBBBBB',
            numExt: 1,
            numInt: 1,
            logo: 'BBBBBB',
            certificado: 'BBBBBB',
            llavePriv: 'BBBBBB',
            contrasena: 'BBBBBB',
            fechaExp: dayjs(currentDate).format(DATE_FORMAT),
            dias: 1,
            correo: 'BBBBBB',
            usuario: 'BBBBBB',
            fechaMod: dayjs(currentDate).format(DATE_FORMAT),
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            fecha: currentDate,
            fechaExp: currentDate,
            fechaMod: currentDate,
          },
          returnedFromService
        );
        axiosStub.get.resolves([returnedFromService]);
        return service.retrieve({ sort: {}, page: 0, size: 10 }).then(res => {
          expect(res).toContainEqual(expected);
        });
      });

      it('should not return a list of RegistInstit', async () => {
        axiosStub.get.rejects(error);

        return service
          .retrieve()
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should delete a RegistInstit', async () => {
        axiosStub.delete.resolves({ ok: true });
        return service.delete(123).then(res => {
          expect(res.ok).toBeTruthy();
        });
      });

      it('should not delete a RegistInstit', async () => {
        axiosStub.delete.rejects(error);

        return service
          .delete(123)
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });
    });
  });
});
