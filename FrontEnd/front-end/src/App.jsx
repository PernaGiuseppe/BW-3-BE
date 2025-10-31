import { useEffect, useState } from 'react'
import {
  BrowserRouter,
  Routes,
  Route,
  Navigate,
  useLocation,
} from 'react-router-dom'
import { LoginPage } from './components/LoginPage'
import { ClientiPage } from './components/ClientiPage'
import { FatturePage } from './components/FatturePage'
import { NavBar } from './components/Navbar'
import { isAuthenticated } from './services/authService'
import 'bootstrap/dist/css/bootstrap.min.css'
import Footer from './components/Footer'

function ProtectedRoute({ element }) {
  return isAuthenticated() ? element : <Navigate to="/login" />
}

function AppContent() {
  const [isAuth, setIsAuth] = useState(isAuthenticated())
  const location = useLocation()

  useEffect(() => {
    setIsAuth(isAuthenticated())
  }, [location])

  return (
    <>
      {isAuth && <NavBar />}
      <Routes>
        <Route path="/login" element={<LoginPage />} />
        <Route
          path="/clienti"
          element={<ProtectedRoute element={<ClientiPage />} />}
        />
        <Route
          path="/fatture"
          element={<ProtectedRoute element={<FatturePage />} />}
        />
        <Route
          path="/"
          element={
            isAuth ? <Navigate to="/clienti" /> : <Navigate to="/login" />
          }
        />
      </Routes>
      <Footer />
    </>
  )
}

export function App() {
  return (
    <BrowserRouter>
      <AppContent />
    </BrowserRouter>
  )
}

export default App
