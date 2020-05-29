import { Moment } from 'moment';
import { IDelegate } from 'app/shared/model/delegate.model';
import { IValidation } from 'app/shared/model/validation.model';
import { IConsent } from 'app/shared/model/consent.model';
import { IProof } from 'app/shared/model/proof.model';
import { IClient } from 'app/shared/model/client.model';
import { Status } from 'app/shared/model/enumerations/status.model';

export interface IEvent {
  id?: string;
  organizationId?: string;
  createdAt?: Moment;
  status?: Status;
  delegate?: IDelegate;
  validation?: IValidation;
  consents?: IConsent;
  proofs?: IProof[];
  user?: IClient;
}

export class Event implements IEvent {
  constructor(
    public id?: string,
    public organizationId?: string,
    public createdAt?: Moment,
    public status?: Status,
    public delegate?: IDelegate,
    public validation?: IValidation,
    public consents?: IConsent,
    public proofs?: IProof[],
    public user?: IClient
  ) {}
}
