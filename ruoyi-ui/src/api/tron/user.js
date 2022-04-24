import request from '@/utils/request'

// 查询业务员列表
export function listUser(query) {
  return request({
    url: '/tron/user/list',
    method: 'get',
    params: query
  })
}

// 查询业务员详细
export function getUser(id) {
  return request({
    url: '/tron/user/' + id,
    method: 'get'
  })
}

// 新增业务员
export function addUser(data) {
  return request({
    url: '/tron/user',
    method: 'post',
    data: data
  })
}

// 修改业务员
export function updateUser(data) {
  return request({
    url: '/tron/user',
    method: 'put',
    data: data
  })
}

// 删除业务员
export function delUser(id) {
  return request({
    url: '/tron/user/' + id,
    method: 'delete'
  })
}

// 导出业务员
export function exportUser(query) {
  return request({
    url: '/tron/user/export',
    method: 'get',
    params: query
  })
}