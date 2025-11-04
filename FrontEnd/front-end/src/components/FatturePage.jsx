import { useState, useEffect } from 'react'
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
} from 'react-bootstrap'
import { getFatture } from '../services/fattureService'

export function FatturePage() {
  const [fatture, setFatture] = useState([])
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState('')
  const [page, setPage] = useState(0)
  const [size, setSize] = useState(10)
  const [totalPages, setTotalPages] = useState(0)
  const [sortBy, setSortBy] = useState('numero')

  // Filtri secondo la consegna
  const [filters, setFilters] = useState({
    clienteId: '',
    statoId: '',
    dataInizio: '',
    dataFine: '',
    anno: '',
    importoMin: '',
    importoMax: '',
  })

  const loadFatture = async (
    currentPage = page,
    currentSortBy = sortBy,
    currentFilters = filters
  ) => {
    setLoading(true)
    setError('')
    try {
      const cleanFilters = {}
      Object.entries(currentFilters).forEach(([key, value]) => {
        if (value) cleanFilters[key] = value
      })

      const result = await getFatture(
        cleanFilters,
        currentPage,
        size,
        currentSortBy
      )
      setFatture(result.content || [])
      setTotalPages(result.totalPages || 0)
    } catch (err) {
      setError('Errore nel caricamento delle fatture')
      console.error(err)
    } finally {
      setLoading(false)
    }
  }

  // Carica solo quando cambiano page o size
  useEffect(() => {
    loadFatture()
  }, [page, size])

  const handleFilterChange = (e) => {
    const { name, value } = e.target
    setFilters((prev) => ({
      ...prev,
      [name]: value,
    }))
  }

  const handleSearch = (e) => {
    e.preventDefault()
    setPage(0)
    loadFatture(0, sortBy, filters)
  }

  const handleResetFilters = async () => {
    const resetFilters = {
      clienteId: '',
      statoId: '',
      dataInizio: '',
      dataFine: '',
      anno: '',
      importoMin: '',
      importoMax: '',
    }

    setFilters(resetFilters)
    setPage(0)
    setSortBy('numero')

    // Ricarica i dati con i filtri resettati passandoli come parametri
    await loadFatture(0, 'numero', resetFilters)
  }

  const formatDate = (dateString) => {
    if (!dateString) return '-'
    try {
      return new Date(dateString).toLocaleDateString('it-IT')
    } catch {
      return dateString
    }
  }

  const formatCurrency = (value) => {
    if (!value) return '€ 0,00'
    return new Intl.NumberFormat('it-IT', {
      style: 'currency',
      currency: 'EUR',
    }).format(value)
  }

  const getPaginationItems = () => {
    const items = []
    const maxPagesToShow = 5
    let startPage = Math.max(0, page - Math.floor(maxPagesToShow / 2))
    let endPage = Math.min(totalPages, startPage + maxPagesToShow)

    if (endPage - startPage < maxPagesToShow) {
      startPage = Math.max(0, endPage - maxPagesToShow)
    }

    for (let i = startPage; i < endPage; i++) {
      items.push(i)
    }
    return items
  }

  return (
    <Container fluid className="py-4">
      <div>
        <h2 className="mb-1">Gestione Fatture</h2>
        <p className="text-muted">
          Visualizza e gestisci le fatture con filtri avanzati
        </p>
      </div>

      {error && (
        <Alert variant="danger" onClose={() => setError('')} dismissible>
          {error}
        </Alert>
      )}

      {/* Sezione Filtri */}
      <div className="bg-light p-4 rounded mb-4 shadow-sm">
        <h5 className="mb-3 fw-bold">Filtri di Ricerca</h5>
        <Form onSubmit={handleSearch}>
          <Row className="mb-3">
            <Col md={6} lg={3}>
              <Form.Group>
                <Form.Label className="fw-semibold">Cliente ID</Form.Label>
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
                <Form.Label className="fw-semibold">
                  Stato Fattura ID
                </Form.Label>
                <Form.Control
                  type="number"
                  name="statoId"
                  value={filters.statoId}
                  onChange={handleFilterChange}
                  placeholder="ID Stato"
                />
              </Form.Group>
            </Col>
            <Col md={6} lg={3}>
              <Form.Group>
                <Form.Label className="fw-semibold">Anno</Form.Label>
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
                <Form.Label className="fw-semibold">Ordina per</Form.Label>
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
                <Form.Label className="fw-semibold">Importo Min (€)</Form.Label>
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
                <Form.Label className="fw-semibold">Importo Max (€)</Form.Label>
                <Form.Control
                  type="number"
                  name="importoMax"
                  value={filters.importoMax}
                  onChange={handleFilterChange}
                  placeholder="0.00"
                  step="0.01"
                />
              </Form.Group>
            </Col>
            <Col md={6} lg={3}>
              <Form.Group>
                <Form.Label className="fw-semibold">Data Da</Form.Label>
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
                <Form.Label className="fw-semibold">Data A</Form.Label>
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
            <Button
              variant="secondary"
              onClick={handleResetFilters}
              type="button"
            >
              Reset Filtri
            </Button>
          </div>
        </Form>
      </div>

      {/* Tabella Fatture */}
      {loading ? (
        <div className="text-center py-5">
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
                  <th>Cliente</th>
                  <th>Id cliente</th>
                  <th>Data</th>
                  <th>Importo</th>
                  <th>Stato fattura</th>
                  <th>Stato fattura ID</th>
                </tr>
              </thead>
              <tbody>
                {fatture.length > 0 ? (
                  fatture.map((fattura) => (
                    <tr key={fattura.id}>
                      <td>{fattura.id}</td>
                      <td>{fattura.numero}</td>
                      <td>
                        {fattura.cliente?.ragioneSociale || fattura.clienteId}
                      </td>
                      <td>{fattura.cliente.id}</td>
                      <td>{formatDate(fattura.data)}</td>
                      <td>{formatCurrency(fattura.importo)}</td>
                      <td>{fattura.statoFattura?.statoFattura || 'N/A'}</td>
                      <td>{fattura.statoFattura?.id}</td>
                    </tr>
                  ))
                ) : (
                  <tr>
                    <td colSpan="8" className="text-center">
                      Nessuna fattura trovata
                    </td>
                  </tr>
                )}
              </tbody>
            </Table>
          </div>

          {/* Paginazione */}
          {totalPages > 0 && (
            <div className="d-flex justify-content-between align-items-center mt-4">
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
                {getPaginationItems().map((pageNum) => (
                  <Pagination.Item
                    key={pageNum}
                    active={pageNum === page}
                    onClick={() => setPage(pageNum)}
                  >
                    {pageNum + 1}
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
          )}
        </>
      )}
    </Container>
  )
}
