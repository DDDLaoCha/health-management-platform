import { BrowserRouter, Routes, Route, Navigate, Link, useNavigate } from 'react-router-dom'
import LoginPage from './pages/LoginPage'
import RegisterPage from './pages/RegisterPage'
import WeightListPage from './pages/WeightListPage'
import { getToken, logout } from './services/auth'

function HomePage() {
  const navigate = useNavigate()

  function handleLogout() {
    logout()
    navigate('/login', { replace: true })
  }

  return (
    <div style={{ padding: '40px', fontFamily: 'sans-serif' }}>
      <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
        <h1 style={{ margin: 0 }}>健康管理平台</h1>
        <button onClick={handleLogout} style={{ padding: '6px 14px', cursor: 'pointer', border: '1px solid #ddd', borderRadius: '4px', background: '#fff' }}>
          退出登录
        </button>
      </div>
      <p style={{ marginTop: '24px' }}><Link to="/weight">查看体重记录</Link></p>
    </div>
  )
}

function PrivateRoute({ children }) {
  return getToken() ? children : <Navigate to="/login" replace />
}

export default function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/login" element={<LoginPage />} />
        <Route path="/register" element={<RegisterPage />} />
        <Route path="/" element={<PrivateRoute><HomePage /></PrivateRoute>} />
        <Route path="/weight" element={<PrivateRoute><WeightListPage /></PrivateRoute>} />
      </Routes>
    </BrowserRouter>
  )
}
