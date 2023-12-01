import request from '@/utils/request'

// 查询设备日志列表
export function listDeviceLog(query) {
  return request({
    url: '/iot/deviceLog/list',
    method: 'get',
    params: query
  })
}
// 当前警告个数
export function warnState() {
  return request({
    url: '/iot/deviceLog/warnState',
    method: 'get'
  })
}
// 折线图报警次数变化
export function warnNumPerHour() {
  return request({
    url: '/iot/deviceLog/warnNumPerHour',
    method: 'get'
  })
}
// 当天警告个数
export function warnStateToday() {
  return request({
    url: '/iot/deviceLog/warnStateToday',
    method: 'get'
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
