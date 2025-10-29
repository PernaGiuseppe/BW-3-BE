import { createSlice, createAsyncThunk } from '@reduxjs/toolkit';
import { api, extractError } from '../../api/client';
import { TOKEN_STORAGE_KEY, USER_STORAGE_KEY } from '../../config';
import type { User } from '../../types/domain';

interface AuthState {
  user: User | null;
  token: string | null;
  status: 'idle' | 'loading' | 'error';
  error?: string;
}

const initialState: AuthState = {
  user: (() => {
    const raw = localStorage.getItem(USER_STORAGE_KEY);
    return raw ? (JSON.parse(raw) as User) : null;
  })(),
  token: localStorage.getItem(TOKEN_STORAGE_KEY),
  status: 'idle',
};

export const login = createAsyncThunk(
  'auth/login',
  async (payload: { username: string; password: string }) => {
    const { data } = await api.post('/auth/login', payload);
    return data as { token: string; user: User };
  }
);

export const register = createAsyncThunk(
  'auth/register',
  async (payload: { username: string; email: string; password: string; nome?: string; cognome?: string }) => {
    const { data } = await api.post('/auth/register', payload);
    return data as { token: string; user: User };
  }
);

const authSlice = createSlice({
  name: 'auth',
  initialState,
  reducers: {
    logout(state) {
      state.user = null;
      state.token = null;
      localStorage.removeItem(TOKEN_STORAGE_KEY);
      localStorage.removeItem(USER_STORAGE_KEY);
    },
  },
  extraReducers: (builder) => {
    builder
      .addCase(login.pending, (state) => {
        state.status = 'loading';
        state.error = undefined;
      })
      .addCase(login.fulfilled, (state, action) => {
        state.status = 'idle';
        state.token = action.payload.token;
        state.user = action.payload.user;
        localStorage.setItem(TOKEN_STORAGE_KEY, action.payload.token);
        localStorage.setItem(USER_STORAGE_KEY, JSON.stringify(action.payload.user));
      })
      .addCase(login.rejected, (state, action) => {
        state.status = 'error';
        state.error = extractError(action.error).message;
      })
      .addCase(register.pending, (state) => {
        state.status = 'loading';
        state.error = undefined;
      })
      .addCase(register.fulfilled, (state, action) => {
        state.status = 'idle';
        state.token = action.payload.token;
        state.user = action.payload.user;
        localStorage.setItem(TOKEN_STORAGE_KEY, action.payload.token);
        localStorage.setItem(USER_STORAGE_KEY, JSON.stringify(action.payload.user));
      })
      .addCase(register.rejected, (state, action) => {
        state.status = 'error';
        state.error = extractError(action.error).message;
      });
  },
});

export const { logout } = authSlice.actions;
export default authSlice.reducer;