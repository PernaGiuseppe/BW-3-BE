import axios from "axios";
import { getToken } from "./authService";

const API_URL = import.meta.env.VITE_API_URL || "http://localhost:3001";

const getAuthHeader = () => ({
  headers: {
    Authorization: `Bearer ${getToken()}`,
  },
});

export const getFatture = async (
  filters = {},
  page = 0,
  size = 10,
  sortBy = "numero"
) => {
  try {
    const params = new URLSearchParams();
    params.append("page", page.toString());
    params.append("size", size.toString());
    params.append("sortBy", sortBy);

    // Mapping dei filtri
    if (filters.clienteId) {
      params.append("clienteId", filters.clienteId);
    }
    if (filters.statoId) {
      params.append("statoFatturaId", filters.statoId);
    }
    if (filters.dataInizio) {
      params.append("dataInizio", filters.dataInizio);
    }
    if (filters.dataFine) {
      params.append("dataFine", filters.dataFine);
    }
    if (filters.anno) {
      params.append("anno", filters.anno);
    }
    if (filters.importoMin) {
      params.append("importoMin", filters.importoMin);
    }
    if (filters.importoMax) {
      params.append("importoMax", filters.importoMax);
    }

    const response = await axios.get(
      `${API_URL}/fatture?${params.toString()}`,
      getAuthHeader()
    );
    return response.data;
  } catch (error) {
    throw error.response?.data || error.message;
  }
};

export const getFatturaById = async (id) => {
  try {
    const response = await axios.get(
      `${API_URL}/fatture/${id}`,
      getAuthHeader()
    );
    return response.data;
  } catch (error) {
    throw error.response?.data || error.message;
  }
};
