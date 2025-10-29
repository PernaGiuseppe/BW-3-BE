import { createSlice, createAsyncThunk } from '@reduxjs/toolkit';
import { api, extractError } from '../../api/client';
import type { Customer } from '../../types/domain';
import { USE_MOCKS } from '../../config';
import { demoCustomers } from '../../mocks/data';

interface CustomersState {
  items: Customer[];
  status: 'idle' | 'loading' | 'error';
  error?: string;
}

const initialState: CustomersState = {
  items: [],
  status: 'idle',
};

export const fetchCustomers = createAsyncThunk('customers/fetchAll', async (params?: Record<string, any>) => {
  if (USE_MOCKS) {
    return demoCustomers as Customer[];
  }
  const { data } = await api.get('/customers', { params });
  return data as Customer[];
});

const customersSlice = createSlice({
  name: 'customers',
  initialState,
  reducers: {},
  extraReducers: (builder) => {
    builder
      .addCase(fetchCustomers.pending, (state) => {
        state.status = 'loading';
        state.error = undefined;
      })
      .addCase(fetchCustomers.fulfilled, (state, action) => {
        state.status = 'idle';
        state.items = action.payload;
      })
      .addCase(fetchCustomers.rejected, (state, action) => {
        state.status = 'error';
        state.error = extractError(action.error).message;
      });
  },
});

export default customersSlice.reducer;