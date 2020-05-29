import { IPurpose } from 'app/shared/model/purpose.model';

export interface IPreference {
  id?: string;
  preferenceId?: string;
  enabled?: boolean;
  purpose?: IPurpose;
}

export class Preference implements IPreference {
  constructor(public id?: string, public preferenceId?: string, public enabled?: boolean, public purpose?: IPurpose) {
    this.enabled = this.enabled || false;
  }
}
