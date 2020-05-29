export interface IDelegate {
  id?: string;
  delegateId?: string;
  name?: string;
}

export class Delegate implements IDelegate {
  constructor(public id?: string, public delegateId?: string, public name?: string) {}
}
