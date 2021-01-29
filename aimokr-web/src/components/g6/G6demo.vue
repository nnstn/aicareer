<template>
    <div>
        <div id="mountNode"></div>
    </div>
</template>

<script>
    import G6 from '@antv/g6';

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
        mounted() {
            this.initG6();
        },
        methods: {
            initG6() {
                const nodes = data.nodes;
                const edges = data.edges;
                nodes.forEach(n => {
                    n.y = -n.y;
                    n.degree = 0;
                    n.inDegree = 0;
                    n.outDegree = 0;
                });
// compute the degree of each node
                const nodeIdMap = new Map();
                nodes.forEach(node => {
                    nodeIdMap.set(node.id, node);
                });
                edges.forEach(e => {
                    const source = nodeIdMap.get(e.source);
                    const target = nodeIdMap.get(e.target);
                    source.outDegree++;
                    target.inDegree++;
                    source.degree++;
                    target.degree++;
                });
                let maxDegree = -9999, minDegree = 9999;
                nodes.forEach(n => {
                    if (maxDegree < n.degree) maxDegree = n.degree;
                    if (minDegree > n.degree) minDegree = n.degree;
                });
                const sizeRange = [1, 20];
                const degreeDataRange = [minDegree, maxDegree];
// 将范围是 degreeDataRange 的 degree 属性映射到范围 sizeRange 上后，
// 写入到 nodes 中元素的‘size’属性中
                scaleNodeProp(nodes, 'size', 'degree', degreeDataRange, sizeRange);
            },
            // 节点遍历，确定图形
            nodeEach(nodes) {
                nodes.forEach(node => {
                    if (!node.style) {
                        node.style = {}
                    }
                    switch (node.class) {         // 根据节点数据中的 class 属性配置图形
                        case 'c0': {
                            node.shape = 'circle';
                            node.size = 40;   // class = 'c0' 时节点图形为 circle
                            break;
                        }
                        case 'c1': {
                            node.shape = 'rect';       // class = 'c1' 时节点图形为 rect
                            node.size = [80, 40];    // class = 'c1' 时节点大小
                            node.style = {
                                stroke: '#00CC00',
                                fill: '#00CC00',
                                radius: 45
                            }
                            break;
                        }
                        case 'c2': {
                            node.shape = 'ellipse';    // class = 'c1' 时节点图形为 ellipse
                            node.size = [80, 40];    // class = 'c2' 时节点大小
                            break;
                        }
                        case 'c3': {
                            node.shape = 'diamond';    // class = 'c1' 时节点图形为 ellipse
                            node.size = [60, 60];    // class = 'c2' 时节点大小
                            break;
                        }
                    }
                })
            }
        }
    }
</script>