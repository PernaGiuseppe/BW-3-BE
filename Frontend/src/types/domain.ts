export type CustomerType = 'PA' | 'SAS' | 'SPA' | 'SRL';

export interface Address {
  id?: number;
  via: string;
  civico: string;
  localita: string;
  cap: string;
  comune: string;
  provincia?: string;
  tipo?: 'SEDE_LEGALE' | 'SEDE_OPERATIVA';
}

export interface InvoiceStatus {
  id: number;
  name: string;
}

export interface Invoice {
  id: number;
  dataEmissione: string; // ISO date
  importo: number;
  numero: string;
  stato: InvoiceStatus;
  clienteId: number;
}

export interface Customer {
  id: number;
  ragioneSociale: string;
  partitaIva: string;
  email: string;
  dataInserimento: string; // ISO date
  dataUltimoContatto: string; // ISO date
  fatturatoAnnuale: number;
  pec?: string;
  telefono?: string;
  emailContatto?: string;
  nomeContatto?: string;
  cognomeContatto?: string;
  telefonoContatto?: string;
  logoAziendale?: string; // URL
  tipo: CustomerType;
  indirizzi: Address[];
}

export type Role = 'USER' | 'ADMIN';

export interface User {
  id?: number;
  username: string;
  email: string;
  nome?: string;
  cognome?: string;
  avatar?: string;
  roles: Role[];
}