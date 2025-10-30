import { useEffect, useState } from "react";
import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import { LoginPage } from "./components/LoginPage";
import { ClientiPage } from "./components/ClientiPage";
import { FatturePage } from "./components/FatturePage";
import { NavBar } from "./components/Navbar";
import { isAuthenticated } from "./services/authService";
import "bootstrap/dist/css/bootstrap.min.css";
import Footer from "./components/Footer";

function ProtectedRoute({ element }) {
  return isAuthenticated() ? element : <Navigate to="/login" />;
}

export function App() {
  const [isAuth, setIsAuth] = useState(isAuthenticated());

  useEffect(() => {
    setIsAuth(isAuthenticated());
  }, []);

  return (
    <BrowserRouter>
      <NavBar />
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
    </BrowserRouter>
  );
}

export default App;
