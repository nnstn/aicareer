import request from '../utils/request';

const auth =  {
    login:(data) => {
        return request({
            url: '/auth/login',
            data: data
        });
    },
    register:function (user) {
        return request({
            url: '/auth/register',
            data: user
        });
    },
    checklogin:function () {
        return request({
            url: '/auth/checklogin'
        });
    }
}


export default auth;
