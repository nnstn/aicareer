<template>
    <mindmap v-model="data" :strokeWidth="3" :xSpacing="30" :gps="false" ></mindmap>
</template>

<script>
    import mindmap from '@hellowuxin/mindmap'

    export default {
        components: { mindmap: mindmap },
        data: () => ({
            data: [{
                "name":"JVM知识脑图",
                "children": [
                    {
                        "name":"预备知识",
                        "children": [
                            { "name":"HTML & CSS" },
                            { "name":"JavaScript" }
                        ]
                    },
                    {
                        "name":"安装",
                        "_children": [
                            { "name": "折叠节点" }
                        ]
                    },
                    {
                        "name":"进阶",
                    },          {
                        "name":"进阶1",
                    },          {
                        "name":"进阶2",
                    },          {
                        "name":"进阶3",
                    },
                ]
            }]
        })
    }
</script>
<style>
    div#mindmap {
        width: 100%;
        height: 100%;
    }
</style>