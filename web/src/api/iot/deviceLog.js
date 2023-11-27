import request from '@/utils/request'

// 查询设备日志列表
export function listDeviceLog(query) {
  return request({
    url: '/iot/deviceLog/list',
    method: 'get',
    params: query
  })
}

// 查询设备日志详细
export function getDeviceLog(id) {
  return request({
    url: '/iot/deviceLog/' + id,
    method: 'get'
  })
}

// 新增设备日志
export function addDeviceLog(data) {
  return request({
    url: '/iot/deviceLog',
    method: 'post',
    data: data
  })
}

// 修改设备日志
export function updateDeviceLog(data) {
  return request({
    url: '/iot/deviceLog',
    method: 'put',
    data: data
  })
}

// 删除设备日志
export function delDeviceLog(id) {
  return request({
    url: '/iot/deviceLog/' + id,
    method: 'delete'
  })
}
