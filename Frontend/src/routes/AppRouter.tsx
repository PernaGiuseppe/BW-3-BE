import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import AppNavbar from '../components/AppNavbar';
import Login from '../pages/Login';
import Register from '../pages/Register';
import CustomersList from '../pages/CustomersList';
import CustomerDetail from '../pages/CustomerDetail';
import ProtectedRoute from '../components/ProtectedRoute';
import CustomersInvoice from '../pages/CustomersInvoice';
import InvoiceDetail from '../pages/InvoiceDetail';

export default function AppRouter() {
  return (
    <BrowserRouter>
      <AppNavbar />
      <div className="with-navbar-offset">
        <Routes>
          <Route path="/" element={<Navigate to="/customers" replace />} />
          <Route path="/login" element={<Login />} />
          <Route path="/register" element={<Register />} />
          <Route
            path="/customers"
            element={
              <ProtectedRoute>
                <CustomersList />
              </ProtectedRoute>
            }
          />
          <Route
            path="/customers/:id"
            element={
              <ProtectedRoute>
                <CustomerDetail />
              </ProtectedRoute>
            }
          />
          <Route
            path="/invoice"
            element={
              <ProtectedRoute>
                <CustomersInvoice />
              </ProtectedRoute>
            }
          />
          <Route
  path="/invoice/:id"
  element={
    <ProtectedRoute>
      <InvoiceDetail />
    </ProtectedRoute>
  }
/>
        </Routes>
      </div>
    </BrowserRouter>
  );
}