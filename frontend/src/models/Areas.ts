export interface Municipality {
  municipalityId: number;
  name: String;
}

export interface Constituency {
  constituencyId: number;
  name: String;
}

export interface Areas {
  constituencies: Constituency[];
  municipalities: Municipality[];
}

export interface SearchbarArea {
  value: number;
  label: String;
}