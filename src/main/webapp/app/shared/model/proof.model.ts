import { IEvent } from 'app/shared/model/event.model';

export interface IProof {
  id?: string;
  filename?: string;
  fileContentType?: string;
  file?: any;
  event?: IEvent;
}

export class Proof implements IProof {
  constructor(public id?: string, public filename?: string, public fileContentType?: string, public file?: any, public event?: IEvent) {}
}
