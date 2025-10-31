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
import { getClienti } from '../services/clienteService'

export function ClientiPage() {
  const [clienti, setClienti] = useState([])
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState('')
  const [page, setPage] = useState(0)
  const [size, setSize] = useState(10)
  const [totalPages, setTotalPages] = useState(0)
  const [sortBy, setSortBy] = useState('ragioneSociale')

  // Filtri secondo la consegna
  const [filters, setFilters] = useState({
    contieneNome: '',
    fatturatoAnnualeMin: '',
    fatturatoAnnualeMax: '',
    dataInserimentoInizio: '',
    dataInserimentoFine: '',
    dataUltimoContattoInizio: '',
    dataUltimoContattoFine: '',
    provinciaSedeLegale: '',
  })

  // Carica solo quando cambiano page, size, sortBy o filtri applicati
  const loadClienti = async (
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

      const result = await getClienti(
        cleanFilters,
        currentPage,
        size,
        currentSortBy
      )
      setClienti(result.content || [])
      setTotalPages(result.totalPages || 0)
    } catch (err) {
      setError('Errore nel caricamento dei clienti')
      console.error(err)
    } finally {
      setLoading(false)
    }
  }

  // Carica solo quando cambiano page o size
  useEffect(() => {
    loadClienti(page, sortBy, filters)
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
    loadClienti(0, sortBy, filters)
  }

  const handleResetFilters = async () => {
    const resetFilters = {
      contieneNome: '',
      fatturatoAnnualeMin: '',
      fatturatoAnnualeMax: '',
      dataInserimentoInizio: '',
      dataInserimentoFine: '',
      dataUltimoContattoInizio: '',
      dataUltimoContattoFine: '',
      provinciaSedeLegale: '',
    }

    setFilters(resetFilters)
    setPage(0)
    setSortBy('ragioneSociale')

    // Ricarica i dati con i filtri resettati
    await loadClienti(0, 'ragioneSociale', resetFilters)
  }

  const handleSortChange = (e) => {
    setSortBy(e.target.value)
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
      <div className="mb-4">
        <h1 className="mb-1">Gestione Clienti</h1>
        <p className="text-muted">
          Visualizza e gestisci i clienti con filtri avanzati
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
            <Col lg={3}>
              <Form.Group>
                <Form.Label className="fw-semibold">Ordina per</Form.Label>
                <Form.Select value={sortBy} onChange={handleSortChange}>
                  <option value="ragioneSociale">Ragione Sociale</option>
                  <option value="fatturatoAnnuale">Fatturato Annuale</option>
                  <option value="dataInserimento">Data Inserimento</option>
                  <option value="dataUltimoContatto">Ultimo Contatto</option>
                  <option value="provinciaSedeLegale">
                    Provincia Sede Legale
                  </option>
                </Form.Select>
              </Form.Group>
            </Col>
            <Col md={6} lg={3}>
              <Form.Group>
                <Form.Label className="fw-semibold">Nome contiene</Form.Label>
                <Form.Control
                  type="text"
                  name="contieneNome"
                  value={filters.contieneNome}
                  onChange={handleFilterChange}
                  placeholder="Ricerca per nome"
                />
              </Form.Group>
            </Col>
            <Col md={6} lg={3}>
              <Form.Group>
                <Form.Label className="fw-semibold">
                  Fatturato Annuale Min (€)
                </Form.Label>
                <Form.Control
                  type="number"
                  name="fatturatoAnnualeMin"
                  value={filters.fatturatoAnnualeMin}
                  onChange={handleFilterChange}
                  placeholder="0.00"
                  step="0.01"
                />
              </Form.Group>
            </Col>
            <Col md={6} lg={3}>
              <Form.Group>
                <Form.Label className="fw-semibold">
                  Fatturato Annuale Max (€)
                </Form.Label>
                <Form.Control
                  type="number"
                  name="fatturatoAnnualeMax"
                  value={filters.fatturatoAnnualeMax}
                  onChange={handleFilterChange}
                  placeholder="9999999.99"
                  step="0.01"
                />
              </Form.Group>
            </Col>
          </Row>

          <Row className="mb-3">
            <Col md={6} lg={3}>
              <Form.Group>
                <Form.Label className="fw-semibold">
                  Data Inserimento Da
                </Form.Label>
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
                <Form.Label className="fw-semibold">
                  Data Inserimento A
                </Form.Label>
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
                <Form.Label className="fw-semibold">
                  Ultimo Contatto Da
                </Form.Label>
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
                <Form.Label className="fw-semibold">
                  Ultimo Contatto A
                </Form.Label>
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

      {/* Tabella Clienti */}
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
                  <th>Ragione Sociale</th>
                  <th>Partita IVA</th>
                  <th>Fatturato Annuale</th>
                  <th>Data Inserimento</th>
                  <th>Ultimo Contatto</th>
                  <th>Provincia Sede Legale</th>
                </tr>
              </thead>
              <tbody>
                {clienti.length > 0 ? (
                  clienti.map((cliente) => (
                    <tr key={cliente.id}>
                      <td>{cliente.id}</td>
                      <td>{cliente.ragioneSociale}</td>
                      <td>{cliente.partitaIva}</td>
                      <td>{formatCurrency(cliente.fatturatoAnnuale)}</td>
                      <td>{formatDate(cliente.dataInserimento)}</td>
                      <td>{formatDate(cliente.dataUltimoContatto)}</td>
                      <td>
                        {cliente.indirizzoLegale?.comune?.provincia?.nome ||
                          '-'}
                      </td>
                    </tr>
                  ))
                ) : (
                  <tr>
                    <td colSpan="7" className="text-center">
                      Nessun cliente trovato
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
