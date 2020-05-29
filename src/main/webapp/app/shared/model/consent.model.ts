import { IPurpose } from 'app/shared/model/purpose.model';

export interface IConsent {
  id?: string;
  purposes?: IPurpose[];
}

export class Consent implements IConsent {
  constructor(public id?: string, public purposes?: IPurpose[]) {}
}
