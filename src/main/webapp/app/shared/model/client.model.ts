import { Moment } from 'moment';
import { IConsent } from 'app/shared/model/consent.model';
import { IEvent } from 'app/shared/model/event.model';

export interface IClient {
  id?: string;
  organizationUserId?: string;
  version?: number;
  createdAt?: Moment;
  updatedAt?: Moment;
  customerId?: string;
  country?: string;
  consents?: IConsent;
  events?: IEvent[];
}

export class Client implements IClient {
  constructor(
    public id?: string,
    public organizationUserId?: string,
    public version?: number,
    public createdAt?: Moment,
    public updatedAt?: Moment,
    public customerId?: string,
    public country?: string,
    public consents?: IConsent,
    public events?: IEvent[]
  ) {}
}
