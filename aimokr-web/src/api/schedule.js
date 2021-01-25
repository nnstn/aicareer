import request from '../utils/request';

const schedule =  {
    fetchData:(query) => {
        return request({
            url: '../schedule.json',
            method: 'get',
            data: query
        });
    }
}

export default schedule;
