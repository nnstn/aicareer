// 右键菜单学习 https://www.bookstack.cn/read/chrome-plugin-develop/spilt.3.spilt.5.8bdb1aac68bbdc44.md
// http://www.ptbird.cn/chrome-extension-contextMenus.html

chrome.contextMenus.create({
    id:'1',
    type:'normal',
    title:"文档收集"
});
chrome.contextMenus.create({
    id: '2',
    type: 'normal',
    title: '添加任务',
    contexts: ['all'],
    onclick: function (info, tab) {
        console.log(info);
        console.log(tab);
        chrome.notifications.create(null, {
            type: 'basic',
            iconUrl: 'img/icon.png',
            title: '这是标题',
            message: '您刚才点击了自定义右键菜单！'
        });
    }
}, function () {
    console.log('contextMenus are create.');
});
