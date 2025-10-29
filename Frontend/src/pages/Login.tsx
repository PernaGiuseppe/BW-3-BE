import { useState } from 'react';
import { Form, Button, Container, Row, Col, Alert, Card } from 'react-bootstrap';
import { useDispatch, useSelector } from 'react-redux';
import { login } from '../store/slices/authSlice';
import { RootState } from '../store';

export default function Login() {
  const dispatch = useDispatch();
  const { status, error, token } = useSelector((s: RootState) => s.auth);
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');

  const onSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    if (!username || !password) return;
    // @ts-ignore
    dispatch(login({ username, password }));
  };

  return (
    <div className="centered-shell">
      <div className="centered-container">
        <Card>
          <Card.Body className="stack">
            <h3 className="mb-0">Accedi</h3>
            {error && <Alert variant="danger">{error}</Alert>}
            {token && <Alert variant="success">Login effettuato!</Alert>}
            <Form onSubmit={onSubmit} className="stack">
              <Form.Group>
                <Form.Label>Username</Form.Label>
                <Form.Control value={username} onChange={(e) => setUsername(e.target.value)} />
              </Form.Group>
              <Form.Group>
                <Form.Label>Password</Form.Label>
                <Form.Control type="password" value={password} onChange={(e) => setPassword(e.target.value)} />
              </Form.Group>
              <div className="cluster">
                <Button type="submit" disabled={status === 'loading'}>
                  {status === 'loading' ? 'Accesso...' : 'Accedi'}
                </Button>
              </div>
            </Form>
          </Card.Body>
        </Card>
      </div>
    </div>
  );
}