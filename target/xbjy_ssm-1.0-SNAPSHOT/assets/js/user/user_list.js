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
                realName:''
            },
            userList:'',
            focusIds:''
        }

    },
    methods: {
        findPage:function(pageNum, pageSize){
            console.log("findPage");
            this.params.pageNum = pageNum;
            this.params.pageSize = pageSize;
            axios({url:path+"/user/findPage",method:'post',data:this.params})
                .then(response => {
                    this.pageInfo = response.data;
                    this.userList=response.data.list;
                    console.log(this.pageInfo);
                    this.setFocus();
                }).catch(function(error){console.log(error)});
        },
        findFocus:function () {
            console.log("findFocus");
            axios({url:path+"/user/findFocus"})
                .then(response => {
                    this.focusIds=response.data;
                    console.log(this.focusIds);
                }).catch(function(error){console.log(error)});
        },
        setFocus:function () {
            console.log("setFocus");
            let flag=false;
            for (let i=0;i<this.userList.length;i++){
                flag=false;
                for (let j=0;j<this.focusIds.length;j++){
                    if (this.userList[i].userId==this.focusIds[j]){
                        this.userList[i].focus=true;
                        flag=true;
                        break;
                    }
                }
                if (!flag){
                    this.userList[i].focus=false;
                }
            }
        },
        toDetail:function (id) {
            window.location.href=path+'/user/toDetail?id='+id;
        },
        focusChange:function (uid,index) {
            console.log(uid);
            console.log(index);
            console.log(this.userList);
            if (this.userList[index].focus){//加关注
                this.addFocus(this.userList[index].userId);
            } else {//取消关注
                this.deleteFocus(this.userList[index].userId);
            }
        },
        addFocus:function (id) {
            axios({url:path+"/user/addFocus",params:{"id":id}})
                .then(response => {
                    alert(response.data.msg);
                    window.location.href=path+'/user/toList';
                }).catch(function(error){console.log(error)});
        },
        deleteFocus:function (id) {
            axios({url:path+"/user/deleteFocus",params:{"id":id}})
                .then(response => {
                    alert(response.data.msg);
                    window.location.href=path+'/user/toList';
                }).catch(function(error){console.log(error)});
        }


    },
    created: function () {
        this.findFocus();
        this.findPage();
        // this.selectDept();
    }

});