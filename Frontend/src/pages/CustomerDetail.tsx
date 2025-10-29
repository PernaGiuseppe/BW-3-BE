import { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { Container, Row, Col, Card, Table, Button, Alert } from 'react-bootstrap';
import { api } from '../api/client';
import type { Customer, Invoice } from '../types/domain';
import { USE_MOCKS } from '../config';
import { getMockCustomer, getMockInvoices } from '../mocks/data';

export default function CustomerDetail() {
  const { id } = useParams();
  const [customer, setCustomer] = useState<Customer | null>(null);
  const [invoices, setInvoices] = useState<Invoice[]>([]);

  useEffect(() => {
    const load = async () => {
      try {
        if (USE_MOCKS) {
          const cid = Number(id);
          setCustomer(getMockCustomer(cid));
          setInvoices(getMockInvoices(cid));
          return;
        }
        const { data } = await api.get(`/customers/${id}`);
        setCustomer(data);
        const inv = await api.get(`/customers/${id}/invoices`);
        setInvoices(inv.data);
      } catch (e) {
        // silently ignore in skeleton
      }
    };
    load();
  }, [id]);

  const sendEmail = () => {
    if (customer?.emailContatto) {
      window.location.href = `mailto:${customer.emailContatto}`;
    }
  };

  return (
    <Container className="py-4">
      <Row className="mb-3">
        <Col>
          <h3>Dettaglio Cliente</h3>
        </Col>
      </Row>

      {!customer && (
        <Alert variant="warning">Cliente non trovato.</Alert>
      )}

      {customer && (
        <Row className="mb-3">
          <Col md={8}>
            <Card>
              <Card.Body>
                <div className="d-flex align-items-center gap-3">
                  {customer.logoAziendale && <img src={customer.logoAziendale} alt="logo" style={{ width: 60, height: 60 }} />}
                  <div>
                    <h4 className="mb-1">{customer.ragioneSociale}</h4>
                    <div className="text-muted">P.IVA {customer.partitaIva} • {customer.tipo}</div>
                  </div>
                </div>
                <div className="mt-3 d-flex gap-2">
                  <Button variant="primary" onClick={sendEmail} disabled={!customer.emailContatto}>Invia email al contatto</Button>
                </div>
              </Card.Body>
            </Card>
          </Col>
          <Col md={4}>
            <Card>
              <Card.Body>
                <div><strong>Email:</strong> {customer.email}</div>
                <div><strong>PEC:</strong> {customer.pec || '-'}</div>
                <div><strong>Telefono:</strong> {customer.telefono || '-'}</div>
                <div><strong>Contatto:</strong> {customer.nomeContatto} {customer.cognomeContatto}</div>
                <div><strong>Email contatto:</strong> {customer.emailContatto || '-'}</div>
                <div><strong>Telefono contatto:</strong> {customer.telefonoContatto || '-'}</div>
              </Card.Body>
            </Card>
          </Col>
        </Row>
      )}

      <Row>
        <Col>
          <Card>
            <Card.Header>Indirizzi</Card.Header>
            <Card.Body>
              <Table size="sm" responsive>
                <thead>
                  <tr>
                    <th>Tipo</th>
                    <th>Via</th>
                    <th>Civico</th>
                    <th>Località</th>
                    <th>CAP</th>
                    <th>Comune</th>
                    <th>Provincia</th>
                  </tr>
                </thead>
                <tbody>
                  {customer?.indirizzi?.map((i, idx) => (
                    <tr key={idx}>
                      <td>{i.tipo}</td>
                      <td>{i.via}</td>
                      <td>{i.civico}</td>
                      <td>{i.localita}</td>
                      <td>{i.cap}</td>
                      <td>{i.comune}</td>
                      <td>{i.provincia}</td>
                    </tr>
                  ))}
                </tbody>
              </Table>
            </Card.Body>
          </Card>
        </Col>
      </Row>

      <Row className="mt-3">
        <Col>
          <Card>
            <Card.Header>Fatture</Card.Header>
            <Card.Body>
              <Table responsive>
                <thead>
                  <tr>
                    <th>Numero</th>
                    <th>Data</th>
                    <th>Importo</th>
                    <th>Stato</th>
                  </tr>
                </thead>
                <tbody>
                  {invoices.map((f) => (
                    <tr key={f.id}>
                      <td>{f.numero}</td>
                      <td>{new Date(f.data).toLocaleDateString('it-IT')}</td>
                      <td>€ {f.importo.toLocaleString('it-IT')}</td>
                      <td>{f.stato?.name}</td>
                    </tr>
                  ))}
                </tbody>
              </Table>
            </Card.Body>
          </Card>
        </Col>
      </Row>
    </Container>
  );
}