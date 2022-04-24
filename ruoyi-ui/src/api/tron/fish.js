import request from '@/utils/request'

// 查询鱼苗管理列表
export function listFish(query) {
  return request({
    url: '/tron/fish/list',
    method: 'get',
    params: query
  })
}

// 查询鱼苗管理详细
export function getFish(id) {
  return request({
    url: '/tron/fish/' + id,
    method: 'get'
  })
}

// 新增鱼苗管理
export function addFish(data) {
  return request({
    url: '/tron/fish',
    method: 'post',
    data: data
  })
}

// 修改鱼苗管理
export function updateFish(data) {
  return request({
    url: '/tron/fish',
    method: 'put',
    data: data
  })
}

// 删除鱼苗管理
export function delFish(id) {
  return request({
    url: '/tron/fish/' + id,
    method: 'delete'
  })
}

// 导出鱼苗管理
export function exportFish(query) {
  return request({
    url: '/tron/fish/export',
    method: 'get',
    params: query
  })
}