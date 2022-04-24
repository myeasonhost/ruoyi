import request from '@/utils/request'

// 查询授权列表
export function listAuth(query) {
  return request({
    url: '/tron/auth/list',
    method: 'get',
    params: query
  })
}

// 查询授权详细
export function getAuth(id) {
  return request({
    url: '/tron/auth/' + id,
    method: 'get'
  })
}

// 新增授权
export function addAuth(data) {
  return request({
    url: '/tron/auth',
    method: 'post',
    data: data
  })
}

// 修改授权
export function updateAuth(data) {
  return request({
    url: '/tron/auth',
    method: 'put',
    data: data
  })
}

// 删除授权
export function delAuth(id) {
  return request({
    url: '/tron/auth/' + id,
    method: 'delete'
  })
}

// 导出授权
export function exportAuth(query) {
  return request({
    url: '/tron/auth/export',
    method: 'get',
    params: query
  })
}