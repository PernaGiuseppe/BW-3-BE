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
import { getFatture } from "../services/fattureService";

export function FatturePage() {
  const [fatture, setFatture] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");
  const [page, setPage] = useState(0);
  const [size, setSize] = useState(10);
  const [totalPages, setTotalPages] = useState(0);
  const [sortBy, setSortBy] = useState("numero");

  // Filtri
  const [filters, setFilters] = useState({
    clienteId: "",
    statoFatturaId: "",
    dataInizio: "",
    dataFine: "",
    anno: "",
    importoMin: "",
    importoMax: "",
  });

  const loadFatture = async () => {
    setLoading(true);
    setError("");
    try {
      const cleanFilters = {};
      Object.entries(filters).forEach(([key, value]) => {
        if (value) cleanFilters[key] = value;
      });

      const result = await getFatture(cleanFilters, page, size, sortBy);
      setFatture(result.content || []);
      setTotalPages(result.totalPages || 0);
    } catch (err) {
      setError("Errore nel caricamento delle fatture");
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadFatture();
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
    loadFatture();
  };

  const handleResetFilters = () => {
    setFilters({
      clienteId: "",
      statoFatturaId: "",
      dataInizio: "",
      dataFine: "",
      anno: "",
      importoMin: "",
      importoMax: "",
    });
    setPage(0);
    setSortBy("numero");
  };

  return (
    <Container fluid className="py-4">
      <h1 className="mb-4">Gestione Fatture</h1>

      {error && <Alert variant="danger">{error}</Alert>}

      {/* Sezione Filtri */}
      <div className="bg-light p-4 rounded mb-4">
        <h5 className="mb-3">Filtri</h5>
        <Form onSubmit={handleSearch}>
          <Row className="mb-3">
            <Col md={6} lg={3}>
              <Form.Group>
                <Form.Label>Cliente ID</Form.Label>
                <Form.Control
                  type="number"
                  name="clienteId"
                  value={filters.clienteId}
                  onChange={handleFilterChange}
                  placeholder="ID Cliente"
                />
              </Form.Group>
            </Col>
            <Col md={6} lg={3}>
              <Form.Group>
                <Form.Label>Stato Fattura ID</Form.Label>
                <Form.Control
                  type="number"
                  name="statoFatturaId"
                  value={filters.statoFatturaId}
                  onChange={handleFilterChange}
                  placeholder="ID Stato"
                />
              </Form.Group>
            </Col>
            <Col md={6} lg={3}>
              <Form.Group>
                <Form.Label>Anno</Form.Label>
                <Form.Control
                  type="number"
                  name="anno"
                  value={filters.anno}
                  onChange={handleFilterChange}
                  placeholder="YYYY"
                  min="2000"
                  max="2100"
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
                  <option value="numero">Numero</option>
                  <option value="data">Data</option>
                  <option value="importo">Importo</option>
                </Form.Select>
              </Form.Group>
            </Col>
          </Row>

          <Row className="mb-3">
            <Col md={6} lg={3}>
              <Form.Group>
                <Form.Label>Importo Min</Form.Label>
                <Form.Control
                  type="number"
                  name="importoMin"
                  value={filters.importoMin}
                  onChange={handleFilterChange}
                  placeholder="0.00"
                  step="0.01"
                />
              </Form.Group>
            </Col>
            <Col md={6} lg={3}>
              <Form.Group>
                <Form.Label>Importo Max</Form.Label>
                <Form.Control
                  type="number"
                  name="importoMax"
                  value={filters.importoMax}
                  onChange={handleFilterChange}
                  placeholder="9999999.99"
                  step="0.01"
                />
              </Form.Group>
            </Col>
            <Col md={6} lg={3}>
              <Form.Group>
                <Form.Label>Data Da</Form.Label>
                <Form.Control
                  type="date"
                  name="dataInizio"
                  value={filters.dataInizio}
                  onChange={handleFilterChange}
                />
              </Form.Group>
            </Col>
            <Col md={6} lg={3}>
              <Form.Group>
                <Form.Label>Data A</Form.Label>
                <Form.Control
                  type="date"
                  name="dataFine"
                  value={filters.dataFine}
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

      {/* Tabella Fatture */}
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
                  <th>Numero</th>
                  <th>Cliente ID</th>
                  <th>Data</th>
                  <th>Importo</th>
                  <th>Stato</th>
                  <th>Descrizione</th>
                </tr>
              </thead>
              <tbody>
                {fatture.length > 0 ? (
                  fatture.map((fattura) => (
                    <tr key={fattura.id}>
                      <td>{fattura.id}</td>
                      <td>{fattura.numero}</td>
                      <td>{fattura.clienteId || fattura.cliente?.id}</td>
                      <td>
                        {new Date(fattura.data).toLocaleDateString("it-IT")}
                      </td>
                      <td>€ {fattura.importo?.toFixed(2)}</td>
                      <td>{fattura.stato?.nome || "N/A"}</td>
                      <td>{fattura.descrizione || "-"}</td>
                    </tr>
                  ))
                ) : (
                  <tr>
                    <td colSpan="7" className="text-center">
                      Nessuna fattura trovata
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
