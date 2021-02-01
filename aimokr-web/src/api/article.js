import request from '../utils/request';

const article =  {
    getAll:(data) => {
        return request({
            url: '/article/getAll',
            data: data
        });
    },
    delete:function (id) {
        return request({
            url: '/article/delete/'+id
        });
    }
}

export default article;