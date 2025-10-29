import { Navbar, Container, Nav } from 'react-bootstrap';
import { Link, NavLink } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
import { RootState } from '../store';
import { logout } from '../store/slices/authSlice';

export default function AppNavbar() {
  const dispatch = useDispatch();
  const { user } = useSelector((s: RootState) => s.auth);
  return (
    <Navbar bg="dark" data-bs-theme="dark" expand="lg" className="app-navbar">
      <Container>
        <Navbar.Brand as={Link} to="/">EPIC ENERGY SERVICES</Navbar.Brand>
        <Navbar.Toggle aria-controls="basic-navbar-nav" />
        <Navbar.Collapse id="basic-navbar-nav">
          <Nav className="me-auto">
            <Nav.Link as={NavLink} to="/customers">Clienti</Nav.Link>
            <Nav.Link as={NavLink} to="/invoice">Fatture</Nav.Link>
          </Nav>
          <Nav>
            {user ? (
              <>
                <Navbar.Text className="me-3">{user.username}</Navbar.Text>
                <Nav.Link onClick={() => dispatch(logout())}>Logout</Nav.Link>
              </>
            ) : (
              <>
                <Nav.Link as={NavLink} to="/login">Login</Nav.Link>
                <Nav.Link as={NavLink} to="/register">Registrati</Nav.Link>
              </>
            )}
          </Nav>
        </Navbar.Collapse>
      </Container>
    </Navbar>
  );
}