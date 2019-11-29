var vm = new Vue({
    el: '#main-container',
    data:function(){
        return{
            pageInfo: {
                pageNum: 1,
                pageSize: 5
            },
            params:{
                pageNum:'',
                pageSize:'',
                title:'',
                userId:'',
                type:"1",//1-按浏览数
                typeSort:'',//1-降序
                timeSort:''//1-降序
            },
            publishUserList:''
        }

    },
    methods: {

        findPage: function (pageNum, pageSize) {
            console.log("findPage");
            console.log(this.params);
            this.params.pageNum = pageNum;
            this.params.pageSize = pageSize;
            axios({url: path + "/article/findPage", method: 'post', data: this.params})
                .then(response => {
                    this.pageInfo = response.data;
                    console.log(this.pageInfo);
                }).catch(function (error) {
                console.log(error)
            });
        },
        findArticlePublishUser: function () {
            axios({url: path + "/article/findArticlePublishUser"})
                .then(response => {
                    this.publishUserList = response.data;
                    console.log(this.publishUserList);
                }).catch(function (error) {
                console.log(error)
            });
        },
        typeSortAsc:function () {
            this.params.typeSort=0;
            this.findPage(1,this.pageInfo.pageSize);
        },
        typeSortDesc:function () {
            this.params.typeSort=1;
            this.findPage(1,this.pageInfo.pageSize);
        },
        timeSortAsc:function () {
            this.params.timeSort=0;
            this.findPage(1,this.pageInfo.pageSize);
        },
        timeSortDesc:function () {
            this.params.timeSort=1;
            this.findPage(1,this.pageInfo.pageSize);
        },
        toDetail:function (id) {
            window.location.href=path+'/article/toDetail?id='+id;
        }

    }
    ,
    created: function () {
        this.findPage();
        this.findArticlePublishUser();
    }
});