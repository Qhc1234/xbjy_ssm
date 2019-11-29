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
                deptId:'',
                sort:'',
                join:''
            },
            deptList:''
        }

    },
    methods: {
        findPage:function(pageNum, pageSize){
            console.log("findPage");
            this.params.pageNum = pageNum;
            this.params.pageSize = pageSize;
            axios({url:path+"/conference/findPage",method:'post',data:this.params})
                .then(response => {
                    this.pageInfo = response.data;
                    console.log(this.pageInfo);
                }).catch(function(error){console.log(error)});
        },
        sortAsc:function () {
            this.params.sort=0;
            this.findPage(1,this.pageInfo.pageSize);
        },
        sortDesc:function () {
            this.params.sort=1;
            this.findPage(1,this.pageInfo.pageSize);
        },
        join:function () {
            this.params.join=$("#join").prop('checked');
            console.log(this.params);
            this.findPage(1,this.pageInfo.pageSize);
        },
        selectDept:function () {
            console.log("selectDept");
            axios({url:path+"/dept/selectAll"})
                .then(response => {
                    this.deptList = response.data;
                    console.log(this.deptList);
                }).catch(function(error){console.log(error)});
        },
        toDetail:function (id) {
            window.location.href=path+'/conference/toDetail?id='+id;
        }


    },
    created: function () {
        this.findPage();
        this.selectDept();
    }
});