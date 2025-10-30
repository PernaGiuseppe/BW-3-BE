import { useState, useEffect } from "react";
import {
  Container,
  Form,
  Table,
  Button,
  Row,
  Col,
  Spinner,
  Alert,
  Pagination,
} from "react-bootstrap";
import { getClienti } from "../services/clienteService";

export function ClientiPage() {
  const [clienti, setClienti] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");
  const [page, setPage] = useState(0);
  const [size, setSize] = useState(10);
  const [totalPages, setTotalPages] = useState(0);
  const [sortBy, setSortBy] = useState("ragioneSociale");

  // Filtri
  const [filters, setFilters] = useState({
    fatturatoMin: "",
    fatturatoMax: "",
    dataInserimentoInizio: "",
    dataInserimentoFine: "",
    dataUltimoContattoInizio: "",
    dataUltimoContattoFine: "",
    nomeContiene: "",
  });

  const loadClienti = async () => {
    setLoading(true);
    setError("");
    try {
      const cleanFilters = {};
      Object.entries(filters).forEach(([key, value]) => {
        if (value) cleanFilters[key] = value;
      });

      const result = await getClienti(cleanFilters, page, size, sortBy);
      setClienti(result.content || []);
      setTotalPages(result.totalPages || 0);
    } catch (err) {
      setError("Errore nel caricamento dei clienti");
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadClienti();
  }, [page, size, sortBy]);

  const handleFilterChange = (e) => {
    const { name, value } = e.target;
    setFilters((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  const handleSearch = (e) => {
    e.preventDefault();
    setPage(0);
    loadClienti();
  };

  const handleResetFilters = () => {
    setFilters({
      fatturatoMin: "",
      fatturatoMax: "",
      dataInserimentoInizio: "",
      dataInserimentoFine: "",
      dataUltimoContattoInizio: "",
      dataUltimoContattoFine: "",
      nomeContiene: "",
    });
    setPage(0);
    setSortBy("ragioneSociale");
  };

  return (
    <Container fluid className="py-4">
      <h1 className="mb-4">Gestione Clienti</h1>

      {error && <Alert variant="danger">{error}</Alert>}

      {/* Sezione Filtri */}
      <div className="bg-light p-4 rounded mb-4">
        <h5 className="mb-3">Filtri</h5>
        <Form onSubmit={handleSearch}>
          <Row className="mb-3">
            <Col md={6} lg={3}>
              <Form.Group>
                <Form.Label>Nome contiene</Form.Label>
                <Form.Control
                  type="text"
                  name="nomeContiene"
                  value={filters.nomeContiene}
                  onChange={handleFilterChange}
                  placeholder="Ricerca per nome"
                />
              </Form.Group>
            </Col>
            <Col md={6} lg={3}>
              <Form.Group>
                <Form.Label>Fatturato Min</Form.Label>
                <Form.Control
                  type="number"
                  name="fatturatoMin"
                  value={filters.fatturatoMin}
                  onChange={handleFilterChange}
                  placeholder="0.00"
                  step="0.01"
                />
              </Form.Group>
            </Col>
            <Col md={6} lg={3}>
              <Form.Group>
                <Form.Label>Fatturato Max</Form.Label>
                <Form.Control
                  type="number"
                  name="fatturatoMax"
                  value={filters.fatturatoMax}
                  onChange={handleFilterChange}
                  placeholder="9999999.99"
                  step="0.01"
                />
              </Form.Group>
            </Col>
            <Col md={6} lg={3}>
              <Form.Group>
                <Form.Label>Ordina per</Form.Label>
                <Form.Select
                  value={sortBy}
                  onChange={(e) => setSortBy(e.target.value)}
                >
                  <option value="ragioneSociale">Ragione Sociale</option>
                  <option value="fatturato">Fatturato</option>
                  <option value="dataInserimento">Data Inserimento</option>
                </Form.Select>
              </Form.Group>
            </Col>
          </Row>

          <Row className="mb-3">
            <Col md={6} lg={3}>
              <Form.Group>
                <Form.Label>Data Inserimento Da</Form.Label>
                <Form.Control
                  type="date"
                  name="dataInserimentoInizio"
                  value={filters.dataInserimentoInizio}
                  onChange={handleFilterChange}
                />
              </Form.Group>
            </Col>
            <Col md={6} lg={3}>
              <Form.Group>
                <Form.Label>Data Inserimento A</Form.Label>
                <Form.Control
                  type="date"
                  name="dataInserimentoFine"
                  value={filters.dataInserimentoFine}
                  onChange={handleFilterChange}
                />
              </Form.Group>
            </Col>
            <Col md={6} lg={3}>
              <Form.Group>
                <Form.Label>Ultimo Contatto Da</Form.Label>
                <Form.Control
                  type="date"
                  name="dataUltimoContattoInizio"
                  value={filters.dataUltimoContattoInizio}
                  onChange={handleFilterChange}
                />
              </Form.Group>
            </Col>
            <Col md={6} lg={3}>
              <Form.Group>
                <Form.Label>Ultimo Contatto A</Form.Label>
                <Form.Control
                  type="date"
                  name="dataUltimoContattoFine"
                  value={filters.dataUltimoContattoFine}
                  onChange={handleFilterChange}
                />
              </Form.Group>
            </Col>
          </Row>

          <div className="d-flex gap-2">
            <Button variant="primary" type="submit">
              Applica Filtri
            </Button>
            <Button variant="secondary" onClick={handleResetFilters}>
              Reset Filtri
            </Button>
          </div>
        </Form>
      </div>

      {/* Tabella Clienti */}
      {loading ? (
        <div className="text-center">
          <Spinner animation="border" />
        </div>
      ) : (
        <>
          <div className="table-responsive">
            <Table striped bordered hover>
              <thead className="table-dark">
                <tr>
                  <th>ID</th>
                  <th>Ragione Sociale</th>
                  <th>Partita IVA</th>
                  <th>Fatturato</th>
                  <th>Data Inserimento</th>
                  <th>Ultimo Contatto</th>
                </tr>
              </thead>
              <tbody>
                {clienti.length > 0 ? (
                  clienti.map((cliente) => (
                    <tr key={cliente.id}>
                      <td>{cliente.id}</td>
                      <td>{cliente.ragioneSociale}</td>
                      <td>{cliente.partitaIva}</td>
                      <td>€ {cliente.fatturato?.toFixed(2)}</td>
                      <td>
                        {new Date(cliente.dataInserimento).toLocaleDateString(
                          "it-IT"
                        )}
                      </td>
                      <td>
                        {cliente.dataUltimoContatto
                          ? new Date(
                              cliente.dataUltimoContatto
                            ).toLocaleDateString("it-IT")
                          : "-"}
                      </td>
                    </tr>
                  ))
                ) : (
                  <tr>
                    <td colSpan="6" className="text-center">
                      Nessun cliente trovato
                    </td>
                  </tr>
                )}
              </tbody>
            </Table>
          </div>

          {/* Paginazione */}
          <div className="d-flex justify-content-between align-items-center">
            <span>
              Pagina {page + 1} di {totalPages}
            </span>
            <Pagination>
              <Pagination.First
                onClick={() => setPage(0)}
                disabled={page === 0}
              />
              <Pagination.Prev
                onClick={() => setPage(page - 1)}
                disabled={page === 0}
              />
              {Array.from({ length: Math.min(5, totalPages) }).map((_, i) => (
                <Pagination.Item
                  key={i}
                  active={i === page}
                  onClick={() => setPage(i)}
                >
                  {i + 1}
                </Pagination.Item>
              ))}
              <Pagination.Next
                onClick={() => setPage(page + 1)}
                disabled={page === totalPages - 1}
              />
              <Pagination.Last
                onClick={() => setPage(totalPages - 1)}
                disabled={page === totalPages - 1}
              />
            </Pagination>
          </div>
        </>
      )}
    </Container>
  );
}
