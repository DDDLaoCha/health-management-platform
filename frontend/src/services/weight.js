import api from './api'

export const getWeightRecords  = ()       => api.get('/weight-records').then(r => r.data)
export const getWeightSummary  = ()       => api.get('/weight-records/summary').then(r => r.data)
export const createWeightRecord = (data)  => api.post('/weight-records', data).then(r => r.data)
export const updateWeightRecord = (id, data) => api.put(`/weight-records/${id}`, data).then(r => r.data)
export const deleteWeightRecord = (id)    => api.delete(`/weight-records/${id}`)
