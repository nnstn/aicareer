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

        <div class="container">
            <div class="calendar-header clear">
                <div class="calendar-box">
                    <div class="search-box">
                        <el-input  placeholder="名称" class="handle-input mr10" v-model="query.tasker"></el-input>
                        <el-button type="primary" icon="el-icon-search" @click="handleSearch">搜索&nbsp;&nbsp;</el-button>
                    </div>
                    <div class="calendar-content" style="text-align: center">
                        <span class="calendar-prev" @click="handlePrevMonth"></span>
                        <span class="calendar-headDate"> {{this.headOptions.date}} </span>
                        <span class="calendar-next" @click="handleNextMonth"></span>
                    </div>
                    <span class="calendar-today" @click="handleToday()"> 今天 </span>
                </div>
            </div>
            <ul class="calendar-week clear">
                <li v-for="(item, index) in weekTitle" :key="index" class="week-item">{{item}}</li>
            </ul>
<!--            let weekarr = dayarr.slice(rowIndex * 7, (rowIndex + 1) * 7)-->


            <!--42天日历-->
            <ul class="calendar-view clear">
                <li v-for="(item, index) in visibleCalendar"
                    :key="index"
                    class="date-view"
                    :class="[
                              {'month-class': !isCurrentMonth(item.date)},
                              {todayBg: isCurrentDay(item.date)},
                              {handleDay: item.clickDay}
                            ]"
                    @click="handleClickDay(item)">

                    <span class="date-day"
                          :style="dayStyle"
                          :class="[{'opacity-class': !isCurrentMonth(item.date)}]">
                      {{item.day}}
                    </span>
                </li>
            </ul>
            <!--与42天日历对应42天任务-->
            <div class="tablebox2">
                <div class="row" v-for="(row,index) in scheduleDom" :index="index" >
                    <table class="scheduletable">
                        <tr v-for="(line,index) in row" :index="index" >
                            <td v-for="(td,index) in line" v-bind:colspan="td.colspan" :index="index" >
                                <div v-if="td.taskName"
                                     v-bind:class="['s',td.taskType,{'noleftradius':td.noleftradius,'norightradius':td.norightradius}]">
                                   {{td.taskName}}
                                </div>
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
    import schedule from '../../../api/schedule';
    import task from '../../../api/task';
    import * as utils from '../../../utils/utils';

    export default {
        name: 'Schedule',
        data() {
            //获取当前日期
            let {year, month, day} = utils.getNewDate(new Date());
            return {
                headOptions: {
                    style: {
                        todayBtn: 'right',
                        combination: 'center',
                        checkBtn: 'right',
                    },
                    date: '',
                },
                query: {
                    startTime:'',
                    endTime:'',
                    tasker: ''
                },

                weekTitle: ['星期一', '星期二', '星期三', '星期四', '星期五', '星期六', '星期日'],
                time: {year, month, day},
                calendarList: [],
                schedules:{},
                scheduleDom: {}
            }
        },
        computed : {
            dayStyle : function () {
                return {
                    textAlign: 'right',
                }
            },
            visibleCalendar () {
                let calendatArr = [];

                let {year, month, day} = utils.getNewDate(utils.getDate(this.time.year, this.time.month, 1));

                let currentFirstDay = utils.getDate(year, month, 1);

                // 获取当前月第一天星期几
                let weekDay = currentFirstDay.getDay();
                //表格的起始时间
                let startTime = currentFirstDay - (weekDay - 1) * 24 * 60 * 60 * 1000;

                // 为了布局一直均取42天为一个单元月
                let monthDayNum = 42;

                for (let i = 0; i < monthDayNum; i++) {
                    let date = new Date(startTime + i * 24 * 60 * 60 * 1000);
                    calendatArr.push({
                        date: date,
                        year: date.getFullYear(),
                        month: date.getMonth(),
                        day: date.getDate(),
                        clickDay: false,
                        taskContainer:[]
                    })
                };

                this.headOptions.date = `${utils.englishMonth(month)} ${year}`;
                return calendatArr
            }
        },
        created() {
            this.calendarList = this.visibleCalendar;

            //计算出本页起始日期,和最终日期，进行日程查询
            this.query.tasker=localStorage.getItem('ms_username');
            this.query.startTime=this.visibleCalendar[0].date;
            this.query.endTime=this.visibleCalendar[41].date;

            this.getData();
        },
        methods: {
            // 是否是当前月
            isCurrentMonth (date) {
                let {year: currentYear, month: currentMonth} = utils.getNewDate(utils.getDate(this.time.year, this.time.month, 1));
                let {year, month} = utils.getNewDate(date);
                return currentYear == year && currentMonth == month
            },
            // 是否是今天
            isCurrentDay (date) {
                let {year: currentYear, month: currentMonth, day: currentDay} = utils.getNewDate(new Date());
                let {year, month, day} = utils.getNewDate(date);
                return currentYear == year && currentMonth == month && currentDay == day;
            },
            // 上一个月
            handlePrevMonth () {
                let prevMonth = utils.getDate(this.time.year,this.time.month,1);
                prevMonth.setMonth(prevMonth.getMonth() - 1);
                this.time = utils.getNewDate(prevMonth);
                this.headOptions.date = `${utils.englishMonth(this.time.month)} ${this.time.year}`;

                this.query.startTime=this.visibleCalendar[0].date;
                this.query.endTime=this.visibleCalendar[41].date;
                this.getData();
            },
            // 下一个月
            handleNextMonth () {
                let nextMonth = utils.getDate(this.time.year,this.time.month,1);
                nextMonth.setMonth(nextMonth.getMonth() + 1);
                this.time = utils.getNewDate(nextMonth);
                this.headOptions.date = `${utils.englishMonth(this.time.month)} ${this.time.year}`;

                this.query.startTime=this.visibleCalendar[0].date;
                this.query.endTime=this.visibleCalendar[41].date;
                this.getData();
            },
            // 点击回到今天
            handleToday () {
                this.time = utils.getNewDate(new Date());
                this.query.startTime=this.visibleCalendar[0].date;
                this.query.endTime=this.visibleCalendar[41].date;
                this.getData();
            },            // 点击回到今天
            handleSearch () {
                this.getData();
            },
            // 点击某一天
            handleClickDay (item) {
                this.$forceUpdate();
                this.calendarList.map( x => {
                    x.clickDay = false;
                });
                this.$set(item, 'clickDay', true);
            },

            getData() {
                task.schedule(this.query).then(res => {
                    if (res.flag) {
                        this.schedules = res.data;
                        this.calculateSchedule();
                    } else {
                        this.$message.error(res.message)
                    }

                });
            },
            calculateSchedule: function () {
                let schedules = this.schedules
                let dayarr = this.visibleCalendar

                let domdata = [
                    [[], [], [],[]],
                    [[], [], [],[]],
                    [[], [], [],[]],
                    [[], [], [],[]],
                    [[], [], [],[]],
                    [[], [], [],[]]
                ]
                let colorMap = {
                    1:'green',
                    2:'blue',
                    3:'orange',
                    4:'red',
                    5:'red2',
                    6:'red3'
                }

                // 遍历 6 个星期
                for (let rowIndex = 0; rowIndex < 6; rowIndex++) {
                    let weekarr = dayarr.slice(rowIndex * 7, (rowIndex + 1) * 7) //取底i个星期数据
                    // 遍历这一周的事情
                    weekarr.forEach(function (theday, colIndex) { // 第rowIndex 个星期每天任务 第 colIndex 天

                        // 今天的任务
                        let thedayTasks = []
                        // 将所有今天发生事情推入今天数组
                        schedules.forEach(function (task, taskIndex) {

                            let {year: startYear,month:  startMonth, day:startDate} = utils.getNewDate(new Date(task.startDate));
                            let {year: endYear,month:  endMonth,day: endDate} = utils.getNewDate(new Date(task.endDate));

                            //任务持续天数
                            task.colspan = (new Date(task.endDate) - new Date(task.startDate)) / (1000 * 60 * 60 * 24) + 1

                            if (startYear === theday.year && startMonth === theday.month && startDate === theday.day) {
                                thedayTasks.push(task)
                            }
                            //如果是本月第一天需要做跨月处理
                            if(rowIndex===0 && colIndex ===0){
                                if(new Date(task.startDate) < dayarr[0].date){
                                    console.log("跨页任务")
                                    task.colspan = (new Date(task.endDate) - dayarr[0].date) / (1000 * 60 * 60 * 24) + 1
                                    thedayTasks.push(task)
                                }
                            }
                        })

                        // 至此我们找到今天所有发生的事情--页面每日最多处理3个任务
                        for (let line = 0; line < 4; line++) {

                            if (theday.taskContainer.indexOf(line) === -1) {
                                let schedule = thedayTasks.shift()
                                if (schedule === undefined) {
                                    domdata[rowIndex][line].push({})
                                } else {
                                    // 将这一天和后colspan天的occupation加入自己的行号
                                    // if(schedule.colspan&&schedule.colspan!=1){
                                    let end = colIndex + schedule.colspan

                                    var _end = end >= 7 ? 7 : end
                                    for (var _i = colIndex + 1; _i < _end; _i++) {
                                        weekarr[_i].taskContainer.push(line)
                                    };

                                    domdata[rowIndex][line].push({
                                        'taskName': schedule.taskName,
                                        'colspan': (_end - colIndex),
                                        'taskType': colorMap[schedule.taskType],
                                        'noleftradius': dayarr[0].date > new Date(schedule.startDate),
                                        'norightradius': end > 7
                                    })

                                    let rest = end - 7
                                    let next = 1
                                    // 处理下一周事情
                                    while (rest > 0) {
                                        let _rest = rest > 7 ? 7 : rest
                                        var nextweek = dayarr.slice((rowIndex + next) * 7, (rowIndex + next + 1) * 7)
                                        if (nextweek === undefined || nextweek.length === 0) {
                                            console.log('任务跨页不用处理。需重新查询')
                                            rest = -1
                                            continue
                                        }

                                        for (let _i = 0; _i < _rest; _i++) {
                                            if (nextweek[_i] === undefined) {
                                                debugger;
                                            }
                                            nextweek[_i].taskContainer.push(line)
                                        }
                                        domdata[rowIndex + next][line].push({
                                            'taskName': schedule.taskName,
                                            'colspan': _rest,
                                            'taskType': colorMap[schedule.taskType],
                                            'noleftradius': true,
                                            'norightradius': rest > 7
                                        })

                                        rest = rest - 7
                                        next++
                                    }
                                }
                            }
                        }
                        while (thedayTasks.length > 0) {
                            console.log("每日工作不要超过4个，你干不过来")
                            console.log(theday.year + '年' + theday.month + '月' + theday.day + '日:还有未处理的任务:' + thedayTasks.shift().taskName)
                        }
                    })
                }
                this.scheduleDom = domdata
                console.log(this.visibleCalendar)
            }

        }
    }
</script>

<style scoped>
    body, h1, h2, h3, h4, h5, h6, hr, p, blockquote, dl, dt, dd, ul, ol, li, button, input, textarea, th, td, span {
        margin: 0;
        padding: 0;
        box-sizing: border-box;
    }
    ul, ol {
        list-style: none
    }
    table {
        width: 100%;
        border-collapse: collapse;
    }
    .container{
        position:relative;
    }
    .clear:after {
        display: block;
        height: 0;
        content: "";
        clear: both
    }
    .calendar-header {
        margin-bottom: 13px;
        width: 100%;
    }
    .calendar-box {
        position: relative;
        height: 32px;
        line-height: 32px;
    }
    .calendar-content {
        width: 100%;
    }
    .calendar-prev {
        display: inline-block;
        vertical-align: middle;
        width: 20px;
        height: 20px;
        background: url('../../../assets/img/left.png') no-repeat;
        background-size: contain;
        cursor: pointer;
    }
    .calendar-next {
        display: inline-block;
        vertical-align: middle;
        width: 20px;
        height: 20px;
        background: url('../../../assets/img/right.png') no-repeat;
        background-size: contain;
        cursor: pointer;
    }

    .calendar-headDate {
        width:20%;
        display:inline-block;
        vertical-align: middle;
        margin: 0 12px;
        font-size: 18px;
        color: #424953;
        -webkit-user-select: none;
        -webkit-touch-callout: none;
        -moz-user-select: none;
        -ms-user-select: none;
        user-select: none;
    }
    .calendar-header .calendar-box .calendar-today {
        position: absolute;
        top: 0;
        right: 0;
        width: 80px;
        height: 30px;
        text-align: center;
        border-radius: 4px;
        font-size: 14px;
        color: #2061FF;
        cursor: pointer;
        background:#eea236;
        color:#fff;
        float:right;
    }

    .calendar-week {
        width: 100%;
        height: 46px;
        line-height: 46px;
        border: 1px solid #E4E7EA;
        border-right: none;
    }
    .week-item {
        float: left;
        width: 14.285%;
        text-align: center;
        font-size: 14px;
        color: #424953;
        border-right: 1px solid #E4E7EA;
        font-weight: 600;
    }
    .calendar-view {
        width: 100%;
        border-left: 1px solid #E4E7EA;
    }
    .date-view {
        float: left;
        width: 14.285%;
        height: 120px;
        border-right: 1px solid #E4E7EA;
        border-bottom: 1px solid #E4E7EA;
        cursor: pointer;
    }
    .date-day {
        padding: 8px 8px 0;
        display: block;
        width: 100%;
        font-size: 14px;
        color: black !important;
    }
    .calendar-num {
        margin-top: 6px;
        display: block;
        width: 100%;
        text-align: center;
        font-size: 30px;
        color: #424953;
    }
    .opacity-class {
        opacity: .5;
    }
    .month-class {
        background-image: linear-gradient(45deg,rgba(000, 000, 000, .03) 25%,transparent 25%,transparent 50%,rgba(000, 000, 000, .03) 50%,rgba(000, 000, 000, .03) 75%,transparent 75%,transparent);
        background-size: 20px 20px;
    }
    .todayBg {
        background: #FCF8E3;
    }
    /*点击日期样式*/
    .handleDay {
        background: #2061FF !important;
    }
    .calendar-num {
        color: #fff !important;
    }

    /*日程部分样式*/
    .tablebox2 {
        position: absolute;
        top: 120px;
        width: 96%;
        height: 720px;
    }

    .tablebox2 .row {
        padding-top: 30px;
        height: 120px;
        box-sizing: border-box;
    }

    .tablebox2 .row .scheduletable {

        width: 100%;
        height: 70px;
    }

    .tablebox2 .row .scheduletable tr {
        height: 20px;
        width: 100%;
    }

    .tablebox2 .row .scheduletable tr td {
        width: 14.2857%;
        padding: 0 2px;
        box-sizing: border-box;
    }

    .tablebox2 .row .scheduletable tr td .s {
        border-radius: 15px;
        font-size: 12px;
        height: 20px;
        line-height: 20px;
        padding-left: 10px;
        margin-top:2px;
    }

    .tablebox2 .row .scheduletable tr td .s.norightradius {
        border-top-right-radius: 0;
        border-bottom-right-radius: 0;
    }

    .tablebox2 .row .scheduletable tr td .s.noleftradius {
        border-top-left-radius: 0;
        border-bottom-left-radius: 0;
    }

    .tablebox2 .row .scheduletable tr td .s.green {
        background: -webkit-linear-gradient(left, #dff0d8, #30ab32);
    }
    .tablebox2 .row .scheduletable tr td .s.blue {
        background: -webkit-linear-gradient(left, #d9edf7, #1089c5);
    }
    .tablebox2 .row .scheduletable tr td .s.orange{
        background: -webkit-linear-gradient(left, #fcf8e3, #efb553);
    }
    .tablebox2 .row .scheduletable tr td .s.red {
        background: -webkit-linear-gradient(left, #f2dede, #d03d3b);
    }
    .tablebox2 .row .scheduletable tr td .s.red2 {
        background: -webkit-linear-gradient(left, #f2dede, #d03d3b);
    }
    .tablebox2 .row .scheduletable tr td .s.red3 {
        background: -webkit-linear-gradient(left, #f2dede, #d03d3b);
    }

    .handle-input {
        width:100px;
        display: inline-block;
    }
    .search-box{
        float:left;

    }
    .search-box button{
        float: right;
        height: 32px;
        padding: 0px 4px;
        margin-left: 4px;
    }

</style>
