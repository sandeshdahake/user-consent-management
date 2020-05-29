export interface IValidation {
  id?: string;
  type?: string;
}

export class Validation implements IValidation {
  constructor(public id?: string, public type?: string) {}
}
