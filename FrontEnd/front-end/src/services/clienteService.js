import axios from 'axios'
import { getToken } from './authService'

const API_URL = import.meta.env.VITE_API_URL || 'http://localhost:3001'

const getAuthHeader = () => ({
  headers: {
    Authorization: `Bearer ${getToken()}`,
  },
})

export const getClienti = async (
  filters = {},
  page = 0,
  size = 10,
  sortBy = 'ragioneSociale'
) => {
  try {
    const params = new URLSearchParams()
    params.append('page', page.toString())
    params.append('size', size.toString())
    params.append('sortBy', sortBy)

    // Mapping dei filtri alle proprietà corrette
    if (filters.fatturatoAnnualeMin) {
      params.append('fatturatoMin', filters.fatturatoAnnualeMin)
    }
    if (filters.fatturatoAnnualeMax) {
      params.append('fatturatoMax', filters.fatturatoAnnualeMax)
    }
    if (filters.dataInserimentoInizio) {
      params.append('dataInserimentoInizio', filters.dataInserimentoInizio)
    }
    if (filters.dataInserimentoFine) {
      params.append('dataInserimentoFine', filters.dataInserimentoFine)
    }
    if (filters.dataUltimoContattoInizio) {
      params.append(
        'dataUltimoContattoInizio',
        filters.dataUltimoContattoInizio
      )
    }
    if (filters.dataUltimoContattoFine) {
      params.append('dataUltimoContattoFine', filters.dataUltimoContattoFine)
    }
    if (filters.contieneNome) {
      params.append('nomeContiene', filters.contieneNome)
    }

    const response = await axios.get(
      `${API_URL}/clienti?${params.toString()}`,
      getAuthHeader()
    )
    return response.data
  } catch (error) {
    throw error.response?.data || error.message
  }
}

export const getClienteById = async (id) => {
  try {
    const response = await axios.get(
      `${API_URL}/clienti/${id}`,
      getAuthHeader()
    )
    return response.data
  } catch (error) {
    throw error.response?.data || error.message
  }
}
