import axios from "axios";
import { getToken } from "./authService";

const API_URL = import.meta.env.VITE_API_URL || "http://localhost:3001";

const getAuthHeader = () => ({
  headers: {
    Authorization: `Bearer ${getToken()}`,
  },
});

export const getClienti = async (
  filters = {},
  page = 0,
  size = 10,
  sortBy = "ragioneSociale"
) => {
  try {
    const params = {
      page,
      size,
      sortBy,
      ...filters,
    };
    const response = await axios.get(`${API_URL}/clienti`, {
      params,
      ...getAuthHeader(),
    });
    return response.data;
  } catch (error) {
    throw error.response?.data || error.message;
  }
};

export const getClienteById = async (id) => {
  try {
    const response = await axios.get(
      `${API_URL}/clienti/${id}`,
      getAuthHeader()
    );
    return response.data;
  } catch (error) {
    throw error.response?.data || error.message;
  }
};
