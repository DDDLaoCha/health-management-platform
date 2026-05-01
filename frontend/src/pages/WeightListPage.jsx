import { useState, useEffect } from 'react'
import { Link } from 'react-router-dom'
import {
  getWeightRecords,
  getWeightSummary,
  createWeightRecord,
  updateWeightRecord,
  deleteWeightRecord,
} from '../services/weight'

const EMPTY_FORM = { recordDate: '', weightKg: '', note: '' }

export default function WeightListPage() {
  const [records, setRecords]   = useState([])
  const [summary, setSummary]   = useState(null)
  const [form, setForm]         = useState(EMPTY_FORM)
  const [editingId, setEditingId] = useState(null)
  const [loading, setLoading]   = useState(true)
  const [saving, setSaving]     = useState(false)
  const [error, setError]       = useState('')

  useEffect(() => { loadAll() }, [])

  async function loadAll() {
    setLoading(true)
    try {
      const [recs, sum] = await Promise.all([getWeightRecords(), getWeightSummary()])
      setRecords(recs)
      setSummary(sum)
    } catch {
      setError('加载失败')
    } finally {
      setLoading(false)
    }
  }

  function startEdit(r) {
    setEditingId(r.id)
    setForm({ recordDate: r.recordDate, weightKg: r.weightKg, note: r.note ?? '' })
  }

  function cancelEdit() {
    setEditingId(null)
    setForm(EMPTY_FORM)
  }

  async function handleSubmit(e) {
    e.preventDefault()
    setSaving(true)
    setError('')
    try {
      const payload = { recordDate: form.recordDate, weightKg: Number(form.weightKg), note: form.note }
      if (editingId) {
        await updateWeightRecord(editingId, payload)
      } else {
        await createWeightRecord(payload)
      }
      setForm(EMPTY_FORM)
      setEditingId(null)
      await loadAll()
    } catch {
      setError(editingId ? '修改失败' : '新增失败')
    } finally {
      setSaving(false)
    }
  }

  async function handleDelete(id) {
    if (!window.confirm('确认删除？')) return
    try {
      await deleteWeightRecord(id)
      await loadAll()
    } catch {
      setError('删除失败')
    }
  }

  if (loading) return <p style={s.tip}>加载中...</p>

  const changeColor = summary?.change > 0 ? '#ff4d4f' : summary?.change < 0 ? '#52c41a' : '#888'

  return (
    <div style={s.page}>
      <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '8px' }}>
        <h2 style={{ margin: 0 }}>体重记录</h2>
        <Link to="/" style={{ fontSize: '14px' }}>← 返回首页</Link>
      </div>

      {/* Summary */}
      {summary?.latestWeight && (
        <div style={s.summary}>
          <div style={s.card}><div style={s.cardLabel}>初始体重</div><div style={s.cardVal}>{summary.initialWeight} kg</div></div>
          <div style={s.card}><div style={s.cardLabel}>最新体重</div><div style={s.cardVal}>{summary.latestWeight} kg</div></div>
          <div style={s.card}>
            <div style={s.cardLabel}>变化</div>
            <div style={{ ...s.cardVal, color: changeColor }}>
              {summary.change > 0 ? '+' : ''}{summary.change} kg
            </div>
          </div>
        </div>
      )}

      {/* Form */}
      <form onSubmit={handleSubmit} style={s.form}>
        <input
          type="date"
          value={form.recordDate}
          onChange={e => setForm(f => ({ ...f, recordDate: e.target.value }))}
          required
          style={s.input}
        />
        <input
          type="number"
          step="0.1"
          placeholder="体重 (kg)"
          value={form.weightKg}
          onChange={e => setForm(f => ({ ...f, weightKg: e.target.value }))}
          required
          style={{ ...s.input, width: '120px' }}
        />
        <input
          type="text"
          placeholder="备注（可选）"
          value={form.note}
          onChange={e => setForm(f => ({ ...f, note: e.target.value }))}
          style={{ ...s.input, flex: 1 }}
        />
        <button type="submit" disabled={saving} style={s.btnPrimary}>
          {saving ? '保存中...' : editingId ? '保存修改' : '新增'}
        </button>
        {editingId && (
          <button type="button" onClick={cancelEdit} style={s.btnGhost}>取消</button>
        )}
      </form>

      {error && <p style={{ color: '#ff4d4f', margin: '8px 0' }}>{error}</p>}

      {/* Table */}
      {records.length === 0 ? (
        <p style={s.tip}>暂无记录</p>
      ) : (
        <table style={s.table}>
          <thead>
            <tr>
              <th style={s.th}>日期</th>
              <th style={s.th}>体重 (kg)</th>
              <th style={s.th}>备注</th>
              <th style={s.th}>操作</th>
            </tr>
          </thead>
          <tbody>
            {records.map(r => (
              <tr key={r.id} style={editingId === r.id ? { background: '#fffbe6' } : {}}>
                <td style={s.td}>{r.recordDate}</td>
                <td style={s.td}>{r.weightKg}</td>
                <td style={s.td}>{r.note ?? '-'}</td>
                <td style={s.td}>
                  <button onClick={() => startEdit(r)} style={s.btnSmall}>编辑</button>
                  <button onClick={() => handleDelete(r.id)} style={{ ...s.btnSmall, color: '#ff4d4f' }}>删除</button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  )
}

const s = {
  page:      { padding: '40px', fontFamily: 'sans-serif', maxWidth: '680px', margin: '0 auto' },
  tip:       { padding: '40px', textAlign: 'center', color: '#888' },
  summary:   { display: 'flex', gap: '16px', margin: '16px 0 24px' },
  card:      { flex: 1, padding: '16px', background: '#f5f5f5', borderRadius: '8px', textAlign: 'center' },
  cardLabel: { fontSize: '12px', color: '#888', marginBottom: '6px' },
  cardVal:   { fontSize: '20px', fontWeight: 'bold' },
  form:      { display: 'flex', gap: '8px', alignItems: 'center', margin: '16px 0', flexWrap: 'wrap' },
  input:     { padding: '7px 10px', border: '1px solid #ddd', borderRadius: '4px', fontSize: '14px' },
  btnPrimary:{ padding: '7px 16px', background: '#1677ff', color: '#fff', border: 'none', borderRadius: '4px', cursor: 'pointer' },
  btnGhost:  { padding: '7px 12px', background: 'none', border: '1px solid #ddd', borderRadius: '4px', cursor: 'pointer' },
  btnSmall:  { marginRight: '8px', padding: '3px 10px', background: 'none', border: '1px solid #ddd', borderRadius: '4px', cursor: 'pointer', fontSize: '13px' },
  table:     { width: '100%', borderCollapse: 'collapse', marginTop: '8px' },
  th:        { textAlign: 'left', padding: '10px 12px', borderBottom: '2px solid #eee', color: '#555', fontSize: '13px' },
  td:        { padding: '10px 12px', borderBottom: '1px solid #f0f0f0', fontSize: '14px' },
}
