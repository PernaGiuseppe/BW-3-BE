import type { Customer, Invoice, InvoiceStatus } from '../types/domain';

export const demoInvoiceStatuses: InvoiceStatus[] = [
  { id: 1, name: 'PAGATA' },
  { id: 2, name: 'EMESSA' },
  { id: 3, name: 'SCADUTA' },
];

export const demoCustomers: Customer[] = [
  {
    id: 1,
    ragioneSociale: 'Alpha S.R.L.',
    partitaIva: 'IT12345678901',
    email: 'info@alpha.example',
    dataInserimento: new Date('2023-01-15').toISOString(),
    dataUltimoContatto: new Date('2024-10-01').toISOString(),
    fatturatoAnnuale: 1200000,
    pec: 'alpha@pec.example',
    telefono: '+39 011 123456',
    emailContatto: 'contatti@alpha.example',
    nomeContatto: 'Luca',
    cognomeContatto: 'Rossi',
    telefonoContatto: '+39 320 1112233',
    logoAziendale: '',
    tipo: 'SRL',
    indirizzi: [
      {
        via: 'Via Roma',
        civico: '10',
        localita: 'Centro',
        cap: '10100',
        comune: 'Torino',
        provincia: 'TO',
        tipo: 'SEDE_LEGALE',
      },
      {
        via: 'Corso Francia',
        civico: '200',
        localita: 'Pozzo Strada',
        cap: '10141',
        comune: 'Torino',
        provincia: 'TO',
        tipo: 'SEDE_OPERATIVA',
      },
    ],
  },
  {
    id: 2,
    ragioneSociale: 'Beta S.P.A.',
    partitaIva: 'IT98765432109',
    email: 'contact@beta.example',
    dataInserimento: new Date('2022-07-20').toISOString(),
    dataUltimoContatto: new Date('2024-09-10').toISOString(),
    fatturatoAnnuale: 5200000,
    pec: 'beta@pec.example',
    telefono: '+39 02 998877',
    emailContatto: 'sales@beta.example',
    nomeContatto: 'Giulia',
    cognomeContatto: 'Bianchi',
    telefonoContatto: '+39 333 4445566',
    logoAziendale: '',
    tipo: 'SPA',
    indirizzi: [
      {
        via: 'Via Dante',
        civico: '25',
        localita: 'Centro',
        cap: '20121',
        comune: 'Milano',
        provincia: 'MI',
        tipo: 'SEDE_LEGALE',
      },
    ],
  },
];

export const demoInvoices: Record<number, Invoice[]> = {
  1: [
    {
      id: 101,
      numero: 'ALP-2024-0001',
      data: new Date('2024-01-31').toISOString(),
      importo: 2500,
      stato: demoInvoiceStatuses[0],
      clienteId: 1,
    },
    {
      id: 102,
      numero: 'ALP-2024-0002',
      data: new Date('2024-06-30').toISOString(),
      importo: 4100,
      stato: demoInvoiceStatuses[1],
      clienteId: 1,
    },
  ],
  2: [
    {
      id: 201,
      numero: 'BET-2024-0001',
      data: new Date('2024-03-15').toISOString(),
      importo: 15000,
      stato: demoInvoiceStatuses[2],
      clienteId: 2,
    },
  ],
};

export const getMockCustomer = (id: number) => demoCustomers.find((c) => c.id === id) || null;
export const getMockInvoices = (customerId: number) => demoInvoices[customerId] || [];