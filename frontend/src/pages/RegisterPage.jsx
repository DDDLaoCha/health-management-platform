import { useState } from 'react'
import { useNavigate, Link } from 'react-router-dom'
import { register } from '../services/auth'

export default function RegisterPage() {
  const navigate = useNavigate()
  const [name, setName]         = useState('')
  const [email, setEmail]       = useState('')
  const [password, setPassword] = useState('')
  const [error, setError]       = useState('')
  const [loading, setLoading]   = useState(false)

  async function handleSubmit(e) {
    e.preventDefault()
    setError('')
    setLoading(true)
    try {
      await register(name, email, password)
      navigate('/')
    } catch (err) {
      setError(err.response?.data?.message || '注册失败，请稍后重试')
    } finally {
      setLoading(false)
    }
  }

  return (
    <div style={styles.container}>
      <div style={styles.card}>
        <h2 style={styles.title}>注册</h2>
        <form onSubmit={handleSubmit}>
          <div style={styles.field}>
            <label>姓名</label>
            <input
              type="text"
              value={name}
              onChange={e => setName(e.target.value)}
              required
              style={styles.input}
              placeholder="你的名字"
            />
          </div>
          <div style={styles.field}>
            <label>邮箱</label>
            <input
              type="email"
              value={email}
              onChange={e => setEmail(e.target.value)}
              required
              style={styles.input}
              placeholder="your@email.com"
            />
          </div>
          <div style={styles.field}>
            <label>密码</label>
            <input
              type="password"
              value={password}
              onChange={e => setPassword(e.target.value)}
              required
              style={styles.input}
              placeholder="••••••••"
            />
          </div>
          {error && <p style={styles.error}>{error}</p>}
          <button type="submit" disabled={loading} style={styles.button}>
            {loading ? '注册中...' : '注册'}
          </button>
        </form>
        <p style={styles.link}>
          已有账号？<Link to="/login">去登录</Link>
        </p>
      </div>
    </div>
  )
}

const styles = {
  container: { display: 'flex', justifyContent: 'center', alignItems: 'center', height: '100vh', background: '#f5f5f5' },
  card:      { background: '#fff', padding: '40px', borderRadius: '8px', boxShadow: '0 2px 12px rgba(0,0,0,0.1)', width: '320px' },
  title:     { margin: '0 0 24px', textAlign: 'center' },
  field:     { display: 'flex', flexDirection: 'column', gap: '6px', marginBottom: '16px' },
  input:     { padding: '8px 12px', border: '1px solid #ddd', borderRadius: '4px', fontSize: '14px' },
  button:    { width: '100%', padding: '10px', background: '#1677ff', color: '#fff', border: 'none', borderRadius: '4px', fontSize: '15px', cursor: 'pointer' },
  error:     { color: '#ff4d4f', fontSize: '13px', margin: '0 0 12px' },
  link:      { textAlign: 'center', marginTop: '16px', fontSize: '13px', color: '#888' },
}
