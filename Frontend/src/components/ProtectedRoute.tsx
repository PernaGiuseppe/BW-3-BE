import { useSelector } from 'react-redux';
import { Navigate } from 'react-router-dom';
import { RootState } from '../store';
import { USE_MOCKS } from '../config';

export default function ProtectedRoute({ children, roles }: { children: JSX.Element; roles?: string[] }) {
  const { token, user } = useSelector((s: RootState) => s.auth);
  if (!token && !USE_MOCKS) return <Navigate to="/login" replace />;
  if (roles && user && !roles.some((r) => user.roles.includes(r as any))) {
    return <Navigate to="/" replace />;
  }
  return children;
}