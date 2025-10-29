import { useEffect, useMemo, useState } from 'react';
import { Container, Row, Col, Table, Form, InputGroup, Button, Alert, Spinner } from 'react-bootstrap';
import { useDispatch, useSelector } from 'react-redux';
import { RootState } from '../store';
import { fetchCustomers } from '../store/slices/customersSlice';
import type { Customer } from '../types/domain';
import { Link } from 'react-router-dom';

type SortKey = 'ragioneSociale' | 'fatturatoAnnuale' | 'dataInserimento' | 'dataUltimoContatto' | 'stato';

export default function CustomersInvoice() {
  const dispatch = useDispatch();
  const { items, status, error } = useSelector((s: RootState) => s.customers);
  const [search, setSearch] = useState('');
  const [sortKey, setSortKey] = useState<SortKey>('ragioneSociale');
  const [sortDir, setSortDir] = useState<'asc' | 'desc'>('asc');

  useEffect(() => {
    // @ts-ignore
    dispatch(fetchCustomers());
  }, [dispatch]);

  const filtered = useMemo(() => {
    let data = items;
    if (search) {
      data = data.filter((c) => c.ragioneSociale.toLowerCase().includes(search.toLowerCase()));
    }
    const toValue = (c: Customer) => {
      switch (sortKey) {
        case 'ragioneSociale':
          return c.ragioneSociale.toLowerCase();
        case 'fatturatoAnnuale':
          return c.fatturatoAnnuale;
        case 'dataInserimento':
          return c.dataInserimento;
        case 'dataUltimoContatto':
          return c.dataUltimoContatto;
        case 'provincia':
          return c.indirizzi.find((i) => i.tipo === 'SEDE_LEGALE')?.provincia || '';
      }
    };
    const sorted = [...data].sort((a, b) => {
      const va = toValue(a);
      const vb = toValue(b);
      if (va < vb) return sortDir === 'asc' ? -1 : 1;
      if (va > vb) return sortDir === 'asc' ? 1 : -1;
      return 0;
    });
    return sorted;
  }, [items, search, sortKey, sortDir]);

  return (
    <Container className="py-4">
      <Row className="mb-3">
        <Col>
          <h3>Fatture</h3>
        </Col>
      </Row>
      <Row className="mb-3">
        <Col md={6}>
          <InputGroup>
            <InputGroup.Text>Ricerca</InputGroup.Text>
            <Form.Control placeholder="Cerca per numero fattura" value={search} onChange={(e) => setSearch(e.target.value)} />
          </InputGroup>
        </Col>
        <Col md={6}>
          <InputGroup>
            <InputGroup.Text>Ordina per</InputGroup.Text>
            <Form.Select value={sortKey} onChange={(e) => setSortKey(e.target.value as SortKey)}>
              <option value="ragioneSociale">Numero Fattura</option>
              <option value="ragioneSociale">Cliente</option>
              <option value="ragioneSociale">Stato Fattura</option>
              <option value="ragioneSociale">Anno Fattura</option>
              <option value="dataInserimento">Data emissione</option>
              <option value="fatturatoAnnuale">Importo</option>
            </Form.Select>
            <Button variant="secondary" onClick={() => setSortDir(sortDir === 'asc' ? 'desc' : 'asc')}>
              {sortDir === 'asc' ? 'Asc' : 'Disc'}
            </Button>
          </InputGroup>
        </Col>
      </Row>
      {status === 'loading' && (
        <div className="d-flex align-items-center gap-2"><Spinner size="sm" /> Caricamento...</div>
      )}
      {error && <Alert variant="danger">{error}</Alert>}
      <Row>
        <Col>
          <Table hover responsive>
            <thead>
              <tr>
                <th className='text-center'>Cliente</th>
                <th className='text-center'>Importo Fattura</th>
                <th className='text-center'>Data Emissione</th>
                <th className='text-center'>Stato</th>
              </tr>
            </thead>
            <tbody>
              {filtered.map((c) => (
                <tr key={c.id}>
                  <td className='text-center'>{c.ragioneSociale}</td>
                  <td className='text-center'>€ {c.fatturatoAnnuale.toLocaleString('it-IT')}</td>
                  <td className='text-center'>{new Date(c.dataUltimoContatto).toLocaleDateString('it-IT')}</td>
                  <td className='text-center'>{c.stato}</td>
                  <td className='text-center'>
                    <Button variant="success" className='mx-1' as={Link} to={`/invoice/${c.id}`} size="sm">Dettagli</Button>
                  
                    <Button variant="warning" className='mx-1'  size="sm">Modifica</Button>
                
                    <Button variant="danger" className='mx-1'  size="sm">Elimina</Button>
                  </td>
                </tr>
              ))}
              {!filtered.length && status === 'idle' && (
                <tr>
                  <td colSpan={7} className="text-center">Nessuna fattura trovata</td>
                </tr>
              )}
            </tbody>
          </Table>
        </Col>
      </Row>
    </Container>
  );
}