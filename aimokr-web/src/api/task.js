import request from '../utils/request';

const task =  {
    getall:(data) => {
        return request({
            url: '/task/getall',
            data: data
        });
    },
    insert:function (data) {
        return request({
            url: '/task/insert',
            data: data
        });
    },
    delete:function (id) {
        return request({
            url: '/task/delete/'+id
        });
    },
    donetask:function (ids) {
        return request({
            url: '/task/donetask',
            data: ids
        });
    },
    update:function (data) {
        return request({
            url: '/task/update',
            data: data
        });
    },
    schedule:function (data) {
        return request({
            url: '/task/schedule',
            data: data
        });
    }
}

export default task;
