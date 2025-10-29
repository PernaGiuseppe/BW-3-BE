import { useState } from 'react';
import { Form, Button, Container, Row, Col, Alert, Card } from 'react-bootstrap';
import { useDispatch, useSelector } from 'react-redux';
import { register } from '../store/slices/authSlice';
import { RootState } from '../store';

export default function Register() {
  const dispatch = useDispatch();
  const { status, error, token } = useSelector((s: RootState) => s.auth);
  const [username, setUsername] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [nome, setNome] = useState('');
  const [cognome, setCognome] = useState('');

  const onSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    if (!username || !email || !password) return;
    // @ts-ignore
    dispatch(register({ username, email, password, nome, cognome }));
  };

  return (
    <div className="centered-shell">
      <div className="centered-container">
        <Card>
          <Card.Body className="stack">
            <h3 className="mb-0">Registrazione</h3>
            {error && <Alert variant="danger">{error}</Alert>}
            {token && <Alert variant="success">Registrazione effettuata!</Alert>}
            <Form onSubmit={onSubmit} className="stack">
              <Form.Group>
                <Form.Label>Username</Form.Label>
                <Form.Control value={username} onChange={(e) => setUsername(e.target.value)} />
              </Form.Group>
              <Form.Group>
                <Form.Label>Email</Form.Label>
                <Form.Control type="email" value={email} onChange={(e) => setEmail(e.target.value)} />
              </Form.Group>
              <Form.Group>
                <Form.Label>Password</Form.Label>
                <Form.Control type="password" value={password} onChange={(e) => setPassword(e.target.value)} />
              </Form.Group>
              <Form.Group>
                <Form.Label>Nome</Form.Label>
                <Form.Control value={nome} onChange={(e) => setNome(e.target.value)} />
              </Form.Group>
              <Form.Group>
                <Form.Label>Cognome</Form.Label>
                <Form.Control value={cognome} onChange={(e) => setCognome(e.target.value)} />
              </Form.Group>
              <div className="cluster">
                <Button type="submit" disabled={status === 'loading'}>
                  {status === 'loading' ? 'Registrazione...' : 'Registrati'}
                </Button>
              </div>
            </Form>
          </Card.Body>
        </Card>
      </div>
    </div>
  );
}