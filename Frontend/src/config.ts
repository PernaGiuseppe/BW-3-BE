export const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080/api';

export const TOKEN_STORAGE_KEY = 'epic_energy_token';
export const USER_STORAGE_KEY = 'epic_energy_user';

// Modalità demo: di default attiva in sviluppo, disattivabile con VITE_USE_MOCKS=false
export const USE_MOCKS: boolean = (
  (import.meta.env.VITE_USE_MOCKS ?? 'true') === 'true' && import.meta.env.DEV
);