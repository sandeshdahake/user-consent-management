import { IPreference } from 'app/shared/model/preference.model';
import { IConsent } from 'app/shared/model/consent.model';

export interface IPurpose {
  id?: string;
  purposeId?: string;
  enabled?: boolean;
  preferences?: IPreference[];
  consent?: IConsent;
}

export class Purpose implements IPurpose {
  constructor(
    public id?: string,
    public purposeId?: string,
    public enabled?: boolean,
    public preferences?: IPreference[],
    public consent?: IConsent
  ) {
    this.enabled = this.enabled || false;
  }
}
