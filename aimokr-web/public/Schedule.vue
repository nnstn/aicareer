<template>
  <div>
    <div class="crumbs">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item>
          <i class="el-icon-lx-calendar"></i> 任务管理
        </el-breadcrumb-item>
        <el-breadcrumb-item>日程查看</el-breadcrumb-item>
      </el-breadcrumb>
    </div>

    <div class="calender" id="calender">
      <div class="currnent">
        {{this.year}}年{{this.month}}月{{this.date}}日
      </div>
      <div class="tablebox1">

        <table class="bgtable">
          <thead>
          <th v-for="(item,index) in daynamearr"  :index="index" :key="item">{{item}}</th>
          </thead>
          <tbody>
          <tr v-for="(week,index) in theweek()" :index="index" :key="week" >
            <td v-for="(day,index) in week"  :index="index" :key="day" v-bind:class="dayclass(day)">{{day.date}}</td>
          </tr>

          </tbody>
        </table>
      </div>
      <div class="tablebox2">
        <div class="row" v-for="(row,index) in domdata" :index="index" :key="row">
          <table class="scheduletable">
            <tr v-for="(line,index) in row" :index="index" :key="line">
              <td v-for="(td,index) in line" v-bind:colspan="td.colspan" :index="index" :key="td">
                <div v-if="td.title" v-bind:class="['s',td.type,{'noleftradius':td.noleftradius,'norightradius':td.norightradius}]">{{td.title}}</div>
              </td>
            </tr>
            <tr style="height:1px;">
              <td></td>
              <td></td>
              <td></td>
              <td></td>
              <td></td>
              <td></td>
              <td></td>
            </tr>
          </table>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Calender.vue',
  data () {
    return {
      // 中文汉字星期名称
      daynamearr: ['星期一', '星期二', '星期三', '星期四', '星期五', '星期六', '星期日'],
      // 本月日期构成，先用二维数组
      dayarr: [],
      year: 2018,
      month: 5,
      date: 1,
      day: {},
      schedules: [
        {'title': '吃饭1', 'start': '20180502', 'end': '20180502', 'type': 'life'},
        {'title': '吃饭2', 'start': '20180502', 'end': '20180502', 'type': 'life'},
        {'title': '吃饭3', 'start': '20180502', 'end': '20180502', 'type': 'life'},
        {'title': '吃饭4', 'start': '20180501', 'end': '20180502', 'type': 'life'},
        {'title': '游泳', 'start': '20180503', 'end': '20180503', 'type': 'life'},
        {'title': '游泳1', 'start': '20180523', 'end': '20180527', 'type': 'work '},
        {'title': '游泳2', 'start': '20180523', 'end': '20180530', 'type': 'important'},
        {'title': '游泳3', 'start': '20180523', 'end': '20180527', 'type': 'work '},
        {'title': '打豆豆', 'start': '20180511', 'end': '20180612', 'type': 'life'}
      ],
      domdata: []
    }
  },
  created: function () {
    // var currentdate = new Date();
    // this.year = currentdate.getFullYear();
    // this.month = currentdate.getMonth();
    // this.date = currentdate.getDate();

    let dayarr = []
    // 1.需要知道这个月的第一天是星期几
    var themonthfirstday = (new Date(this.year, this.month - 1, 1)).getDay()

    // 2.需要知道本月有多少天
    // 经典算法：判断本月是不是(1,3,5,7,8,10,12)->31 (4,6,9,11)->30 2月算闰年4 100,400
    // 优化算法:下一个月减1毫秒的日期，为本月最后一天
    var y = this.month === 12 ? this.year + 1 : this.year
    var m = this.month === 12 ? 1 : this.month + 1
    var themonthdaymount = (new Date(new Date(y, m - 1, 1) - 1)).getDate()

    // 3.需要知道上个月最后一天是几号
    var premonthlastdate = (new Date(new Date(this.year, this.month - 1, 1) - 1)).getDate()
    var prey = this.month === 1 ? this.year - 1 : this.year
    var prem = this.month === 1 ? 12 : this.month - 1
    while (themonthfirstday-- > 0) {
      dayarr.unshift({'year': prey, 'month': prem, 'date': premonthlastdate--, 'premonth': true, 'occupation': []})
    }
    var count = 0
    while (themonthdaymount-- > 0) {
      dayarr.push({'year': this.year, 'month': this.month, 'date': ++count, 'currnentday': count === this.date, 'occupation': []})
    }

    count = 0
    var nexty = this.month === 12 ? this.year + 1 : this.year
    var nextm = this.month === 12 ? 1 : this.month + 1
    while (dayarr.length < 42) {
      dayarr.push({'year': nexty, 'month': nextm, 'date': ++count, 'nextmonth': true, 'occupation': []})
    }
    this.dayarr = dayarr
    this.calcucalendar()
  },
  methods: {
    // 返回第number周的日期序列,序号从0开始
    theweek: function () {
      let _arr = []
      for (let i = 0; i < 6; i++) {
        let _week = []
        for (let j = 0; j < 7; j++) {
          _week.push(this.dayarr[i * 7 + j])
        }
        _arr.push(_week)
      }
      return _arr
    },
    calcucalendar: function () {
      let schedules = this.schedules
      let dayarr = this.dayarr

      let domdata = [
        [[], [], []],
        [[], [], []],
        [[], [], []],
        [[], [], []],
        [[], [], []],
        [[], [], []]
      ]
      for (let rowindex = 0; rowindex < 6; rowindex++) {
        let weekarr = dayarr.slice(rowindex * 7, (rowindex + 1) * 7)
        // 遍历这一周的事情
        weekarr.forEach(function (theday, index) {
          // 今天要做事情
          let thedayschedules = []
          // 将所有今天发生事情推入今天数组
          schedules.forEach(function (schedule, index2) {
            let startYear = parseInt(schedule.start.substr(0, 4))
            let startMonth = parseInt(schedule.start.substr(4, 2))
            let startDate = parseInt(schedule.start.substr(6, 2))

            let endYear = parseInt(schedule.end.substr(0, 4))
            let endMonth = parseInt(schedule.end.substr(4, 2))
            let endDate = parseInt(schedule.end.substr(6, 2))

            schedule.colspan = (new Date(endYear, endMonth - 1, endDate) - new Date(startYear, startMonth - 1, startDate)) / (1000 * 60 * 60 * 24) + 1
            if (startYear === theday.year && startMonth === theday.month && startDate === theday.date) {
              thedayschedules.push(schedule)
            }
          })
          // 至此我们找到今天所有发生的事情
          for (let line = 0; line < 3; line++) {
            if (theday.occupation.indexOf(line) === -1) {
              let schedule = thedayschedules.shift()
              if (schedule === undefined) {
                domdata[rowindex][line].push({})
              } else {
                // 将这一天和后colspan天的occupation加入自己的行号
                // if(schedule.colspan&&schedule.colspan!=1){
                let end = index + schedule.colspan

                var _end = end >= 7 ? 7 : end
                for (var _i = index + 1; _i < _end; _i++) {
                  weekarr[_i].occupation.push(line)
                };
                domdata[rowindex][line].push({'title': schedule.title, 'colspan': (_end - index), 'type': schedule.type, 'noleftradius': false, 'norightradius': end > 7})

                let rest = end - 7
                let next = 1
                // 处理下一周事情
                while (rest > 0) {
                  let _rest = rest > 7 ? 7 : rest
                  var nextweek = dayarr.slice((rowindex + next) * 7, (rowindex + next + 1) * 7)
                  if (nextweek === undefined || nextweek.length === 0) {
                    console.log('任务跨月')
                    rest = -1
                    continue
                  }

                  for (let _i = 0; _i < _rest; _i++) {
                    if (nextweek[_i] === undefined) {
                      debugger
                    }
                    nextweek[_i].occupation.push(line)
                  }
                  domdata[rowindex + next][line].push({'title': schedule.title, 'colspan': _rest, 'type': schedule.type, 'noleftradius': true, 'norightradius': rest > 7})

                  rest = rest - 7
                  next++
                }
                // }else{
                //    //当只有一列情况
                //    domdata[rowindex][line].push({"title":schedule.title,"colspan":schedule.colspan,"type":schedule.type,"noleftradius":false,"norightradius":false});
                // }
              }
            }
          }
          while (thedayschedules.length > 0) {
            console.log(theday.year + '年' + theday.month + '月' + theday.date + '日:还有未处理的任务:' + thedayschedules.shift().title)
          }
        })
      };
      this.domdata = domdata
    }
  },
  computed: {
    // vue计算属性computed方法内传参
    dayclass: function () {
      return function (day) {
        if (day.premonth || day.nextmonth) {
          return 'gray'
        }
        if (day.currnentday) {
          return 'today'
        }
        return 'date'
      }
    }
  }
}
</script>

<style scoped>
  *{
    margin:0px;
    padding: 0px ;
    font: 14px 'Helvetica Neue', Helvetica, Arial, sans-serif;
  }
  .calender{
    position:relative;
    width: 1000px;
    height: 660px;
    border: 1px solid #333;
    margin: 0 auto;
  }
  .calender .currnent{
    position: absolute;
    width:300px;
    height:30px;
    /* left:-200px; */
  }
  /*日历底座部分样式*/
  .tablebox1{
    position:absolute;
    top:30px;
    width:100%;
    height:600px;
  }
  table{
    width: 100%;
    border-collapse: collapse;
  }
  table.bgtable thead tr th{
    height:30px;
  }
  table.bgtable tbody tr td{
    height:100px;
    box-sizing: border-box;/*高度中含有边框高度*/
    border:1px solid #ccc;
    text-align: right;
    vertical-align: top;
    padding:4px;
  }
  table.bgtable tbody tr td.gray{
    background: #eee;
  }
  table.bgtable tbody tr td.today{
    background: #fcf8e3;
  }

  /*日程部分样式*/
  .tablebox2{
    position:absolute;
    top:60px;
    width:100%;
    height:600px;
  }
  .tablebox2 .row{
    padding-top:30px;
    height:100px;
    box-sizing: border-box;
  }
  .tablebox2 .row .scheduletable{
    width:100%;
    height:70px;
  }
  .tablebox2 .row .scheduletable tr{
    height:20px;
    width:100%;
  }
  .tablebox2 .row .scheduletable tr td{
    width:14.2857%;
    padding:0 2px;
    box-sizing: border-box;
  }
  .tablebox2 .row .scheduletable tr td .s{
    border-radius: 15px;
    font-size: 12px;
    height:20px;
    line-height: 20px;
    padding-left:10px;
  }
  .tablebox2 .row .scheduletable tr td .s.norightradius{
    border-top-right-radius: 0;
    border-bottom-right-radius: 0;
  }
  .tablebox2 .row .scheduletable tr td .s.noleftradius{
    border-top-left-radius: 0;
    border-bottom-left-radius: 0;
  }
  .tablebox2 .row .scheduletable tr td .s.life{
    background: -webkit-linear-gradient(top,#8BC34A,#C5E1A5);
  }
  .tablebox2 .row .scheduletable tr td .s.work{
    background: -webkit-linear-gradient(top,#1976D2,#03A9F4);
  }
  .tablebox2 .row .scheduletable tr td .s.important{
    background: -webkit-linear-gradient(top,#F44336,#EF9A9A);
  }
</style>
